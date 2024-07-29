#ifndef MEMORIA_CPU_H_
#define MEMORIA_CPU_H_

#include "../m_gestor.h"
#include "../../utils/include/protocolo.h"

void atender_memoria_cpu(void*);
u_int32_t atender_acceso_a_tabla(t_buffer* unBuffer);
t_instruccion* atender_pc(t_buffer* unBuffer);
t_instruccion* buscar_instruccion_pc(int pid, uint32_t pc);
u_int32_t buscarFrame(u_int32_t nroDePagina, u_int32_t pid);
t_proceso_en_memoria* buscarProcesoPorId(uint32_t pid);
u_int32_t calcular_cantPaginasNecesarias(u_int32_t bytes); 
void atender_resize(t_buffer* unBuffer);
void ampliar_proceso(t_proceso_en_memoria* proceso, uint32_t cant_pags_nueva);
void reducir_proceso(t_proceso_en_memoria* proceso, uint32_t cant_pags_nueva);
void* atender_ped_lectura(int pid,int direc_fisica, int cant_bytes_a_leer);
void atender_ped_escritura(t_buffer* unBuffer);


#endif