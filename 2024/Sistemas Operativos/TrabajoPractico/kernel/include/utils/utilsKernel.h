#ifndef UTILS_KERNEL_H
#define UTILS_KERNEL_H

#include "../k_gestor.h"
#include "../comunicacion/kernel_interfaces.h"

void levantar_log_y_config(char*);

void terminar_programa_kernel();

void iniciar_listas_y_colas();

void iniciar_semaforos_y_mutex();

void conectarse_a_cpu_dispatch();

void conectarse_a_cpu_interrupt();

void conectarse_a_memoria();

void crear_hilo_atendedor_de_nuevas_interfaces();

void destruir_listas_y_colas();

void destruir_semaforos_y_mutex();

void encolar(t_list* cola, void* elemento);

void* desencolar_protegido(t_list* cola, pthread_mutex_t* mutex);

void encolar_protegido(t_list* cola, void* elemento, pthread_mutex_t* mutex);

void liberar_parametros(t_instruccion instruccion);

#endif