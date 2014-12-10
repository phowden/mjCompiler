import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;

public class ErrorReporter {

    public static final String OVERLOAD_METHOD = "Error on line %d: method '%s' is attempting to overload '%s'  \n";

    public static final String DOUBLE_CLASS_DECL = "Error on line %d: class '%s' already defined\n";
    public static final String DOUBLE_METH_DECL = "Error on line %d: method '%s' already defined in class '%s'\n";
    public static final String DOUBLE_VAR_DECL = "Error on line %d: variable '%s' already defined\n";

    public static final String CYCLIC_INHERIT = "Error on line %d: cyclic inheritance involving '%s' detected\n";

    public static final String SYM_NOT_FOUND = "Error on line %d: symbol %s not found\n";

    public static final String TYPE_MISMATCH = "Error on line %d: type mismatch, expected %s but found %s\n";

    public static final String USED_BEFORE_INIT = "Error on line %d: variable %s used before initilization\n";

    private static boolean rtError = false;

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

    public static void reportSymbolNotFound(ParserRuleContext ctx, String symName) {
        reportError(ctx,SYM_NOT_FOUND,symName,null);
    }

    public static void reportTypeMismatch(ParserRuleContext ctx, ValueType expectedType, ValueType foundType) {
        reportError(ctx,TYPE_MISMATCH,expectedType.getType(),foundType.getType());
    }

    public static void reportReturnTypeMismatch(ParserRuleContext ctx, ValueType expectedType, ValueType foundType) {
        rtError = true;
        reportError(ctx,TYPE_MISMATCH,expectedType.getType(),foundType.getType());
        rtError = false;
    }

    public static void reportUsedBeforeInitialized(ParserRuleContext ctx, String symName) {
        reportError(ctx,USED_BEFORE_INIT,symName,null);
    }

    public static void reportError(ParserRuleContext ctx, String msg, String symbolName, String optSymbol) {
        String input = ctx.getStart().getInputStream().toString();
        String[] lines = input.split("\n");
        String line;
        if (rtError) {
            line= lines[ctx.getStop().getLine()-2];
        } else {
            line= lines[ctx.getStart().getLine()-1];
        }
        int symbolIndex = line.trim().indexOf(symbolName);
        String space = "";
        for (int i = 0; i < symbolIndex; ++i) space += " ";
        System.err.println("\n"+line.trim()+"\n"+space+"^");
        int lineNumber = ctx.getStart().getLine();
        if (optSymbol == null) {
            System.err.printf(msg,lineNumber,symbolName);
        } else {
            System.err.printf(msg,lineNumber,symbolName,optSymbol);
        }
        MJUtils.markErrorOccured();
    }
}
