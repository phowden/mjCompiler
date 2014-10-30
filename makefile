all:
	java -jar /usr/local/lib/antlr-4.4-complete.jar MiniJava.g
	javac *.java
clean:
	rm *.class
grun:
	java org.antlr.v4.runtime.misc.TestRig MiniJava goal -tokens example.mj
grug:
	java org.antlr.v4.runtime.misc.TestRig MiniJava goal -tokens -gui example.mj
