#ifndef MEMORIA_KERNEL_H_
#define MEMORIA_KERNEL_H_

#include "../m_gestor.h"
#include "../../utils/include/protocolo.h"

void atender_memoria_kernel(void*);

void atender_crear_proceso(t_buffer* buffer);

void crearProcesoEnMemoria(uint32_t pid, char* path);

void atender_finalizar_proceso(t_buffer* buffer);

void finalizarProceso(uint32_t pid);
#endif