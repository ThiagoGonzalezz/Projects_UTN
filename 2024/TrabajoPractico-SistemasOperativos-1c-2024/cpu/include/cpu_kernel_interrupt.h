#ifndef CPU_KERNEL_INTERRUPT_H_
#define CPU_KERNEL_INTERRUPT_H_

#include "c_gestor.h"

extern int motivo_int;
extern u_int32_t pid_proceso;

void atender_cpu_kernel_interrupt();

#endif