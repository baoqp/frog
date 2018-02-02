grammar FrogSql;


sql:dml statement*
;


dml:
INSERT                      # insert
|UPDATE                     # update
|DELETE                     # delete
|SELECT                     # select
;

statement:
GLOBAL_TABLE                # globalTable
|BLANK                      # blank
|PARAMETER                  # parameter
|ITERABLE_PARAMETER         # iterableParameter
|plainText                  # text
|logicalOp                  # logical
;



plainText:
(FIELD|PLAINTEXT)
;
logicalOp:
LOGICAL_AND
|LOGICAL_OR
|LOGICAL_LT
|LOGICAL_LE
|LOGICAL_GT
|LOGICAL_GE
|LOGICAL_EQ
|LOGICAL_NE
|LOGICAL_NOT
;

INSERT:'insert';
UPDATE:'update';
DELETE:'delete';
SELECT:'select';
GLOBAL_TABLE:'#table';
DOT:'.';
COLON:':';
LOGICAL_AND:'and';
LOGICAL_OR:'or';
LOGICAL_LT: '<';
LOGICAL_LE: '<=';
LOGICAL_GT: '>';
LOGICAL_GE: '>=';
LOGICAL_EQ: '=';
LOGICAL_NE: '!=' | '<>';
LOGICAL_NOT: 'not';
BLANK: (' ')+;
NUMBER :[1-9]([0-9])*;
FIELD:[a-zA-Z_] ([a-zA-Z0-9_])*;
PLAINTEXT:[a-zA-Z0-9_\\.\\*\\(\\)\\',]+;   // 如果使用排除不可以出现的字符的正则表达式会有问题
PARAMETER:COLON (NUMBER|FIELD) (DOT FIELD)*;
ITERABLE_PARAMETER: 'in' BLANK PARAMETER;
WS: ('\r' | '\t' |'\n')+ -> skip;
