public class LabelFactory {

    private static final String IF_BASE = "IF_";
    private static final String WHILE_BASE = "WHILE_";
    private static final String TERN_BASE = "TERN_";

    private static int ifNum = 0;
    private static int whileNum = 0;

    private LabelFactory() { }

    public static String getIfLabel() {
        return IF_BASE + ifNum;
    }

    public static String getWhileLabel() {
        return WHILE_BASE + whileNum;
    }

    public static String getNextIfLabel() {
        ++ifNum;
        return getIfLabel();
    }

    public static String getNextWhileLabel() {
        ++whileNum;
        return getWhileLabel();
    }
}
