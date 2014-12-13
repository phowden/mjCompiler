import java.util.Set;
import java.util.HashSet;

import java.io.PrintWriter;

import java.io.IOException;

/** Representation of the main class of a MiniJava file. The main class
  contains the 'main' method of the file. The representation contains
  the representations of all the classes in the file.
 **/
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

    /** Returns the contained class that matches the given
      class name, returns null if no matching class is found
     **/
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

    /**Sets the single statement in the main method **/
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

    /** Compiles the main class, and all the included MJClasses
      to jasmin jvm instructions, then writes each to an 
      appropriately named *.j file
     **/
    public void compile() throws IOException {
        //Initialize print writer for main class
        PrintWriter writer = new PrintWriter(name+".j","UTF-8");
        //Class and super header
        String mainClass = ".class public "+name;
        mainClass += "\n.super java/lang/Object";
        //Main class init method
        mainClass += "\n.method public <init>()V";
        mainClass += "\naload_0";
        mainClass += "\ninvokenonvirtual java/lang/Object/<init>()V";
        mainClass += "\nreturn";
        mainClass += "\n.end method";
        //Main class main method
        mainClass += "\n\n.method public static main([Ljava/lang/String;)V";
        mainClass += "\n.limit stack 32";
        mainClass += "\n.limit locals 1";
        mainClass += "\n" + statement.jasminify();
        mainClass += "\nreturn";
        mainClass += "\n.end method";
        //Write main class to file
        writer.write(mainClass);
        writer.close();
        //Write each included MJClass to an appropriate file
        for (MJClass mjClass : classes) {
            writer = new PrintWriter(mjClass.getName()+".j","UTF-8");
            writer.write(mjClass.jasminify());
            writer.close();
        }
    }

}
