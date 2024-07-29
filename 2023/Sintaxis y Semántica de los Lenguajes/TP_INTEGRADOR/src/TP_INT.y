%{
#include <math.h>
#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include "funcionesListados.c"

//#include "funcionesFlex.c"
 //#include "funcionesFlex.h"



extern FILE *yyin; //para evitar el error que teniamos en Windows con el archivo de entrada
extern struct NodoCaractNoReconocidos* listaErroresLexicos; //para que flex guarde los errores lexicos en esta lista
extern int linActual;
extern int colActual;
extern void imprimirListaCaracNoReco();
extern void lineaSeparadora1(int sep1);
extern void lineaPrincipal1(int sep1);
extern void lineaFinal1(int sep1);

int yylex();
int yyerror (const char *s);
int yywrap(){ return(1); }
char* id;
int errorSintactico;

char* tipo = NULL;
char* identificador;
char* auxIdInvocacion;
int tipoSent;
int cantPunteros = 0;
int flagArray;

NodoSentencias* listaSentencias = NULL;

NodoAuxiliarF auxiliarFun;

NodoFunciones* listaFunDecla = NULL;

NodoParametros* listaAuxParametrosDecla = NULL;

NodoParametrosInv* listaAuxParametrosInv = NULL;

NodoErrorSintatico* listaErrorSintactico = NULL;

NodoErroresSemanticos* listaDeErroresSemanticos = NULL;

NodoVarible* listaDeVars=NULL;
NodoVarible* variablesAdeclarar = NULL;

%}

%union {
    char op;
    int dval;
    char* str;
}

%locations



%token <dval> NUM
%token <dval> FLOAT
%token <str> NOMBRE_TIPO
%token <str> IDENTIFICADOR
%token <str> LITERAL_CADENA
%token <str> OR
%token <str> AND
%token <str> OP_ADD
%token <str> OP_SUB
%token <str> OP_MAS_IG
%token <str> OP_COMP
%token <str> SIMB_DIFF
%token <str> RETURN
%token <str> DO
%token <str> WHILE
%token <str> IF
%token <str> ELSE
%token <str> FOR
%token <str> SWITCH
%token <str> CONSTANTE_CARACTER


%type <str> expPrimaria
%type <str> expPostfijo
%type <str> expUnaria
%type <str> expMultiplicativa
%type <str> expAditiva
%type <str> opRecursionExpAditiva
%type <str> inicializacionOpcional
%type <str> exp
%type <str> expAsignacion
%type <str> expCondicional
%type <str> expOr
%type <str> opRecursionOr
%type <str> expAnd
%type <str> opRecursionAnd
%type <str> expIgualdad
%type <str> expRelacional
%type <str> desigualdad
%type <str> noTerminal3
%type <str> declarador







%%

input:    
    | input line
;

line: declaracion 
    | sentencia
    | error ';' 
     ;


declaracion: NOMBRE_TIPO {eliminarNodosVariablesAdeclarar(&variablesAdeclarar);tipo=$<str>1;} punteroOpc IDENTIFICADOR arrayOpc {id = $<str>4;}  bifurcacionDeclaraciones
;

punteroOpc: /* vacio */
    | '*' punteroOpc {cantPunteros++;}
;

arrayOpc: /* vacio */
    | '[' exp ']' arrayOpc {flagArray=1;}

bifurcacionDeclaraciones: declaracionFuncion 
    | declaracionVariable 
; 

declaracionVariable: inicializacionOpcional {insertarVariables(&variablesAdeclarar,tipo, id, linActual,cantPunteros,flagArray, $<str>1); cantPunteros = 0;flagArray=0;} op_RecursionListaDeDeclaradores ';' {declararVariablesProvisorias(variablesAdeclarar,&listaDeVars);}
;

op_RecursionListaDeDeclaradores: 
    |','  declarador {insertarVariables(&variablesAdeclarar,tipo, id, linActual,cantPunteros,flagArray,$<str>2);cantPunteros = 0;flagArray=0;} op_RecursionListaDeDeclaradores 
;
    

declarador: punteroOpc IDENTIFICADOR arrayOpc {id = $2;} inicializacionOpcional{$$=$<str>5;} //{$$ = $2; /*printf("Finaliza declaración de %s\n", $<str>1);*/ }
;

inicializacionOpcional: /* vacio */ { $$ = "op";} 
    | '=' exp {$$ = $<str>2; } 
;

// Arrancan las expresiones

exp: expAsignacion { $$ = $<str>1; } 

expAsignacion: expCondicional { $$ = $<str>1; } 
    | expUnaria operAsignacion expAsignacion { $$ = $3; }
;

operAsignacion: '=' | OP_MAS_IG;

expCondicional: expOr { $$ = $1; } 
    | expOr '?' exp ':' expCondicional  { $$ = $<str>2; } 
;

expOr: expAnd opRecursionOr { $$ = $<str>1; }
; 

opRecursionOr: /*vacio*/ { $$ = ""; }
    | OR expAnd opRecursionOr { $$ = $<str>2; }
;

expAnd: expIgualdad opRecursionAnd { $$ = $<str>1; }
;

opRecursionAnd: /*vacio */ { $$ = ""; }
    | AND expIgualdad opRecursionAnd { $$ = $<str>2; }

expIgualdad: expRelacional noTerminal3 { $$ = $<str>1; };

noTerminal3: noTerminal2 noTerminal3 { $$ = $<str>1; }
    | { $$ = ""; }
;

noTerminal2:
    OP_COMP expRelacional 
    | SIMB_DIFF expRelacional 
;

expRelacional: expAditiva opRecursionExpRelacional
;

opRecursionExpRelacional: /*vacio*/ 
    |desigualdad opRecursionExpRelacional

desigualdad: '>''=' expAditiva { $$ = $<str>1; }
    |'>' expAditiva { $$ = $<str>1; }
    |'<''=' expAditiva { $$ = $<str>1; }
    |'<'    expAditiva { $$ = $<str>1; }
;

expAditiva: expMultiplicativa opRecursionExpAditiva { char* res = compararTipos($1, $2, linActual); }
;

opRecursionExpAditiva: /*vacio*/ { $$ = ""; }
    | op_Suma_Resta expMultiplicativa opRecursionExpAditiva { $$ = compararTipos($2, $3, linActual); }

op_Suma_Resta: '+' | '-'
;

expMultiplicativa: expUnaria primerOperando {$$ = $<str>1; }
;

primerOperando: segundoOperando primerOperando
    |
;

segundoOperando: 
    '*' expUnaria 
    | '/' expUnaria 
;

expUnaria: expPostfijo eliminaRecursividad { $$ = $<str>1 ; }
    | OP_ADD expUnaria     {}
    | OP_SUB expUnaria     {}
    | operUnario expUnaria { $$ = $<str>2 ; } 
    
;
operUnario: '*' | '&' | '-' | '!' ;

eliminaRecursividad:
    parteOpcional eliminaRecursividad 
    |
;

parteOpcional: 
    OP_ADD 
    | OP_SUB 
;
    



expPostfijo: expPrimaria opRecursionExpPostfijo /*{ printf("Expprimeria: %s\n", $1); $$ = $1 ;}*/
;


expPrimaria: IDENTIFICADOR {auxIdInvocacion = $1; char* tipo = buscarEnTabla(listaDeVars,$1,linActual) ;$$ = tipo; } 
    | NUM { $$ = "int" ;}
    | FLOAT {$$ = "float";}
    | LITERAL_CADENA { $$ = "string"; }
    | CONSTANTE_CARACTER { $$ = "char"; }
    | '(' exp ')' { $$ = ""; }
;


opRecursionExpPostfijo: /*vacio*/
    |op_Llave_Parentesis opRecursionExpPostfijo
;

op_Llave_Parentesis:'[' exp ']'
    |'(' op_listaArgumentos ')' {validarInvocacion(linActual, auxIdInvocacion, &listaAuxParametrosInv); listaAuxParametrosInv = NULL; }
;

op_listaArgumentos: /*vacio*/ 
    |listaArgumentos 

listaArgumentos: paramInv opRecursionListaArgum
;

opRecursionListaArgum: /* vacio */ 
    |',' paramInv opRecursionListaArgum
;

paramInv: IDENTIFICADOR {insertarParamInv(&listaAuxParametrosInv, obtenerTipo(listaDeVars,$1))}
        | NUM {insertarParamInv(&listaAuxParametrosInv, "int")}
        | FLOAT {insertarParamInv(&listaAuxParametrosInv, "float")}
        | LITERAL_CADENA {insertarParamInv(&listaAuxParametrosInv, "char*")}
        | CONSTANTE_CARACTER {insertarParamInv(&listaAuxParametrosInv, "char")}
;

//Arrancan la declaracion y definicion de funciones

declaracionFuncion:  '('{auxiliarFun.identificador = id; auxiliarFun.numLinea = linActual; auxiliarFun.tipo = tipo; auxiliarFun.parametros = NULL;} op_listaParametros ')'  division_decla_y_def ;

division_decla_y_def: ';'{insertarFuncionesDeclaradas(&listaFunDecla, auxiliarFun.identificador, auxiliarFun.numLinea, auxiliarFun.tipo, listaAuxParametrosDecla); listaAuxParametrosDecla = NULL; } 
    |sentenciaCompuesta {insertarFuncionesDefinidas(&listaFunDecla, auxiliarFun.identificador, auxiliarFun.numLinea, auxiliarFun.tipo, listaAuxParametrosDecla); listaAuxParametrosDecla = NULL; }

op_listaParametros: /*vacio*/
    |listaParametros 
    
listaParametros: parametro op_masParametros

op_masParametros: /*vacio*/
    |',' parametro op_masParametros

parametro:{cantPunteros=0;flagArray=0;} NOMBRE_TIPO punteroOpc IDENTIFICADOR arrayOpc {insertarUltimoParametro(&listaAuxParametrosDecla, $2, $<str>4);insertarVariables(&listaDeVars,$2,$<str>4,linActual,cantPunteros,flagArray,"op");cantPunteros = 0;flagArray=0;}

// Arrancan sentencias

sentencias:/*vacío*/
    | sentencia sentencias
    | declaracion sentencias
    | error ';'
    ;

sentencia: sentenciaCompuesta
        | sentenciaExpresion 
        | sentenciaSalto    
        | sentenciaIteracion
        | sentenciaSeleccion
;

sentenciaCompuesta: '{' sentencias '}' {tipoSent=1; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);}
;

sentenciaExpresion:   opc ';' {tipoSent=2; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);}
;

sentenciaSalto: RETURN  opc ';' {tipoSent=5; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);}
;

/* espacios_puntoycoma: ';'
    | '\n' espacios_puntoycoma
; */


opc:/*vacio*/ 
    |exp ;

sentenciaIteracion: WHILE{tipoSent=4; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);}'('exp')' sentencia
    | DO {tipoSent=4; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);} sentencia WHILE'('exp')'';'
    | FOR{tipoSent=4; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);}'(' opc ';' opc ';' opc ')' sentencia
;

sentenciaSeleccion: IF{tipoSent=3; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);} '(' exp ')' sentencia opcionElse 
    | SWITCH {tipoSent=3; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);}'('exp')' sentencia
;

opcionElse: | ELSE sentencia

%%

/* Llamada por yyparse ante un error */
int yyerror (const char *s) { agregarNodoErrorSintactico (&listaErrorSintactico, linActual); ;return 0; } 

int main() {
    
    //Definiendo el archivo a analizar
    const char *nombre_archivo = "testPresentacion.c";

    //Asignación del archivo de entrada
    yyin = fopen(nombre_archivo, "r");
    if (yyin == NULL) {
        perror("Error al abrir el archivo");
        return 1;
    }

    yyparse(); // Llamar a la función generada por Bison para analizar la entrada
   
    fclose(yyin);

    char opcion;
    do {
        lineaPrincipal1(38);
        printf("%c      Menu: Presione una tecla        %c\n",186,186);
        lineaSeparadora1(38);
        printf("%c1. Imprimir declaraciones de funciones%c\n",186,186);
        printf("%c2. Imprimir lista de variables        %c\n",186,186);
        printf("%c3. Imprimir errores lexicos           %c\n",186,186);
        printf("%c4. Imprimir errores sintacticos       %c\n",186,186);
        printf("%c5. Imprimir errores semanticos        %c\n",186,186);
        printf("%c0. Cerrar Consola                     %c\n",186,186);
        lineaFinal1(38);
        printf("Opcion: ");
        
        opcion = getchar();  
        
        switch (opcion) {
            case '1':
                mostrarListaFuncionesDeclaradas(listaFunDecla);
                break;
            case '2':
                imprimirListaDeVars(listaDeVars);
                break;
            case '3':
                imprimirListaCaracNoReco(listaErroresLexicos);
            break;
            case '4':
                imprimirListaDeErrorSintactico(listaErrorSintactico);
            break;
            case '5':
                imprimirListaDeErroresSemanticos(listaDeErroresSemanticos);
            break;
            case '0':  
                printf("Saliendo...\n");
            break;
            default:
                printf("Opcion no valida. Por favor, elige una opcion valida.\n");
        }
        getchar();
    } while(opcion != '0');

   // getchar();

    return 0;
}