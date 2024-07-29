#ifndef VRR_H
#define VRR_H

#include "../../k_gestor.h"
#include "../planificacion_corto_plazo.h"

void planificar_corto_plazo_vrr();

t_pcb* seleccionar_proximo_proceso_vrr();

void controlar_vrr(bool* flag_volvio_proceso);

void enviar_a_ready_plus(t_pcb* pcb);

void loggear_lista_pids_en_ready_plus();

void devolver_a_cpu_por_vrr(t_pcb* pcb);

void controlar_nuevo_quantum();

bool cola_ready_plus_esta_vacia();

#endif 
