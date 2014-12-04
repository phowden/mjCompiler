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
        return null;
    }
}

class LessThanExpression extends Expression {
    Expression lhs, rhs;

    public LessThanExpression(Expression l, Expression r) {
        this.lhs = l;
        this.rhs = r;
    }
    public String jasminify() {
        return null;
    }
}

class PlusMinusExpression extends Expression {
    Expression lhs, rhs;

    public PlusMinusExpression(Expression l, Expression r) {
        this.lhs = l;
        this.rhs = r;
    }
    public String jasminify() {
        return null;
    }
}

class MultiplyExpression extends Expression {
    Expression lhs, rhs;

    public MultiplyExpression(Expression l, Expression r) {
        this.lhs = l;
        this.rhs = r;
    }
    public String jasminify() {
        return null;
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
    String methodName;
    List<Expression> parameters;

    public MethodCallExpression(Expression o, String m, List<Expression> p) {
        this.object = o;
        this.methodName = m;
        this.parameters = p;
    }
    public String jasminify() {
        String instruct = object.jasminify();
        instruct += "\n";
        for (Expression e : parameters) {
            instruct += "\n" + e.jasminify();
        }
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
    //TODO IT RIGHT
    public String jasminify() {
        return null;
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
