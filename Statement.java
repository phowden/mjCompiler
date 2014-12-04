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
        return null;
    }
}

class AssignmentStatement extends Statement {
    String identifier;
    Expression value;
    
    public String jasminify() {
        return null;
    }
}

class ArrayAssignmentStatement extends Statement {
    String identifier;
    Expression index, value;

    public String jasminify() {
        return null;
    }
}
