#ifndef MEMORIA_INTERFAZ_H_
#define MEMORIA_INTERFAZ_H_

#include "../m_gestor.h"
#include "../../utils/include/protocolo.h"

void atender_memoria_interfaz(void*);

void* atender_ped_lectura_io(int direc_fisica, int cant_bytes_a_leer);
void atender_ped_escritura_io(t_buffer* unBuffer);

#endif