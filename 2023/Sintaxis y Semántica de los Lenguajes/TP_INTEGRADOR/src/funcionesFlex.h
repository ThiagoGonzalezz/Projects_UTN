#include "funcionesFlex.c"

/// Funciones Listado Caracteres No Reconocidos


NodoCaractNoReconocidos* crearNodoCNR(const char* caracter, int linea, int col);

NodoCaractNoReconocidos* buscarUltimoNodo(NodoCaractNoReconocidos* lista);

void agregarNodoCaracNoReco(NodoCaractNoReconocidos** cabeza, const char* caracter, int linea, int col);

void insertarCaracteresNoReconocidos(NodoCaractNoReconocidos** listaCaracNoRecc, const char* palabra, int lineaAct, int colAct);

void imprimirListaCaracNoReco(NodoCaractNoReconocidos* listaCaracNoReco);

