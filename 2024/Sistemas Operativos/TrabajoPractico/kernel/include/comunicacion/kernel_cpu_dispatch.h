#ifndef KERNEL_CPU_DISPATCH_H_
#define KERNEL_CPU_DISPATCH_H_

#include "../k_gestor.h"
#include "../../utils/include/protocolo.h"

void enviar_a_ejecutar(t_pcb* proceso);

t_proceso_desalojado* recibir_retorno_de_proceso_cpu();

#endif