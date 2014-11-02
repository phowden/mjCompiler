import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;

public class ErrorReporter {

    public static String OVERLOAD_METHOD = "Error on line %d: method '%s' already defined as '%s'\n";

    public static String DOUBLE_CLASS_DECL = "Error on line %d: class '%s' already defined\n";
    public static String DOUBLE_METH_DECL = "Error on line %d: method '%s' already defined in class '%s'\n";
    public static String DOUBLE_VAR_DECL = "Error on line %d: variable '%s' already defined\n";

    public static String CYCLIC_INHERIT = "Error on line %d: cyclic inheritance involving '%s' detected\n";

    public static void reportOverloading(ParserRuleContext ctx, String newMethodName, String oldMethod) {
        reportError(ctx,OVERLOAD_METHOD,newMethodName,oldMethod);
    }

    public static void reportDoubleClassDecl(ParserRuleContext ctx, String className) {
        reportError(ctx,DOUBLE_CLASS_DECL,className,null);
    }

    public static void reportDoubleMethDecl(ParserRuleContext ctx, String methodName, String className) {
        reportError(ctx,DOUBLE_METH_DECL,methodName,className);
    }

    public static void reportDoubleVarDecl(ParserRuleContext ctx, String symbolName) {
        reportError(ctx,DOUBLE_VAR_DECL,symbolName,null);
    }

    public static void reportCyclicInheritance(ParserRuleContext ctx, String className) {
        reportError(ctx,CYCLIC_INHERIT,className,null);
    }

    private static void reportError(ParserRuleContext ctx, String msg, String symbolName, String optSymbol) {
        String input = ctx.getStart().getInputStream().toString();
        String[] lines = input.split("\n");
        String line = lines[ctx.getStart().getLine()-1];
        System.err.println("\n"+line.trim()+"\n^");
        int lineNumber = ctx.getStart().getLine();
        if (optSymbol == null) {
            System.err.printf(msg,lineNumber,symbolName);
        } else {
            System.err.printf(msg,lineNumber,symbolName,optSymbol);
        }
    }
}
