import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.ParserRuleContext;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;


/** Visits nodes in ANTLR generated AST and resolves symbols to their
  proper representation.

  Passes twice over the AST.

  The first pass only resolves classes and
  their inheritance structure. The first pass is responsible for detecting and
  reporting incorrect inheritance structures.

  The second pass resolves the rest of the symbols: methods, variables, and fields.
 **/
public class ResolveVisitor extends MiniJavaBaseVisitor<ValueType> {

    private MainClass mainClass;
    private MJClass currentMJClass;
    private Method currentMethod;

    private boolean classResolveStep;

    //Inheritance map of String(Extender) to String(SuperClass) 
    private HashMap<String,String> inheritanceMap;

    //Set of methods discarded during resolution
    private Set<ParserRuleContext> discardedMethods;

    public ResolveVisitor() {
        this.mainClass = null;
        this.currentMJClass = null;
        this.currentMethod = null;
        this.classResolveStep = true;
        this.inheritanceMap = new LinkedHashMap<String,String>();
        this.discardedMethods = new HashSet<ParserRuleContext>();
    }

    /** Takes an ANTLR generated AST and resolves them into classes, methods and variables **/
    public void resolve(ParseTree tree) {
        //Resovlves classes
        this.visit(tree);
        this.classResolveStep = false;
        //Resolves everything else
        this.visit(tree);
    }

    /** Visits the main class declaration **/
    @Override public ValueType visitMainClass(@NotNull MiniJavaParser.MainClassContext ctx) {
        if (classResolveStep) {
            String mainClassName = ctx.IDENTIFIER(0).getText();
            mainClass = new MainClass(mainClassName);
        }
        return visitChildren(ctx);
    }

    /** Visits class declarations **/
    @Override public ValueType visitClassDecl(@NotNull MiniJavaParser.ClassDeclContext ctx) {
        List<TerminalNode> classIdentifiers = ctx.IDENTIFIER();
        String className = classIdentifiers.get(0).getText();

        if (classResolveStep) {
            //Check for double declaration
            if (className.equals(mainClass.getName())) {
                //Report an error
                ErrorReporter.reportDoubleClassDecl(ctx,className);
                return null;
            }
            if (mainClass.getClass(className) != null) {
                //Report double declaration error
                ErrorReporter.reportDoubleClassDecl(ctx,className);
                return null;
            }

            //Scope into the class
            currentMJClass = new MJClass(className);
            currentMethod = null;
            mainClass.addClass(currentMJClass);
            //Resolve inheritance and check for cycles
            resolveInheritance(classIdentifiers);
            resolveCyclicInheritance(ctx);
            return null;
        } else {
            //Scope into the current class
            currentMJClass = mainClass.getClass(className);
            currentMethod = null;
            return visitChildren(ctx);
        }
    }

    /** Visit variable declarations **/ 
    @Override public ValueType visitVarDecl(@NotNull MiniJavaParser.VarDeclContext ctx) {
        if (currentMethod != null) {
            //Local declaration
            varDeclInMethod(ctx);
        } else if (currentMJClass != null) {
            //Field declaration
            varDeclInMJClass(ctx);
        }
        return null;
    }

    /** Visit method declaration **/
    @Override public ValueType visitMethodDecl(@NotNull MiniJavaParser.MethodDeclContext ctx) {

        String methodName = ctx.IDENTIFIER(0).getText();
        ValueType returnType = new ValueType(ctx.type(0).getText());
        List<Symbol> params = getMethodParams(ctx);

        Method newMethod = new Method(returnType,methodName,params,currentMJClass);

        //Check double declaration
        if (currentMJClass.getMethod(methodName,false) != null) {
            //Report double declaration error
            ErrorReporter.reportDoubleMethDecl(ctx,newMethod.toString(),currentMJClass.getName());
            //Catelog the method as discarded
            discardedMethods.add(ctx);
            return null;
        } 

        Method superMethod = currentMJClass.getMethod(methodName,true);

        //Check for overloading
        if (superMethod != null) {
            if (MJUtils.isOverloading(newMethod,superMethod)) {
                //Report attempted overloading
                ErrorReporter.reportOverloading(ctx,newMethod.getName(),superMethod.toString());
                //Catelog the method as discarded
                discardedMethods.add(ctx);
                return null;
            }
        }
        //Add method to current class
        currentMJClass.addMethod(newMethod);
        //Scope into method
        currentMethod = newMethod;
        visitChildren(ctx);
        return null;
    }

    /** Resolves local variable declarations **/
    public void varDeclInMethod(MiniJavaParser.VarDeclContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        String varType = ctx.type().getText();
        //Check double declaration
        if (currentMethod.getVariable(varName) != null ) {
            //Report double declaration error
            ErrorReporter.reportDoubleVarDecl(ctx,varName);
        } else {
            //Create the appropriate type
            ValueType type;
            if (mainClass.getClass(varType) != null) {
                type = mainClass.getClass(varType).getType();
            } else {
                type = new ValueType(varType);
            }
            //Add variable to current method
            currentMethod.addVariable(varName,type);
        }
    }

    /** Resolves field declaration **/
    public void varDeclInMJClass(MiniJavaParser.VarDeclContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        String varType = ctx.type().getText();
        //Check for double declaration
        if (MJUtils.findVariable(currentMJClass,currentMethod,varName) != null) {
            //Report double declaration error
            ErrorReporter.reportDoubleVarDecl(ctx,varName);
        } else {
            //Create the appropriate type
            ValueType type;
            if (mainClass.getClass(varType) != null) {
                type = mainClass.getClass(varType).getType();
            } else {
                type = new ValueType(varType);
            }
            //Add to current class
            currentMJClass.addField(varName,type);
        }
    }

    /** Resolves inheritance given a list of up to two items. Index 0
         belonging to the current class, and index 1 belonging to the super class
     **/
    public void resolveInheritance(List<TerminalNode> identifiers) {
        final int CLASS = 0;
        final int SUPER = 1;
        //If class has a super, add it to the inheritance map
        if (identifiers.size() == 2) {
            inheritanceMap.put(identifiers.get(CLASS).getText(),identifiers.get(SUPER).getText());
        }

        String className = identifiers.get(CLASS).getText();

        List<String> resolved = new ArrayList<>();

        //Resolve inheritances in the map
        for (Map.Entry<String,String> classEntry : inheritanceMap.entrySet()) {
            MJClass superClass = mainClass.getClass(classEntry.getValue());
            MJClass extender = mainClass.getClass(classEntry.getKey());
            if (superClass != null && extender != null) {
                extender.setSuperClass(superClass);
                resolved.add(extender.getName());
            }
        }

        //Remove resolved class relations from inheritance map
        for (String resolvedClass : resolved) {
            inheritanceMap.remove(resolvedClass);
        }
    }

    /** Resolves cyclic inheritance in class inheritance path **/
    public void resolveCyclicInheritance(ParserRuleContext ctx) {
        //For each class, check for cyclic inheritance
        for (MJClass startingClass : mainClass.getClasses()) {
            Set<MJClass> classesInPath = new HashSet<>();
            MJClass currentClass = startingClass;
            //Loop through classes via their super class
            while ( (currentClass = currentClass.getSuperClass()) != null) {
                //If the class hasn't already been seen in the inheritance list
                if ( !classesInPath.contains(currentClass.getSuperClass())) {
                    classesInPath.add(currentClass);
                } else {
                    //Report a cyclic inheritance error
                    ErrorReporter.reportCyclicInheritance(ctx,currentClass.getName());
                    //delete the cycle causing super class
                    currentClass.setSuperClass(null);
                }
            }
        }
    }

    /** Gets method parameters from a method declaration context **/
    public List<Symbol> getMethodParams(MiniJavaParser.MethodDeclContext ctx) {
        List<TerminalNode> identifiers = ctx.IDENTIFIER();
        List<MiniJavaParser.TypeContext> types = ctx.type();
        //If there are no parameters, return an empty list
        if (types.size() < 2) {
            return Collections.emptyList();
        }

        List<Symbol> params = new ArrayList<>();
        //Construct parameter list
        for (int i = 1; i < types.size(); ++i) {
            String paramName = identifiers.get(i).getText();
            ValueType paramType = new ValueType(types.get(i).getText());
            params.add(new Symbol(paramName,paramType));
        }

        return params;
    }

    public MainClass getMainClass() {
        return this.mainClass;
    }
}
