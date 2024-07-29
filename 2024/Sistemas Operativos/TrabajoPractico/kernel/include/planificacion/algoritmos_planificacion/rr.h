#ifndef RR_H_
#define RR_H_

#include "../../k_gestor.h"
#include "../planificacion_corto_plazo.h"

void planificar_corto_plazo_rr();

void controlar_rr(bool* flag_volvio_proceso);

t_pcb* proximo_a_ejecutar_rr();

void devolver_a_cpu_por_rr(t_pcb* pcb);

#endif 
