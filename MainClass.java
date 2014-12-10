import java.util.Set;
import java.util.HashSet;

import java.io.PrintWriter;

import java.io.IOException;

public class MainClass {

    private String name;
    private Set<MJClass> classes;
    private Statement statement;

    public MainClass(String n) {
        this.name = n;
        this.classes = new HashSet<MJClass>(5);
        this.statement = null;
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

    public void setStatement(Statement s) {
        this.statement = s;
    }

    public String toString() {
        String msg = "MainClass: "+this.name;
        msg += "\nClasses: \n";
        for (MJClass mjClass : classes) {
            msg += mjClass.toString();
        }
        return msg;
    }

    //TODO: Actually implement
    public void compile() throws IOException {
        PrintWriter writer = new PrintWriter(name+".j","UTF-8");
        String mainClass = ".class public "+name;
        mainClass += "\n.super java/lang/Object";
        mainClass += "\n.method public <init>()V";
        mainClass += "\naload_0";
        mainClass += "\ninvokenonvirtual java/lang/Object/<init>()V";
        mainClass += "\nreturn";
        mainClass += "\n.end method";
        mainClass += "\n\n.method public static main([Ljava/lang/String;)V";
        mainClass += "\n.limit stack 32";
        mainClass += "\n.limit locals 1";
        mainClass += "\n" + statement.jasminify();
        mainClass += "\nreturn";
        mainClass += "\n.end method";
        writer.write(mainClass);
        //System.out.println(mainClass);
        writer.close();
        for (MJClass mjClass : classes) {
            writer = new PrintWriter(mjClass.getName()+".j","UTF-8");
            writer.write(mjClass.jasminify());
            //System.out.println(mjClass.jasminify());
            writer.close();
        }
    }

}
