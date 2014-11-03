import java.util.List;

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

    public static boolean isOverloading(Method newMethod, Method oldMethod) {
        if (newMethod == null || oldMethod == null) {
            return false;
        }

        if (!newMethod.getName().equals(oldMethod.getName())) {
            return false;
        }
        if (!newMethod.getReturnType().equals(oldMethod.getReturnType())) {
            System.out.println("RTYPE");
            return true;
        }

        List<Symbol> newParams = newMethod.getParams();
        List<Symbol> oldParams = oldMethod.getParams();

        if (newParams.size() != oldParams.size()) {
            return true;
        }

        for (int i = 0; i < newParams.size(); ++i) {
            Symbol newParamI = newParams.get(i);
            Symbol oldParamI = oldParams.get(i);
            if (!oldParamI.getType().equals(newParamI.getType())) {
                System.out.println("PTL: "+newParamI.getType());
                return true;
            }
        }

        return false;
    }

}
