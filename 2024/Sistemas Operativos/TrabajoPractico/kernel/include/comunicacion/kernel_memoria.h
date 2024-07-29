#ifndef KERNEL_MEMORIA_H_
#define KERNEL_MEMORIA_H_

#include "../k_gestor.h"
#include "../../utils/include/protocolo.h"

void atender_kernel_memoria();

uint32_t validar_creacion_proceso_en_memoria();

void solicitar_creacion_proceso_en_memoria(uint32_t pid, char* path);

void solicitar_eliminar_proceso_a_memoria(uint32_t pid);

#endif