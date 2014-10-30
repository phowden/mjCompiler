import java.util.Set;
import java.util.HashSet;

public class MainClass {

    private Set<MJClass> classes;

    public MainClass() {
        this.classes = new HashSet<MJClass>(5);
    }

    public void addClass(MJClass mjClass) {
        this.classes.add(mjClass);
    }

    public MJClass getClass(String className) {
        for (MJClass mjClass : classes) {
            if (mjClass.getName().equals(className)) {
                return mjClass;
            }
        }
        return null;
    }

    public String toString() {
        String msg = "Classes: \n";
        for (MJClass mjClass : classes) {
            msg += mjClass.toString();
        }
        return msg;
    }

}
