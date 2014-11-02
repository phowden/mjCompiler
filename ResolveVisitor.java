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

    public void resolve(ParseTree tree) {
        this.visit(tree);
        this.classResolveStep = false;
        this.visit(tree);
    }

    @Override public ValueType visitMainClass(@NotNull MiniJavaParser.MainClassContext ctx) {
        if (classResolveStep) {
            String mainClassName = ctx.IDENTIFIER(0).getText();
            mainClass = new MainClass(mainClassName);
        }
        return visitChildren(ctx);
    }

    @Override public ValueType visitClassDecl(@NotNull MiniJavaParser.ClassDeclContext ctx) {
        List<TerminalNode> classIdentifiers = ctx.IDENTIFIER();
        String className = classIdentifiers.get(0).getText();

        if (classResolveStep) {
            
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

            currentMJClass = new MJClass(className);
            currentMethod = null;
            mainClass.addClass(currentMJClass);
            resolveInheritance(classIdentifiers);
            resolveCyclicInheritance(ctx);
            return null;
        } else {
            currentMJClass = mainClass.getClass(className);
            currentMethod = null;
            return visitChildren(ctx);
        }
    }

    @Override public ValueType visitVarDecl(@NotNull MiniJavaParser.VarDeclContext ctx) {
        if (currentMethod != null) {
            varDeclInMethod(ctx);
        } else if (currentMJClass != null) {
            varDeclInMJClass(ctx);
        }
        return null;
    }

    @Override public ValueType visitMethodDecl(@NotNull MiniJavaParser.MethodDeclContext ctx) {

        String methodName = ctx.IDENTIFIER(0).getText();
        ValueType returnType = new ValueType(ctx.type(0).getText());
        List<Symbol> params = getMethodParams(ctx);

        Method newMethod = new Method(returnType,methodName,params);

        if (currentMJClass.getMethod(methodName,false) != null) {
            //Report double declaration error
            ErrorReporter.reportDoubleMethDecl(ctx,newMethod.toString(),currentMJClass.getName());
            //Catelog the method as discarded
            discardedMethods.add(ctx);
            return null;
        } 

        Method superMethod = currentMJClass.getMethod(methodName,true);

        if (superMethod != null) {
            if (MJUtils.isOverloading(newMethod,superMethod)) {
                //Report attempted overloading
                ErrorReporter.reportOverloading(ctx,newMethod.getName(),superMethod.toString());
                //Catelog the method as discarded
                discardedMethods.add(ctx);
                return null;
            }
        }
        currentMJClass.addMethod(newMethod);
        currentMethod = newMethod;
        visitChildren(ctx);
        return null;
    }

    public void varDeclInMethod(MiniJavaParser.VarDeclContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        String varType = ctx.type().getText();
        if (MJUtils.findVariable(currentMJClass,currentMethod,varName) != null) {
            //Report double declaration error
            ErrorReporter.reportDoubleVarDecl(ctx,varName);
        } else {
            ValueType type = new ValueType(varType);
            currentMethod.addVariable(varName,type);
        }
    }

    public void varDeclInMJClass(MiniJavaParser.VarDeclContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        if (MJUtils.findVariable(currentMJClass,currentMethod,varName) != null) {
            //Report double declaration error
            ErrorReporter.reportDoubleVarDecl(ctx,varName);
        } else {
            ValueType type = new ValueType(ctx.type().getText());
            currentMJClass.addField(varName,type);
        }
    }



    public void resolveInheritance(List<TerminalNode> identifiers) {
        //If class has a super, add it to the inheritance map
        if (identifiers.size() == 2) {
            inheritanceMap.put(identifiers.get(0).getText(),identifiers.get(1).getText());
        }

        String className = identifiers.get(0).getText();

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

    public void resolveCyclicInheritance(ParserRuleContext ctx) {
        for (MJClass startingClass : mainClass.getClasses()) {
            Set<MJClass> classesInPath = new HashSet<>();
            //classesInPath.add(startingClass);
            MJClass currentClass = startingClass;
            //System.out.println("SC: "+startingClass.toString());
            while ( (currentClass = currentClass.getSuperClass()) != null) {
                //System.out.println("CC: "+currentClass.toString());
                if ( !classesInPath.contains(currentClass.getSuperClass())) {
                    classesInPath.add(currentClass);
                } else {
                    //Report a cyclic inheritance error
                    ErrorReporter.reportCyclicInheritance(ctx,currentClass.getName());
                    //Delete the super class causing the cycle
                    resolveCycle(classesInPath);
                    //TODO Better
                    currentClass.setSuperClass(null);
                }
            }
        }
    }

    public void resolveCycle(Set<MJClass> cycle) {

        /* For each class in the cycle, examine the inheritance chain if the target class
           were to have no super class.  */
        //TODO FIGURE THIS SHIT OUT
    }

    public List<Symbol> getMethodParams(MiniJavaParser.MethodDeclContext ctx) {
        List<TerminalNode> identifiers = ctx.IDENTIFIER();
        List<MiniJavaParser.TypeContext> types = ctx.type();
        if (types.size() < 2) {
            return Collections.emptyList();
        }
        List<Symbol> params = new ArrayList<>();

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

    public void printClasses() {
        System.out.println(mainClass.toString());
    }
}
