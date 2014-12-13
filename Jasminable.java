/** Defines an interface that allows classes implementing it to return 
  their representation in Jasmin code, which can be compiled to JVM
  runnable class files.
 **/
public interface Jasminable {
    public String jasminify();
}
