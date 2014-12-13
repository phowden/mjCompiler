import java.util.List;

/** Utility class for various tasks required by one or more 
  **/
public class MJUtils {

    public static boolean error = false;

    private MJUtils() { } 

    /** Attempts to find the given variable name in the given method and class. Looks first
        in the method, then if not found in the method in the class. Returns null if the
        variable is not found in either
     **/
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

    /** Compares the given methods checking if the new method is attempting
      to overload the old method.
     **/
    public static boolean isOverloading(Method newMethod, Method oldMethod) {
        if (newMethod == null || oldMethod == null) {
            return false;
        }

        //Simple equals check
        if (!newMethod.getName().equals(oldMethod.getName())) {
            return false;
        }
        //Return type check
        if (!newMethod.getReturnType().equals(oldMethod.getReturnType())) {
            return true;
        }

        List<Symbol> newParams = newMethod.getParams();
        List<Symbol> oldParams = oldMethod.getParams();

        if (newParams.size() != oldParams.size()) {
            return true;
        }

        //Compare parameters
        for (int i = 0; i < newParams.size(); ++i) {
            Symbol newParamI = newParams.get(i);
            Symbol oldParamI = oldParams.get(i);
            if (!oldParamI.getType().equals(newParamI.getType())) {
                return true;
            }
        }

        return false;
    }

    /** Marks that an error has occurred somewhere in the compiling proccess.
      Indicates that the next step of compilation should not occur
     **/
    public static void markErrorOccured() {
        error = true;
    }


    public static String symbolToJasminType(Symbol s) {
        return typeToJasminType(s.getType());
    }
        
    /** Takes a type object and returns the jasmin representation
      of that type.
     **/
    public static String typeToJasminType(ValueType type) {
        if (type.equals(ValueType.INT_TYPE)) {
            return "I";
        } else if (type.equals(ValueType.INT_ARR_TYPE)) {
            return "[I";
        } else if (type.equals(ValueType.BOOL_TYPE)) {
            return "Z";
        } else {
            return "L"+type.getType()+";";
        }
    }
}
