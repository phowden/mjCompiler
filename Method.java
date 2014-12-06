import java.util.List;
import java.util.ArrayList;

public class Method {

   private String name;
   private List<Symbol> params;
   private List<Symbol> variables; 
   private ValueType returnType;

   private MJClass classBelongsTo;

   public Method(ValueType r, String n, List<Symbol> p, MJClass c) {
       this.returnType = r;
       this.name = n;
       this.params = p;
       this.classBelongsTo = c;
       this.variables = new ArrayList<Symbol>();
       addParams();
   }

   public void addVariable(String n, ValueType t) {
       variables.add(new Symbol(n,t,this));
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

   private void addParams() {
       for(Symbol param : params) {
           Symbol paramLocal = new Symbol(param.getName(),param.getType(),param.getClassBelongsTo(),this);
           variables.add(paramLocal);
       }
   }
}
