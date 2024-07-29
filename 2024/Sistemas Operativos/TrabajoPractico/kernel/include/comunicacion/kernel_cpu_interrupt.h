#ifndef KERNEL_CPU_INTERRUPT_H_
#define KERNEL_CPU_INTERRUPT_H_

#include "../k_gestor.h"
#include "../../utils/include/protocolo.h"

void enviar_interrupcion(uint32_t pid_a_interrumpir, t_tipo_interrupcion tipo);

#endif