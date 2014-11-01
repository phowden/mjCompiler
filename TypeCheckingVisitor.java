import org.antlr.v4.runtime.misc.NotNull;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TypeCheckingVisitor<ValueType> extends MiniJavaBaseVisitor<ValueType> {

    private MainClass mainClass;

    public TypeCheckingVisitor(MainClass mc) {
        this.mainClass = mc;
    }

    @Override public ValueType visitVarDecl(@NotNull MiniJavaParser.VarDeclContext ctx) {
        super.visitVarDecl(ctx);
        return null;
    }

    
    @Override public ValueType visitAssignStat(@NotNull MiniJavaParser.AssignStatContext ctx) {
        super.visitAssignStat(ctx);
        String id = ctx.IDENTIFIER().getText();
        ValueType value = visit(ctx.expression());
        System.out.println("Value: "+value);
        System.out.println(id);
        return null;
    }

    @Override public  ValueType visitPlusMinusExpr(@NotNull MiniJavaParser.PlusMinusExprContext ctx) {
        super.visitPlusMinusExpr(ctx);
        List<MiniJavaParser.ExpressionContext> exps = ctx.expression();
        int i = 0;
        for (MiniJavaParser.ExpressionContext ep : exps) {
            System.out.println(i+++":"+ep.getText());
        }
        return null;
    }

    @Override public ValueType visitThisExpr(@NotNull MiniJavaParser.ThisExprContext ctx) {
        super.visitThisExpr(ctx);
        System.out.println("THISHISH");
        return null;
    }

}


