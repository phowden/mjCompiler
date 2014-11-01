import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.HashSet;


public class ResolveVisitor extends MiniJavaBaseVisitor<ValueType> {

    private MainClass mainClass;
    private MJClass currentMJClass;
    private Method currentMethod;

    private boolean skipMode;

    //Inheritance map of String(Extender) to String(SuperClass) 
    private HashMap<String,String> inheritanceMap;

    public ResolveVisitor() {
        this.mainClass = null;
        this.currentMJClass = null;
        this.currentMethod = null;
        this.skipMode = false;
        this.inheritanceMap = new LinkedHashMap<String,String>();
    }

    @Override public ValueType visitMainClass(@NotNull MiniJavaParser.MainClassContext ctx) {
        String mainClassName = ctx.IDENTIFIER(0).getText();
        mainClass = new MainClass(mainClassName);
        return null;
    }

    @Override public ValueType visitClassDecl(@NotNull MiniJavaParser.ClassDeclContext ctx) {
        List<TerminalNode> classIdentifiers = ctx.IDENTIFIER();

        String className = classIdentifiers.get(0).getText();

        if (className.equals(mainClass.getName())) {
            //Report an error
            //TODO Actually report the error
            skipMode = true;
            return null;
        }

        if (mainClass.getClass(className) != null) {
            //Report double declaration error
            //TODO Actually report the error
            skipMode = true;
            return null;
        }

        currentMJClass = new MJClass(className);
        currentMethod = null;
        skipMode = false;
        mainClass.addClass(currentMJClass);
        resolveInheritance(classIdentifiers);
        resolveCyclicInheritance();
        return visitChildren(ctx);
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
        System.out.println("METHOD");

       if (skipMode) {
           System.out.println("Skipping method");
           return null;
       }

       String methodName = ctx.IDENTIFIER(0).getText();
       String returnType = ctx.type(0).getText();
       System.out.println("Method: "+methodName+ " returns "+ returnType);
       return null;
    }

    public void varDeclInMethod(MiniJavaParser.VarDeclContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        if (MJUtils.findVariable(currentMJClass,currentMethod,varName) != null) {
            //Report double declaration error
            //TODO Actually report the error
        } else {
            ValueType type = new ValueType(ctx.type().getText());
            currentMethod.addVariable(varName,type);
        }
    }

    public void varDeclInMJClass(MiniJavaParser.VarDeclContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        if (MJUtils.findVariable(currentMJClass,currentMethod,varName) != null) {
            //Report double declaration error
            //TODO Actually report the error
        } else {
            ValueType type = new ValueType(ctx.type().getText());
            currentMJClass.addField(varName,type);
        }
    }



    public void resolveInheritance(List<TerminalNode> identifiers) {
        //If class has a super, add it to the inheritance map
        if (identifiers.size() == 2) {
            System.out.println("Adding: "+ identifiers.get(0).getText()+"->"+identifiers.get(1).getText());
            inheritanceMap.put(identifiers.get(0).getText(),identifiers.get(1).getText());
        }

        String className = identifiers.get(0).getText();
        System.out.println("Resolving "+className);

        List<String> resolved = new ArrayList<>();

        //Resolve inheritances in the map
        //TODO, remove entries so we don't have to resolve them every time
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

    public void resolveCyclicInheritance() {
        for (MJClass startingClass : mainClass.getClasses()) {
            Set<MJClass> classesInPath = new HashSet<>();
            //classesInPath.add(startingClass);
            MJClass currentClass = startingClass;
            System.out.println("SC: "+startingClass.toString());
            while ( (currentClass = currentClass.getSuperClass()) != null) {
                System.out.println("CC: "+currentClass.toString());
                if ( !classesInPath.contains(currentClass.getSuperClass())) {
                    classesInPath.add(currentClass);
                } else {
                    //Report a cyclic inheritance error
                    //TODO actually report the error
                    //Delete the super class causing the cycle
                    resolveCycle(classesInPath);
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

    public void printClasses() {
        System.out.println(mainClass.toString());
    }
}
