public class MJUtils {

    public static Symbol findVariable(MJClass mjClass, Method method, String variableName) {
        Symbol var = null;

        if (method != null) {
           var = method.getVariable(variableName);
        }

        if (var == null) {
            var = mjClass.getField(variableName);
        }

        return var;
    }
}
