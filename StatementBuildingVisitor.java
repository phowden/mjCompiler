import org.antlr.v4.runtime.misc.NotNull;
import java.util.List;
import java.util.ArrayList;

/** Visitor that builds Statements by visiting ANTLR generated AST nodes **/
public class StatementBuildingVisitor extends MiniJavaBaseVisitor<Statement> {

    private final MainClass mainClass;
    private final MJClass mjClass;
    private final Method method;

    private final ExpressionBuildingVisitor expVisitor;

    public StatementBuildingVisitor(MainClass mc, MJClass mjc, Method m) {
        this.mainClass = mc;
        this.mjClass = mjc;
        this.method = m;
        this.expVisitor = new ExpressionBuildingVisitor(mainClass,mjClass,method);
    }

    /** Visits scoped statements **/
    @Override public Statement visitScopedStat(@NotNull MiniJavaParser.ScopedStatContext ctx) {
        List<Statement> statements = new ArrayList<>();
        for (MiniJavaParser.StatementContext statementCtx : ctx.statement()) {
            statements.add(visit(statementCtx));
        }
        return new Statement.ScopedStatement(statements);
    }

    /** Visits if statements **/
    @Override public Statement visitIfStat(@NotNull MiniJavaParser.IfStatContext ctx) {
        final int IF_TRUE= 0;
        final int IF_FALSE= 1;
        
        Expression predicate = expVisitor.visit(ctx.expression());
        Statement ifTrue = visit(ctx.statement(IF_TRUE));
        Statement ifFalse = visit(ctx.statement(IF_FALSE));
        return new Statement.IfStatement(predicate,ifTrue,ifFalse);
    }

    /** Visits while statements **/
    @Override public Statement visitWhileStat(@NotNull MiniJavaParser.WhileStatContext ctx) {
       Expression predicate = expVisitor.visit(ctx.expression());
       Statement body = visit(ctx.statement());
       return new Statement.WhileStatement(predicate,body);
    }
    
    /** Visits print statements **/
    @Override public Statement visitPrintlnStat(@NotNull MiniJavaParser.PrintlnStatContext ctx) {
        Expression expression = expVisitor.visit(ctx.expression());
        return new Statement.PrintlnStatement(expression);
    }

    /** Visits assignment statements **/
    @Override public Statement visitAssignStat(@NotNull MiniJavaParser.AssignStatContext ctx) {
        String idName = ctx.IDENTIFIER().getText();
        Symbol idSym = MJUtils.findVariable(mjClass,method,idName);
        return new Statement.AssignmentStatement(idSym,expVisitor.visit(ctx.expression()));
    }

    /** Visit assignment to array indexes **/
    @Override public Statement visitArrAssignStat(@NotNull MiniJavaParser.ArrAssignStatContext ctx) {
        String idName = ctx.IDENTIFIER().getText();
        Symbol idSym = MJUtils.findVariable(mjClass,method,idName);
        final int INDEX = 0;
        final int VALUE = 1;
        Expression index = expVisitor.visit(ctx.expression(INDEX));
        Expression value = expVisitor.visit(ctx.expression(VALUE));

        return new Statement.ArrayAssignmentStatement(idSym,index,value);
    }
}

