import java.util.List;

public class Method {

   private  String name;
   private  List<Symbol> params;
   private  ValueType returnType;

   public Method(String n, List<Symbol> p, ValueType r) {
       this.name = n;
       this.params = p;
       this.returnType = r;
   }

   public String getName() {
       return this.name;
   }

   public List<Symbol> getParams() {
       return this.params;
   }

   public ValueType getReturnType() {
       return this.returnType;
   }

}
