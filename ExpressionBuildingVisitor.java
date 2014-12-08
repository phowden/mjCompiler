import org.antlr.v4.runtime.misc.NotNull;

public class ExpressionBuildingVisitor extends MiniJavaBaseVisitor<Expression> {


    @Override public Expression visitParenedExpr(@NotNull MiniJavaParser.ParenedExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override public Expression visitMultExpr(@NotNull MiniJavaParser.MultExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));
        return new MultiplyExpression(lhs,rhs);
    }

    @Override public Expression visitAndExpr(@NotNull MiniJavaParser.AndExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));
        return new AndExpression(lhs,rhs);
    }

    @Override public Expression visitNewArrExpr(@NotNull MiniJavaParser.NewArrExprContext ctx) {
        return new NewArrayExpression(visit(ctx.expression());
    }

    @Override public Expression visitPlusMinusExpr(@NotNull MiniJavaParser.PlusMinusExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));
    }

    @Override public Expression visitNegateExpr(@NotNull MiniJavaParser.NegateExprContext ctx) {
        return new NegateExpression(visit(ctx.expression()));
    }

    @Override public Expression visitArrAccessExpr(@NotNull MiniJavaParser.ArrAccessExprContext ctx) {
        final int ARRAY = 0;
        final int INDEX = 1;
        Expression array = visit(ctx.expression(ARRAY));
        Expression index = visit(ctx.expression(INDEX));
        return new ArrayAccessExpression(array,index);
    }

    @Override public Expression visitLessThanExpr(@NotNull MiniJavaParser.LessThanExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));
        return new LessThanExpression(lhs,rhs);
    }

    @Override public Expression visitArrLengthExpr(@NotNull MiniJavaParser.ArrLengthExprContext ctx) {
        return new ArrayLengthExpression(visit(ctx.expression()));
    }

    @Override public Expression visitMethodCallExpr(@NotNull MiniJavaParser.MethodCallExprContext ctx) {
        return null; //TODO: Actually implement
    }

    @Override public Expression visitTernaryExpr(@NotNull MiniJavaParser.TernaryExprContext ctx)  {
        final int PRED = 0;
        final int TRUE = 1;
        final int FALSE = 2;
        Expression predicate = visit(ctx.expression(PRED));
        Expression ifTrue = visit(ctx.expression(TRUE));
        Expression ifFalse = visit(ctx.expression(FALSE));

        return new TernaryExpression(predicate,ifTrue,ifFalse);
    }

    @Override public Expression visitIdExpr(@NotNull MiniJavaParser.IdExprContext ctx) {
        return null; //TODO: Actually implement
    }

    @Override public Expression visitNewObjExpr(@NotNull MiniJavaParser.NewObjExprContext ctx) {
        return null; //TODO: Actually implement
    }

    @Override public Expression visitTrueLitExpr(@NotNull MiniJavaParser.TrueLitExprContext ctx) {
        return new BooleanLiteralExpression(true);
    }
    
    @Override public Expression visitFalseLitExpr(@NotNull MiniJavaParser.FalseLitExprContext ctx) {
        return new BooleanLiteralExpression(false);
    }

    @Override public Expression visitIntLitExpr(@NotNull MiniJavaParser.IntLitExprContext ctx) {
        return null; //TODO: Actually implement
    }

    @Override public Expression visitThisExpr(@NotNull MiniJavaParser.ThisExprContext ctx) {
        return new ThisExpression();
    }
}
