#ifndef MAIN_KERNEL_H
#define MAIN_KERNEL_H

#include "k_gestor.h"
#include "utils/utilsKernel.h"
#include "comunicacion/kernel_cpu_interrupt.h"
#include "comunicacion/kernel_cpu_dispatch.h"
#include "comunicacion/kernel_memoria.h"
#include "comunicacion/kernel_interfaces.h"
#include "consola/consola.h"
#include "planificacion/planificacion.h"
#include "manejo_recursos/manejo_recursos.h"

// ------------------------------------------------------------------------------------------
// -- Config y Log --
// ------------------------------------------------------------------------------------------

t_log *logger_kernel;
t_log *logger_secundario_kernel;
t_config* config_kernel;

// ------------------------------------------------------------------------------------------
// -- Conexiones --
// ------------------------------------------------------------------------------------------

char *ip_kernel;

char *ip_cpu;
char *puerto_cpu_dispatch;
char *puerto_cpu_interrupt;
int fd_cpu_dispatch;
int fd_cpu_interrupt;

char *ip_memoria;
char *puerto_memoria;
int fd_k_memoria;

char* puerto_escucha;
int fd_server_interfaces;
int fd_interfaz;

// ------------------------------------------------------------------------------------------
// -- Planificacion --
// ------------------------------------------------------------------------------------------

u_int32_t quantum;
intptr_t grado_multiprogramacion;
char* algoritmo_planificacion;

t_algoritmo_planificador_corto_plazo algoritmo_planificador_corto_plazo;
int flag_planificacion_iniciada;
u_int32_t contador_pids;
intptr_t pid_buscado;
t_pcb* proceso_running;
t_temporal* timer;
int64_t quantum_sobrante;
t_proceso_desalojado* ultimo_desalojo;
bool* flag_termino_de_ejecutar_actual;
t_control_clock controlador_clock;

// ------------------------------------------------------------------------------------------
// -- Interfaces --
// ------------------------------------------------------------------------------------------

char* nombre_interfaz_buscada;
t_pcb* proceso_en_interfaz_buscado;

// ------------------------------------------------------------------------------------------
// -- Recursos --
// ------------------------------------------------------------------------------------------

char** nombres_recursos;
char** instancias_recursos; 
t_dictionary* dic_registro_recursos;
t_pcb* proceso_a_liberar_recursos;

// ------------------------------------------------------------------------------------------
// -- Listas--
// ------------------------------------------------------------------------------------------

t_list *cola_new;
t_list *cola_ready;
t_list *cola_ready_plus;
t_list *cola_eliminacion;
t_list *lista_exit;
t_list *lista_procesos_en_sistema; 
t_list *lista_interfaces_en_sistema;

// ------------------------------------------------------------------------------------------
// -- Mutex --
// ------------------------------------------------------------------------------------------

pthread_mutex_t mutex_cola_new;
pthread_mutex_t mutex_cola_ready;
pthread_mutex_t mutex_cola_ready_plus;
pthread_mutex_t mutex_cola_eliminacion;
pthread_mutex_t mutex_contador_pids;
pthread_mutex_t mutex_lista_exit;
pthread_mutex_t mutex_lista_procesos_en_sistema;
pthread_mutex_t mutex_logger;
pthread_mutex_t mutex_gestion_planificador;
pthread_mutex_t mutex_cambio_en_multiprogamacion;
pthread_mutex_t mutex_comunicacion_memoria;
pthread_mutex_t mutex_lista_interfaces;
pthread_mutex_t mutex_liberacion_recursos;
pthread_mutex_t mutex_clock;
pthread_mutex_t mutex_interrupciones;

// ------------------------------------------------------------------------------------------
// -- Semaforos --
// ------------------------------------------------------------------------------------------

sem_t new_disponible;
sem_t ready_disponible;
sem_t disponibilidad_multiprogramacion;
sem_t sem_planificacion;
sem_t proceso_a_eliminar_disponible;


#endif