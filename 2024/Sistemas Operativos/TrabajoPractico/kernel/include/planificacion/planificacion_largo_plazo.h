#ifndef PLANIFICACION_LARGO_PLAZO_H_
#define PLANIFICACION_LARGO_PLAZO_H_

#include "../k_gestor.h"
#include "../comunicacion/kernel_memoria.h"
#include "planificacion.h"
 
//////////
void crear_proceso(char* path);

t_pcb* crear_pcb();

uint32_t nuevo_pid();

t_registros iniciar_registros();

t_paquete* empaquetar_inicio_proceso(t_pcb pcb, char* path);

void mandar_proceso_a_new(t_pcb* pcb);

void eliminar_proceso(intptr_t pid);

void ver_como_eliminar(t_pcb* pcb);

void iniciar_hilo_planificador_largo_plazo();

void actualizar_grado_multiprogramacion(intptr_t  nuevoGrado);

void iniciar_hilo_finalizador_de_procesos();

void eliminador_de_procesos();

#endif