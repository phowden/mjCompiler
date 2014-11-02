import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Method {

   private String name;
   private List<Symbol> params;
   private Set<Symbol> variables; 
   private ValueType returnType;

   public Method(ValueType r, String n, List<Symbol> p) {
       this.returnType = r;
       this.name = n;
       this.params = p;
       this.variables = new HashSet<Symbol>();
   }

   public void addVariable(String n, ValueType t) {
       variables.add(new Symbol(n,t));
   }

   public String getName() {
       return this.name;
   }

   public List<Symbol> getParams() {
       return this.params;
   }

   public Set<Symbol> getVariables() {
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

   public ValueType getReturnType() {
       return this.returnType;
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

}
