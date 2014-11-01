// Generated from MiniJava.g by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MiniJavaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MiniJavaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultExpr(@NotNull MiniJavaParser.MultExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(@NotNull MiniJavaParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(@NotNull MiniJavaParser.IdExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(@NotNull MiniJavaParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(@NotNull MiniJavaParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ternaryExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernaryExpr(@NotNull MiniJavaParser.TernaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newArrExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrExpr(@NotNull MiniJavaParser.NewArrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code plusMinusExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusMinusExpr(@NotNull MiniJavaParser.PlusMinusExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#classDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDecl(@NotNull MiniJavaParser.ClassDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrAssignStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrAssignStat(@NotNull MiniJavaParser.ArrAssignStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code objType}
	 * labeled alternative in {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjType(@NotNull MiniJavaParser.ObjTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenedExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenedExpr(@NotNull MiniJavaParser.ParenedExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code trueLitExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueLitExpr(@NotNull MiniJavaParser.TrueLitExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#mainClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainClass(@NotNull MiniJavaParser.MainClassContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intArrType}
	 * labeled alternative in {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntArrType(@NotNull MiniJavaParser.IntArrTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code scopedStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScopedStat(@NotNull MiniJavaParser.ScopedStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intLitExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLitExpr(@NotNull MiniJavaParser.IntLitExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#methodDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDecl(@NotNull MiniJavaParser.MethodDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negateExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegateExpr(@NotNull MiniJavaParser.NegateExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code falseLitExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseLitExpr(@NotNull MiniJavaParser.FalseLitExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrAccessExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrAccessExpr(@NotNull MiniJavaParser.ArrAccessExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lessThanExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessThanExpr(@NotNull MiniJavaParser.LessThanExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrLengthExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrLengthExpr(@NotNull MiniJavaParser.ArrLengthExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStat(@NotNull MiniJavaParser.AssignStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#goal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGoal(@NotNull MiniJavaParser.GoalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStat(@NotNull MiniJavaParser.WhileStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(@NotNull MiniJavaParser.BoolTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStat(@NotNull MiniJavaParser.IfStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisExpr(@NotNull MiniJavaParser.ThisExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newObjExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewObjExpr(@NotNull MiniJavaParser.NewObjExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodCallExpr}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCallExpr(@NotNull MiniJavaParser.MethodCallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code printlnStat}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintlnStat(@NotNull MiniJavaParser.PrintlnStatContext ctx);
}