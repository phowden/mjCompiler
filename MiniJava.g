grammar MiniJava;

goal:	mainClass ( classDecl )* ;

mainClass:	'class' IDENTIFIER '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' IDENTIFIER ')' '{' statement '}' '}' ;

classDecl:	'class' IDENTIFIER ( 'extends' IDENTIFIER )? '{' ( varDecl )* ( methodDecl )* '}' ;

varDecl:	type IDENTIFIER ';' ;

methodDecl:	'public' type IDENTIFIER '(' ( type IDENTIFIER ( ',' type IDENTIFIER )* )? ')' '{' ( varDecl )* ( statement )* 'return' expression ';' '}' ;

type:	'int' '[' ']'          # intArrType
		|	'boolean'          # boolType
		|	'int'              # intType
		|	IDENTIFIER         # objType
        ;

statement:	'{' ( statement )* '}'                                # scopedStat
		|	'if' '(' expression ')' statement 'else' statement    # ifStat
		|	'while' '(' expression ')' statement                  # whileStat
		|	'System.out.println' '(' expression ')' ';'           # printlnStat
		|	IDENTIFIER '=' expression ';'                         # assignStat
		|	IDENTIFIER '[' expression ']' '=' expression ';'      # arrAssignStat
		;

expression:	expression '&&' expression                                              # andExpr
		| expression '<' expression                                                 # lessThanExpr
		| expression ( '+' | '-' ) expression                                       # plusMinusExpr
		| expression '*' expression                                                 # multExpr
        | expression '[' expression ']'                                             # arrAccessExpr
		| expression '.' 'length'                                                   # arrLengthExpr
		| expression '.' IDENTIFIER '(' ( expression ( ',' expression )* )? ')'     # methodCallExpr
		| expression '?' expression ':' expression                                  # ternaryExpr
		| INT_LIT                                                                   # intLitExpr
		| 'true'                                                                    # trueLitExpr
		| 'false'                                                                   # falseLitExpr
		| 'this'                                                                    # thisExpr
		| 'new' 'int' '[' expression ']'                                            # newArrExpr
		| 'new' IDENTIFIER '(' ')'                                                  # newObjExpr
		| IDENTIFIER                                                                # idExpr
		| '!' expression                                                            # negateExpr
		| '(' expression ')'                                                        # parenedExpr 
		;

IDENTIFIER	: ( ('a'..'z') | ('A'..'Z') | '_' )  ( ('a'..'z') | ('A'..'Z') | ('0'..'9' ) | '_')* ;
INT_LIT			: [0-9]+;
NEWLINE			:	'\r'? '\n' ->skip;
WS					: [ \t]+ -> skip;

COMMENT
:   '/*' .*? '*/' -> skip
;

LINE_COMMENT
:   '//' ~[\r\n]* -> skip
;
