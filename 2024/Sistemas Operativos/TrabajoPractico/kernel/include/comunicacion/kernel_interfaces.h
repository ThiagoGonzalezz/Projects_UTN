#ifndef KERNEL_INTERFACES_H_
#define KERNEL_INTERFACES_H_

#include "../k_gestor.h"
#include "../manejo_interfaces/manejo_interfaces.h"
#include "../../utils/include/protocolo.h"

void atender_interfaces_entrantes();

t_presentacion_interfaz recibir_presentacion(int fd_interfaz);

void enviar_solicitud_ejecutar_instruccion(int fd_interfaz, t_instruccion* instruccion_io, uint32_t pid);

t_resultado_io recibir_resultado_io(int fd_interfaz);

#endif