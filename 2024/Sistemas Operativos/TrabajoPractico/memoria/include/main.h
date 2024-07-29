#ifndef MAIN_MEMORIA_H_
#define MAIN_MEMORIA_H_

#include "m_gestor.h"
#include "../include/utils/utilsMemoria.h"
#include "../include/comunicacion/memoria_cpu.h"
#include "../include/comunicacion/memoria_kernel.h"
#include "../include/comunicacion/memoria_interfaz.h"

//VARIABLES GLOBALES
t_log *logger_memoria;
t_config* config_memoria;

char *ip_memoria;
char *puerto_memoria;
u_int32_t tam_memoria;
u_int32_t tam_pagina;
char* path_instrucciones;
u_int32_t retardo_peticiones;
void *espacio_usuario;
u_int32_t cant_frames;
t_list *procesosEnMemoria;
t_list *frames_memoria;
pthread_mutex_t mutex_procesosEnMemoria;
u_int32_t cant_frames_disponibles;

int fd_server;
int fd_cpu;
int fd_kernel;
int fd_interfaz;

#endif