import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.*;

/**
  An improvement on ANTLR's default error strategy, prints errors in a more helpful and informative fashion
 **/
public class BetterErrorStrategy extends DefaultErrorStrategy {

    @Override
    protected void reportUnwantedToken(@NotNull Parser recognizer) {
        //Don't keep reporting unwanted tokens
        if (inErrorRecoveryMode(recognizer)) {
            return;
        }
        beginErrorCondition(recognizer);

        Token t = recognizer.getCurrentToken();
        String tokenText = getTokenErrorDisplay(t);
        IntervalSet expecting = getExpectedTokens(recognizer);
        String msg = "Unexpected " + tokenText + ", ";
        msg += "expected one of the following: " + expecting.toString(recognizer.getTokenNames());

        recognizer.notifyErrorListeners(t,msg,null);
        MJUtils.markErrorOccured();
    }

    @Override
    protected void reportNoViableAlternative(@NotNull Parser recognizer, @NotNull NoViableAltException e) {
        TokenStream tokens = recognizer.getInputStream();
        String input;

        if (tokens!=null) {
            if (e.getStartToken().getType()==Token.EOF) {
                input = "<EOF>";
            } else {
                input = tokens.getText(e.getStartToken(), e.getOffendingToken());
            }
        } else {
            input = "<unknown input>";
        }

        String msg = "No viable alternative at input "+escapeWSAndQuote(input);
        recognizer.notifyErrorListeners(e.getOffendingToken(), msg, e);
    }

    @Override
    protected void reportInputMismatch(@NotNull Parser recognizer, @NotNull InputMismatchException e) {
        String msg = "Mismatch input " + getTokenErrorDisplay(e.getOffendingToken());
        msg += ", expected one of the following " + e.getExpectedTokens().toString(recognizer.getTokenNames());
        recognizer.notifyErrorListeners(e.getOffendingToken(), msg, e);
    }

}
