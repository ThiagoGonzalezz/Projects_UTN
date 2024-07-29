#ifndef MAIN_CPU_H
#define MAIN_CPU_H

#include "c_gestor.h"
#include "utilsCpu.h"
#include "cpu_kernel_interrupt.h"
#include "cpu_kernel_dispatch.h"
#include "cpu_memoria.h"

#include <../../kernel/include/main.h>
//VARIABLES GLOBALES
t_log *logger_cpu;
t_log* logger_sec_cpu;
t_config* config_cpu;
uint32_t handshake_cpu=2; // VER
char *ip_cpu;

char *puerto_escucha_dispatch;
char *puerto_escucha_interrupt;
int fd_servidor_interrupt;
int fd_servidor_dispatch;
int fd_kernel_interrupt;
int fd_kernel_dispatch; 
t_registros registros_cpu;

char *ip_memoria;
char *puerto_memoria;
int fd_memoria;
bool interrupcion;
int interrupcion_pid;
int interrupcion_motivo;
int pid;
u_int32_t tamanio_pagina;
char* algoritmo_tlb;
int cantidad_entradas_tlb;

t_list *lista_interrupciones;
pthread_mutex_t mutex_lista_interrupciones;

t_proceso_a_ejecutar *a_ejecutar;

#endif

