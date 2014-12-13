/** 
  Provides labels for the statement and expressions that utilize
  branching. 
  getNext* method return a new label with an incremented index;
  get* method return the current label with the current index;
 **/
public class LabelFactory {

    private static final String IF_BASE = "IF_";
    private static final String END_IF_BASE = "END_IF_";

    private static final String WHILE_BASE = "WHILE_";
    private static final String END_WHILE_BASE = "END_WHILE_";

    private static final String TERN_BASE = "TERN_";
    private static final String END_TERN_BASE = "END_TERN_";

    private static final String AND_BASE = "AND_";
    private static final String END_AND_BASE = "END_AND_";

    private static final String LT_BASE = "LT_";
    private static final String END_LT_BASE = "END_LT_";

    private static final String NEG_BASE = "NEG_";
    private static final String END_NEG_BASE = "END_NEG_";

    private static int ifNum = 0;
    private static int whileNum = 0;
    private static int ternNum = 0;
    private static int andNum = 0;
    private static int ltNum = 0;
    private static int negNum = 0;

    private LabelFactory() { }

    public static String getIfLabel() {
        return IF_BASE + ifNum;
    }

    public static String getEndIfLabel() {
        return END_IF_BASE + ifNum;
    }

    public static String getWhileLabel() {
        return WHILE_BASE + whileNum;
    }

    public static String getEndWhileLabel() {
        return END_WHILE_BASE + whileNum;
    }

    public static String getTernLabel() {
        return TERN_BASE + ternNum;
    }

    public static String getEndTernLabel() {
        return END_TERN_BASE + ternNum;
    }

    public static String getAndLabel() {
        return AND_BASE + andNum;
    }

    public static String getEndAndLabel() {
        return END_AND_BASE + andNum;
    }

    public static String getLtLabel() {
        return LT_BASE + ltNum;
    }

    public static String getEndLtLabel() {
        return END_LT_BASE + ltNum;
    }

    public static String getNegLabel() {
        return NEG_BASE + negNum;
    }

    public static String getEndNegLabel() {
        return END_NEG_BASE + negNum;
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

    public static String getNextNegLabel() {
        ++negNum;
        return getNegLabel();
    }
}
