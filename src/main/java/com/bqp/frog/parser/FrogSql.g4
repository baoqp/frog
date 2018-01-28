grammar FrogSql;


sql: dml block
;


block: statement+
;

dml:
INSERT                  # insert
|UPDATE                 # update
|DELETE                 # delete
|SELECT                 # select
;

statement:
//JDBC_PARAMETER          # jdbcParameter
TEXT                     # text
|BLANK                   # blank
;


INSERT:'insert';
UPDATE:'update';
DELETE:'delete';
SELECT:'select';
COLON:':';
NUMBER:[1-9]([0-9]*);
DOT:'.';
FIELD:[a-zA-Z_]([a-zA-Z0-9_]*);
JDBC_PARAMETER:COLON(NUMBER|FIELD)(DOT FIELD)*;
BLANK: (' ' | '\t' | '\r' | '\n')+;
TEXT: [a-zA-Z0-9\\._]+;
