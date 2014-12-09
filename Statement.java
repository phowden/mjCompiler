import java.util.List;

public abstract class Statement implements Jasminable {

    public static class ScopedStatement extends Statement {
        List<Statement> statements;

        public ScopedStatement(List<Statement> s) {
            this.statements = s;
        }

        public String jasminify() {
            String instructions = "";
            for (Statement statement : statements) {
                instructions += statement.jasminify();
            }
            return instructions;
        }
    }

    public static class IfStatement extends Statement {
        Expression predicate;
        Statement ifTrue, ifFalse;

        public IfStatement(Expression p, Statement t, Statement f) {
            this.predicate = p;
            this.ifTrue = t;
            this.ifFalse = f;
        }

        public String jasminify() {
            //Check the predicate
            String instruct = predicate.jasminify();
            instruct += "\nifeq " + LabelFactory.getNextIfLabel();
            //On true
            instruct += "\n" + ifTrue.jasminify();
            instruct += "\ngoto " + LabelFactory.getEndIfLabel();
            //On false
            instruct += "\n" + LabelFactory.getIfLabel() + ":";
            instruct += "\n" + ifFalse.jasminify();
            instruct += "\n" + LabelFactory.getEndIfLabel() + ":";

            return instruct;
        }
    }

    public static class WhileStatement extends Statement {
        Expression predicate;
        Statement body;

        public WhileStatement(Expression p, Statement b) {
            this.predicate = p;
            this.body = b;
        }

        public String jasminify() {
            //Set where to loop up to
            String instruct = LabelFactory.getNextWhileLabel();
            //Check predicate
            instruct += "\n" + predicate.jasminify();
            instruct += "\nifeq " + LabelFactory.getEndWhileLabel();
            //Body
            instruct += "\n" + body.jasminify();
            //Loop back to predicate
            instruct += "\ngoto " + LabelFactory.getWhileLabel();
            instruct += "\n" + LabelFactory.getEndWhileLabel();

            return instruct;
        }
    }

    public static class PrintlnStatement extends Statement {
        Expression expression;

        public PrintlnStatement(Expression e) {
            this.expression = e;
        }

        public String jasminify() {
            String instruct = "getstatic java/lang/System/out/ Ljava/io/PrintStream;";
            instruct += "\n" + expression.jasminify();
            instruct += "\ninvokevirtual java/io/PrintStream/println(I)V";
            return instruct;
        }
    }

    public static class AssignmentStatement extends Statement {
        Symbol identifier;
        Expression value;

        public AssignmentStatement(Symbol i, Expression v) {
            this.identifier = i;
            this.value = v;
        }

        public String jasminify() {
            if (identifier.isField()) {
                return jasminifyFieldAssign();
            } else {
                return jasminifyLocalAssign();
            }
        }

        private String jasminifyFieldAssign() {
            String instruct = value.jasminify();
            String type = identifier.getType().getType();
            String className = identifier.getClassBelongsTo().getName();

            instruct += "\naload_0";
            instruct += "\nputfield " + className + "/" + identifier.getName() + " " + type;
            return instruct;
        }

        private String jasminifyLocalAssign() {
            String instruct = value.jasminify();
            int localIndex = identifier.getMethodBelongsTo().indexOfVariable(identifier.getName());
            String type = identifier.getType().getType();
            if (type.equals(ValueType.INT_TYPE)) {
                instruct += "\nistore " + localIndex;
            } else if (type.equals(ValueType.BOOL_TYPE)) {
                instruct += "\nistore " + localIndex;
            } else {
                instruct += "astore " + localIndex;
            }
            return instruct;
        }
    }

    public static class ArrayAssignmentStatement extends Statement {
        Symbol identifier;
        Expression index, value;

        public ArrayAssignmentStatement(Symbol i, Expression x, Expression v) {
            this.identifier = i;
            this.index = x;
            this.value = v;
        }

        public String jasminify() {
            String instruct = "";
            if (identifier.isField()) {
                String className = identifier.getClassBelongsTo().getName();
                String identifierName = identifier.getName();
                String type = identifier.getType().getType();

                instruct += "\naload_0";
                instruct += "getfield " + className + "/" + identifierName + " [I";
            } else {
                int localIndex = identifier.getMethodBelongsTo().indexOfVariable(identifier.getName());
                instruct += "aload " + localIndex;
            }

            instruct += "\n" + index.jasminify();
            instruct += "\n" + value.jasminify();
            instruct += "\niastore";
            return instruct;
        }
    }
}
