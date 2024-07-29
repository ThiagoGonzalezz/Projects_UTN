#ifndef FIFO_H_
#define FIFO_H_

#include"../../k_gestor.h"
#include "../planificacion_corto_plazo.h"

void planificar_corto_plazo_por_fifo();

t_pcb* proximo_a_ejecutar_fifo();

void devolver_a_cpu_por_fifo(t_pcb* pcb);

#endif 
