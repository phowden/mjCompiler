import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.ArrayList;

/** Visits each expression in ANTLR's produced AST. Builds an Expression object
  and returns it.
 **/
public class ExpressionBuildingVisitor extends MiniJavaBaseVisitor<Expression> {

    /* Fields that describe the current context. Allows for proper symbol
       resolution of identifiers, methods, etc
     **/
    private final MainClass mainClass;
    private final MJClass mjClass;
    private final Method method;

    public ExpressionBuildingVisitor(MainClass mc, MJClass c, Method m) {
        this.mainClass = mc;
        this.mjClass = c;
        this.method = m;
    }

    /** Visits expressions within parentheses **/
    @Override public Expression visitParenedExpr(@NotNull MiniJavaParser.ParenedExprContext ctx) {
        //Visit the contained expression
        return visit(ctx.expression());
    }

    /** Visits multiplication expressions **/
    @Override public Expression visitMultExpr(@NotNull MiniJavaParser.MultExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));
        return new Expression.MultiplyExpression(lhs,rhs);
    }

    /**  Visits && expressions **/
    @Override public Expression visitAndExpr(@NotNull MiniJavaParser.AndExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));
        return new Expression.AndExpression(lhs,rhs);
    }

    /** Visits new array creation expressions **/
    @Override public Expression visitNewArrExpr(@NotNull MiniJavaParser.NewArrExprContext ctx) {
        return new Expression.NewArrayExpression(visit(ctx.expression()));
    }

    /** Visits addition expressions **/
    @Override public Expression visitPlusExpr(@NotNull MiniJavaParser.PlusExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));

        return new Expression.PlusMinusExpression(lhs,rhs,true);
    }

    /** Visits subtraction expressions **/
    @Override public Expression visitMinusExpr(@NotNull MiniJavaParser.MinusExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));

        return new Expression.PlusMinusExpression(lhs,rhs,false);
    }

    /** Visits arithmetic negation expressions **/
    @Override public Expression visitNegExpr(@NotNull MiniJavaParser.NegExprContext ctx) {
        return new Expression.NegateExpression(visit(ctx.expression()));
    }

    /** Visists boolean not expressions **/
    @Override public Expression visitNotExpr(@NotNull MiniJavaParser.NotExprContext ctx) {
        return new Expression.NotExpression(visit(ctx.expression()));
    }

    /** Visits array access expression **/
    @Override public Expression visitArrAccessExpr(@NotNull MiniJavaParser.ArrAccessExprContext ctx) {
        final int ARRAY = 0;
        final int INDEX = 1;
        Expression array = visit(ctx.expression(ARRAY));
        Expression index = visit(ctx.expression(INDEX));
        return new Expression.ArrayAccessExpression(array,index);
    }

    /** Visits less than expressions **/
    @Override public Expression visitLessThanExpr(@NotNull MiniJavaParser.LessThanExprContext ctx) {
        final int LHS = 0;
        final int RHS = 1;
        Expression lhs = visit(ctx.expression(LHS));
        Expression rhs = visit(ctx.expression(RHS));
        return new Expression.LessThanExpression(lhs,rhs);
    }

    /** Visits array length expressions **/
    @Override public Expression visitArrLengthExpr(@NotNull MiniJavaParser.ArrLengthExprContext ctx) {
        return new Expression.ArrayLengthExpression(visit(ctx.expression()));
    }

    /** Visits method call expressions **/
    @Override public Expression visitMethodCallExpr(@NotNull MiniJavaParser.MethodCallExprContext ctx) {
        final int OBJECT = 0;
        Expression object = visit(ctx.expression(OBJECT));

        /* Do a bunch of ugly stuff to get method */
        //Initialize a new type checker that doesn't attempt to report errors
        TypeCheckingVisitor typeCheck = new TypeCheckingVisitor(mainClass,mjClass,method,true);
        //Get the type of the object expression
        ValueType objectType = typeCheck.visit(ctx.expression(OBJECT));
        //Get the class
        MJClass objectClass = mainClass.getClass(objectType.getType());
        //Finally get the method
        Method methodToCall = objectClass.getMethod(ctx.IDENTIFIER().getText(),true);

        //Get the parameters to the method call
        List<Expression> params = new ArrayList<>();
        for (int paramIndex = 1; paramIndex < ctx.expression().size(); ++paramIndex) {
            params.add(visit(ctx.expression(paramIndex)));
        }

        return new Expression.MethodCallExpression(object,methodToCall,params);
    }

    /** Visits ternary expressions **/
    @Override public Expression visitTernaryExpr(@NotNull MiniJavaParser.TernaryExprContext ctx)  {
        final int PRED = 0;
        final int TRUE = 1;
        final int FALSE = 2;
        Expression predicate = visit(ctx.expression(PRED));
        Expression ifTrue = visit(ctx.expression(TRUE));
        Expression ifFalse = visit(ctx.expression(FALSE));

        return new Expression.TernaryExpression(predicate,ifTrue,ifFalse);
    }

    /** Visits identifier expressions **/
    @Override public Expression visitIdExpr(@NotNull MiniJavaParser.IdExprContext ctx) {
        //Get the name of the identifier
        String idName = ctx.IDENTIFIER().getText();
        //Get the symbol
        Symbol idSym = MJUtils.findVariable(mjClass,method,idName);
        return new Expression.IdentifierExpression(idSym);
    }

    /** Visits new object instantiation expressions **/
    @Override public Expression visitNewObjExpr(@NotNull MiniJavaParser.NewObjExprContext ctx) {
        String objName = ctx.IDENTIFIER().getText();
        MJClass objClass = mainClass.getClass(objName);
        return new Expression.NewObjectExpression(objClass);
    }

    /** Visits boolean true literals **/
    @Override public Expression visitTrueLitExpr(@NotNull MiniJavaParser.TrueLitExprContext ctx) {
        return new Expression.BooleanLiteralExpression(true);
    }
    
    /** Visits boolean false literals **/
    @Override public Expression visitFalseLitExpr(@NotNull MiniJavaParser.FalseLitExprContext ctx) {
        return new Expression.BooleanLiteralExpression(false);
    }

    /** Visit integer literals **/
    @Override public Expression visitIntLitExpr(@NotNull MiniJavaParser.IntLitExprContext ctx) {
        int value = Integer.parseInt(ctx.INT_LIT().getText());
        return new Expression.IntegerLiteralExpression(value);
    }

    /** visit this literals **/
    @Override public Expression visitThisExpr(@NotNull MiniJavaParser.ThisExprContext ctx) {
        return new Expression.ThisExpression();
    }
}
