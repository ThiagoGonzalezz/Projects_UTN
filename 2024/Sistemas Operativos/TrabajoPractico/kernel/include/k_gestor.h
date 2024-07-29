#ifndef K_GESTOR_H_
#define K_GESTOR_H_

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <readline/readline.h>

#include "../../utils/include/utils.h"
#include "../../utils/include/inicioYCierre.h"
#include "utils/utilsKernel.h"

#include <commons/log.h>
#include <commons/config.h>
#include <commons/string.h>
#include <commons/temporal.h>
#include <commons/collections/queue.h>
#include <commons/collections/list.h>
#include <commons/collections/dictionary.h>

#define LOG_FILE_PATH_KERNEL "kernel.log"
#define LOG_FILE_PATH_SECUNDARIO_KERNEL "kernel_secundario.log"

// ------------------------------------------------------------------------------------------
// -- Config y Log --
// ------------------------------------------------------------------------------------------

extern t_log *logger_kernel;
extern t_log *logger_secundario_kernel;
extern t_config* config_kernel;

// ------------------------------------------------------------------------------------------
// -- Conexiones --
// ------------------------------------------------------------------------------------------

extern char *ip_kernel;

extern char *ip_cpu;
extern char *puerto_cpu_dispatch;
extern char *puerto_cpu_interrupt;
extern int fd_cpu_dispatch;
extern int fd_cpu_interrupt;

extern char *ip_memoria;
extern char *puerto_memoria;
extern int fd_k_memoria;

extern char* puerto_escucha;
extern int fd_server_interfaces;
extern int fd_interfaz;

// ------------------------------------------------------------------------------------------
// -- Planificacion --
// ------------------------------------------------------------------------------------------

extern u_int32_t quantum;
extern intptr_t grado_multiprogramacion;
extern char* algoritmo_planificacion;

extern t_algoritmo_planificador_corto_plazo algoritmo_planificador_corto_plazo;
extern int flag_planificacion_iniciada;
extern u_int32_t contador_pids;
extern intptr_t pid_buscado;
extern t_pcb* proceso_running;
extern t_temporal* timer;
extern int64_t quantum_sobrante;
extern t_proceso_desalojado* ultimo_desalojo;
extern bool* flag_termino_de_ejecutar_actual;
extern t_control_clock controlador_clock; 

// ------------------------------------------------------------------------------------------
// -- Interfaces --
// ------------------------------------------------------------------------------------------

extern char* nombre_interfaz_buscada;
extern t_pcb* proceso_en_interfaz_buscado;

// ------------------------------------------------------------------------------------------
// -- Recursos --
// ------------------------------------------------------------------------------------------

extern char** nombres_recursos;
extern char** instancias_recursos; 
extern t_dictionary* dic_registro_recursos;
extern t_pcb* proceso_a_liberar_recursos;
extern pthread_mutex_t mutex_liberacion_recursos;

// ------------------------------------------------------------------------------------------
// -- Listas --
// ------------------------------------------------------------------------------------------

extern t_list *cola_new;
extern t_list *cola_ready;
extern t_list *cola_ready_plus;
extern t_list *cola_eliminacion;
extern t_list *lista_exit;
extern t_list *lista_procesos_en_sistema; 
extern t_list *lista_interfaces_en_sistema;


// ------------------------------------------------------------------------------------------
// -- Mutex --
// ------------------------------------------------------------------------------------------

extern pthread_mutex_t mutex_cola_new;
extern pthread_mutex_t mutex_cola_ready;
extern pthread_mutex_t mutex_cola_ready_plus;
extern pthread_mutex_t mutex_cola_eliminacion;
extern pthread_mutex_t mutex_contador_pids;
extern pthread_mutex_t mutex_lista_exit;
extern pthread_mutex_t mutex_lista_procesos_en_sistema;
extern pthread_mutex_t mutex_logger;
extern pthread_mutex_t mutex_gestion_planificador;
extern pthread_mutex_t mutex_cambio_en_multiprogamacion;
extern pthread_mutex_t mutex_comunicacion_memoria;
extern pthread_mutex_t mutex_lista_interfaces;
extern pthread_mutex_t mutex_clock;
extern pthread_mutex_t mutex_interrupciones;

// ------------------------------------------------------------------------------------------
// -- Semaforos --
// ------------------------------------------------------------------------------------------

extern sem_t new_disponible;
extern sem_t ready_disponible;
extern sem_t disponibilidad_multiprogramacion;
extern sem_t sem_planificacion;
extern sem_t proceso_a_eliminar_disponible;

#endif