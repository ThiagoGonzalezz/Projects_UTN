#ifndef PLANIFICACION_CORTO_PLAZO_H_
#define PLANIFICACION_CORTP_PLAZO_H_

#include "../k_gestor.h"
#include "../comunicacion/kernel_cpu_dispatch.h"
#include "../comunicacion/kernel_cpu_interrupt.h"
#include "../comunicacion/kernel_interfaces.h"
#include "planificacion.h"
#include "algoritmos_planificacion/fifo.h"
#include "algoritmos_planificacion/rr.h"
#include "algoritmos_planificacion/vrr.h"
#include "../manejo_recursos/manejo_recursos.h"

void iniciar_hilo_planificador_corto_plazo();

void setear_planificacion();

void atender_devolucion(t_pcb* proceso_en_running, t_proceso_desalojado* contexto_desalojo);

void funcion_clock(bool* flag_volvio_proceso);

void activar_clock(bool* flag_volvio_proceso);

void interrumpir_por_clock(uint32_t pid);

void actualizar_contexto(t_pcb* proceso, t_registros contexto);

t_pcb* primero_en_ready();

void planificar_corto_plazo();

void enviar_a_running(t_pcb* pcb);

void atender_syscall(t_pcb* proceso, t_instruccion syscall);

void mutear_clock();

void desmutear_clock();

bool* crear_flag_controlador_de_clock();

void loggear_bloqueo(uint32_t pid, char* nombre_bloqueador);

char* sanitizar_nombre_recurso(char* nombre);

#endif