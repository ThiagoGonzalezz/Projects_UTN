#ifndef INICIO_Y_CIERRE_H
#define INICIO_Y_CIERRE_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <commons/log.h>
#include <commons/config.h>
#include <commons/string.h>

#include <sys/socket.h>
#include <unistd.h>
#include <netdb.h>
#include <assert.h>

#include "utils.h"


// ------------------------------------------------------------------------------------------
// -- Handshakes --
// ------------------------------------------------------------------------------------------

//Borrar despues de cambiarlo en memoria el handshake 4
#define HANDSHAKE_KERNEL 1
#define HANDSHAKE_KERNEL_DISPATCH 5
#define HANDSHAKE_KERNEL_INTERRUPT 6
#define HANDSHAKE_CPU 2
#define HANDSHAKE_MEMORIA 3
#define HANDSHAKE_IO 4 


// ------------------------------------------------------------------------------------------
// -- Declaraciones de funciones --
// ------------------------------------------------------------------------------------------

int iniciar_servidor(t_log *logger, const char *name, char *ip, char *puerto);

t_config *iniciar_config(char *path);

int crear_conexion(char* ip, char* puerto, t_log* logger);

int esperar_cliente(int socket_servidor, t_log* logger);

void liberar_conexion(int socket_cliente);

void terminar_programa(int conexion, t_log *logger, t_config *config);

int levantar_conexion_como_cliente(char*, char*, t_log*);

////////////////////////////////////////////////////////////////////////////////

// Funcion handshake en el cliente
void handshake_cliente(uint32_t handshake, int conexion, t_log* logger, char* nombre_servidor);

int32_t handshake_servidor(int socket_cliente, t_log* logger);

#endif
