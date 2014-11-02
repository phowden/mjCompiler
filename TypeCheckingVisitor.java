import org.antlr.v4.runtime.misc.NotNull;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TypeCheckingVisitor extends MiniJavaBaseVisitor<ValueType> {

    private MainClass mainClass;
    private MJClass currentMJClass;
    private Method currentMethod;

    public TypeCheckingVisitor(MainClass mc) {
        this.mainClass = mc;
        this.currentMJClass = null;
        this.currentMethod = null;
    }

    @Override public ValueType visitClassDecl(@NotNull MiniJavaParser.ClassDeclContext ctx) {
        String className = ctx.IDENTIFIER().get(0).getText();
        currentMJClass = mainClass.getClass(className);
        visitChildren(ctx);
        return null;
    }

    @Override public ValueType visitMethodDecl(@NotNull MiniJavaParser.MethodDeclContext ctx) {
        String methodName = ctx.IDENTIFIER(0).getText();
        currentMethod = currentMJClass.getMethod(methodName,false);
        if (currentMJClass.getSuperClass() != null) {
            Method superMethod = currentMJClass.getSuperClass().getMethod(methodName,true);
            if (superMethod != null && MJUtils.isOverloading(currentMethod,superMethod))  {
                ErrorReporter.reportOverloading(ctx,methodName,superMethod.getName());
            }
        }
        visitChildren(ctx);
        return null;
    }

    @Override public ValueType visitAssignStat(@NotNull MiniJavaParser.AssignStatContext ctx) {
        return null;
    }

    @Override public  ValueType visitPlusMinusExpr(@NotNull MiniJavaParser.PlusMinusExprContext ctx) {
        return null;
    }

    @Override public ValueType visitThisExpr(@NotNull MiniJavaParser.ThisExprContext ctx) {
        return new ValueType("wtf");
    }

}


