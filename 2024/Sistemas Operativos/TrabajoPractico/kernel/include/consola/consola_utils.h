#ifndef CONSOLA_UTILS_H_
#define CONSOLA_UTILS_H_

#include "../k_gestor.h"
#include "consola_comandos.h"

bool validar_instruccion_consola(char* leido);

int contarParametros(char **array);

bool ejecutar_instruccion_consola(char* leido);

bool atender_instruccion_consola(char* leido);

#endif

