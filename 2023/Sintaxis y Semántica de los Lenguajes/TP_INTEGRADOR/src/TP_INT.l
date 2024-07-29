%{
#include "TP_INT.tab.h"
#include <stdio.h>
#include <string.h>
#include "funcionesFlex.h"

struct NodoCaractNoReconocidos* listaErroresLexicos = NULL;
int flagConcat;
int colActual = 1, linActual = 1;
char* caracterAux = "error";

%}

%x tokenNoReconocido

TIPOS char|int|float|double|void
IDENTIFICADORES [a-zA-Z_][a-zA-Z0-9_]*
LITERAL_CADENA \"[^\"]*\"
CONSTANTE_CARACTER \'[^\']\'
SIMB_OP [+\-*/%&|!~^<>=?:;.,()\[\]{}]
FLOAT [0-9]*\.([eE][+-]?[0-9]+)?[fFlL]?|[0-9]*\.[0-9]+([eE][+-]?[0-9]+)?[fFlL]?|[0-9]+([eE][+-]?[0-9]+)[fFlL]? 

%%
\n                { colActual = 1; linActual += 1;}
[0-9]+            { yylval.dval = atoi(yytext); colActual += yyleng; return NUM; }
return            { return RETURN; }
while             { return WHILE; }
do                { return DO; }
for               { return FOR; }
if                { return IF; }
else              { return ELSE; }
switch            { return SWITCH; }
{TIPOS}           { yylval.str = strdup(yytext); colActual += yyleng; return NOMBRE_TIPO; }
!=                { yylval.str = strdup(yytext);  colActual += yyleng; return SIMB_DIFF; }
\|\|              { colActual += yyleng; return OR; }
\&\&              { colActual += yyleng; return AND; }
\+\+              { colActual += yyleng; return OP_ADD; }
\-\-              { colActual += yyleng; return OP_SUB; }
\+=               { colActual += yyleng; return OP_MAS_IG; }
==                { colActual += yyleng; return OP_COMP; }
{IDENTIFICADORES} { yylval.str = strdup(yytext); colActual += yyleng; return IDENTIFICADOR; }
{CONSTANTE_CARACTER}  { yylval.str = strdup(yytext); colActual += yyleng; return CONSTANTE_CARACTER; }
{LITERAL_CADENA}  { colActual += yyleng; return LITERAL_CADENA; }
{FLOAT}           { yylval.dval = atof(yytext); colActual += yyleng; return FLOAT; } 
{SIMB_OP}         { colActual += yyleng; return yytext[0];}
[ \t]+            { colActual += yyleng; }
[\/\/][^\n]*      { colActual += yyleng; }
[^ ]              { caracterAux = strdup(yytext); colActual += 1; BEGIN(tokenNoReconocido);}
<<EOF>>           { printf("\nLectura y analisis del archivo completado.\n");yyterminate(); }

<tokenNoReconocido>{
    [ ]       { insertarCaracteresNoReconocidos(&listaErroresLexicos, caracterAux, linActual, colActual - strlen(caracterAux)); colActual += 1; BEGIN(INITIAL);}
    \t        { insertarCaracteresNoReconocidos(&listaErroresLexicos, caracterAux, linActual, colActual - strlen(caracterAux)); colActual += 1; BEGIN(INITIAL);}
    \n        { insertarCaracteresNoReconocidos(&listaErroresLexicos, caracterAux, linActual, colActual - strlen(caracterAux)); colActual = 1; linActual += 1; BEGIN(INITIAL);}
    [^ \t\n]+ { caracterAux = strcat(caracterAux, yytext); colActual += yyleng;}
}
 
%%
