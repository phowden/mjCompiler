import java.util.List;

public abstract class Statement implements Jasminable{
}

class ScopedStatement extends Statement {
    List<Statement> statements;

    public String jasminify() {
        String instructions = "";
        for (Statement statement : statements) {
            instructions += statement.jasminify();
        }
        return instructions;
    }
}

class IfStatement extends Statement {
    Expression predicate;
    Statement ifTrue, ifFalse;

    public String jasminify() {
        return null;
    }
}

class WhileStatement extends Statement {
    Expression predicate;
    Statement body;

    public String jasminify() {
        return null;
    }
}

class PrintlnStatement extends Statement {
    Expression expression;

    public String jasminify() {
        String instruct = "getstatic java/lang/System/out/ Ljava/io/PrintStream;";
        instruct += "\n" + expression.jasminify();
        instruct += "\ninvokevirtual java/io/PrintStream/println(I)V";
        return instruct;
    }
}

class AssignmentStatement extends Statement {
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
        if (typeOf.equals(ValueType.INT_TYPE)) {
            instruct += "\nistore " + localIndex;
        } else if (typeOf.equals(ValueType.BOOL_TYPE)) {
            instruct += "\nistore " + localIndex;
        } else {
            instruct += "astore " + localIndex;
        }
        return instruct;
    }
}

class ArrayAssignmentStatement extends Statement {
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
