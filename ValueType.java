public class ValueType {

    public static final ValueType INT_TYPE = new ValueType("int");
    public static final ValueType INT_ARR_TYPE = new ValueType("int[]");
    public static final ValueType BOOL_TYPE = new ValueType("boolean");
    
    private final String type;

    public ValueType(String t) {
        this.type = t;
    }

    public String getType() {
        return this.type;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof ValueType)) {
            return false;
        }
        ValueType v = (ValueType)object;
        return this.getType().equals(v.getType());
    }

    public int hashCode() {
        return type.hashCode();
    }

    public String toString() {
        return type;
    }
}
