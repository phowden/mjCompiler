import org.antlr.v4.runtime.misc.NotNull;
import java.util.List;
import java.util.ArrayList;

public class StatementBuildingVisitor extends MiniJavaBaseVisitor<Statement> {

    private final ExpressionBuildingVisitor expVisitor;
    private final MainClass mainClass;
    private final MJClass mjClass;
    private final Method method;

    public StatementBuildingVisitor(MainClass mc, MJClass mjc, Method m) {
        this.mainClass = mc;
        this.mjClass = mjc;
        this.method = m;
        this.expVisitor = new ExpressionBuildingVisitor(mainClass,mjClass,method);
    }

    @Override public Statement visitScopedStat(@NotNull MiniJavaParser.ScopedStatContext ctx) {
        List<Statement> statements = new ArrayList<>();
        for (MiniJavaParser.StatementContext statementCtx : ctx.statement()) {
            statements.add(visit(statementCtx));
        }
        return new ScopedStatement(statements);
    }

    @Override public Statement visitIfStat(@NotNull MiniJavaParser.IfStatContext ctx) {
        final int IF_TRUE= 0;
        final int IF_FALSE= 1;
        Expression predicate = expVisitor.visit(ctx.expression());
        Statement ifTrue = visit(ctx.statement(IF_TRUE));
        Statement ifFalse = visit(ctx.statement(IF_FALSE));
        return new IfStatement(predicate,ifTrue,ifFalse);
    }

    @Override public Statement visitWhileStat(@NotNull MiniJavaParser.WhileStatContext ctx) {
       Expression predicate = expVisitor.visit(ctx.expression());
       Statement body = visit(ctx.statement());
       return new WhileStatement(predicate,body);
    }
    
    @Override public Statement visitPrintlnStat(@NotNull MiniJavaParser.PrintlnStatContext ctx) {
        Expression expression = expVisitor.visit(ctx.expression());
        return new PrintlnStatement(expression);
    }

    //TODO: How to get symbol? Calls from other visitors won't pass symbols
    public Statement visitAssignStat(@NotNull MiniJavaParser.AssignStatContext ctx) {
        String idName = ctx.IDENTIFIER().getText();
        Symbol idSym = MJUtils.findVariable(mjClass,method,idName);
        return new AssignmentStatement(idSym,expVisitor.visit(ctx.expression()));
    }

    //TODO: How to get symbol? Calls from other visitors won't pass symbols
    public Statement visitArrAssignStat(@NotNull MiniJavaParser.ArrAssignStatContext ctx, Symbol id) {
        String idName = ctx.IDENTIFIER().getText();
        Symbol idSym = MJUtils.findVariable(mjClass,method,idName);
        final int INDEX = 0;
        final int VALUE = 1;
        Expression index = expVisitor.visit(ctx.expression(INDEX));
        Expression value = expVisitor.visit(ctx.expression(VALUE));

        return new ArrayAssignmentStatement(idSym,index,value);
    }
}

