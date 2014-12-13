/** Represents a type **/
public class ValueType {

    public static final ValueType INT_TYPE = new ValueType("int");
    public static final ValueType INT_ARR_TYPE = new ValueType("int[]");
    public static final ValueType BOOL_TYPE = new ValueType("boolean");

    public static final ValueType NULL_TYPE = new ValueType("null");
    
    private final String type;

    private final ValueType superType;

    public ValueType(String t) {
        this(t,null);
    }

    public ValueType(String t, ValueType s) {
        this.type = t;
        this.superType = s;
    }

    public String getType() {
        return this.type;
    }

    public ValueType getSuperType() {
        return this.superType;
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

        boolean thisTypeMatches =  this.getType().equals(v.getType());
        if (!thisTypeMatches && superType != null) {
            return superType.equals(object);
        }
        
        if (!thisTypeMatches && v.getSuperType() != null) {
            return v.getSuperType().equals(this);
        }

        return thisTypeMatches;
    }

    public int hashCode() {
        return type.hashCode();
    }

    public String toString() {
        if (superType == null) {
            return type;
        } else {
            return type+"->"+superType.toString();
        }
    }
}
