grammar FrogSql;


sql:dml statement*
;


dml:
INSERT                  # insert
|UPDATE                 # update
|DELETE                 # delete
|SELECT                 # select
;

statement:
FIELD                   # text
|BLANK                  # blank
|jdbcParameter          # parameter

;

jdbcParameter:
COLON (NUMBER|FIELD) (DOT FIELD)*
;



INSERT:'insert';
UPDATE:'update';
DELETE:'delete';
SELECT:'select';
DOT:'.';
COLON:':';
NUMBER : [1-9]([0-9])*;
FIELD:[a-zA-Z0-9_\\.]+;
BLANK: (' ' | '\t' | '\r' | '\n')+;

