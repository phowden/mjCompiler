import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.HashSet;

/** Type checker that verifies that types are correc throughout the program. Visits
  ANTLR generated AST nodes to check each expression **/
public class TypeCheckingVisitor extends MiniJavaBaseVisitor<ValueType> {

    private MainClass mainClass;
    private MJClass currentMJClass;
    private Method currentMethod;

    //Keep track of scope depth in if/while
    private int ifLevel;
    private int whileLevel;

    //Keep track of initialized symbols and potentially initialized symbols
    private Set<Symbol> initializedSymbols;
    private Set<Symbol> ifElseInitialized;
    private Set<Symbol> whileInitialized;

    boolean ignoreErrors;

    public TypeCheckingVisitor(MainClass mc) {
        this(mc,null,null,false);
    }

    public TypeCheckingVisitor(MainClass mc, MJClass mjc, Method m) {
        this(mc,mjc,m,false);
    }

    public TypeCheckingVisitor(MainClass mc, MJClass mjc, Method m, boolean i) {
        this.mainClass = mc;
        this.currentMJClass = mjc;
        this.currentMethod = m;

        this.ifLevel = 0;
        this.whileLevel = 0;

        this.initializedSymbols = new HashSet<Symbol>();
        this.ifElseInitialized = new HashSet<Symbol>();

        this.ignoreErrors = i;
    }

    public MainClass getMainClass() {
        return this.mainClass;
    }

    /** Visit the main class declaration **/
    @Override public ValueType visitMainClass(@NotNull MiniJavaParser.MainClassContext ctx) {
        visitChildren(ctx);
        mainClass.setStatement(new StatementBuildingVisitor(mainClass,null,null).visit(ctx.statement()));
        return null;
    }

    /** Visit class declarations **/
    @Override public ValueType visitClassDecl(@NotNull MiniJavaParser.ClassDeclContext ctx) {
        String className = ctx.IDENTIFIER().get(0).getText();
        currentMJClass = mainClass.getClass(className);
        visitChildren(ctx);
        return null;
    }

    /** Visit method declarations **/
    @Override public ValueType visitMethodDecl(@NotNull MiniJavaParser.MethodDeclContext ctx) {
        String methodName = ctx.IDENTIFIER(0).getText();
        currentMethod = currentMJClass.getMethod(methodName,false);
        initializedSymbols = new HashSet<Symbol>();

        //Check for overloading
        if (currentMJClass.getSuperClass() != null) {
            Method superMethod = currentMJClass.getSuperClass().getMethod(methodName,true);
            if (superMethod != null && MJUtils.isOverloading(currentMethod,superMethod))  {
                ErrorReporter.reportOverloading(ctx,methodName,superMethod.getName());
                currentMJClass.removeMethod(currentMethod);
            }
        }
        //Type check statements in the method
        visitChildren(ctx);
        //Type check the return type
        ValueType returnType = visit(ctx.expression());
        if (!currentMethod.getReturnType().equals(returnType)) {
            ErrorReporter.reportReturnTypeMismatch(ctx,currentMethod.getReturnType(),returnType);
        }
        //Build method statements
        StatementBuildingVisitor builder = new StatementBuildingVisitor(mainClass,currentMJClass,currentMethod);
        for (MiniJavaParser.StatementContext stCtx : ctx.statement()) {
            currentMethod.addStatement(builder.visit(stCtx));
        }
        //Visit and build return expression
        ExpressionBuildingVisitor expBuilder = new ExpressionBuildingVisitor(mainClass,currentMJClass,currentMethod);
        currentMethod.setReturnExpression(expBuilder.visit(ctx.expression()));
        return null;
    }

    /** Visit if/else statements **/
    @Override public ValueType visitIfStat(@NotNull MiniJavaParser.IfStatContext ctx) {
        final int IF = 0;
        final int ELSE = 1;

        //Type check the predicate
        ValueType expressionType = visit(ctx.expression());
        if(!expressionType.equals(ValueType.BOOL_TYPE)) {
            ErrorReporter.reportTypeMismatch(ctx,ValueType.BOOL_TYPE,expressionType);
        }
        //Scope into if
        ++ifLevel;
        //Store the original set
        Set<Symbol> original = ifElseInitialized;
        //New set for this if block
        Set<Symbol> thisIfSet = new HashSet<>();
        ifElseInitialized = thisIfSet;
        //Visit the if statement
        visit(ctx.statement(IF));
        //New set for the else block
        Set<Symbol> thisElseSet = new HashSet<>();
        ifElseInitialized = thisElseSet;
        //Visit the else block
        visit(ctx.statement(ELSE));
        //Add symbols that were initialized in both blocks to initialized set
        for (Symbol sym : thisIfSet) {
            if (thisElseSet.contains(sym)) {
                original.add(sym);
            }
        }
        //Store this extended set
        ifElseInitialized = original;
        //Scope out of if
        --ifLevel;
        //If at base level, everything initialized in if/else are initialized
        if (ifLevel == 0) {
            for (Symbol sym : ifElseInitialized) {
                initializedSymbols.add(sym);
                if (whileLevel > 0) {
                    whileInitialized.add(sym);
                }
            }
            ifElseInitialized = new HashSet<>();
        }
        return null;
    }

    /** Type check while statements **/
    @Override public ValueType visitWhileStat(@NotNull MiniJavaParser.WhileStatContext ctx) {
        //Type check the predicate
        ValueType expressionType = visit(ctx.expression());
        if(!expressionType.equals(ValueType.BOOL_TYPE)) {
            ErrorReporter.reportTypeMismatch(ctx,ValueType.BOOL_TYPE,expressionType);
        }
        //Scope into while
        whileLevel++;
        whileInitialized = new HashSet<Symbol>();
        //Visit the statement
        visit(ctx.statement());
        //Scope out of while
        whileLevel--;
        whileInitialized = new HashSet<Symbol>();
        return null;
    }

    /** Type check print statements **/
    @Override public ValueType visitPrintlnStat(@NotNull MiniJavaParser.PrintlnStatContext ctx) {
        ValueType expressionType = visit(ctx.expression());
        //Print statements only print ints
        if(!expressionType.equals(ValueType.INT_TYPE)) {
            ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,expressionType);
        }
        return null;
    }

    /** Type check assignment statements **/
    @Override public ValueType visitAssignStat(@NotNull MiniJavaParser.AssignStatContext ctx) {
        String idName = ctx.IDENTIFIER().getText();
        Symbol idSym = MJUtils.findVariable(currentMJClass,currentMethod,idName);
        //Check symbol not found
        if (idSym == null) {
            ErrorReporter.reportSymbolNotFound(ctx,idName);
        } else {
            //Check that the types match
            ValueType expressionType = visit(ctx.expression());
            if (!idSym.getType().equals(expressionType)) {
                ErrorReporter.reportTypeMismatch(ctx,idSym.getType(),expressionType);
            }
            //Check that the variable was initialized
            if (idSym.isLocal()) {
                if (ifLevel > 0) {
                    ifElseInitialized.add(idSym);
                    if (whileLevel > 0) {
                        whileInitialized.add(idSym);
                    } 
                } else {
                    initializedSymbols.add(idSym);
                    if (whileLevel > 0) {
                        whileInitialized.add(idSym);
                    }
                }
            }
        }
        return null;
    }

    /** Type check array assignement **/
    @Override public ValueType visitArrAssignStat(@NotNull MiniJavaParser.ArrAssignStatContext ctx) {
        String idName = ctx.IDENTIFIER().getText();
        Symbol idSym = MJUtils.findVariable(currentMJClass,currentMethod,idName);
        //Check symbol not found 
        if (idSym == null) {
            ErrorReporter.reportSymbolNotFound(ctx,idName);
        } else {
            //Type check index and value 
            ValueType indexExpressionType = visit(ctx.expression(0));
            ValueType valueExpressionType = visit(ctx.expression(1));

            if (!idSym.getType().equals(ValueType.INT_ARR_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_ARR_TYPE,idSym.getType());
            }
            if (!indexExpressionType.equals(ValueType.INT_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,indexExpressionType);
            }
            if (!valueExpressionType.equals(ValueType.INT_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,valueExpressionType);
            }

            //Check initialized before use
            if (idSym.isLocal()) {
                if (ifLevel > 0) {
                    ifElseInitialized.add(idSym);
                } else if (whileLevel > 0) {
                    whileInitialized.add(idSym);
                } else {
                    initializedSymbols.add(idSym);
                }
            }
        }
        return ValueType.NULL_TYPE;
    }

    /** Type check expressions in parentheses **/
    @Override public ValueType visitParenedExpr(@NotNull MiniJavaParser.ParenedExprContext ctx) {
        return visit(ctx.expression());
    }

    /** Type check multiplication expressions **/
    @Override public ValueType visitMultExpr(@NotNull MiniJavaParser.MultExprContext ctx) {
        ValueType lExprType = visit(ctx.expression(0));
        ValueType rExprType = visit(ctx.expression(1));

        if (!ignoreErrors) {
            //Type check both sides
            if (!lExprType.equals(ValueType.INT_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,lExprType);
            } else if (!rExprType.equals(ValueType.INT_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,rExprType);
            }
        }

        return ValueType.INT_TYPE;
    }

    /** Type check && expressions **/
    @Override public ValueType visitAndExpr(@NotNull MiniJavaParser.AndExprContext ctx) {
        ValueType lExprType = visit(ctx.expression(0));
        ValueType rExprType = visit(ctx.expression(1));

        if (!ignoreErrors) {
            //Type check both sides
            if (!lExprType.equals(ValueType.BOOL_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.BOOL_TYPE,lExprType);
            } else if (!rExprType.equals(ValueType.BOOL_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.BOOL_TYPE,rExprType);
            }
        }

        return ValueType.BOOL_TYPE;
    }

    /** Type check new array expressions **/
    @Override public ValueType visitNewArrExpr(@NotNull MiniJavaParser.NewArrExprContext ctx) {
        ValueType exprType = visit(ctx.expression());

        if (!ignoreErrors) {
            if (!exprType.equals(ValueType.INT_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,exprType);
            }
        }

        return ValueType.INT_ARR_TYPE;
    }

    /** Type check addition expression **/
    @Override public ValueType visitPlusExpr(@NotNull MiniJavaParser.PlusExprContext ctx) {
        ValueType lExprType = visit(ctx.expression(0));
        ValueType rExprType = visit(ctx.expression(1));

        if (!ignoreErrors) {
            //Type check both sides
            if (!lExprType.equals(ValueType.INT_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,lExprType);
            } else if (!rExprType.equals(ValueType.INT_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,rExprType);
            }
        }

        return ValueType.INT_TYPE;
    }
    
    /** Type check subtraction expression **/
    @Override public ValueType visitMinusExpr(@NotNull MiniJavaParser.MinusExprContext ctx) {
        ValueType lExprType = visit(ctx.expression(0));
        ValueType rExprType = visit(ctx.expression(1));

        if (!ignoreErrors) {
            //Type check both sides
            if (!lExprType.equals(ValueType.INT_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,lExprType);
            } else if (!rExprType.equals(ValueType.INT_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,rExprType);
            }
        }

        return ValueType.INT_TYPE;
    }

    /** Type check boolean not expression **/
    @Override public ValueType visitNotExpr(@NotNull MiniJavaParser.NotExprContext ctx) {
        ValueType exprType = visit(ctx.expression());

        if (!ignoreErrors) {
            if (!exprType.equals(ValueType.BOOL_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.BOOL_TYPE,exprType);
            }
        }

        return ValueType.BOOL_TYPE;
    }

    /** Type check arithmetic negation expression **/
    @Override public ValueType visitNegExpr(@NotNull MiniJavaParser.NegExprContext ctx) {
        ValueType exprType = visit(ctx.expression());

        if (!ignoreErrors) {
            if (!exprType.equals(ValueType.INT_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,exprType);
            }
        }

        return ValueType.INT_TYPE;
    }

    /** Type check array access expressions **/
    @Override public ValueType visitArrAccessExpr(@NotNull MiniJavaParser.ArrAccessExprContext ctx) {
        ValueType arrExpr = visit(ctx.expression(0));
        ValueType indExpr = visit(ctx.expression(1));

        if (!ignoreErrors) {
            //Type check index and value
            if (!arrExpr.equals(ValueType.INT_ARR_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_ARR_TYPE,arrExpr);
            }
            if (!indExpr.equals(ValueType.INT_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,indExpr);
            }
        }

        return ValueType.INT_TYPE;
    }

    /** Type check less than expression **/
    @Override public ValueType visitLessThanExpr(@NotNull MiniJavaParser.LessThanExprContext ctx) {
        ValueType lExprType = visit(ctx.expression(0));
        ValueType rExprType = visit(ctx.expression(1));

        if (!ignoreErrors) {
            //Type check both sides
            if (!lExprType.equals(ValueType.INT_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,lExprType);
            } else if (!rExprType.equals(ValueType.INT_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.INT_TYPE,rExprType);
            }
        }

        return ValueType.BOOL_TYPE;
    }

    /** Type check array length expressions **/
    @Override public ValueType visitArrLengthExpr(@NotNull MiniJavaParser.ArrLengthExprContext ctx) {
        ValueType exprType = visit(ctx.expression());

        if (!ignoreErrors) {
            if (!exprType.equals(ValueType.INT_ARR_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.BOOL_TYPE,exprType);
            }
        }

        return ValueType.INT_TYPE;
    }

    /** Type check method calls **/
    @Override public ValueType visitMethodCallExpr(@NotNull MiniJavaParser.MethodCallExprContext ctx) {
        ValueType objType = visit(ctx.expression(0));
        String methodName = ctx.IDENTIFIER().getText();
        List<ValueType> paramTypes = new ArrayList<>();

        //Get the types of all the parameters
        for (int i = 1; i < ctx.expression().size(); ++i) {
            paramTypes.add(visit(ctx.expression(i)));
        }

        //Get the object on which to call the method
        MJClass objClass = mainClass.getClass(objType.getType());

        if (objClass == null) {
            ErrorReporter.reportSymbolNotFound(ctx,objType.getType());
            return ValueType.NULL_TYPE;
        }

        Method method = objClass.getMethod(methodName,true);

        if (!ignoreErrors) {
            if (method == null) {
                ErrorReporter.reportSymbolNotFound(ctx,methodName);
                return ValueType.NULL_TYPE;
            }

            //Check the correct number of parameters
            if (paramTypes.size() != method.getParams().size()) {
                ErrorReporter.reportError(ctx,"Error on line %d: argument list of size %s, expected size %s",paramTypes.size()+"",method.getParams().size()+"");
                return ValueType.NULL_TYPE;
            }

            //Type check the parameters
            List<Symbol> methodParams = method.getParams();
            for (int i = 0; i < paramTypes.size(); ++i) {
                ValueType foundType = paramTypes.get(i);
                ValueType methType =  methodParams.get(i).getType();
                if (!foundType.equals(methType)) {
                    ErrorReporter.reportTypeMismatch(ctx,methType,foundType);
                    return ValueType.NULL_TYPE;
                }
            }
        }

        return method.getReturnType();
    }

    /** Type check ternary expression **/
    @Override public ValueType visitTernaryExpr(@NotNull MiniJavaParser.TernaryExprContext ctx)  {
        ValueType predicate = visit(ctx.expression(0));
        ValueType expr2 = visit(ctx.expression(1));
        ValueType expr3 = visit(ctx.expression(2));

        if (!ignoreErrors) {
            //Type check predicate
            if (!predicate.equals(ValueType.BOOL_TYPE)) {
                ErrorReporter.reportTypeMismatch(ctx,ValueType.BOOL_TYPE,expr1);
            }

            //Type check result expressions
            if (!expr2.equals(expr3)) {
                ErrorReporter.reportTypeMismatch(ctx,expr3,expr2);
            }
        }

        return expr2;
    }

    /* ID Expressions check that the ID exists, then pass through their type
       Identifier expression
       new Object expression
     */
    @Override public ValueType visitIdExpr(@NotNull MiniJavaParser.IdExprContext ctx) {
        String idName = ctx.IDENTIFIER().getText();
        Symbol idSym = MJUtils.findVariable(currentMJClass,currentMethod,idName);
        //Check that symbol exists
        if (idSym == null) {
            ErrorReporter.reportSymbolNotFound(ctx,idName);
            return ValueType.NULL_TYPE;
        } else {
            if (!ignoreErrors) {
                //Check initialization
                if (idSym.isLocal() && !currentMethod.getParams().contains(idSym)) {
                    if (ifLevel > 0) {
                        if (!initializedSymbols.contains(idSym) && !ifElseInitialized.contains(idSym)) {
                            ErrorReporter.reportUsedBeforeInitialized(ctx,idName);
                        }
                    } else if (whileLevel > 0) {
                        if (!initializedSymbols.contains(idSym) && !whileInitialized.contains(idSym)) {
                            ErrorReporter.reportUsedBeforeInitialized(ctx,idName);
                        }
                    } else {
                        if (!initializedSymbols.contains(idSym)) {
                            ErrorReporter.reportUsedBeforeInitialized(ctx,idName);
                        }
                    }
                }
            }
            return idSym.getType();
        }
    }

    @Override public ValueType visitNewObjExpr(@NotNull MiniJavaParser.NewObjExprContext ctx) {
        String objName = ctx.IDENTIFIER().getText();
        MJClass objClass = mainClass.getClass(objName);
        if (objClass == null) {
            ErrorReporter.reportSymbolNotFound(ctx,objName);
            return ValueType.NULL_TYPE;
        } else {
            return objClass.getType();
        }
    }


    /* Pass through visit methods just return the appropriate type
       True/False Literals
       Integer literals
       "this" expression
     */
    @Override public ValueType visitTrueLitExpr(@NotNull MiniJavaParser.TrueLitExprContext ctx) {
        return ValueType.BOOL_TYPE;
    }

    @Override public ValueType visitFalseLitExpr(@NotNull MiniJavaParser.FalseLitExprContext ctx) {
        return ValueType.BOOL_TYPE;
    }

    @Override public ValueType visitIntLitExpr(@NotNull MiniJavaParser.IntLitExprContext ctx) {
        return ValueType.INT_TYPE;
    }

    @Override public ValueType visitThisExpr(@NotNull MiniJavaParser.ThisExprContext ctx) {
        return currentMJClass.getType();
    }
}


