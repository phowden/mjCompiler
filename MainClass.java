import java.util.Set;
import java.util.HashSet;

public class MainClass {

    String name;
    private Set<MJClass> classes;

    public MainClass(String n) {
        this.name = n;
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

    public Set<MJClass> getClasses() {
        return this.classes;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        String msg = "MainClass: "+this.name;
        msg += "\nClasses: \n";
        for (MJClass mjClass : classes) {
            msg += mjClass.toString();
        }
        return msg;
    }

}
