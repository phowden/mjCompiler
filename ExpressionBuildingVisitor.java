import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.ArrayList;

public class ExpressionBuildingVisitor extends MiniJavaBaseVisitor<Expression> {

    private final MainClass mainClass;
    private final MJClass mjClass;
    private final Method method;

    public ExpressionBuildingVisitor(MainClass mc, MJClass c, Method m) {
        this.mainClass = mc;
        this.mjClass = c;
        this.method = m;
    }

    @Override public Expression visitParenedExpr(@NotNull MiniJavaParser.ParenedExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override public Expression visitMultExpr(@NotNull MiniJavaParser.MultExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));
        return new Expression.MultiplyExpression(lhs,rhs);
    }

    @Override public Expression visitAndExpr(@NotNull MiniJavaParser.AndExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));
        return new Expression.AndExpression(lhs,rhs);
    }

    @Override public Expression visitNewArrExpr(@NotNull MiniJavaParser.NewArrExprContext ctx) {
        return new Expression.NewArrayExpression(visit(ctx.expression()));
    }

    @Override public Expression visitPlusExpr(@NotNull MiniJavaParser.PlusExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));

        return new Expression.PlusMinusExpression(lhs,rhs,true);
    }

    @Override public Expression visitMinusExpr(@NotNull MiniJavaParser.MinusExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));

        return new Expression.PlusMinusExpression(lhs,rhs,false);
    }

    @Override public Expression visitNegExpr(@NotNull MiniJavaParser.NegExprContext ctx) {
        return new Expression.NegateExpression(visit(ctx.expression()));
    }

    @Override public Expression visitNotExpr(@NotNull MiniJavaParser.NotExprContext ctx) {
        return new Expression.NotExpression(visit(ctx.expression()));
    }

    @Override public Expression visitArrAccessExpr(@NotNull MiniJavaParser.ArrAccessExprContext ctx) {
        final int ARRAY = 0;
        final int INDEX = 1;
        Expression array = visit(ctx.expression(ARRAY));
        Expression index = visit(ctx.expression(INDEX));
        return new Expression.ArrayAccessExpression(array,index);
    }

    @Override public Expression visitLessThanExpr(@NotNull MiniJavaParser.LessThanExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));
        return new Expression.LessThanExpression(lhs,rhs);
    }

    @Override public Expression visitArrLengthExpr(@NotNull MiniJavaParser.ArrLengthExprContext ctx) {
        return new Expression.ArrayLengthExpression(visit(ctx.expression()));
    }

    @Override public Expression visitMethodCallExpr(@NotNull MiniJavaParser.MethodCallExprContext ctx) {
        final int OBJECT = 0;
        Expression object = visit(ctx.expression(OBJECT));

        //Do a bunch of ugly stuff to get method
        TypeCheckingVisitor typeCheck = new TypeCheckingVisitor(mainClass,mjClass,method,true);
        ValueType objectType = typeCheck.visit(ctx.expression(OBJECT));
        MJClass objectClass = mainClass.getClass(objectType.getType());
        Method methodToCall = objectClass.getMethod(ctx.IDENTIFIER().getText(),true);

        List<Expression> params = new ArrayList<>();
        for (int paramIndex = 1; paramIndex < ctx.expression().size(); ++paramIndex) {
            params.add(visit(ctx.expression(paramIndex)));
        }
        return new Expression.MethodCallExpression(object,methodToCall,params);
    }

    @Override public Expression visitTernaryExpr(@NotNull MiniJavaParser.TernaryExprContext ctx)  {
        final int PRED = 0;
        final int TRUE = 1;
        final int FALSE = 2;
        Expression predicate = visit(ctx.expression(PRED));
        Expression ifTrue = visit(ctx.expression(TRUE));
        Expression ifFalse = visit(ctx.expression(FALSE));

        return new Expression.TernaryExpression(predicate,ifTrue,ifFalse);
    }

    @Override public Expression visitIdExpr(@NotNull MiniJavaParser.IdExprContext ctx) {
        String idName = ctx.IDENTIFIER().getText();
        Symbol idSym = MJUtils.findVariable(mjClass,method,idName);
        return new Expression.IdentifierExpression(idSym);
    }

    @Override public Expression visitNewObjExpr(@NotNull MiniJavaParser.NewObjExprContext ctx) {
        String objName = ctx.IDENTIFIER().getText();
        MJClass objClass = mainClass.getClass(objName);
        return new Expression.NewObjectExpression(objClass);
    }

    @Override public Expression visitTrueLitExpr(@NotNull MiniJavaParser.TrueLitExprContext ctx) {
        return new Expression.BooleanLiteralExpression(true);
    }
    
    @Override public Expression visitFalseLitExpr(@NotNull MiniJavaParser.FalseLitExprContext ctx) {
        return new Expression.BooleanLiteralExpression(false);
    }

    @Override public Expression visitIntLitExpr(@NotNull MiniJavaParser.IntLitExprContext ctx) {
        int value = Integer.parseInt(ctx.INT_LIT().getText());
        return new Expression.IntegerLiteralExpression(value);
    }

    @Override public Expression visitThisExpr(@NotNull MiniJavaParser.ThisExprContext ctx) {
        return new Expression.ThisExpression();
    }
}
