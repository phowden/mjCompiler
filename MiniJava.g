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

expression: expression '[' expression ']'                                           # arrAccessExpr
		| expression '.' 'length'                                                   # arrLengthExpr
		| expression '.' IDENTIFIER '(' ( expression ( ',' expression )* )? ')'     # methodCallExpr
        | '-' expression                                                            # negExpr
		| '!' expression                                                            # notExpr
		| 'new' 'int' '[' expression ']'                                            # newArrExpr
		| 'new' IDENTIFIER '(' ')'                                                  # newObjExpr
		| expression '+' expression                                                 # plusExpr
        | expression '-' expression                                                 # minusExpr
		| expression '*' expression                                                 # multExpr
		| expression '<' expression                                                 # lessThanExpr
        | expression '&&' expression                                                # andExpr
		| expression '?' expression ':' expression                                  # ternaryExpr
		| INT_LIT                                                                   # intLitExpr
		| 'true'                                                                    # trueLitExpr
		| 'false'                                                                   # falseLitExpr
		| 'this'                                                                    # thisExpr
		| IDENTIFIER                                                                # idExpr
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
