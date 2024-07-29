#ifndef CONSOLA_COMANDOS_H_
#define CONSOLA_COMANDOS_H_

#include "../k_gestor.h"
#include "consola_utils.h"
#include "consola_comandos_utils.h"
#include "../planificacion/planificacion.h"

void help();

void ejecutar_script(char* path);

void cerrar_consola();

void listar_procesos_por_estado();

void hilo_actualizar_grado_multiprogramacion(intptr_t nuevo_grado);

void iniciar_proceso(char* path);

void hilo_finalizar_proceso(intptr_t pid);

#endif

