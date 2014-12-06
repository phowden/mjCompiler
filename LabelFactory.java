public class LabelFactory {

    private static final String IF_BASE = "IF_";
    private static final String WHILE_BASE = "WHILE_";
    private static final String TERN_BASE = "TERN_";
    private static final String AND_BASE = "AND_";
    private static final String LT_BASE = "LT_";

    private static int ifNum = 0;
    private static int whileNum = 0;
    private static int ternNum = 0;
    private static int andNum = 0;
    private static int ltNum = 0;

    private LabelFactory() { }

    public static String getIfLabel() {
        return IF_BASE + ifNum;
    }

    public static String getWhileLabel() {
        return WHILE_BASE + whileNum;
    }

    public static String getTernLabel() {
        return TERN_BASE + ternNum;
    }

    public static String getAndLabel() {
        return AND_BASE + andNum;
    }

    public static String getLtLabel() {
        return LT_BASE + ltNum;
    }

    public static String getNextIfLabel() {
        ++ifNum;
        return getIfLabel();
    }

    public static String getNextWhileLabel() {
        ++whileNum;
        return getWhileLabel();
    }

    public static String getNextTernLabel() {
        ++ternNum;
        return getTernLabel();
    }

    public static String getNextAndLabel() {
        ++andNum;
        return getAndLabel();
    }

    public static String getNextLtLabel() {
        ++ltNum;
        return getLtLabel();
    }
}
