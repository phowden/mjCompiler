import java.util.Set;
import java.util.HashSet;

public class MJClass {
    
    private MJClass superClass;
    private String name;
    private Set<Symbol> fields;
    private Set<Method> methods;

    public MJClass(MJClass s, String n) {
        this.superClass = s;
        this.name = n;
        this.fields = new HashSet<Symbol>();
        this.methods = new HashSet<Method>();
    }

    public MJClass(String n) {
        this(null,n);
    }

    public void setSuperClass(MJClass s) {
        this.superClass = s;
    }

    public void addField(Symbol s) {
        this.fields.add(s);
    }

    public void addMethod(Method m) {
        this.methods.add(m);
    }

    public MJClass getSuperClass() {
        return this.superClass;
    }

    public String getName() {
        return this.name;
    }

    public Set<Symbol> getFields() {
        return this.fields;
    }

    public Set<Method> getMethods() {
        return this.methods;
    }

    public String toString() {
        //TODO Include other parts of the class (10/30)
        String msg = this.name;
        if (this.superClass != null) {
            msg += " extends " + this.superClass.getName();
        }
        return msg += "\n";
    }


}
