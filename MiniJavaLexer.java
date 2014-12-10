// Generated from MiniJava.g by ANTLR 4.4
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniJavaLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__36=1, T__35=2, T__34=3, T__33=4, T__32=5, T__31=6, T__30=7, T__29=8, 
		T__28=9, T__27=10, T__26=11, T__25=12, T__24=13, T__23=14, T__22=15, T__21=16, 
		T__20=17, T__19=18, T__18=19, T__17=20, T__16=21, T__15=22, T__14=23, 
		T__13=24, T__12=25, T__11=26, T__10=27, T__9=28, T__8=29, T__7=30, T__6=31, 
		T__5=32, T__4=33, T__3=34, T__2=35, T__1=36, T__0=37, IDENTIFIER=38, INT_LIT=39, 
		NEWLINE=40, WS=41, COMMENT=42, LINE_COMMENT=43;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'", "'\\u001B'", "'\\u001C'", "'\\u001D'", "'\\u001E'", 
		"'\\u001F'", "' '", "'!'", "'\"'", "'#'", "'$'", "'%'", "'&'", "'''", 
		"'('", "')'", "'*'", "'+'"
	};
	public static final String[] ruleNames = {
		"T__36", "T__35", "T__34", "T__33", "T__32", "T__31", "T__30", "T__29", 
		"T__28", "T__27", "T__26", "T__25", "T__24", "T__23", "T__22", "T__21", 
		"T__20", "T__19", "T__18", "T__17", "T__16", "T__15", "T__14", "T__13", 
		"T__12", "T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", 
		"T__3", "T__2", "T__1", "T__0", "IDENTIFIER", "INT_LIT", "NEWLINE", "WS", 
		"COMMENT", "LINE_COMMENT"
	};


	public MiniJavaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MiniJava.g"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2-\u012f\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f"+
		"\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3"+
		"\25\3\25\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3"+
		"\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3"+
		"\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3"+
		"\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3"+
		"\37\3 \3 \3 \3 \3 \3 \3 \3!\3!\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3$"+
		"\3%\3%\3%\3%\3%\3%\3&\3&\3\'\5\'\u00fc\n\'\3\'\7\'\u00ff\n\'\f\'\16\'"+
		"\u0102\13\'\3(\6(\u0105\n(\r(\16(\u0106\3)\5)\u010a\n)\3)\3)\3)\3)\3*"+
		"\6*\u0111\n*\r*\16*\u0112\3*\3*\3+\3+\3+\3+\7+\u011b\n+\f+\16+\u011e\13"+
		"+\3+\3+\3+\3+\3+\3,\3,\3,\3,\7,\u0129\n,\f,\16,\u012c\13,\3,\3,\3\u011c"+
		"\2-\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36"+
		";\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-\3\2\7\5\2C\\aac|\6\2\62;C\\aac|\3\2"+
		"\62;\4\2\13\13\"\"\4\2\f\f\17\17\u0134\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2"+
		"\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2"+
		"\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3"+
		"\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3"+
		"\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65"+
		"\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3"+
		"\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2"+
		"\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\3Y\3\2\2\2\5"+
		"[\3\2\2\2\7b\3\2\2\2\td\3\2\2\2\13j\3\2\2\2\rl\3\2\2\2\17n\3\2\2\2\21"+
		"p\3\2\2\2\23r\3\2\2\2\25t\3\2\2\2\27w\3\2\2\2\31{\3\2\2\2\33}\3\2\2\2"+
		"\35\u0082\3\2\2\2\37\u0088\3\2\2\2!\u008a\3\2\2\2#\u008f\3\2\2\2%\u0091"+
		"\3\2\2\2\'\u0099\3\2\2\2)\u009e\3\2\2\2+\u00a6\3\2\2\2-\u00a8\3\2\2\2"+
		"/\u00ad\3\2\2\2\61\u00b4\3\2\2\2\63\u00bb\3\2\2\2\65\u00ce\3\2\2\2\67"+
		"\u00d0\3\2\2\29\u00d2\3\2\2\2;\u00d4\3\2\2\2=\u00d6\3\2\2\2?\u00dd\3\2"+
		"\2\2A\u00e4\3\2\2\2C\u00e6\3\2\2\2E\u00e9\3\2\2\2G\u00ee\3\2\2\2I\u00f2"+
		"\3\2\2\2K\u00f8\3\2\2\2M\u00fb\3\2\2\2O\u0104\3\2\2\2Q\u0109\3\2\2\2S"+
		"\u0110\3\2\2\2U\u0116\3\2\2\2W\u0124\3\2\2\2YZ\7_\2\2Z\4\3\2\2\2[\\\7"+
		"r\2\2\\]\7w\2\2]^\7d\2\2^_\7n\2\2_`\7k\2\2`a\7e\2\2a\6\3\2\2\2bc\7.\2"+
		"\2c\b\3\2\2\2de\7y\2\2ef\7j\2\2fg\7k\2\2gh\7n\2\2hi\7g\2\2i\n\3\2\2\2"+
		"jk\7]\2\2k\f\3\2\2\2lm\7/\2\2m\16\3\2\2\2no\7,\2\2o\20\3\2\2\2pq\7*\2"+
		"\2q\22\3\2\2\2rs\7<\2\2s\24\3\2\2\2tu\7k\2\2uv\7h\2\2v\26\3\2\2\2wx\7"+
		"k\2\2xy\7p\2\2yz\7v\2\2z\30\3\2\2\2{|\7>\2\2|\32\3\2\2\2}~\7o\2\2~\177"+
		"\7c\2\2\177\u0080\7k\2\2\u0080\u0081\7p\2\2\u0081\34\3\2\2\2\u0082\u0083"+
		"\7h\2\2\u0083\u0084\7c\2\2\u0084\u0085\7n\2\2\u0085\u0086\7u\2\2\u0086"+
		"\u0087\7g\2\2\u0087\36\3\2\2\2\u0088\u0089\7A\2\2\u0089 \3\2\2\2\u008a"+
		"\u008b\7x\2\2\u008b\u008c\7q\2\2\u008c\u008d\7k\2\2\u008d\u008e\7f\2\2"+
		"\u008e\"\3\2\2\2\u008f\u0090\7}\2\2\u0090$\3\2\2\2\u0091\u0092\7g\2\2"+
		"\u0092\u0093\7z\2\2\u0093\u0094\7v\2\2\u0094\u0095\7g\2\2\u0095\u0096"+
		"\7p\2\2\u0096\u0097\7f\2\2\u0097\u0098\7u\2\2\u0098&\3\2\2\2\u0099\u009a"+
		"\7g\2\2\u009a\u009b\7n\2\2\u009b\u009c\7u\2\2\u009c\u009d\7g\2\2\u009d"+
		"(\3\2\2\2\u009e\u009f\7d\2\2\u009f\u00a0\7q\2\2\u00a0\u00a1\7q\2\2\u00a1"+
		"\u00a2\7n\2\2\u00a2\u00a3\7g\2\2\u00a3\u00a4\7c\2\2\u00a4\u00a5\7p\2\2"+
		"\u00a5*\3\2\2\2\u00a6\u00a7\7\177\2\2\u00a7,\3\2\2\2\u00a8\u00a9\7v\2"+
		"\2\u00a9\u00aa\7t\2\2\u00aa\u00ab\7w\2\2\u00ab\u00ac\7g\2\2\u00ac.\3\2"+
		"\2\2\u00ad\u00ae\7u\2\2\u00ae\u00af\7v\2\2\u00af\u00b0\7c\2\2\u00b0\u00b1"+
		"\7v\2\2\u00b1\u00b2\7k\2\2\u00b2\u00b3\7e\2\2\u00b3\60\3\2\2\2\u00b4\u00b5"+
		"\7n\2\2\u00b5\u00b6\7g\2\2\u00b6\u00b7\7p\2\2\u00b7\u00b8\7i\2\2\u00b8"+
		"\u00b9\7v\2\2\u00b9\u00ba\7j\2\2\u00ba\62\3\2\2\2\u00bb\u00bc\7U\2\2\u00bc"+
		"\u00bd\7{\2\2\u00bd\u00be\7u\2\2\u00be\u00bf\7v\2\2\u00bf\u00c0\7g\2\2"+
		"\u00c0\u00c1\7o\2\2\u00c1\u00c2\7\60\2\2\u00c2\u00c3\7q\2\2\u00c3\u00c4"+
		"\7w\2\2\u00c4\u00c5\7v\2\2\u00c5\u00c6\7\60\2\2\u00c6\u00c7\7r\2\2\u00c7"+
		"\u00c8\7t\2\2\u00c8\u00c9\7k\2\2\u00c9\u00ca\7p\2\2\u00ca\u00cb\7v\2\2"+
		"\u00cb\u00cc\7n\2\2\u00cc\u00cd\7p\2\2\u00cd\64\3\2\2\2\u00ce\u00cf\7"+
		"\60\2\2\u00cf\66\3\2\2\2\u00d0\u00d1\7+\2\2\u00d18\3\2\2\2\u00d2\u00d3"+
		"\7-\2\2\u00d3:\3\2\2\2\u00d4\u00d5\7?\2\2\u00d5<\3\2\2\2\u00d6\u00d7\7"+
		"t\2\2\u00d7\u00d8\7g\2\2\u00d8\u00d9\7v\2\2\u00d9\u00da\7w\2\2\u00da\u00db"+
		"\7t\2\2\u00db\u00dc\7p\2\2\u00dc>\3\2\2\2\u00dd\u00de\7U\2\2\u00de\u00df"+
		"\7v\2\2\u00df\u00e0\7t\2\2\u00e0\u00e1\7k\2\2\u00e1\u00e2\7p\2\2\u00e2"+
		"\u00e3\7i\2\2\u00e3@\3\2\2\2\u00e4\u00e5\7=\2\2\u00e5B\3\2\2\2\u00e6\u00e7"+
		"\7(\2\2\u00e7\u00e8\7(\2\2\u00e8D\3\2\2\2\u00e9\u00ea\7v\2\2\u00ea\u00eb"+
		"\7j\2\2\u00eb\u00ec\7k\2\2\u00ec\u00ed\7u\2\2\u00edF\3\2\2\2\u00ee\u00ef"+
		"\7p\2\2\u00ef\u00f0\7g\2\2\u00f0\u00f1\7y\2\2\u00f1H\3\2\2\2\u00f2\u00f3"+
		"\7e\2\2\u00f3\u00f4\7n\2\2\u00f4\u00f5\7c\2\2\u00f5\u00f6\7u\2\2\u00f6"+
		"\u00f7\7u\2\2\u00f7J\3\2\2\2\u00f8\u00f9\7#\2\2\u00f9L\3\2\2\2\u00fa\u00fc"+
		"\t\2\2\2\u00fb\u00fa\3\2\2\2\u00fc\u0100\3\2\2\2\u00fd\u00ff\t\3\2\2\u00fe"+
		"\u00fd\3\2\2\2\u00ff\u0102\3\2\2\2\u0100\u00fe\3\2\2\2\u0100\u0101\3\2"+
		"\2\2\u0101N\3\2\2\2\u0102\u0100\3\2\2\2\u0103\u0105\t\4\2\2\u0104\u0103"+
		"\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0104\3\2\2\2\u0106\u0107\3\2\2\2\u0107"+
		"P\3\2\2\2\u0108\u010a\7\17\2\2\u0109\u0108\3\2\2\2\u0109\u010a\3\2\2\2"+
		"\u010a\u010b\3\2\2\2\u010b\u010c\7\f\2\2\u010c\u010d\3\2\2\2\u010d\u010e"+
		"\b)\2\2\u010eR\3\2\2\2\u010f\u0111\t\5\2\2\u0110\u010f\3\2\2\2\u0111\u0112"+
		"\3\2\2\2\u0112\u0110\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0114\3\2\2\2\u0114"+
		"\u0115\b*\2\2\u0115T\3\2\2\2\u0116\u0117\7\61\2\2\u0117\u0118\7,\2\2\u0118"+
		"\u011c\3\2\2\2\u0119\u011b\13\2\2\2\u011a\u0119\3\2\2\2\u011b\u011e\3"+
		"\2\2\2\u011c\u011d\3\2\2\2\u011c\u011a\3\2\2\2\u011d\u011f\3\2\2\2\u011e"+
		"\u011c\3\2\2\2\u011f\u0120\7,\2\2\u0120\u0121\7\61\2\2\u0121\u0122\3\2"+
		"\2\2\u0122\u0123\b+\2\2\u0123V\3\2\2\2\u0124\u0125\7\61\2\2\u0125\u0126"+
		"\7\61\2\2\u0126\u012a\3\2\2\2\u0127\u0129\n\6\2\2\u0128\u0127\3\2\2\2"+
		"\u0129\u012c\3\2\2\2\u012a\u0128\3\2\2\2\u012a\u012b\3\2\2\2\u012b\u012d"+
		"\3\2\2\2\u012c\u012a\3\2\2\2\u012d\u012e\b,\2\2\u012eX\3\2\2\2\13\2\u00fb"+
		"\u00fe\u0100\u0106\u0109\u0112\u011c\u012a\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}