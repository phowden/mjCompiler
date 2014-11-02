public class Symbol {

    private String name;
    private ValueType type;

    public Symbol(String n, ValueType t) {
        this.name = n;
        this.type = t;
    }
    
    public String getName() {
        return this.name;
    }

    public ValueType getType() {
        return this.type;
    }

    public String toString() {
        return type.toString() + " " + name + ";";
    }

}


