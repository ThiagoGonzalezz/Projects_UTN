#ifndef C_GESTOR_H_
#define C_GESTOR_H_

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

#include "../../utils/include/utils.h"
#include "../../utils/include/inicioYCierre.h"
#include "../../utils/include/protocolo.h"

#include <commons/log.h>
#include <commons/config.h>
#include <commons/collections/list.h>

#define LOG_FILE_PATH_CPU "cpu.log"
#define LOG_SEC_FILE_PATH_CPU "cpu_sec.log"

//VARIABLES GLOBALES
extern t_log *logger_cpu;
extern t_log *logger_sec_cpu;
extern t_config* config_cpu;

extern t_registros registros_cpu;
extern t_proceso_a_ejecutar* a_ejecutar;

extern char *ip_cpu;

extern int cantidad_entradas_tlb;
extern char *algoritmo_tlb;

extern char *puerto_escucha_dispatch;
extern char *puerto_escucha_interrupt;
extern int fd_servidor_interrupt;
extern int fd_servidor_dispatch;
extern int fd_kernel_interrupt;
extern int fd_kernel_dispatch; 

extern char *ip_memoria;
extern char *puerto_memoria;
extern int fd_memoria;

extern bool interrupcion;
extern int interrupcion_pid;
extern int interrupcion_motivo;
extern int pid;
extern u_int32_t tamanio_pagina;

extern t_list *lista_interrupciones;
extern pthread_mutex_t mutex_lista_interrupciones;

#endif