#include "../include/utils/utilsMemoria.h"

void levantar_log_y_config(char* path)
{
	config_memoria = config_create(path);
	logger_memoria = log_create(LOG_FILE_PATH_MEMORIA, "MEMORIA", true, LOG_LEVEL_INFO);

    ip_memoria = config_get_string_value(config_memoria,"IP_MEMORIA");
    puerto_memoria = config_get_string_value(config_memoria,"PUERTO_MEMORIA");

    tam_memoria= atoi(config_get_string_value(config_memoria,"TAM_MEMORIA"));
    tam_pagina= atoi(config_get_string_value(config_memoria,"TAM_PAGINA"));
    path_instrucciones= config_get_string_value(config_memoria,"PATH_INSTRUCCIONES");
    retardo_peticiones= atoi(config_get_string_value(config_memoria,"RETARDO_RESPUESTA"));

}

void escuchar_conexiones_entrantes(){

    pthread_t hilo_atender;


    while(1){

        int fd_cliente = esperar_cliente(fd_server, logger_memoria); //FD SERVER ESTA "HARDCODEADO" PORQ ES VAR. GLOBAL
        int cliente = handshake_servidor(fd_cliente, logger_memoria);

        switch(cliente) {
            case 4:
                pthread_create(&hilo_atender, NULL, (void*) atender_memoria_interfaz, (void*) &fd_cliente);
                pthread_detach(hilo_atender);
                break;
            default:
                log_error(logger_memoria, "Cliente desconocido");
                break;
        }
    }
}

void inicializar_frames() {
    for (int i = 0; i < cant_frames; i++) {
        t_frame* frame = malloc(sizeof(t_frame));
        if (frame == NULL) {
            perror("No se pudo asignar memoria para el frame");
            exit(EXIT_FAILURE);
        }
        frame->disponible = true;
        list_add(frames_memoria, frame);
        cant_frames_disponibles ++;
    }
}

void inicializar_memoria(){
    espacio_usuario = malloc(tam_memoria); //Se reservan la cantidad de bytes necesarios para el void* espacio_usuario
    cant_frames = tam_memoria/tam_pagina;
    cant_frames_disponibles = 0;
    pthread_mutex_init(&mutex_procesosEnMemoria, NULL);
    procesosEnMemoria = list_create(); 
    frames_memoria = list_create();
    inicializar_frames();
}

void finalizar_memoria() 
{  
    free(espacio_usuario);
    for (int i = 0; i < list_size(procesosEnMemoria); i++) {
    t_proceso_en_memoria *procesoEnMemoria = list_get(procesosEnMemoria, i);
    liberar_instrucciones_en_lista(procesoEnMemoria->instrucciones);
    }

    list_destroy_and_destroy_elements(frames_memoria, free);
    pthread_mutex_destroy(&mutex_procesosEnMemoria);
}

void terminar_programa_memoria(){
    finalizar_memoria();
	terminar_programa(fd_server, logger_memoria, config_memoria);
}

u_int32_t calcular_cantPaginasNecesarias(u_int32_t cantBytes){
    u_int32_t cant_pagsNecesarias;
    cant_pagsNecesarias = (cantBytes+tam_pagina -1)/ tam_pagina; //hago la cuenta así para redondear cuando da con coma.
                                                                        // Porque si la cuenta da por ejemplo 2.5, el proceso igual va a tener 3 páginas
    return cant_pagsNecesarias;
}

op_code convertir_op_code(char *op_code) { 
    if (strcmp(op_code, "SET") == 0) {
        return SET;
    } else if (strcmp(op_code, "SUM") == 0) {
        return SUM;
    } else if (strcmp(op_code, "SUB") == 0) {
        return SUB;
    } else if (strcmp(op_code, "MOV_IN") == 0) {
        return MOV_IN;
    } else if (strcmp(op_code, "MOV_OUT") == 0) {
        return MOV_OUT;
    } else if (strcmp(op_code, "RESIZE") == 0) {
        return RESIZE;
    } else if (strcmp(op_code, "JNZ") == 0) {
        return JNZ;
    } else if (strcmp(op_code, "IO_GEN_SLEEP") == 0) {
        return IO_GEN_SLEEP;
    } else if (strcmp(op_code, "IO_STDOUT_WRITE") == 0) {
        return IO_STDOUT_WRITE;
    }else if (strcmp(op_code, "IO_STDIN_READ") == 0) {
        return IO_STDIN_READ;
    }else if (strcmp(op_code, "COPY_STRING") == 0) {
        return COPY_STRING;
    }else if (strcmp(op_code, "WAIT") == 0) {
        return WAIT;
    }else if (strcmp(op_code, "SIGNAL") == 0) {
        return SIGNAL;
    }else if (strcmp(op_code, "IO_FS_CREATE") == 0) {
        return IO_FS_CREATE;
    }else if (strcmp(op_code, "IO_FS_DELETE") == 0) {
        return IO_FS_DELETE;
    }else if (strcmp(op_code, "IO_FS_TRUNCATE") == 0) {
        return IO_FS_TRUNCATE;
    }else if (strcmp(op_code, "IO_FS_WRITE") == 0) {
        return IO_FS_WRITE;
    }else if (strcmp(op_code, "IO_FS_READ") == 0) {
        return IO_FS_READ;
    }else if (strcmp(op_code, "EXIT") == 0){
        return FIN;
    } else {
        // si no es una instruccion
        return -1; 
    }
}

t_list* obtener_instrucciones_del_path(char *path_relativo) {
    t_list* instrucciones = list_create();
    if (!instrucciones) {
        fprintf(stderr, "Error al crear la lista de instrucciones\n");
        return NULL;
    }

    char* path_absoluto = malloc(strlen(path_instrucciones) + strlen(path_relativo) + 1);
    if (!path_absoluto) {
        fprintf(stderr, "Error al asignar memoria para el path absoluto\n");
        list_destroy(instrucciones);
        return NULL;
    }

    strcpy(path_absoluto, path_instrucciones);
    string_append(&path_absoluto, path_relativo);

    FILE* archivo = fopen(path_absoluto, "r");
    if (archivo == NULL) {
        fprintf(stderr, "Error al abrir el archivo: %s\n", path_absoluto);
        free(path_absoluto);
        list_destroy(instrucciones);
        return NULL;
    }

    char *linea = NULL;
    size_t len = 0;
    ssize_t read;

    while ((read = getline(&linea, &len, archivo)) != -1) {
        char **tokens = string_split(linea, " ");
        if (!tokens) {
            fprintf(stderr, "Error al dividir la línea en tokens\n");
            continue;
        }

        if (tokens[0] == NULL) {
            // Si la línea está vacía o solo tiene espacios, continúa con la siguiente línea
            string_iterate_lines(tokens, (void *) free);
            free(tokens);
            continue;
        }

        t_instruccion* nuevaInstruccion = malloc(sizeof(t_instruccion));
        if (!nuevaInstruccion) {
            fprintf(stderr, "Error al asignar memoria para nueva instrucción\n");
            string_iterate_lines(tokens, (void *) free);
            free(tokens);
            break;
        }

        nuevaInstruccion->identificador = convertir_op_code(tokens[0]);
        int i;
        for (i = 1; tokens[i] != NULL; i++);

        nuevaInstruccion->parametros = malloc((i) * sizeof(char*));
        if (!nuevaInstruccion->parametros) {
            fprintf(stderr, "Error al asignar memoria para los parámetros de la instrucción\n");
            free(nuevaInstruccion);
            string_iterate_lines(tokens, (void *) free);
            free(tokens);
            break;
        }

        for (int j = 1; j < i; j++) {
            nuevaInstruccion->parametros[j - 1] = strdup(tokens[j]);
        }
        nuevaInstruccion->parametros[i - 1] = NULL;

        list_add(instrucciones, nuevaInstruccion);

        string_iterate_lines(tokens, (void *) free);
        free(tokens);

       /*
        printf("Identificador: %d\n", nuevaInstruccion->identificador);
        printf("Parámetros:\n");
        for (int j = 0; nuevaInstruccion->parametros[j] != NULL; j++) {
            printf("- %s\n", nuevaInstruccion->parametros[j]);
        }*/
    }

    fclose(archivo);
    free(path_absoluto);
    if (linea) {
        free(linea);
    }

    if (list_is_empty(instrucciones)) {
        list_destroy(instrucciones);
        instrucciones = NULL;
    }

    return instrucciones;
}


void crearProcesoEnMemoria(uint32_t pid, char* path){
    pthread_mutex_lock(&mutex_procesosEnMemoria);
    t_proceso_en_memoria* nuevoProceso = malloc(sizeof(t_proceso_en_memoria));

    nuevoProceso->pid = pid;
    nuevoProceso->tabla_paginas=  NULL;
    nuevoProceso->cant_pags = 0;
    nuevoProceso->instrucciones = obtener_instrucciones_del_path(path);

    list_add(procesosEnMemoria, nuevoProceso);
    pthread_mutex_unlock(&mutex_procesosEnMemoria);
    
    log_info(logger_memoria, "PID: %d - Tamaño: %d", pid, nuevoProceso->cant_pags);
}

void marcarFrameComoOcupado(u_int32_t nro_frame){
    t_frame *frame = list_get(frames_memoria, nro_frame);
    frame->disponible = false;
    cant_frames_disponibles --;
}

void marcarFrameComoLibre(u_int32_t nro_frame){
    t_frame *frame = list_get(frames_memoria, nro_frame);
    frame->disponible = true;
    cant_frames_disponibles ++;
}

t_proceso_en_memoria* buscarProcesoPorId(uint32_t pid) {
    pthread_mutex_lock(&mutex_procesosEnMemoria);
    for (int i = 0; i < list_size(procesosEnMemoria); i++) {
        t_proceso_en_memoria* procesoEnMemoria = list_get(procesosEnMemoria, i);
        //printf("%d\n",procesoEnMemoria->pid);
        if (procesoEnMemoria->pid == pid) {
            pthread_mutex_unlock(&mutex_procesosEnMemoria);
            return procesoEnMemoria;
        }
    }
    return NULL;
}

int obtenerPosicionEnListaDeProcesos(uint32_t pid){
     for (int i = 0; i < list_size(procesosEnMemoria); i++) {
        t_proceso_en_memoria* procesoEnMemoria = list_get(procesosEnMemoria, i);
        if (procesoEnMemoria->pid == pid) {
            return i;
        }
    }
    return -1;
}

void destruirTablaDePaginas(t_proceso_en_memoria *proceso) {
    if (proceso != NULL) {
        log_info(logger_memoria, "PID: %d - Tamaño: %d", proceso->pid, proceso->cant_pags);
        if (proceso->tabla_paginas != NULL) {
            free(proceso->tabla_paginas);
        }
        if (!list_is_empty(proceso->instrucciones)) {
            liberar_instrucciones_en_lista(proceso->instrucciones);
        }
    }
}

void finalizarProceso(uint32_t pid) {
    t_proceso_en_memoria* procesoEnMemoria = buscarProcesoPorId(pid);
    if (procesoEnMemoria != NULL) {
        for (int i = 0; i < procesoEnMemoria->cant_pags; i++) {
            t_pagina pagina = procesoEnMemoria->tabla_paginas[i];
            marcarFrameComoLibre(pagina.nroFrame);
        }
        destruirTablaDePaginas(procesoEnMemoria);
        free(list_remove(procesosEnMemoria, obtenerPosicionEnListaDeProcesos(pid))); 
    }
}

 u_int32_t buscarFrame(u_int32_t nroDePagina, u_int32_t pid){
    t_proceso_en_memoria* procesoEnMemoria = buscarProcesoPorId(pid);
    if (procesoEnMemoria != NULL && nroDePagina < procesoEnMemoria->cant_pags) {
        u_int32_t frame = procesoEnMemoria->tabla_paginas[nroDePagina].nroFrame;    
        return frame;
    }
    else{
        return -1; //error
    }
}   

int obtener_frame_libre(){
    for(int i=0; i < list_size(frames_memoria);i++){
        t_frame *frame = list_get(frames_memoria, i);
        if(frame->disponible){
            return i;
        }
    }
    // no se debería llegar nunca acá
    printf("No se encuentra ningún frame libre\n");
    return -1;
}

void agregar_paginas(t_proceso_en_memoria* proceso, int paginas_a_agregar){
    int cant_pags_actual = proceso->cant_pags;
    if(proceso->tabla_paginas != NULL){
        proceso->tabla_paginas = realloc(proceso->tabla_paginas, (paginas_a_agregar + cant_pags_actual) * sizeof(t_pagina));

        for (int i = cant_pags_actual; i < cant_pags_actual + paginas_a_agregar; i++) {
            int frameAOcupar = obtener_frame_libre();
            proceso->tabla_paginas[i].nroPagina = i;
            proceso->tabla_paginas[i].nroFrame = frameAOcupar;
            marcarFrameComoOcupado(frameAOcupar);
        }
    }
    else{
        proceso->tabla_paginas = malloc(paginas_a_agregar * sizeof(t_pagina));
        for(int i=0; i<paginas_a_agregar; i++){
            int frameAOcupar = obtener_frame_libre();
            proceso->tabla_paginas[i].nroPagina=i;
            proceso->tabla_paginas[i].nroFrame=frameAOcupar;
            marcarFrameComoOcupado(frameAOcupar);
        }
    }
    proceso->cant_pags += paginas_a_agregar;
}

void ampliar_proceso(t_proceso_en_memoria* proceso, uint32_t cant_pags_nueva){
    int paginas_a_agregar = cant_pags_nueva - proceso->cant_pags;
    if(cant_frames_disponibles >= paginas_a_agregar){
        agregar_paginas(proceso, paginas_a_agregar);
        //SUCCESS
        t_buffer* buffer = crear_buffer_pro();
		cargar_int_al_buffer_pro(buffer, SUCCESS);
		t_paquete* paquete = crear_paquete_pro(SUCCESS, buffer);
		enviar_paquete_pro(paquete, fd_cpu);
		destruir_paquete_pro(paquete);
    }
    else{
        //OUT_OF_MEMORY;
        t_buffer* buffer = crear_buffer_pro();
		cargar_int_al_buffer_pro(buffer, OUT_OF_MEMORY);
		t_paquete* paquete = crear_paquete_pro(OUT_OF_MEMORY, buffer);
		enviar_paquete_pro(paquete, fd_cpu);
		destruir_paquete_pro(paquete);
    }
}

void sacar_paginas(t_proceso_en_memoria* proceso, int paginas_a_sacar) {
    int cant_pags_actual = proceso->cant_pags;
    for (int i = cant_pags_actual - 1; i >= cant_pags_actual - paginas_a_sacar; i--) {
        marcarFrameComoLibre(proceso->tabla_paginas[i].nroFrame);
    }
    
    proceso->tabla_paginas = realloc(proceso->tabla_paginas, (cant_pags_actual - paginas_a_sacar) * sizeof(t_pagina));
    proceso->cant_pags -= paginas_a_sacar;
}

void reducir_proceso(t_proceso_en_memoria* proceso, uint32_t cant_pags_nueva){
    int paginas_a_sacar = proceso->cant_pags - cant_pags_nueva;
    sacar_paginas(proceso, paginas_a_sacar);
}