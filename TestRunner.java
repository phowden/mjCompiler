import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.misc.Nullable;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestRunner {

	private static final int N_ARGS = 1;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		if (args.length != N_ARGS) {
			System.out.println("Usage: java TestRunner targetfile");
			System.exit(-1);
		}
		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(args[0]));
		MiniJavaLexer lexer = new MiniJavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MiniJavaParser parser = new MiniJavaParser(tokens);

		parser.removeErrorListeners(); 

		parser.addErrorListener(new BetterErrorListener());
		parser.setErrorHandler(new BetterErrorStrategy());

		ParseTree tree = parser.goal();

        ResolveVisitor r = new ResolveVisitor();
        r.resolve(tree);

        if (MJUtils.error) { 
            System.exit(-1);
        }

        TypeCheckingVisitor t = new TypeCheckingVisitor(r.getMainClass());
        t.visit(tree);

        if (MJUtils.error) {
            System.exit(-1);
        }

        System.out.println("\n");
        MainClass mainClass = t.getMainClass();

        try {
            mainClass.compile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

	}
}
