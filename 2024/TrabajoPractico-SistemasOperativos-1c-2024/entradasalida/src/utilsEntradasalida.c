#include "../include/utilsEntradasalida.h"

void levantar_log_y_config(char* path)
{
    config_entradasalida = config_create(path);
    logger_entradasalida = log_create(LOG_FILE_PATH_ENTRADASALIDA, "ENTRADASALIDA", true, LOG_LEVEL_INFO);
    logger_sec_io = log_create(LOG_SEC_PATH_ENTRADASALIDA, "ENTRADASALIDA", true, LOG_LEVEL_INFO);

    ip_memoria = config_get_string_value(config_entradasalida,"IP_MEMORIA");
    puerto_memoria = config_get_string_value(config_entradasalida,"PUERTO_MEMORIA");
    ip_kernel = config_get_string_value(config_entradasalida,"IP_KERNEL");
    puerto_kernel = config_get_string_value(config_entradasalida,"PUERTO_KERNEL");
    tipo_interfaz = config_get_string_value(config_entradasalida, "TIPO_INTERFAZ");
	tiempo_unidad_trabajo = config_get_int_value(config_entradasalida, "TIEMPO_UNIDAD_TRABAJO");

    if(strcmp(tipo_interfaz, "DIALFS") == 0){
        path_base_dialfs = config_get_string_value(config_entradasalida,"PATH_BASE_DIALFS");
        block_size = config_get_int_value(config_entradasalida,"BLOCK_SIZE");
        block_count = config_get_int_value(config_entradasalida,"BLOCK_COUNT");
        retraso_compactacion = config_get_int_value(config_entradasalida,"RETARDO_COMPACTACION");
        printf("CONFIG PATH_BASE_DIALFS: %s\n", path_base_dialfs);    
    }

    log_info(logger_sec_io, "TIPO DE INTERFAZ: %s | TIEMPO DE LA UNIDAD DE TRABAJO: %d\n", tipo_interfaz, tiempo_unidad_trabajo);
    log_info(logger_sec_io, "IP_KERNEL: %s | PUERTO_KERNEL: %s \n", ip_kernel, puerto_kernel);
    log_info(logger_sec_io, "IP_MEMORIA: %s | PUERTO_MEMORIA: %s \n", ip_memoria, puerto_memoria);
}

void terminar_programa_entradasalida(){    
    liberar_conexion(fd_kernel);
    terminar_programa(fd_memoria, logger_entradasalida, config_entradasalida);
}