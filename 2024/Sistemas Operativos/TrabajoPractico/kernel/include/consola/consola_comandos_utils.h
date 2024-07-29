#ifndef CONSOLA_COMANDOS_UTILS_H_
#define CONSOLA_COMANDOS_UTILS_H_

#include "../k_gestor.h"

void mostrar_proceso(t_pcb* pcb);

void mostrar_procesos_del_estado(t_list* estado);

void mostrar_proceso_running();

void mostrar_procesos_bloqueados();

bool esta_bloqueado(t_pcb* pcb);

#endif