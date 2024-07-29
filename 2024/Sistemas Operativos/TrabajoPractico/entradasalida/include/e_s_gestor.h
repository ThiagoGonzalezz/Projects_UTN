#ifndef E_S_GESTOR_H_
#define E_S_GESTOR_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <unistd.h>
#include <readline/readline.h>
#include <sys/mman.h>
#include <math.h>
#include <dirent.h>
#include <sys/stat.h>

#include "../../utils/include/utils.h"
#include "../../utils/include/inicioYCierre.h"
#include "../../utils/include/protocolo.h"

#include <commons/log.h>
#include <commons/config.h>
#include <commons/string.h>
#include <commons/collections/list.h>
#include <commons/bitarray.h>


#define LOG_FILE_PATH_ENTRADASALIDA "entradasalida.log"
#define LOG_SEC_PATH_ENTRADASALIDA "entradasalida_sec.log"

//VARIABLES GLOBALES
extern t_presentacion_interfaz presentacion;
extern t_log *logger_entradasalida;
extern t_log *logger_sec_io;
extern t_config* config_entradasalida;

extern char *tipo_interfaz;
extern int tiempo_unidad_trabajo;

extern char *ip_kernel;
extern char *puerto_kernel;
extern int fd_kernel;

extern char* ip_memoria;
extern char* puerto_memoria;
extern int fd_memoria;

extern char* path_base_dialfs;
extern int block_size;
extern int block_count;
extern int retraso_compactacion;
extern FILE* bloques;
extern FILE* archivo_bitmap;
extern void* bloques_map;
extern t_bitarray* bitarray_memoria;
extern void* bitmap_memoria;

#endif