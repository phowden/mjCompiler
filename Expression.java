import java.util.List;

public abstract class Expression implements Jasminable {

    public static class AndExpression extends Expression {
        Expression lhs, rhs;

        public AndExpression(Expression l, Expression r) {
            this.lhs = l;
            this.rhs = r;
        }
        public String jasminify() {
            String andLabel = LabelFactory.getNextAndLabel();
            String endAndLabel = LabelFactory.getEndAndLabel();

            //Push LHS Expression and check
            String instruct = lhs.jasminify();
            instruct += "\nifeq " + andLabel;

            //Push RHS Expression and check
            instruct += "\n" + rhs.jasminify();
            instruct += "\nifeq " + andLabel;

            //Neither are false, result is true
            instruct += "\niconst_1";
            instruct += "\ngoto " + endAndLabel;

            //If either are false, result is false
            instruct += "\n" + andLabel + ":";
            instruct += "\niconst_0";

            instruct += "\n" + endAndLabel + ":";

            return instruct;
        }
    }

    public static class LessThanExpression extends Expression {
        Expression lhs, rhs;

        public LessThanExpression(Expression l, Expression r) {
            this.lhs = l;
            this.rhs = r;
        }
        public String jasminify() {
            String ltLabel = LabelFactory.getNextLtLabel();
            String endLtLabel = LabelFactory.getEndLtLabel();

            //Push LHS and RHS
            String instruct = lhs.jasminify();
            instruct += "\n" + rhs.jasminify();

            //If less than, branch
            instruct += "\nif_icmplt " + ltLabel;
            //Else push false
            instruct += "\niconst_0";
            instruct += "\ngoto " + endLtLabel;

            //LHS < RHS
            instruct += "\n" + ltLabel+ ":";
            instruct += "\niconst_1";

            instruct += "\n" + endLtLabel + ":";

            return instruct;
        }
    }

    public static class PlusMinusExpression extends Expression {
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

    public static class MultiplyExpression extends Expression {
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

    public static class ArrayAccessExpression extends Expression {
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

    public static class ArrayLengthExpression extends Expression {
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

    public static class MethodCallExpression extends Expression {
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
            for (Expression e : parameters) {
                instruct += "\n" + e.jasminify();
            }
            instruct += "\ninvokevirtual " + method.toJasmin();
            return instruct;
        }
    }

    public static class TernaryExpression extends Expression {
        Expression predicate, ifTrue, ifFalse;

        public TernaryExpression(Expression p, Expression t, Expression f) {
            this.predicate = p;
            this.ifTrue = t;
            this.ifFalse = f;
        }

        public String jasminify() {
            String ternLabel = LabelFactory.getNextTernLabel();
            String endTernLabel = LabelFactory.getEndTernLabel();

            String instruct = predicate.jasminify();
            instruct += "\nifeq " + ternLabel;
            instruct += "\n" + ifTrue.jasminify();
            instruct += "\ngoto " + endTernLabel;

            instruct += "\n" + ternLabel+ ":";
            instruct += "\n" + ifFalse.jasminify();
            instruct += "\n" + endTernLabel + ":";

            return instruct;
        }
    }

    public static class NewArrayExpression extends Expression {
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

    public static class NegateExpression extends Expression {
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

    public static class ParenthesesExpression extends Expression {
        Expression expression;

        public ParenthesesExpression(Expression e) {
            this.expression = e;
        }
        public String jasminify() {
            return expression.jasminify();
        }
    }

    public static class IdentifierExpression extends Expression {
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
            String type = MJUtils.typeToJasminType(identifier.getType());

            String instruct = "aload_0\n";
            instruct += "getfield " + className + "/" + identifierName + " " + type;
            return instruct;
        }
        private String jasminifyLocal() {
            int localIndex = identifier.getMethodBelongsTo().indexOfVariable(identifier.getName());
            ValueType typeOf = identifier.getType();
            if (typeOf.equals(ValueType.INT_TYPE)) {
                return "iload "+localIndex;
            } else if (typeOf.equals(ValueType.BOOL_TYPE)) {
                return "iload "+localIndex;
            } else {
                return "aload "+localIndex;
            }
        }
    }

    public static class NewObjectExpression extends Expression {
        MJClass objectClass;

        public NewObjectExpression(MJClass o) {
            this.objectClass = o;
        }
        public String jasminify() {
            String instruct = "new " + objectClass.getType().getType() + "\n";
            instruct += "dup\n";
            instruct += "invokespecial " + objectClass.getType().getType() + "/<init>()V";
            return instruct;
        }
    }

    public static class ThisExpression extends Expression {
        public String jasminify() {
            return "aload_0";
        }
    }

    public static class IntegerLiteralExpression extends Expression {
        int value;

        public IntegerLiteralExpression(int v) {
            this.value = v;
        }
        public String jasminify() {
            return "ldc "+value;
        }
    }

    public static class BooleanLiteralExpression extends Expression {
        boolean value;

        public BooleanLiteralExpression(boolean v) {
            this.value = v;
        }
        public String jasminify() {
            return "iconst_"+ (value ? 1 : 0);
        }
    }
}
