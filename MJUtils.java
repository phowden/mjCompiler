import java.util.List;

public class MJUtils {

    private MJUtils() { } 

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

    //TODO: Actually implement
    public static Statement generateStatement(MiniJavaParser.StatementContext ctx) {
        return null;
    }

    //JASMIN CODE GENERATION METHDOS
    public static String symbolToJasminType(Symbol s) {
        return typeToJasminType(s.getType());
    }
        
    public static String typeToJasminType(ValueType type) {
        if (type.equals(ValueType.INT_TYPE)) {
            return "I";
        } else if (type.equals(ValueType.INT_ARR_TYPE)) {
            return "[I";
        } else if (type.equals(ValueType.BOOL_TYPE)) {
            return "Z";
        } else {
            return type.getType();
        }
    }
}
