import java.util.List;
import java.util.ArrayList;

public class Method implements Jasminable {

    private String name;
    private List<Symbol> params;
    private List<Symbol> variables; 
    private ValueType returnType;

    private MJClass classBelongsTo;

    private List<Statement> statements;

    private Expression returnExpression;

    public Method(ValueType r, String n, List<Symbol> p, MJClass c) {
        this.returnType = r;
        this.name = n;
        this.params = p;
        this.classBelongsTo = c;
        this.variables = new ArrayList<Symbol>();
        this.statements = new ArrayList<Statement>();
        addParams();
    }

    public void addVariable(String n, ValueType t) {
        variables.add(new Symbol(n,t,this));
    }

    public void addStatement(Statement stat) {
        statements.add(stat);
    }

    public void setReturnExpression(Expression e) {
        this.returnExpression = e;
    }

    public String getName() {
        return this.name;
    }

    public List<Symbol> getParams() {
        return this.params;
    }

    public List<Symbol> getVariables() {
        return this.variables;
    }

    public Symbol getVariable(String symbolName) {
        for (Symbol sym : variables) {
            if (sym.getName().equals(symbolName)) {
                return sym;
            }
        }

        for (Symbol sym : params) {
            if (sym.getName().equals(symbolName)) {
                return sym;
            }
        }
        return null;
    }

    public int indexOfVariable(String symbolName) {
        return variables.indexOf(getVariable(symbolName));
    }

    public ValueType getReturnType() {
        return this.returnType;
    }

    public MJClass getClassBelongsTo() {
        return this.classBelongsTo;
    }

    public String toString() {
        String msg = "public "+returnType.getType() + " " + name;
        msg += "(";
        for(int i = 0; i < params.size(); ++i) {
            if(i != 0) {
                msg += ", ";
            }
            msg += params.get(i).toString();
        }
        msg += ")";
        return msg;
    }

    public String toJasmin() {
        String jasmin = classBelongsTo.getName() + "/" + name;
        jasmin += "(";
        for (Symbol param : params) {
            jasmin += MJUtils.symbolToJasminType(param);
        }
        jasmin += ")";
        jasmin += MJUtils.typeToJasminType(returnType);
        return jasmin;
    }

    public String toJasminHeader() {
        String jasmin = name + "(";
        for (Symbol param : params) {
            jasmin += MJUtils.symbolToJasminType(param);
        }
        jasmin += ")";
        jasmin += MJUtils.typeToJasminType(returnType);
        return jasmin;
    }

    private void addParams() {
        //Add "this" to the method
        Symbol thisSymbol = new Symbol("this",classBelongsTo.getType(),classBelongsTo);
        variables.add(thisSymbol);
        List<Symbol> tempParams = new ArrayList<>();
        for(Symbol param : params) {
            Symbol paramLocal = new Symbol(param.getName(),param.getType(),param.getClassBelongsTo(),this);
            tempParams.add(paramLocal);
            variables.add(paramLocal);
        }
        this.params = tempParams;
    }

    public String jasminify() {
        //Method header
        String jasmin = ".method public " + toJasminHeader();
        //Stack and local limits
        jasmin += "\n.limit stack " + stackLimit();
        jasmin += "\n.limit locals " + variables.size();
        //Body
        for (Statement stat : statements) {
            jasmin += "\n" + stat.jasminify();
        }
        jasmin += "\n" + returnExpression.jasminify();

        if (returnType.equals(ValueType.INT_TYPE) || returnType.equals(ValueType.BOOL_TYPE)) {
            jasmin += "\nireturn";
        } else {
            jasmin += "\nareturn";
        }

        //Method footer
        jasmin += "\n.end method";

        return jasmin;
    }

    private int stackLimit() {
        //TODO: Find a better limit
        return 32;
    }
}
