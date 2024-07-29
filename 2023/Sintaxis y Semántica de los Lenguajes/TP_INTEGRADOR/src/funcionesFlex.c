#include <string.h>
#include <stdio.h>
#include <stdlib.h>

/// Funciones Listado Caracteres No Reconocidos

typedef struct NodoCaractNoReconocidos {
    const char* caracter;
    int linea;
    int primerCol;
    int ultCol;
    struct NodoCaractNoReconocidos* sgte;
} NodoCaractNoReconocidos;

NodoCaractNoReconocidos* crearNodoCNR(const char* caracter, int linea, int col) {
    NodoCaractNoReconocidos* nuevoNodo = (NodoCaractNoReconocidos*)malloc(sizeof(NodoCaractNoReconocidos));
    nuevoNodo->caracter = strdup(caracter);
    nuevoNodo->linea = linea;
    nuevoNodo->primerCol = col;
    nuevoNodo->ultCol = col;
    nuevoNodo->sgte = NULL;
    return nuevoNodo;
}

NodoCaractNoReconocidos* buscarUltimoNodo(NodoCaractNoReconocidos* lista) {
    NodoCaractNoReconocidos* actual = lista;

    while (actual != NULL && actual->sgte != NULL) {
        actual = actual->sgte;
    }

    return actual;
}

void agregarNodoCaracNoReco(NodoCaractNoReconocidos** cabeza, const char* caracter, int linea, int col) {
    NodoCaractNoReconocidos* nuevoNodo = crearNodoCNR(caracter, linea, col);
    if (*cabeza == NULL) {
        *cabeza = nuevoNodo;
    } else {
        NodoCaractNoReconocidos* actual = *cabeza;
        while (actual->sgte != NULL) {
            actual = actual->sgte;
        }
        actual->sgte = nuevoNodo;
    }
}
extern void lineaSeparadora3(int sep1,int sep2,int sep3);
extern void lineaFinal3(int sep1,int sep2,int sep3);
extern void lineaPrincipal3(int sep1,int sep2,int sep3);


void insertarCaracteresNoReconocidos(NodoCaractNoReconocidos** listaCaracNoRecc, const char* palabra, int lineaAct, int colAct){
    NodoCaractNoReconocidos* ultimoNodo = buscarUltimoNodo(*listaCaracNoRecc);
    
    agregarNodoCaracNoReco(listaCaracNoRecc, palabra, lineaAct, colAct);
}

void imprimirListaCaracNoReco(NodoCaractNoReconocidos* listaCaracNoReco) {
    printf("\nListado de Errores Lexicos:\n");
    lineaPrincipal3(6,8,32);
    printf("%cFila  %cColumna %c            Cadena              %c\n",186,186,186,186);
    lineaSeparadora3(6,8,32);

    NodoCaractNoReconocidos* actual = listaCaracNoReco;

    while (actual != NULL) {
        printf("%c %-4d %c %-6d %c %-30s %c\n",186, actual->linea, 186 , actual->primerCol, 186, actual->caracter,186);
        actual = actual->sgte;
    }

    lineaFinal3(6,8,32);
}



