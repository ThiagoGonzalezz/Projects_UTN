#ifndef MAIN_ENTRADA_SALIDA_H_
#define MAIN_ENTRADA_SALIDA_H_

#include "e_s_gestor.h"
#include "utilsEntradasalida.h"
#include "interfaz_kernel.h"

#include <commons/bitarray.h>

t_log *logger_entradasalida;
t_log *logger_sec_io;
t_config* config_entradasalida;
uint32_t handshake_io=4;
t_presentacion_interfaz presentacion;

char* path_base_dialfs;
char* path_al_FS;
int block_size;
int block_count;
int retraso_compactacion;
FILE* bloques;
FILE* archivo_bitmap;
void* bloques_map;
t_bitarray* bitarray_memoria;
void* bitmap_memoria;

char *tipo_interfaz;
int tiempo_unidad_trabajo;

char *ip_kernel;
char *puerto_kernel;
int fd_kernel;

char* ip_memoria;
char* puerto_memoria;
int fd_memoria;

t_list* lista_nombre_archivos;

bool esNombreProhibido(void*);

void atender_segun_interfaz();
void levantar_archivos_base();

#endif