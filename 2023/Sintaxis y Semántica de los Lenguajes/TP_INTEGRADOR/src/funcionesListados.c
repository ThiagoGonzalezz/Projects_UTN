#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "funcionesListadoFachero.c"

extern struct NodoErroresSemanticos* listaDeErroresSemanticos;
extern struct NodoFunciones* listaFunDecla;

//ERRORES SEMANT struct NodoErroresSemanticos* listaDeErroresSemanticos;
typedef struct NodoErroresSemanticos{
    int linea;
    char* identificador;
    char* mensaje;
    struct NodoErroresSemanticos* siguiente;   
}NodoErroresSemanticos;

NodoErroresSemanticos* crearNodoEroresSemanticos(int numeroLinea, char* identificador, char* mensaje ) {
    NodoErroresSemanticos* nuevoNodo = (NodoErroresSemanticos*)malloc(sizeof(NodoErroresSemanticos));
    nuevoNodo->linea = numeroLinea;
    nuevoNodo->identificador = strdup(identificador);
    nuevoNodo->mensaje = strdup(mensaje);
    nuevoNodo->siguiente = NULL;
    return nuevoNodo;
}

void insertarNodoErrorSemantico(NodoErroresSemanticos** lista, int linea, char* identificador, char* mensaje ) {
    NodoErroresSemanticos* nuevoNodo = crearNodoEroresSemanticos(linea,identificador, mensaje);
    
    if (*lista == NULL) {
        *lista = nuevoNodo;
    } else {
        NodoErroresSemanticos* actual = *lista;
        while (actual->siguiente != NULL) {
            actual = actual->siguiente;
        }
        actual->siguiente = nuevoNodo;
    }
}

void imprimirListaDeErroresSemanticos (NodoErroresSemanticos* listaErroresSemanticos){
    NodoErroresSemanticos* actual = listaErroresSemanticos;
    printf("\nLISTA DE ERRORES SEMANTICOS: \n");
    lineaPrincipal3(17,7,87);
    printf("%c %-15s %c %-5s %c %-84s  %c \n",186, "IDENTIFICADOR",186, "LINEA",186, "MENSAJE",186);
    lineaSeparadora3(17,7,87);
    while (actual != NULL) {
        printf("%c %-15s %c %-5d %c %-85s %c\n",186, actual->identificador,186, actual->linea,186, actual->mensaje,186);
        actual = actual->siguiente;
        if(actual != NULL){
            lineaSeparadora3(17,7,87);
        }
    }
    lineaFinal3(17,7,87);        
    printf("\n");

};

/* FUNCIONES TABLA DE SIMBOLOS DE VARIABLE */
// Definicion de la estructura del nodo tipo de variable
typedef struct NodoVarible {
    char* nombre;
    char* tipo;
    int numLinea;
    int cantPunteros;
    int flagArray;
    struct NodoVarible* siguiente;
} NodoVarible;

// Funcion para crear un nuevo nodo del tipo de variable
NodoVarible* crearNodo( char* tipo, char* nombre, int numeroLinea ) {
    NodoVarible* nuevoNodo = (NodoVarible*)malloc(sizeof(NodoVarible));
    nuevoNodo->nombre = strdup(nombre);
    nuevoNodo->tipo = strdup(tipo);
    nuevoNodo->numLinea = numeroLinea;
    nuevoNodo->siguiente = NULL;
    return nuevoNodo;
}

// Función para añadir un nodo al final de la lista para el tipo de varible
void agregarNodo(NodoVarible** lista, char* tipo, char* nombre, int numeroLinea ) {
    NodoVarible* nuevoNodo = crearNodo(tipo,nombre, numeroLinea);
    
    if (*lista == NULL) {
        *lista = nuevoNodo;
    } else {
        NodoVarible* actual = *lista;
        while (actual->siguiente != NULL) {
            actual = actual->siguiente;
        }
        actual->siguiente = nuevoNodo;
    }
}






char* compararTipos (char* primer_tipo, char* segundo_tipo,int numLinea){
    if(strcmp(segundo_tipo, "") != 0 && !(strcmp(primer_tipo, "error") == 0 || strcmp(segundo_tipo, "error") == 0  )&& strcmp(primer_tipo, segundo_tipo) != 0 ) {
        char mensaje[100];
        sprintf(mensaje,"Error al operar entre %s y %s", primer_tipo, segundo_tipo);

        insertarNodoErrorSemantico(&listaDeErroresSemanticos,numLinea, "", mensaje);
        return "error";
    }

    return primer_tipo;
}

/* FUNCIONES TABLA DE SIMBOLOS DE FUNCIONES */
//Nodo auxiliar para almacenar temporalmente el tipo, id y parametros en una declaracion

typedef struct NodoParametros {
    char* identificador;
    char* tipo;
    struct NodoParametros* siguiente;
} NodoParametros;

typedef struct NodoAuxiliarF {
    char* identificador;
    char* tipo;
    int numLinea;
    struct NodoParametros* parametros;    
} NodoAuxiliarF;

typedef struct NodoErrorSintatico {
    int numLinea;
    struct NodoErrorSintatico* siguiente;
} NodoErrorSintatico;


//Funciones para crear nodos de los auxiliares
NodoParametros* crearNodoParametros(char* tipo, char* id) {
    NodoParametros* nuevoNodo = (NodoParametros*)malloc(sizeof(NodoParametros));
    nuevoNodo->identificador = strdup(id);
    nuevoNodo->tipo = strdup(tipo);
    nuevoNodo->siguiente = NULL;
    return nuevoNodo;
}

//Funcion para insertar parametros a la lista de cada funcion
void insertarUltimoParametro(NodoParametros** cabeza, char* tipo, char* id){
    NodoParametros* nuevoNodo = crearNodoParametros(tipo, id);
    if (*cabeza == NULL) {
        *cabeza = nuevoNodo;
    } else {
        NodoParametros* actual = *cabeza;
        while (actual->siguiente != NULL) {
            actual = actual->siguiente;
        }
        actual->siguiente = nuevoNodo;
    }
}

//Funcion para chequear si los parametros de la funcion definida coinciden con los parametros de la funcion declarada
int parametrosSonIguales(NodoParametros* parametrosDeclarados, NodoParametros* parametrosDefinidos) {
    
    while (parametrosDeclarados != NULL && parametrosDefinidos != NULL) { //mientras sigo habiendo parametros en ambas listas sigo comparando
        
        if (strcmp(parametrosDeclarados->tipo, parametrosDefinidos->tipo)) {
            return 0; //Si los parametros tienen distinto id o tipo la lista de parametros son diferentes

        }
        parametrosDeclarados = parametrosDeclarados->siguiente;
        parametrosDefinidos = parametrosDefinidos->siguiente;
    }
    
   
    if (parametrosDeclarados != NULL || parametrosDefinidos != NULL) {
        return 0; // Si una lista es más larga que la otra, la lista de parametros es diferente
    }
    
    return 1; // Si no pasa ninguno de esos casos, las listas de parametros son iguales
}

//Nodo Funciones declaradas y definidas
typedef struct NodoFunciones {
    char* identificador;
    char* tipo;
    int numLinea;
    struct NodoParametros* parametros;
    struct NodoFunciones* siguiente;
    int flagError;
    int flagDefinida;    
} NodoFunciones;

//Funcion para crear el nodo de las funciones
NodoFunciones* crearNodoFunciones(char* tipo, char* id, int numeroLinea, NodoParametros* parametros, int tipoError, int flagDefinida) {
    NodoFunciones* nuevoNodo = (NodoFunciones*)malloc(sizeof(NodoFunciones));
    nuevoNodo->identificador = strdup(id);
    nuevoNodo->tipo = strdup(tipo);
    nuevoNodo->numLinea = numeroLinea;
    nuevoNodo->parametros = parametros;
    nuevoNodo->siguiente = NULL;
    nuevoNodo->flagError = tipoError;
    nuevoNodo->flagDefinida= flagDefinida;
    return nuevoNodo;
}

//Funcion para insertar ultimo la funcion en su respectiva lista
void insertarUltimoFunciones(NodoFunciones** cabeza, char* idFuncion, char* tipo, int linea, NodoParametros* parametros, int tipoError, int flagDefinida){
    NodoFunciones* nuevoNodo = crearNodoFunciones(tipo, idFuncion, linea, parametros, tipoError, flagDefinida);
    if (*cabeza == NULL) {
        *cabeza = nuevoNodo;
    } else {
        NodoFunciones* actual = *cabeza;
        while (actual->siguiente != NULL) {
            actual = actual->siguiente;
        }
        actual->siguiente = nuevoNodo;
    }
}

//Funcion para buscar si una funcion con ese identificador ya fue declarada
NodoFunciones* buscarNodoFunciones(NodoFunciones* cabeza, char* idFuncion) {
    NodoFunciones* actual = cabeza;

    while (actual != NULL) {
        if (strcmp(actual->identificador, idFuncion) == 0) {
            return actual;
        }
        actual = actual->siguiente;
    }

    return NULL;
}

//Funcion para insertar funciones declaradas
void insertarFuncionesDeclaradas(NodoFunciones** lista, char* idFuncion, int linea, char* tipo, NodoParametros* parametros) {
    NodoFunciones* encontro = buscarNodoFunciones(*lista, idFuncion);
    if(encontro==NULL){
        insertarUltimoFunciones(lista, idFuncion, tipo, linea, parametros, 0, 0); // 0 porque no hay error 
    }else{
        insertarNodoErrorSemantico(&listaDeErroresSemanticos, linea, idFuncion, "La funcion ya fue declarada previamente"); //1 porque hay error de doble declaracion de esa funcion, por lo que es invalida
    }
    }

//Funcion para insertar funciones definidas
void insertarFuncionesDefinidas(NodoFunciones** listaDeclaradas, char* idFuncion, int linea, char* tipo, NodoParametros* listaParametros) {
    NodoFunciones* encontroDeclarada = buscarNodoFunciones(*listaDeclaradas, idFuncion);
    
    
    //Si no fue declarada insertar en la lista de declaradas
    if(encontroDeclarada == NULL ){
        insertarUltimoFunciones(listaDeclaradas, idFuncion, tipo, linea, listaParametros, 0, 1); //1 porque se define la función 
        //como no fue declarada, al definirla se la declara sin error

    }else if((strcmp(encontroDeclarada->tipo,tipo) == 0) && parametrosSonIguales(encontroDeclarada->parametros, listaParametros) && (encontroDeclarada->flagDefinida == 0)){
        encontroDeclarada->flagDefinida=1;
        //La función ya fue declarada y solo se la está definiendo, no hay error

    }else if(strcmp(encontroDeclarada->tipo,tipo) != 0){
        insertarNodoErrorSemantico(&listaDeErroresSemanticos, linea, idFuncion, "La funcion ya fue declarada previamente con otro tipo de dato.");

    }else if(parametrosSonIguales(encontroDeclarada->parametros,listaParametros) !=1 ){
        insertarNodoErrorSemantico(&listaDeErroresSemanticos, linea, idFuncion, "La funcion ya fue declarada previamente con distintos parametros.");

    }else if(encontroDeclarada->flagDefinida){
        insertarNodoErrorSemantico(&listaDeErroresSemanticos, linea, idFuncion ,"La funcion ya fue definida previamente.");}
}

void imprimirRestoParametros(NodoParametros* parametrosDeclarados){
    NodoParametros* actual = parametrosDeclarados;
    if(actual){actual = actual->siguiente;}
     while (actual != NULL) {
        const char* auxId = actual->identificador;
        const char* auxEspacio = " ";
        char* auxTipo = strcat(actual -> tipo, auxEspacio);
        char* parametro = strcat(auxTipo, auxId);
        printf("%c                 %c                 %c %-15s %c                 %c\n",186,186,186, parametro,186,186);
        actual = actual->siguiente;
     }
}

const char* primerParametro(NodoParametros* parametrosDeclarados){
    NodoParametros* actual = parametrosDeclarados;
    
    if(actual){
    const char* auxId = actual->identificador;
    char* auxTipo = strcat(actual -> tipo, " ");
    char* parametro = strcat(auxTipo, auxId); 
    return parametro;
    }else{
    char* parametro = strdup(" ");
    return parametro;
    }
}

//Funciones para imprimir Listados de las tablas de simbolos de las funciones declaradas y definidas

void mostrarListaFuncionesDeclaradas(NodoFunciones* cabeza) {
    NodoFunciones* actual = cabeza;

    printf("\nLISTADO DE FUNCIONES DECLARADAS:\n");
    lineaPrincipal4(17,17,17,17);
    printf("%c %-15s %c %-15s %c %-15s %c %-15s %c \n",186, "ID_FUNCION",186, "TIPO", 186,"PARAMETROS",186, "NRO LINEA",186);
    lineaSeparadora4(17,17,17,17);

    while (actual != NULL) {
        printf("%c %-15s %c %-15s %c %-15s %c %-15d %c\n",186, actual->identificador,186, actual->tipo,186, primerParametro(actual->parametros),186, actual->numLinea,186/*,  "Funcion declarada correctamente.                        |" */);
        imprimirRestoParametros(actual -> parametros);
        actual = actual->siguiente;
        if(actual != NULL){
            lineaSeparadora4(17,17,17,17);
        }
    }
    lineaFinal4(17,17,17,17);
    printf("\n");
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/* FUNCIONES TABLA DE SIMBOLOS DE VARIABLE */
// Definicion de la estructura del nodo tipo de variable


// Funcion para crear un nuevo nodo del tipo de variable

NodoVarible* crearNodoVar(char* id, int linea,char* tipo,int cantPunteros,int flagArray) {
    NodoVarible* nuevoNodo = (NodoVarible*)malloc(sizeof(NodoVarible));
    nuevoNodo->nombre = strdup(id);
    nuevoNodo->tipo = strdup(tipo);
    nuevoNodo->numLinea = linea;
    nuevoNodo->numLinea = linea;
    nuevoNodo->cantPunteros = cantPunteros;
    nuevoNodo->flagArray = flagArray;
    nuevoNodo->siguiente = NULL;

    return nuevoNodo;
}
void insertarUltimoVar(NodoVarible** cabeza, char* id, int linea,char* tipo,int cantPunteros, int flagArray){
    NodoVarible* nuevoNodo = crearNodoVar(id, linea, tipo,cantPunteros,flagArray);
    if (*cabeza == NULL) {
        *cabeza = nuevoNodo;
    } else {
        NodoVarible* actual = *cabeza;
        while (actual->siguiente != NULL) {
            actual = actual->siguiente;
        }
        actual->siguiente = nuevoNodo;
    }
}
NodoVarible* buscarNodoVariable(NodoVarible* cabeza, char* id) {
    NodoVarible* actual = cabeza;

    while (actual != NULL) {
        if (strcmp(actual->nombre, id) == 0) {
            return actual;
        }
        actual = actual->siguiente;
    }

    return NULL;
}

void imprimirListaDeVars (NodoVarible* lista) {

    printf("\nListado de variables definidas:\n");
    lineaPrincipal5(8,22,13,13,7);
    printf("%c Tipo   %c Identificador        %c Es Puntero? %c  Es Array?  %c Linea       %c\n",186,186,186,186,186,186);
    lineaSeparadora5(8,22,13,13,2);

    NodoVarible* actual = lista;

    while (actual != NULL) {
        char* puntero;
        char* array;
        if(actual->cantPunteros == 0){puntero = "No";}
        else{puntero = "Si";}
        if(actual->flagArray == 0){array = "No";}
        else{array = "Si";}
        printf("%c %-6s %c %-20s %c %-11s %c %-11s %c %-11i %c\n",186, actual->tipo, 186 ,actual->nombre, 186,puntero,186,array,186, actual->numLinea,186 );
        actual = actual->siguiente;
    }

    lineaFinal5(8,22,13,13,7);
}

void insertarVariables(NodoVarible** listaVar, char* tipo,char* identificador, int linea,int cantPunteros, int flagArray,char* tipoExp){
    if( strcmp(tipoExp, "op") != 0 && strcmp(tipo, tipoExp) != 0 ) {
        char mensaje[100];
        sprintf(mensaje,"Error en la asignacion de la variable, tipos incompatibles", identificador );

        insertarNodoErrorSemantico(&listaDeErroresSemanticos,linea, identificador, mensaje);

       
    }else{

    NodoVarible* encontroDec = buscarNodoVariable(*listaVar, identificador);

    //Si no fue declarada insertar en la lista de declaradas
    if(encontroDec == NULL ){
        insertarUltimoVar(listaVar, identificador, linea, tipo,cantPunteros,flagArray); //1 porque se define la función 
        //como no fue declarada, al definirla se la declara sin error

    }else if(strcmp(encontroDec->tipo,tipo) != 0){
        insertarNodoErrorSemantico(&listaDeErroresSemanticos, linea, identificador, "La variable ya fue declarada previamente con otro tipo de dato.");

    }else if(strcmp(encontroDec->tipo,tipo) == 0){
        insertarNodoErrorSemantico(&listaDeErroresSemanticos, linea, identificador, "La variable ya fue declarada previamente con el mismo tipo de dato.");
    }
   }
}


void declararVariablesProvisorias (NodoVarible* listaVarADeclarar, NodoVarible** listaDeVariables) {
    while (listaVarADeclarar != NULL) {
        insertarVariables(listaDeVariables, listaVarADeclarar->tipo, listaVarADeclarar->nombre, listaVarADeclarar->numLinea,listaVarADeclarar->cantPunteros,listaVarADeclarar->flagArray, listaVarADeclarar->tipo);

        listaVarADeclarar = listaVarADeclarar->siguiente;
    }
}

//eliminar nodos de lsita de variables
void eliminarNodosVariablesAdeclarar(NodoVarible** listaVarADeclarar){
    NodoVarible* actual = *listaVarADeclarar;
    NodoVarible* aux;
    while (actual != NULL) {
        aux = actual;
        actual = actual->siguiente;
        free(aux);
    }
    *listaVarADeclarar = NULL;
}


// buscar el tipo de un identificador (unirlo con el otro buscar)
char* buscarEnTabla(NodoVarible* lista ,char* id,int linea) {
  
   NodoVarible* varAux = buscarNodoVariable(lista,id);
    if ( varAux == NULL) {
        insertarNodoErrorSemantico(&listaDeErroresSemanticos,linea, id, "La variable no fue declarada previamente.");
        return "error";
    }
    if(varAux->flagArray == 1){
        return "array";
    }
    if(varAux->cantPunteros > 0){
        return "puntero";
    }
    return varAux->tipo;

     // Si no se encuentra el valor, se devuelve "error"
}

// Funciones de sentencias

typedef struct NodoSentencias {
    char* tipoSentencia;
    int linea;
    struct NodoSentencias* sgte;
} NodoSentencias;

NodoSentencias* crearNodoSentencia(int tipoSent, int lin) {
    NodoSentencias* nuevoNodoSent = (NodoSentencias*)malloc(sizeof(NodoSentencias));
    nuevoNodoSent->tipoSentencia = (char*)malloc(50);  
    switch(tipoSent) {
        case 1: strcpy(nuevoNodoSent->tipoSentencia, "Sentencia Compuesta"); break;
        case 2: strcpy(nuevoNodoSent->tipoSentencia, "Sentencia de Expresion"); break;
        case 3: strcpy(nuevoNodoSent->tipoSentencia, "Sentencia de Seleccion"); break;
        case 4: strcpy(nuevoNodoSent->tipoSentencia, "Sentencia de Iteracion"); break;
        case 5: strcpy(nuevoNodoSent->tipoSentencia, "Sentencia de Salto"); break;
        default: break;
    }
    nuevoNodoSent->linea=lin;
    nuevoNodoSent->sgte = NULL;
    return nuevoNodoSent;
}

void agregarNodoSentencia(NodoSentencias** cabeza, int tipoSent, int lin) {
    NodoSentencias* nuevoNodoSent = crearNodoSentencia(tipoSent,lin);
    
    if (*cabeza == NULL) {
        *cabeza = nuevoNodoSent;
    } else {
        NodoSentencias* actual = *cabeza;
        while (actual->sgte != NULL) {
            actual = actual->sgte;
        }
        actual->sgte = nuevoNodoSent;
    }
}
  
void mostrarListaSent(NodoSentencias* cabeza) {
    NodoSentencias* actual = cabeza;
    printf("\nListado de sentencias: \n");
    lineaPrincipal2(24,14);
    printf("%c          Tipo          %c     Linea    %c\n",186,186,186);
    lineaSeparadora2(24,14);

    while (actual != NULL) {
        printf("%c %-22s %c %-12d %c\n",186, actual->tipoSentencia,186, actual->linea,186);
        actual = actual->sgte;
        if(actual != NULL){
            lineaSeparadora2(24,14);
        }
        
    }        
    lineaFinal2(24,14);
}

// funciones para la impresion de errores sintaticos

NodoErrorSintatico* crearNodoErrorSintatico( int numeroLinea ) {
    NodoErrorSintatico* nuevoNodo = (NodoErrorSintatico*)malloc(sizeof(NodoErrorSintatico));
    nuevoNodo->numLinea = numeroLinea;
    nuevoNodo->siguiente = NULL;
    return nuevoNodo;
}

void agregarNodoErrorSintactico(NodoErrorSintatico** listaErrorSintatico, int numeroLinea ){
    NodoErrorSintatico* nuevoNodo = crearNodoErrorSintatico(numeroLinea);
    
    if (*listaErrorSintatico == NULL) {
        *listaErrorSintatico = nuevoNodo;
    } else {
        NodoErrorSintatico* actual = *listaErrorSintatico;
        while (actual->siguiente != NULL) {
            actual = actual->siguiente;
        }
        actual->siguiente = nuevoNodo;
    }
};

void imprimirListaDeErrorSintactico (NodoErrorSintatico* listaErrorSintatico){
    NodoErrorSintatico* actual = listaErrorSintatico;
    printf("\nLista de Errores Sintacticos: \n");
    lineaPrincipal1(30);
    printf("%c             Linea            %c\n",186,186);
    lineaSeparadora1(30);

    while (actual != NULL) {
        printf("%c %-28d %c\n",186, actual->numLinea,186);
        actual = actual->siguiente;
        if(actual != NULL){
            lineaSeparadora1(30);
        }
    }        
    lineaFinal1(30);
};

//VALIDACION DE PARAMETROS

typedef struct NodoParametrosInv{
    char* tipo;
    struct NodoParametrosInv* siguiente;
}NodoParametrosInv;

NodoParametrosInv* crearNodoParametrosInv(char* tipo){
    NodoParametrosInv* nuevoNodo = (NodoParametrosInv*)malloc(sizeof(NodoParametrosInv));
    nuevoNodo->tipo = strdup(tipo);
    nuevoNodo->siguiente = NULL;
    return nuevoNodo;
}

void insertarParamInv(NodoParametrosInv** cabeza, char* tipo){
    NodoParametrosInv* nuevoNodo = crearNodoParametrosInv(tipo);
    if (*cabeza == NULL) {
        *cabeza = nuevoNodo;
    } else {
        NodoParametrosInv* actual = *cabeza;
        while (actual->siguiente != NULL) {
            actual = actual->siguiente;
        }
        actual->siguiente = nuevoNodo;
    }
}

NodoVarible* buscarVariable(NodoVarible* cabeza, char* idVar) {
    NodoVarible* actual = cabeza;

    while (actual != NULL) {
        if (strcmp(actual->nombre, idVar) == 0) {
            return actual;
        }
        actual = actual->siguiente;
    }

    return NULL;
}


char* obtenerTipo(NodoVarible* listaDeVars,char* identificador) {
    NodoVarible* encontro = buscarVariable(listaDeVars, identificador);
    if (encontro){  
        return encontro->tipo;
    } else {
        return "noDeclarado";
    }
}

//Funcion para chequear si en una invocacion de funcion los parametros son correctos
void parametrosBienInvocados(NodoParametros* parametrosDeclarados, NodoParametrosInv* parametrosInvocados, int linea, char* idInvocacion){
    
    NodoParametros* listaAuxParamDecla = parametrosDeclarados;
    NodoParametrosInv* listaAuxParamInv = parametrosInvocados;

    while (listaAuxParamDecla != NULL && listaAuxParamInv != NULL) { //mientras sigo habiendo parametros en ambas listas sigo comparando
        
        if (strcmp(listaAuxParamDecla->tipo, listaAuxParamInv->tipo)) {
            if(strcmp(listaAuxParamInv->tipo,"noDeclarado") == 0){ //Si no esta declarada
                insertarNodoErrorSemantico(&listaDeErroresSemanticos,linea, idInvocacion, "El parametro utilizado en la invocacion no fue declarado previamente");
                return;
            }else{
                insertarNodoErrorSemantico(&listaDeErroresSemanticos,linea, idInvocacion, "El tipo de los parametros invocados no es el correcto");
                return;
           //Si los parametros tienen distinto tipo, la funcion fue invocada erroneamente
            }
        }
        listaAuxParamInv = listaAuxParamInv->siguiente;
        listaAuxParamDecla = listaAuxParamDecla->siguiente;
        
    }
    
    if ((listaAuxParamInv != NULL) || (listaAuxParamDecla != NULL)) {
        insertarNodoErrorSemantico(&listaDeErroresSemanticos,linea, idInvocacion, "La cantidad de parametros utilizados en la invocacion es incorrecta");
       //Si la cantidad de parametros es distinta, la funcion fue invocada erroneamente
    }
    // Si no pasa ninguno de esos casos, la funcion fue invocada correctamente
}

void validarInvocacion(int linea, char* idInvocacion, NodoParametrosInv** parametrosInv){
    NodoFunciones* funcionDecla = buscarNodoFunciones(listaFunDecla, idInvocacion);
    
    if(funcionDecla == NULL){insertarNodoErrorSemantico(&listaDeErroresSemanticos, linea, idInvocacion, "La funcion invocada no se encuentra declarada previamente");
    }else{parametrosBienInvocados(funcionDecla->parametros, *parametrosInv, linea, idInvocacion);}
}
 
