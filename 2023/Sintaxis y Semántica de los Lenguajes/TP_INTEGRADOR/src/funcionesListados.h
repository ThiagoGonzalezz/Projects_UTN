#include "funcionesListados.c"  




/*Funciones para la Tabla de Simbolos de Funciones*/ 

NodoParametros* crearNodoParametros(char* tipo, char* id);

//Funcion para insertar parametros a la lista de cada funcion
void insertarUltimoParametro(NodoParametros** cabeza, char* tipo, char* id);

//Funcion para chequear si los parametros de la funcion definida coinciden con los parametros de la funcion declarada
int parametrosSonIguales(NodoParametros* parametrosDeclarados, NodoParametros* parametrosDefinidos);

//Funcion para crear el nodo de las funciones
NodoFunciones* crearNodoFunciones(char* tipo, char* id, int numeroLinea, NodoParametros* parametros, int tipoError);

//Funcion para insertar ultimo la funcion en su respectiva lista
void insertarUltimoFunciones(NodoFunciones** cabeza, char* idFuncion, char* tipo, int linea, NodoParametros* parametros, int tipoError);

//Funcion para buscar si una funcion con ese identificador ya fue declarada
NodoFunciones* buscarNodoFunciones(NodoFunciones* cabeza, char* idFuncion);

//Funcion para insertar funciones declaradas
void insertarFuncionesDeclaradas(NodoFunciones** lista, char* idFuncion, int linea, char* tipo, NodoParametros* parametros);

//Funcion para insertar funciones definidas
void insertarFuncionesDefinidas(NodoFunciones** listaDeclaradas, /*NodoFunciones** listaDefinidas,*/ char* idFuncion, int linea, char* tipo, NodoParametros* listaParametros);

//Funciones para imprimir Listados de las tablas de simbolos de las funciones declaradas y definidas
void mostrarListaFuncionesDeclaradas(NodoFunciones* cabeza);

//void mostrarListaFuncionesDefinidas(NodoFunciones* cabeza);

//Funcion para chequear si los parametros de la funcion estan bien invocados TP INTEGRADOR
int parametrosBienInvocados(NodoParametros* parametrosDeclarados, NodoParametros* parametrosInvocados)


/* FUNCIONES SENTENCIAS */

extern void agregarNodoSentencia(NodoSentencias** cabeza, int tipo, int linea);
extern NodoSentencias* crearNodoSentencia(int tipoSent, int lin);
extern void mostrarListaSent(NodoSentencias* cabeza);



/* FUNCIONES TABLA DE SIMBOLOS DE VARIABLE */
// Definicion de la estructura del nodo tipo de variable

// Funcion para crear un nuevo nodo del tipo de variable
NodoVarible* crearNodo( char* tipo, char* nombre, int numeroLinea );

// Función para añadir un nodo al final de la lista para el tipo de varible
void agregarNodo(NodoVarible** lista, char* tipo, char* nombre, int numeroLinea );

void imprimirListaDeVars (NodoVarible* lista);

void manejarDeclaraciones(char* tipo,char* identificador,int numLinea);

// funciones para la impresion de errores sintaticos

void agregarNodoErrorSintactico(NodoErrorSintatico** listaErrorSintatico, int numeroLinea );

void agregarNodoErrorSintactico(NodoErrorSintatico** listaErrorSintatico, int numeroLinea );

void imprimirListaDeErrorSintactico(NodoErrorSintatico* listaErrorSintatico);

