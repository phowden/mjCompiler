import java.util.List;

public abstract class Expression implements Jasminable {
}

class AndExpression extends Expression {
    Expression lhs, rhs;

    public AndExpression(Expression l, Expression r) {
        this.lhs = l;
        this.rhs = r;
    }
    public String jasminify() {
        //Push LHS Expression and check
        String instruct = lhs.jasminify();
        instruct += "\nifeq " + LabelFactory.getNextAndLabel();

        //Push RHS Expression and check
        instruct += rhs.jasminify();
        instruct += "\nifeq " + LabelFactory.getAndLabel();

        //Neither are false, result is true
        instruct += "\niconst_1";
        instruct += "\ngoto " + LabelFactory.getEndAndLabel();

        //If either are false, result is false
        instruct += "\n" + LabelFactory.getAndLabel() + ":";
        instruct += "\niconst_0";

        instruct += "\n" + LabelFactory.getEndAndLabel() + ":";
        
        return instruct;
    }
}

class LessThanExpression extends Expression {
    Expression lhs, rhs;

    public LessThanExpression(Expression l, Expression r) {
        this.lhs = l;
        this.rhs = r;
    }
    public String jasminify() {
        //Push LHS and RHS
        String instruct = lhs.jasminify();
        instruct += "\n" + rhs.jasminify();

        //If less than, branch
        instruct += "\nif_icmplt " + LabelFactory.getNextLtLabel();
        //Else push false
        instruct += "\niconst_0";
        instruct += "\ngoto " + LabelFactory.getEndLtLabel();

        //LHS < RHS
        instruct += "\n" + LabelFactory.getLtLabel() + ":";
        instruct += "\niconst_1";

        instruct += "\n" + LabelFactory.getEndLtLabel() + ":";
        
        return instruct;
    }
}

class PlusMinusExpression extends Expression {
    Expression lhs, rhs;
    String plusMinusInstruct;

    public PlusMinusExpression(Expression l, Expression r, boolean plus) {
        this.lhs = l;
        this.rhs = r;
        this.plusMinusInstruct = (plus) ? "iadd" : "isub";
    }
    public String jasminify() {
        String instruct = lhs.jasminify();
        instruct += "\n";
        instruct += rhs.jasminify();
        instruct += "\n" + plusMinusInstruct;
        return instruct;
    }
}

class MultiplyExpression extends Expression {
    Expression lhs, rhs;

    public MultiplyExpression(Expression l, Expression r) {
        this.lhs = l;
        this.rhs = r;
    }
    public String jasminify() {
        String instruct = lhs.jasminify();
        instruct += "\n";
        instruct += rhs.jasminify();
        instruct += "\nimul";
        return instruct;
    }
}

class ArrayAccessExpression extends Expression {
    Expression array, index;

    public ArrayAccessExpression(Expression a, Expression i) {
        this.array = a;
        this.index = i;
    }
    public String jasminify() {
        String instruct = array.jasminify();
        instruct += "\n";
        instruct += index.jasminify();
        instruct += "\narraylength";
        return instruct;
    }
}

class ArrayLengthExpression extends Expression {
    Expression array;

    public ArrayLengthExpression(Expression a) {
        this.array = a;
    }
    public String jasminify() {
        String instruct = array.jasminify();
        instruct += "\narraylength";
        return instruct;
    }
}

class MethodCallExpression extends Expression {
    Expression object;
    Method method;
    List<Expression> parameters;

    public MethodCallExpression(Expression o, Method m, List<Expression> p) {
        this.object = o;
        this.method= m;
        this.parameters = p;
    }
    public String jasminify() {
        String instruct = object.jasminify();
        instruct += "\n";
        for (Expression e : parameters) {
            instruct += "\n" + e.jasminify();
        }
        instruct += "\ninvokevirtual " + method.toJasmin();
        return instruct;
    }
}

class TernaryExpression extends Expression {
    Expression predicate, ifTrue, ifFalse;

    public TernaryExpression(Expression p, Expression t, Expression f) {
        this.predicate = p;
        this.ifTrue = t;
        this.ifFalse = f;
    }

    public String jasminify() {
        String instruct = predicate.jasminify();
        instruct += "\nifeq " + LabelFactory.getNextTernLabel();
        instruct += "\n" + ifTrue.jasminify();
        instruct += "\ngoto " + LabelFactory.getEndTernLabel();
        
        instruct += "\n" + LabelFactory.getTernLabel() + ":";
        instruct += "\n" + ifFalse.jasminify();
        instruct += "\n" + LabelFactory.getEndTernLabel() + ":";

        return instruct;
    }
}

class NewArrayExpression extends Expression {
    Expression size;

    public NewArrayExpression(Expression s) {
        this.size = s;
    }
    public String jasminify() {
        String instruct = size.jasminify();
        instruct += "\nnewarray int";
        return instruct;
    }
}

class NegateExpression extends Expression {
    Expression expression;

    public NegateExpression(Expression e) {
        this.expression = e;
    }
    public String jasminify() {
        String instruct = expression.jasminify();
        instruct += "\nineg";
        return instruct;
    }
}

class ParenthesesExpression extends Expression {
    Expression expression;

    public ParenthesesExpression(Expression e) {
        this.expression = e;
    }
    public String jasminify() {
        return expression.jasminify();
    }
}

class IdentifierExpression extends Expression {
    Symbol identifier;

    public IdentifierExpression(Symbol i) {
        this.identifier = i;
    }
    public String jasminify() {
        if (identifier.isField()) {
            return jasminifyField();
        } else {
            return jasminifyLocal();
        }
    }
    private String jasminifyField() {
        String className = identifier.getClassBelongsTo().getName();
        String identifierName = identifier.getName();
        String type = identifier.getType().getType();

        String instruct = "aload_0\n";
        instruct += "getfield " + className + "/" + identifierName + " " + type;
        return instruct;
    }
    private String jasminifyLocal() {
        int localIndex = identifier.getMethodBelongsTo().indexOfVariable(identifier.getName());
        String typeOf = identifier.getType().getType();
        if (typeOf.equals(ValueType.INT_TYPE)) {
            return "iload "+localIndex;
        } else if (typeOf.equals(ValueType.BOOL_TYPE)) {
            return "iload "+localIndex;
        } else {
            return "aload "+localIndex;
        }
    }
}

class NewObjectExpression extends Expression {
    Symbol object;

    public NewObjectExpression(Symbol o) {
        this.object = o;
    }
    public String jasminify() {
        String instruct = "new " + object.getType().getType() + "\n";
        instruct += "dup\n";
        instruct += "invokespecial " + object.getType().getType() + "/<init>()V";
        return instruct;
    }
}

class ThisExpression extends Expression {
    public String jasminify() {
        return "aload_0";
    }
}

class IntegerLiteralExpression extends Expression {
    int value;

    public IntegerLiteralExpression(int v) {
        this.value = v;
    }
    public String jasminify() {
        return "sipush "+value;
    }
}

class BooleanLiteralExpression extends Expression {
    boolean value;

    public BooleanLiteralExpression(boolean v) {
        this.value = v;
    }
    public String jasminify() {
        return "iconst_"+ (value ? 1 : 0);
    }
}
