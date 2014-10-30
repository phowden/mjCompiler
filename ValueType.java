public class ValueType {

    public static final String INT_TYPE = "int";
    public static final String INT_ARR_TYPE = "int[]";
    public static final String BOOL_TYPE = "boolean";
    
    private final String type;

    public ValueType(String t) {
        this.type = t;
    }

    public String getType() {
        return this.type;
    }

}
