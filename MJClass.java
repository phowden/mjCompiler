import java.util.Set;
import java.util.HashSet;

public class MJClass implements Jasminable {

    private MJClass superClass;
    private String name;
    private Set<Symbol> fields;
    private Set<Method> methods;
    private ValueType type;

    public MJClass(MJClass s, String n) {
        this.superClass = s;
        this.name = n;
        this.fields = new HashSet<Symbol>();
        this.methods = new HashSet<Method>();
        if (s != null) {
            this.type = new ValueType(name,superClass.getType());
        } else {
            this.type = new ValueType(name);
        }
    }

    public MJClass(String n) {
        this(null,n);
    }

    public void setSuperClass(MJClass s) {
        this.superClass = s;
        if (s != null) {
            this.type = new ValueType(this.name,superClass.getType());
        } else {
            this.type = new ValueType(this.name);
        }
    }

    public void addField(String n, ValueType t) {
        this.fields.add(new Symbol(n,t,this));
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

    public ValueType getType() {
        return this.type;
    }

    public Set<Symbol> getFields() {
        return this.fields;
    }

    public Symbol getField(String symbolName) {
        for (Symbol sym : fields) {
            if (sym.getName().equals(symbolName)) {
                return sym;
            }
        }
        if (superClass != null) {
            return superClass.getField(symbolName);
        }
        return null;
    }

    public void removeMethod(Method removeMe) {
        this.methods.remove(removeMe);
    }

    public Set<Method> getMethods() {
        return this.methods;
    }

    public Method getMethod(String methodName, boolean checkSupers) {
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        if (checkSupers && this.superClass != null) {
            return superClass.getMethod(methodName,checkSupers);
        }
        return null;
    }

    public String toString() {
        //TODO Include other parts of the class (10/30)
        String msg = "class " + this.name;
        if (this.superClass != null) {
            msg += " extends " + this.superClass.getName();
        }
        msg += " {\n";
        for (Symbol sym : fields) {
            msg += " " + sym.toString();
        }
        msg += "\n";
        for (Method m : methods) {
            msg += " " + m.toString() + ";\n";
        }
        return msg + "}\n";
    }

    public String jasminify() {
        //Class declaration
        String jasmin = ".class public " + name;
        //Super class
        jasmin += "\n.super " + ((superClass != null) ? superClass.getName() : "java/lang/Object");
        jasmin += "\n";
        //Declare fields
        for (Symbol symbol : fields) {
            jasmin += "\n.field private " + symbol.getName() + " " + MJUtils.typeToJasminType(symbol.getType());
        }
        jasmin += "\n";
        //Implement methods
        for (Method method : methods) {
            jasmin += "\n" + method.jasminify() + "\n";
        }
        //Standard constructor
        jasmin += "\n.method public <init>()V";
        jasmin += "\naload_0";
        jasmin += "\ninvokenonvirtual java/lang/Object/<init>()V";
        jasmin += "\nreturn";
        jasmin += "\n.end method";

        return jasmin;
    }
}
