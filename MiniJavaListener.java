// Generated from MiniJava.g by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MiniJavaParser}.
 */
public interface MiniJavaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultExpr(@NotNull MiniJavaParser.MultExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultExpr(@NotNull MiniJavaParser.MultExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(@NotNull MiniJavaParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(@NotNull MiniJavaParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdExpr(@NotNull MiniJavaParser.IdExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdExpr(@NotNull MiniJavaParser.IdExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(@NotNull MiniJavaParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(@NotNull MiniJavaParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intType}
	 * labeled alternative in {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntType(@NotNull MiniJavaParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntType(@NotNull MiniJavaParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ternaryExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTernaryExpr(@NotNull MiniJavaParser.TernaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ternaryExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTernaryExpr(@NotNull MiniJavaParser.TernaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newArrExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewArrExpr(@NotNull MiniJavaParser.NewArrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newArrExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewArrExpr(@NotNull MiniJavaParser.NewArrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code plusExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPlusExpr(@NotNull MiniJavaParser.PlusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code plusExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPlusExpr(@NotNull MiniJavaParser.PlusExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#classDecl}.
	 * @param ctx the parse tree
	 */
	void enterClassDecl(@NotNull MiniJavaParser.ClassDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#classDecl}.
	 * @param ctx the parse tree
	 */
	void exitClassDecl(@NotNull MiniJavaParser.ClassDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrAssignStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterArrAssignStat(@NotNull MiniJavaParser.ArrAssignStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrAssignStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitArrAssignStat(@NotNull MiniJavaParser.ArrAssignStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code objType}
	 * labeled alternative in {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 */
	void enterObjType(@NotNull MiniJavaParser.ObjTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code objType}
	 * labeled alternative in {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 */
	void exitObjType(@NotNull MiniJavaParser.ObjTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenedExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenedExpr(@NotNull MiniJavaParser.ParenedExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenedExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenedExpr(@NotNull MiniJavaParser.ParenedExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueLitExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTrueLitExpr(@NotNull MiniJavaParser.TrueLitExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueLitExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTrueLitExpr(@NotNull MiniJavaParser.TrueLitExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void enterMainClass(@NotNull MiniJavaParser.MainClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void exitMainClass(@NotNull MiniJavaParser.MainClassContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intArrType}
	 * labeled alternative in {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntArrType(@NotNull MiniJavaParser.IntArrTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intArrType}
	 * labeled alternative in {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntArrType(@NotNull MiniJavaParser.IntArrTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code scopedStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterScopedStat(@NotNull MiniJavaParser.ScopedStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code scopedStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitScopedStat(@NotNull MiniJavaParser.ScopedStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLitExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIntLitExpr(@NotNull MiniJavaParser.IntLitExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLitExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIntLitExpr(@NotNull MiniJavaParser.IntLitExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#methodDecl}.
	 * @param ctx the parse tree
	 */
	void enterMethodDecl(@NotNull MiniJavaParser.MethodDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#methodDecl}.
	 * @param ctx the parse tree
	 */
	void exitMethodDecl(@NotNull MiniJavaParser.MethodDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseLitExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFalseLitExpr(@NotNull MiniJavaParser.FalseLitExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseLitExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFalseLitExpr(@NotNull MiniJavaParser.FalseLitExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(@NotNull MiniJavaParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(@NotNull MiniJavaParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrAccessExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrAccessExpr(@NotNull MiniJavaParser.ArrAccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrAccessExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrAccessExpr(@NotNull MiniJavaParser.ArrAccessExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lessThanExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLessThanExpr(@NotNull MiniJavaParser.LessThanExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lessThanExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLessThanExpr(@NotNull MiniJavaParser.LessThanExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrLengthExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrLengthExpr(@NotNull MiniJavaParser.ArrLengthExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrLengthExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrLengthExpr(@NotNull MiniJavaParser.ArrLengthExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignStat(@NotNull MiniJavaParser.AssignStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignStat(@NotNull MiniJavaParser.AssignStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(@NotNull MiniJavaParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(@NotNull MiniJavaParser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStat(@NotNull MiniJavaParser.WhileStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStat(@NotNull MiniJavaParser.WhileStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code minusExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMinusExpr(@NotNull MiniJavaParser.MinusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code minusExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMinusExpr(@NotNull MiniJavaParser.MinusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(@NotNull MiniJavaParser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(@NotNull MiniJavaParser.BoolTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfStat(@NotNull MiniJavaParser.IfStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfStat(@NotNull MiniJavaParser.IfStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code negExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNegExpr(@NotNull MiniJavaParser.NegExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNegExpr(@NotNull MiniJavaParser.NegExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterThisExpr(@NotNull MiniJavaParser.ThisExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitThisExpr(@NotNull MiniJavaParser.ThisExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newObjExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewObjExpr(@NotNull MiniJavaParser.NewObjExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newObjExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewObjExpr(@NotNull MiniJavaParser.NewObjExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code methodCallExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMethodCallExpr(@NotNull MiniJavaParser.MethodCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code methodCallExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMethodCallExpr(@NotNull MiniJavaParser.MethodCallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printlnStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterPrintlnStat(@NotNull MiniJavaParser.PrintlnStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printlnStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitPrintlnStat(@NotNull MiniJavaParser.PrintlnStatContext ctx);
}