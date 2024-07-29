#ifndef M_GESTOR_H_
#define M_GESTOR_H_

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

#include "../../utils/include/utils.h"
#include "../../utils/include/inicioYCierre.h"

#include <commons/log.h>
#include <commons/config.h>
#include <commons/collections/list.h>

#define LOG_FILE_PATH_MEMORIA "memoria.log"

//VARIABLES GLOBALES
extern t_log *logger_memoria;
extern t_config* config_memoria;

extern char *ip_memoria;
extern char *puerto_memoria;
extern u_int32_t tam_memoria;
extern u_int32_t tam_pagina;
extern void *espacio_usuario;
extern char* path_instrucciones;
extern u_int32_t cant_frames;
extern u_int32_t retardo_peticiones;
extern t_list *procesosEnMemoria;
extern t_list *frames_memoria;
extern pthread_mutex_t mutex_procesosEnMemoria;
extern u_int32_t cant_frames_disponibles;
extern int fd_server;
extern int fd_cpu;
extern int fd_kernel;
extern int fd_interfaz;

#endif