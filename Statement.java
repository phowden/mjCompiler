import java.util.List;

/** Representation of Statement productions from the MiniJava grammar **/
public abstract class Statement implements Jasminable {

    /** Representation of scoped statements **/
    public static class ScopedStatement extends Statement {
        List<Statement> statements;

        public ScopedStatement(List<Statement> s) {
            this.statements = s;
        }

        public String jasminify() {
            String instructions = "";
            for (Statement statement : statements) {
                instructions += "\n"+statement.jasminify();
            }
            return instructions;
        }
    }

    /** Representation of if/else statements **/
    public static class IfStatement extends Statement {
        Expression predicate;
        Statement ifTrue, ifFalse;

        public IfStatement(Expression p, Statement t, Statement f) {
            this.predicate = p;
            this.ifTrue = t;
            this.ifFalse = f;
        }

        public String jasminify() {
            String ifLabel = LabelFactory.getNextIfLabel();
            String endIfLabel = LabelFactory.getEndIfLabel();
            //Check the predicate
            String instruct = predicate.jasminify();
            instruct += "\nifeq " + ifLabel;
            //On true
            instruct += "\n" + ifTrue.jasminify();
            instruct += "\ngoto " + endIfLabel;
            //On false
            instruct += "\n" + ifLabel + ":";
            instruct += "\n" + ifFalse.jasminify();
            instruct += "\n" + endIfLabel + ":";

            return instruct;
        }
    }

    /** Representation of while statements **/
    public static class WhileStatement extends Statement {
        Expression predicate;
        Statement body;

        public WhileStatement(Expression p, Statement b) {
            this.predicate = p;
            this.body = b;
        }

        public String jasminify() {
            String whileLabel = LabelFactory.getNextWhileLabel();
            String endWhileLabel = LabelFactory.getEndWhileLabel();
            //Set where to loop up to
            String instruct = whileLabel + ":";
            //Check predicate
            instruct += "\n" + predicate.jasminify();
            instruct += "\nifeq " + endWhileLabel;
            //Body
            instruct += "\n" + body.jasminify();
            //Loop back to predicate
            instruct += "\ngoto " + whileLabel;
            instruct += "\n" + endWhileLabel + ":";

            return instruct;
        }
    }

    /** Representation of print statements **/
    public static class PrintlnStatement extends Statement {
        Expression expression;

        public PrintlnStatement(Expression e) {
            this.expression = e;
        }

        public String jasminify() {
            String instruct = "getstatic java/lang/System/out Ljava/io/PrintStream;";
            instruct += "\n" + expression.jasminify();
            instruct += "\ninvokevirtual java/io/PrintStream/println(I)V";
            return instruct;
        }
    }

    /** Representation of assignment statements **/
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

        /** Turns representation of a field assignment into jasmin jvm instructions **/
        private String jasminifyFieldAssign() {
            String type = MJUtils.typeToJasminType(identifier.getType());
            String className = identifier.getClassBelongsTo().getName();

            String instruct = "aload_0";
            instruct += "\n" + value.jasminify();
            instruct += "\nputfield " + className + "/" + identifier.getName() + " " + type;
            return instruct;
        }

        /** Turns representation of a local variable assignment into jasmin jvm instructions **/
        private String jasminifyLocalAssign() {
            String instruct = value.jasminify();
            int localIndex = identifier.getMethodBelongsTo().indexOfVariable(identifier.getName());
            ValueType type = identifier.getType();
            if (type.equals(ValueType.INT_TYPE)) {
                instruct += "\nistore " + localIndex;
            } else if (type.equals(ValueType.BOOL_TYPE)) {
                instruct += "\nistore " + localIndex;
            } else {
                instruct += "\nastore " + localIndex;
            }
            return instruct;
        }
    }

    /** Representation of assignment to array indexes **/
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
            //Get the array reference onto the stack
            if (identifier.isField()) {
                String className = identifier.getClassBelongsTo().getName();
                String identifierName = identifier.getName();
                String type = identifier.getType().getType();

                instruct += "\naload_0";
                instruct += "\ngetfield " + className + "/" + identifierName + " [I";
            } else {
                int localIndex = identifier.getMethodBelongsTo().indexOfVariable(identifier.getName());
                instruct += "\naload " + localIndex;
            }

            //Load the index and value expressions onto the stack
            instruct += "\n" + index.jasminify();
            instruct += "\n" + value.jasminify();
            //Store
            instruct += "\niastore";
            return instruct;
        }
    }
}
