#ifndef PLANIFICACION_H_
#define PLANIFICACION_H_

#include "../k_gestor.h"
#include "planificacion_largo_plazo.h"
#include "planificacion_corto_plazo.h"
#include "../manejo_recursos/manejo_recursos.h"

void detener_planificacion();

void iniciar_planificacion();

void iniciar_hilos_planificacion();

t_pcb* pcb_por_pid(intptr_t pid);

bool tiene_el_pid_buscado(void* pcb);

void enviar_a_exit(t_pcb* pcb, t_motivo_desalojo motivo);

void actualizar_estado_loggeando(t_pcb * pcb, t_estados estado);

void loggear_lista_pids_en_ready();

void enviar_a_ready(t_pcb* pcb);

char* estado_to_string(t_estados estado);

t_pcb* extraer_especifico_protegido(t_list* lista, pthread_mutex_t* mutex, uint32_t pid);

char* motivo_to_string(t_motivo_desalojo motivo);

void validar_planificacion_prendida();

void pausar_cambios_de_estado();

void despausar_cambios_de_estado();

#endif