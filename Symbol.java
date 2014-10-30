public class Symbol {

    private MJClass owner;
    private String name;
    private ValueType type;

    public Symbol(MJClass o, String n, ValueType t) {
        this.owner = o;
        this.name = n;
        this.type = t;
    }
    
    public MJClass getOwner() {
        return this.owner;
    }

    public String getName() {
        return this.name;
    }

    public ValueType getType() {
        return this.type;
    }

}


