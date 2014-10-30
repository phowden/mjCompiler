import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class ResolveVisitor<ValueType> extends MiniJavaBaseVisitor<ValueType> {

    private MainClass mainClass;
    private MJClass currentMJClass;

    private HashMap<String,String> inheritanceMap;

    public ResolveVisitor() {
        this.mainClass = new MainClass();
        this.currentMJClass = null;
        this.inheritanceMap = new LinkedHashMap<String,String>();
    }

    @Override public ValueType visitClassDecl(@NotNull MiniJavaParser.ClassDeclContext ctx) {
        List<TerminalNode> classIdentifiers = ctx.IDENTIFIER();

        currentMJClass = new MJClass(classIdentifiers.get(0).getText());
        mainClass.addClass(currentMJClass);
        resolveInheritance(classIdentifiers);
        return null;
    }

    public void printClasses() {
        System.out.println(mainClass.toString());
    }

    public void resolveInheritance(List<TerminalNode> identifiers) {
        //If class has a super, add it to the inheritance map
        if (identifiers.size() == 2) {
            inheritanceMap.put(identifiers.get(0).getText(),identifiers.get(1).getText());
        }

        String className = identifiers.get(0).getText();
        System.out.println("Resolving "+className);

        //Resolve inheritances in the map
        for (Map.Entry<String,String> classEntry : inheritanceMap.entrySet()) {
            System.out.println(classEntry.getKey() + "::"+classEntry.getValue());
            if (className.equals(classEntry.getValue())) {
                MJClass superClass = mainClass.getClass(className);
                MJClass extender = mainClass.getClass(classEntry.getKey());
                extender.setSuperClass(superClass);
            }
        }
    }

}
