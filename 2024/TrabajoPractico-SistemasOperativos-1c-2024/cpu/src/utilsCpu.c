#include "../include/utilsCpu.h"

extern t_registros registros_cpu;

u_int32_t pid_proceso;

void levantar_log_y_config(char* path)
{
	config_cpu = config_create(path);
	logger_cpu = log_create(LOG_FILE_PATH_CPU, "CPU", true, LOG_LEVEL_INFO);
    logger_sec_cpu = log_create(LOG_SEC_FILE_PATH_CPU, "CPU", true, LOG_LEVEL_INFO);
    ip_memoria = config_get_string_value(config_cpu,"IP_MEMORIA");
    puerto_memoria = config_get_string_value(config_cpu,"PUERTO_MEMORIA");
    ip_cpu = config_get_string_value(config_cpu, "IP_CPU");
    puerto_escucha_dispatch = config_get_string_value(config_cpu, "PUERTO_ESCUCHA_DISPATCH");
	puerto_escucha_interrupt = config_get_string_value(config_cpu, "PUERTO_ESCUCHA_INTERRUPT");
    cantidad_entradas_tlb = atoi(config_get_string_value(config_cpu, "CANTIDAD_ENTRADAS_TLB"));
    algoritmo_tlb = config_get_string_value(config_cpu, "ALGORITMO_TLB");

    log_info(logger_sec_cpu, "IP_MEMORIA: %s | PUERTO_MEMORIA: %s \n", ip_memoria, puerto_memoria);
    log_info(logger_sec_cpu, "IP_CPU: %s | PUERTO_ESCUCHA_DISPATCH: %s | PUERTO_ESCUCHA_INTERRUPT: %s\n", ip_cpu, puerto_escucha_dispatch, puerto_escucha_interrupt);
    
    lista_interrupciones = list_create();
    pthread_mutex_init(&mutex_lista_interrupciones, NULL);
}

void terminar_programa_cpu(){
    liberar_conexion(fd_kernel_interrupt);
	liberar_conexion(fd_kernel_dispatch);
	liberar_conexion(fd_servidor_interrupt);
	liberar_conexion(fd_servidor_dispatch);
	terminar_programa(fd_memoria, logger_cpu, config_cpu);
}


void imprimir_registros_por_pantalla(t_registros registros)
{
	printf("-------------------\n");
	printf("Valores de los Registros:\n");
	printf(" + AX = %i\n", registros.AX);
	printf(" + BX = %i\n", registros.BX);
	printf(" + CX = %i\n", registros.CX);
	printf(" + DX = %i\n", registros.DX);
	printf(" + EAX = %i\n", registros.EAX);
	printf(" + EBX = %i\n", registros.EBX);
	printf(" + ECX = %i\n", registros.ECX);
	printf(" + EDX = %i\n", registros.EDX);
	printf(" + SI = %i\n", registros.SI);
	printf(" + DI = %i\n", registros.DI);
	printf("-------------------\n");
}

void *get_registro(char* string_registro){
	
	void *registro;

	if (!strcmp(string_registro, "AX") || !strcmp(string_registro, "AX\n"))
    {
        registro = &registros_cpu.AX;	
    }
    if (!strcmp(string_registro, "BX") || !strcmp(string_registro, "BX\n"))
    {
        registro = &registros_cpu.BX;
    }
    if (!strcmp(string_registro, "CX") || !strcmp(string_registro, "CX\n"))
    {
        registro = &registros_cpu.CX;
    }
    if (!strcmp(string_registro, "DX") || !strcmp(string_registro, "DX\n"))
    {
        registro = &registros_cpu.DX;
    }
	if (!strcmp(string_registro, "EAX") || !strcmp(string_registro, "EAX\n"))
    {
        registro = &registros_cpu.EAX;
    }
    if (!strcmp(string_registro, "EBX") || !strcmp(string_registro, "EBX\n"))
    {
        registro = &registros_cpu.EBX;
    }
    if (!strcmp(string_registro, "ECX") || !strcmp(string_registro, "ECX\n"))
    {
        registro = &registros_cpu.ECX;
    }
    if (!strcmp(string_registro, "EDX") || !strcmp(string_registro, "EDX\n"))
    {
        registro = &registros_cpu.EDX;
    }
	if (!strcmp(string_registro, "SI") || !strcmp(string_registro, "SI\n"))
    {
        registro = &registros_cpu.SI;
    }
    if (!strcmp(string_registro, "DI") || !strcmp(string_registro, "DI\n"))
    {
        registro = &registros_cpu.DI;
    }
	return registro;
}
void asignarValorARegistro(char *string_registro, char* valor){
	if (!strcmp(string_registro, "AX") || !strcmp(string_registro, "AX\n"))
    {
        registros_cpu.AX = atoi(valor);
    }
    if (!strcmp(string_registro, "BX") || !strcmp(string_registro, "BX\n"))
    {
        registros_cpu.BX = atoi(valor);
    }
    if (!strcmp(string_registro, "CX") || !strcmp(string_registro, "CX\n"))
    {
        registros_cpu.CX = atoi(valor);
    }
    if (!strcmp(string_registro, "DX") || !strcmp(string_registro, "DX\n"))
    {
        registros_cpu.DX = atoi(valor);
    }
	if (!strcmp(string_registro, "EAX") || !strcmp(string_registro, "EAX\n"))
    {
        registros_cpu.EAX = atoi(valor);
    }
    if (!strcmp(string_registro, "EBX") || !strcmp(string_registro, "EBX\n"))
    {
        printf("Valor: %s\n", valor);
        registros_cpu.EBX = atoi(valor);
    }
    if (!strcmp(string_registro, "ECX") || !strcmp(string_registro, "ECX\n"))
    {
        registros_cpu.ECX = atoi(valor);
    }
    if (!strcmp(string_registro, "EDX") || !strcmp(string_registro, "EDX\n"))
    {
        registros_cpu.EDX = atoi(valor);
    }
	if (!strcmp(string_registro, "SI") || !strcmp(string_registro, "SI\n"))
    {
        registros_cpu.SI = atoi(valor);
    }
    if (!strcmp(string_registro, "DI") || !strcmp(string_registro, "DI\n"))
    {
        registros_cpu.DI = atoi(valor);
    }
    if (!strcmp(string_registro, "PC") || !strcmp(string_registro, "PC\n"))
    {
        registros_cpu.PC = atoi(valor) - 1;
    }
}

int get_valor_registro(char* string_registro){
    int valor;

	if (!strcmp(string_registro, "AX") || !strcmp(string_registro, "AX\n"))
    {
        valor = registros_cpu.AX;	
    }
    if (!strcmp(string_registro, "BX") || !strcmp(string_registro, "BX\n"))
    {
        valor = registros_cpu.BX;	
    }
    if (!strcmp(string_registro, "CX") || !strcmp(string_registro, "CX\n"))
    {
        valor = registros_cpu.CX;	
    }
    if (!strcmp(string_registro, "DX") || !strcmp(string_registro, "DX\n"))
    {
        valor = registros_cpu.DX;	
    }
	if (!strcmp(string_registro, "EAX") || !strcmp(string_registro, "EAX\n"))
    {
        valor = registros_cpu.EAX;	
    }
    if (!strcmp(string_registro, "EBX") || !strcmp(string_registro, "EBX\n"))
    {
        valor = registros_cpu.EBX;	
    }
    if (!strcmp(string_registro, "ECX") || !strcmp(string_registro, "ECX\n"))
    {
        valor = registros_cpu.ECX;	
    }
    if (!strcmp(string_registro, "EDX") || !strcmp(string_registro, "EDX\n"))
    {
        valor = registros_cpu.EDX;	
    }
	if (!strcmp(string_registro, "SI") || !strcmp(string_registro, "SI\n"))
    {
        valor = registros_cpu.SI;	
    }
    if (!strcmp(string_registro, "DI") || !strcmp(string_registro, "DI\n"))
    {
        valor = registros_cpu.DI;	
    }
	return valor;
}

int get_tamanio_registro(char* string_registro){
    int tamanio;
    if (!strcmp(string_registro, "AX") || !strcmp(string_registro, "AX\n") 
    || !strcmp(string_registro, "BX") || !strcmp(string_registro, "BX\n")
    || !strcmp(string_registro, "CX") || !strcmp(string_registro, "CX\n")
    || !strcmp(string_registro, "DX") || !strcmp(string_registro, "DX\n"))
    {
        tamanio = sizeof(u_int8_t);

    }else if (!strcmp(string_registro, "EAX") || !strcmp(string_registro, "EAX\n")
    || !strcmp(string_registro, "EBX") || !strcmp(string_registro, "EBX\n")
    || !strcmp(string_registro, "ECX") || !strcmp(string_registro, "ECX\n")
    || !strcmp(string_registro, "EDX") || !strcmp(string_registro, "EDX\n")
    || !strcmp(string_registro, "SI") || !strcmp(string_registro, "SI\n")
    || !strcmp(string_registro, "DI") || !strcmp(string_registro, "DI\n"))
    {
        tamanio = sizeof(u_int32_t);	
    }
	return tamanio;
}

// Ciclo instrucciones----------------------------------------------------------------------------
t_buffer* fetch(t_proceso_a_ejecutar* a_ejecutar){
    t_buffer* buffer_a_enviar = crear_buffer_pro();
    cargar_int_al_buffer_pro(buffer_a_enviar, a_ejecutar->pid);
    cargar_uint32_al_buffer_pro(buffer_a_enviar, a_ejecutar->contexto.PC);
    
    t_paquete* paquete_a_enviar = crear_paquete_pro(INSTRUCCION, buffer_a_enviar);
    enviar_paquete_pro(paquete_a_enviar, fd_memoria);
    destruir_paquete_pro(paquete_a_enviar);
    t_buffer* buffer_recibido = recibir_instruccion();
    log_info(logger_cpu, "PID: %d - FETCH - Program Counter: %d", a_ejecutar->pid, registros_cpu.PC);

    pid_proceso = a_ejecutar->pid;
    return buffer_recibido;
}
t_buffer* recibir_instruccion(){
	t_buffer * buffer;
    int cod_op = recibir_operacion(fd_memoria);
    cod_op+=0;
	buffer = recibir_todo_el_buffer_pro(fd_memoria);
	return buffer;
}
t_instruccion* decode(t_buffer* instruccion_a_decodificar){
   t_instruccion* instruccion_decodificada = extraer_instruccion_del_buffer_pro(instruccion_a_decodificar);
   destruir_buffer_pro(instruccion_a_decodificar);
   return instruccion_decodificada;
}

t_instruccion* execute(t_instruccion* instruccion){

    int param_count = 0;    
    while (instruccion->parametros[param_count] != NULL) {
        param_count++;
    }

    switch (instruccion->identificador)
    {
    case SET:
        loggear_exec(instruccion, param_count, "SET");
        set(instruccion);
        registros_cpu.PC++;
        break;
    case SUM:
        loggear_exec(instruccion, param_count, "SUM");
        sum(instruccion);
        registros_cpu.PC++;
        break;
    case SUB:
        loggear_exec(instruccion, param_count, "SUB");
        sub(instruccion);
        registros_cpu.PC++;
        break;
    case JNZ:
        loggear_exec(instruccion, param_count, "JNZ");
        jnz(instruccion);
        break;
    case IO_GEN_SLEEP:
        loggear_exec(instruccion, param_count, "IO_GEN_SLEEP");
        registros_cpu.PC++;
        enviar_contexto(SYSCALL_IO, instruccion);
        liberar_instruccion(instruccion);
        free(a_ejecutar);
        return NULL;
    case MOV_IN:
        loggear_exec(instruccion, param_count, "MOV_IN");
        mov_in(instruccion);
        registros_cpu.PC++;
        break;
    case MOV_OUT:
        loggear_exec(instruccion, param_count, "MOV_OUT");
        mov_out(instruccion);
        registros_cpu.PC++;
        break;
    case IO_STDIN_READ:
        loggear_exec(instruccion, param_count, "IO_STDIN_READ");
        registros_cpu.PC++;
        stdin_read(instruccion);
        liberar_instruccion(instruccion);
        free(a_ejecutar);
        return NULL;
    case IO_STDOUT_WRITE:
        loggear_exec(instruccion, param_count, "IO_STDOUT_WRITE");
        registros_cpu.PC++;
        stdout_write(instruccion);
        liberar_instruccion(instruccion);
        free(a_ejecutar);
        return NULL;
    case RESIZE:
        loggear_exec(instruccion, param_count, "RESIZE");
        resize(instruccion);
        break;
    case COPY_STRING:
        loggear_exec(instruccion, param_count, "COPY_STRING");
        copy_string(instruccion);
        registros_cpu.PC++;
        break;
    case WAIT:
        loggear_exec(instruccion, param_count, "WAIT");
        registros_cpu.PC++;
        enviar_contexto(SYSCALL, instruccion);
        liberar_instruccion(instruccion);
        free(a_ejecutar);
        return NULL;
    case SIGNAL:
        loggear_exec(instruccion, param_count, "SIGNAL");
        registros_cpu.PC++;
        enviar_contexto(SYSCALL, instruccion);
        liberar_instruccion(instruccion);
        free(a_ejecutar);
        return NULL;
    case IO_FS_CREATE:
        registros_cpu.PC++;
        io_fs_create(instruccion);
        liberar_instruccion(instruccion);
        free(a_ejecutar);
        return NULL;
    case IO_FS_DELETE:
        registros_cpu.PC++;
        io_fs_delete(instruccion);
        liberar_instruccion(instruccion);
        free(a_ejecutar);
        return NULL;
    case IO_FS_TRUNCATE:
        registros_cpu.PC++;
        io_fs_truncate(instruccion);
        liberar_instruccion(instruccion);
        free(a_ejecutar);
        return NULL;
    case IO_FS_WRITE:
        registros_cpu.PC++;
        io_fs_write(instruccion);
        liberar_instruccion(instruccion);
        free(a_ejecutar);
        return NULL;
    case IO_FS_READ:
        registros_cpu.PC++;
        io_fs_read(instruccion);
        liberar_instruccion(instruccion);
        free(a_ejecutar);
        return NULL;
    case FIN:
        loggear_exec(instruccion, param_count, "FIN");
        enviar_contexto(SUCCESS, instruccion);
        liberar_instruccion(instruccion);
        free(a_ejecutar);
        return NULL;
    default:
        liberar_instruccion(instruccion);
        free(a_ejecutar);
        return NULL;
    }
    imprimir_registros_por_pantalla(registros_cpu);
    return instruccion;
}

void check_interrupt(t_instruccion* instruccion){
    if(instruccion != NULL){
        if(!lista_interrupciones_esta_vacia()){
            t_interrupcion* interrupcion = proxima_interrupcion_protegido();

            if(interrupcion->pid_proceso_interrumpido == pid_proceso){
                log_info(logger_sec_cpu, "Interrupcion motivo: %d", interrupcion->tipo);
                t_motivo_desalojo motivo_desalojo;
                switch (interrupcion->tipo)
                {
                    case INTERRUPT_CLOCK:
                        motivo_desalojo = CLOCK;
                        enviar_contexto(motivo_desalojo, instruccion);
                        free(a_ejecutar);
                    break;
                    case INTERRUPT_CONSOLA:
                        motivo_desalojo = CONSOLA_KERNEL;
                        enviar_contexto(motivo_desalojo, instruccion);
                        free(a_ejecutar);
                    break;
                }
                free(interrupcion);
                liberar_instruccion(instruccion);
            }else{
                free(interrupcion);
                check_interrupt(instruccion);
            }
        }else{
            t_proceso_a_ejecutar *siguiente_a_ejecutar = malloc(sizeof(t_proceso_a_ejecutar));
            siguiente_a_ejecutar->pid = a_ejecutar->pid;
            siguiente_a_ejecutar->contexto = registros_cpu;
            destruir_instruccion(instruccion,0);
            check_interrupt(execute(decode(fetch(siguiente_a_ejecutar))));
            free(siguiente_a_ejecutar);
        }
    }
}

// INSTRUCCIONES------------------------------------------------

// Por ejemplo: SET AX 1
void set(t_instruccion* instruccion){
	asignarValorARegistro(instruccion->parametros[0],instruccion->parametros[1]);
}

// Por ejemplo: SUM AX BX, SUB AX BX
void sum(t_instruccion* instruccion){
    int aux1 = get_valor_registro(instruccion->parametros[0]);
    int aux2 = get_valor_registro(instruccion->parametros[1]);
    int resultado = aux1 + aux2;
    char* cadenaResultado = malloc(sizeof(char)*5);
    sprintf(cadenaResultado, "%d", resultado);
    asignarValorARegistro(instruccion->parametros[0],cadenaResultado);
    free(cadenaResultado);
}
void sub(t_instruccion* instruccion){
    int aux1 = get_valor_registro(instruccion->parametros[0]);
    int aux2 = get_valor_registro(instruccion->parametros[1]);;
    int resultado = aux1 - aux2;
    char *cadenaResultado = malloc(sizeof(char)*5);
    sprintf(cadenaResultado, "%d", resultado);
    asignarValorARegistro(instruccion->parametros[0],cadenaResultado);
    free(cadenaResultado);
}

// Por ejemplo: JNZ AX 45
void jnz(t_instruccion* instruccion){

    if(get_valor_registro(instruccion->parametros[0]) != 0){

        asignarValorARegistro("PC", instruccion->parametros[1]);
    }
    else{
        registros_cpu.PC++;
    }
}

// MOV_IN REG_VAL REG_DIR
void mov_in(t_instruccion* instruccion){

    int direccion_logica = get_valor_registro(instruccion->parametros[1]);
    int tamanio_reg = get_tamanio_registro(instruccion->parametros[0]);
    int cant_paginas = cantidad_paginas_a_leer(direccion_logica, tamanio_reg);
    int direccion_fisica = 0;

    if(cant_paginas == 1){
        direccion_fisica = traducir(direccion_logica);

        t_buffer* buffer_a_enviar = crear_buffer_pro();
        cargar_int_al_buffer_pro(buffer_a_enviar, pid_proceso);
        cargar_int_al_buffer_pro(buffer_a_enviar, direccion_fisica);
        cargar_int_al_buffer_pro(buffer_a_enviar, tamanio_reg);

        t_paquete* paquete_a_enviar = crear_paquete_pro(PEDIDO_DE_LECTURA, buffer_a_enviar);
        enviar_paquete_pro(paquete_a_enviar, fd_memoria);
        destruir_paquete_pro(paquete_a_enviar);

        int lec_op = recibir_operacion(fd_memoria);
        log_info(logger_sec_cpu, "Op_lectura: %d", lec_op);
        t_buffer* buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
        void* resultado = extraer_algo_del_buffer_pro(buffer_recibido);
        char* valor_a_registrar = malloc(tamanio_reg + 1);
        sprintf(valor_a_registrar,"%d",*(int*)resultado);
        asignarValorARegistro(instruccion->parametros[0], valor_a_registrar);
        
        u_int8_t valor_a_loggear = *(u_int8_t*)resultado;
        char* mensaje = string_from_format("PID: %d - Accion: LEER - Direccion Fisica: %d - Valor: %u", pid_proceso, direccion_fisica, valor_a_loggear);
	    log_info(logger_cpu, "%s", mensaje);

        free(mensaje);
        free(resultado);
        free(valor_a_registrar);
        destruir_buffer_pro(buffer_recibido);
    }else{
        int bytes_leidos = 0;
        int bytes_a_leer_en_actual = 0;
        int desplazamiento = 0;
        int num_pag = 0;
        int bytes_libres = 0;
        void* dato_reconstruido = malloc(tamanio_reg);

        for(int i=0; i<cant_paginas; i++){
            
            direccion_fisica = traducir(direccion_logica + bytes_leidos);
            
            num_pag = floor((direccion_logica + bytes_leidos)/ tamanio_pagina);
            desplazamiento = (direccion_logica + bytes_leidos) - (num_pag * tamanio_pagina);

            bytes_libres = tamanio_pagina - desplazamiento;

            if(bytes_libres < tamanio_pagina){
                bytes_a_leer_en_actual = bytes_libres;
            }else if ((tamanio_reg - bytes_leidos) > tamanio_pagina){
                bytes_a_leer_en_actual = tamanio_pagina;
            }else{
                bytes_a_leer_en_actual = tamanio_reg - bytes_leidos;
            }

            t_buffer* buffer_a_enviar = crear_buffer_pro();
            cargar_int_al_buffer_pro(buffer_a_enviar, pid_proceso);
            cargar_int_al_buffer_pro(buffer_a_enviar, direccion_fisica);
            cargar_int_al_buffer_pro(buffer_a_enviar, bytes_a_leer_en_actual);

            t_paquete* paquete_a_enviar = crear_paquete_pro(PEDIDO_DE_LECTURA, buffer_a_enviar);
            enviar_paquete_pro(paquete_a_enviar, fd_memoria);
            destruir_paquete_pro(paquete_a_enviar);

            int lec_op = recibir_operacion(fd_memoria);
            log_info(logger_sec_cpu, "Op_lectura: %d", lec_op);
            t_buffer* buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
            void* resultado = extraer_algo_del_buffer_pro(buffer_recibido);
            memcpy(dato_reconstruido + bytes_leidos, resultado, bytes_a_leer_en_actual);
            bytes_leidos += bytes_a_leer_en_actual;

            u_int8_t val = *(u_int8_t*)resultado;
            int bits_a_tirar = 8 - tamanio_reg - 1;

            u_int8_t valor_a_loggear = val >> bits_a_tirar;
            char* mensaje = string_from_format("PID: %d - Accion: LEER - Direccion Fisica: %d - Valor: %u", pid_proceso, direccion_fisica, valor_a_loggear);
	        log_info(logger_cpu, "%s", mensaje);

            destruir_buffer_pro(buffer_recibido);
            free(resultado);
            free(mensaje);
        }
        char* valor_a_registrar = malloc(tamanio_reg + 1);
        sprintf(valor_a_registrar,"%d",*(int*)dato_reconstruido);
        asignarValorARegistro(instruccion->parametros[0], valor_a_registrar);
        free(valor_a_registrar);
        free(dato_reconstruido);
    }
}

// MOV_OUT REG_DIR REG_VAL
void mov_out(t_instruccion* instruccion){
    int valor = get_valor_registro(instruccion->parametros[1]);

    int tam_registro = get_tamanio_registro(instruccion->parametros[1]);
    void* valor_casteado = malloc(tam_registro + 1);
    memcpy(valor_casteado, &valor, tam_registro);
    
    int direccion_logica = get_valor_registro(instruccion->parametros[0]);
    int direccion_fisica = 0;
    int cant_pags = cantidad_paginas_a_leer(direccion_logica,tam_registro);

    if(cant_pags == 1){
        direccion_fisica = traducir(direccion_logica);
        t_buffer* buffer_a_enviar = crear_buffer_pro();
        cargar_int_al_buffer_pro(buffer_a_enviar, pid_proceso);
        cargar_int_al_buffer_pro(buffer_a_enviar, direccion_fisica);
        cargar_int_al_buffer_pro(buffer_a_enviar,tam_registro);
        cargar_stream_al_buffer_pro(buffer_a_enviar, valor_casteado, tam_registro);

        t_paquete* paquete_a_enviar = crear_paquete_pro(PEDIDO_DE_ESCRITURA, buffer_a_enviar);
        enviar_paquete_pro(paquete_a_enviar, fd_memoria);
        destruir_paquete_pro(paquete_a_enviar);

        int rta = recibir_operacion(fd_memoria);
        t_buffer* buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
        int rta_final = extraer_int_del_buffer_pro(buffer_recibido);
        if(rta_final == OK){
            printf("Mov Out OK\n");
        }else{
            printf("Mov Out no OK\n");
        }

        log_info(logger_cpu, "PID: %d - Accion: ESCIRBIR - Direccion Fisica: %d - Valor: %i", pid_proceso, direccion_fisica, valor);
        destruir_buffer_pro(buffer_recibido);
    }else{
        int bytes_escritos = 0;
        int bytes_a_escribir_en_actual = 0;
        int desplazamiento = 0;
        int num_pag = 0;
        int bytes_libres = 0;
        for (int i = 0; i < cant_pags; i++)
        {
            direccion_fisica = traducir(direccion_logica + bytes_escritos);
            
            num_pag = floor((direccion_logica + bytes_escritos)/ tamanio_pagina);
            desplazamiento = (direccion_logica + bytes_escritos) - (num_pag * tamanio_pagina);
            bytes_libres = tamanio_pagina - desplazamiento;
            if(bytes_libres < tamanio_pagina){
                bytes_a_escribir_en_actual = bytes_libres;
            }else if ((tam_registro - bytes_escritos) > tamanio_pagina){
                bytes_a_escribir_en_actual = tamanio_pagina;
            }else{
                bytes_a_escribir_en_actual = tam_registro - bytes_escritos;
            }

            void* valor_para_memoria = malloc(bytes_a_escribir_en_actual);
            memcpy(valor_para_memoria, valor_casteado + bytes_escritos, bytes_a_escribir_en_actual);

            t_buffer* buffer_a_enviar = crear_buffer_pro();
            cargar_int_al_buffer_pro(buffer_a_enviar, pid_proceso);
            cargar_int_al_buffer_pro(buffer_a_enviar, direccion_fisica);
            cargar_int_al_buffer_pro(buffer_a_enviar,bytes_a_escribir_en_actual);
            cargar_stream_al_buffer_pro(buffer_a_enviar, valor_para_memoria, bytes_a_escribir_en_actual);

            t_paquete* paquete_a_enviar = crear_paquete_pro(PEDIDO_DE_ESCRITURA, buffer_a_enviar);
            enviar_paquete_pro(paquete_a_enviar, fd_memoria);
            destruir_paquete_pro(paquete_a_enviar);

            int rta = recibir_operacion(fd_memoria);
            t_buffer* buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
            int rta_final = extraer_int_del_buffer_pro(buffer_recibido);
            if(rta_final == OK){
                printf("Mov Out OK\n");
            }else{
                printf("Mov Out NO OK\n");
            }
            destruir_buffer_pro(buffer_recibido);
            bytes_escritos += bytes_a_escribir_en_actual;

            log_info(logger_cpu, "PID: %d - Accion: ESCIRBIR - Direccion Fisica: %d - Valor: %i", pid_proceso, direccion_fisica, valor);
            free(valor_para_memoria);
        }
    }
    free(valor_casteado);
}

// Por ejemplo: RESIZE 128
void resize(t_instruccion* instruccion){

    int tam = atoi(instruccion->parametros[0]);

    t_buffer* buffer_a_enviar_ = crear_buffer_pro();
    cargar_uint32_al_buffer_pro(buffer_a_enviar_, pid_proceso);
    cargar_int_al_buffer_pro(buffer_a_enviar_, tam);

    t_paquete* paquete_a_enviar_ = crear_paquete_pro(RESIZE, buffer_a_enviar_);
    enviar_paquete_pro(paquete_a_enviar_, fd_memoria);
    destruir_paquete_pro(paquete_a_enviar_);

    int resultado = recibir_operacion(fd_memoria);
    t_buffer* buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
    if(resultado == OUT_OF_MEMORY){
        registros_cpu.PC++;
        enviar_contexto(OUT_OF_MEMORY, instruccion);
    }
    else
    {
        registros_cpu.PC++;
    }   
    destruir_buffer_pro(buffer_recibido);
}

// Por ejemplo: COPY_STRING 8
void copy_string(t_instruccion* instruccion){

    char* si = "SI";
    char* di = "DI";

    int dir_log_src = get_valor_registro(si);
    int dir_log_dest = get_valor_registro(di);
    int direccion_fisica = 0;

    int tamanio = atoi(instruccion->parametros[0]);
    int cant_paginas_leer = cantidad_paginas_a_leer(dir_log_src, tamanio);
    void* dato_reconstruido = malloc(tamanio);

    if(cant_paginas_leer == 1){
        direccion_fisica = traducir(dir_log_src);

        t_buffer* buffer_a_enviar = crear_buffer_pro();
        cargar_int_al_buffer_pro(buffer_a_enviar, pid_proceso);
        cargar_int_al_buffer_pro(buffer_a_enviar, direccion_fisica);
        cargar_int_al_buffer_pro(buffer_a_enviar, tamanio);

        t_paquete* paquete_a_enviar = crear_paquete_pro(PEDIDO_DE_LECTURA, buffer_a_enviar);
        enviar_paquete_pro(paquete_a_enviar, fd_memoria);
        destruir_paquete_pro(paquete_a_enviar);

        int lec_op = recibir_operacion(fd_memoria);
        log_info(logger_sec_cpu, "Op_lectura: %d", lec_op);
        t_buffer* buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
        dato_reconstruido = extraer_algo_del_buffer_pro(buffer_recibido);

        char* leido = malloc(tamanio + 2*sizeof(char));
        snprintf(leido,tamanio + 2*sizeof(char),"%s\n",(char*) dato_reconstruido);
        leido[tamanio] = '\0';

        log_info(logger_cpu,"PID: %d - Accion: LEER - Direccion Fisica: %d - Valor: %s\n", pid_proceso, direccion_fisica, leido);
        free(leido);
        
    }else{
        int bytes_leidos = 0;
        int bytes_a_leer_en_actual = 0;
        int desplazamiento = 0;
        int num_pag = 0;
        int bytes_libres = 0;

        for(int i=0; i<cant_paginas_leer; i++){
            
            direccion_fisica = traducir(dir_log_src + bytes_leidos);
            
            num_pag = floor((dir_log_src + bytes_leidos)/ tamanio_pagina);
            desplazamiento = (dir_log_src + bytes_leidos) - (num_pag * tamanio_pagina);

            bytes_libres = tamanio_pagina - desplazamiento;

            if(bytes_libres < tamanio_pagina){
                bytes_a_leer_en_actual = bytes_libres;
            }else if ((tamanio - bytes_leidos) > tamanio_pagina){
                bytes_a_leer_en_actual = tamanio_pagina;
            }else{
                bytes_a_leer_en_actual = tamanio - bytes_leidos;
            }

            t_buffer* buffer_a_enviar = crear_buffer_pro();
            cargar_int_al_buffer_pro(buffer_a_enviar, pid_proceso);
            cargar_int_al_buffer_pro(buffer_a_enviar, direccion_fisica);
            cargar_int_al_buffer_pro(buffer_a_enviar, bytes_a_leer_en_actual);

            t_paquete* paquete_a_enviar = crear_paquete_pro(PEDIDO_DE_LECTURA, buffer_a_enviar);
            enviar_paquete_pro(paquete_a_enviar, fd_memoria);
            destruir_paquete_pro(paquete_a_enviar);

            int lec_op = recibir_operacion(fd_memoria);
            log_info(logger_sec_cpu, "Op_lectura: %d", lec_op);
            t_buffer* buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
            void* resultado = extraer_algo_del_buffer_pro(buffer_recibido);
            memcpy(dato_reconstruido + bytes_leidos, resultado, bytes_a_leer_en_actual);
            bytes_leidos += bytes_a_leer_en_actual;

            char* leido = malloc(bytes_a_leer_en_actual + 2*sizeof(char));
            snprintf(leido,bytes_a_leer_en_actual + 2*sizeof(char),"%s\n",(char*) resultado);
            leido[bytes_a_leer_en_actual] = '\0';

            log_info(logger_cpu,"PID: %d - Accion: LEER - Direccion Fisica: %d - Valor: %s\n", pid_proceso, direccion_fisica, leido);
            free(leido);

            destruir_buffer_pro(buffer_recibido);
            free(resultado);
        }
    }

    int cant_pags_escribir = cantidad_paginas_a_leer(dir_log_dest,tamanio);

    if(cant_pags_escribir == 1){
        direccion_fisica = traducir(dir_log_dest);
        t_buffer* buffer_a_enviar = crear_buffer_pro();
        cargar_int_al_buffer_pro(buffer_a_enviar, pid_proceso);
        cargar_int_al_buffer_pro(buffer_a_enviar, direccion_fisica);
        cargar_int_al_buffer_pro(buffer_a_enviar,tamanio);
        cargar_stream_al_buffer_pro(buffer_a_enviar, dato_reconstruido, tamanio);

        t_paquete* paquete_a_enviar = crear_paquete_pro(PEDIDO_DE_ESCRITURA, buffer_a_enviar);
        enviar_paquete_pro(paquete_a_enviar, fd_memoria);
        destruir_paquete_pro(paquete_a_enviar);

        int rta = recibir_operacion(fd_memoria);
        t_buffer* buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
        int rta_final = extraer_int_del_buffer_pro(buffer_recibido);
        if(rta_final == OK){
            char* leido = malloc(tamanio + 2*sizeof(char));
            snprintf(leido,tamanio + 2*sizeof(char),"%s\n",(char*) dato_reconstruido);
            leido[tamanio] = '\0';

            log_info(logger_cpu,"PID: %d - Accion: ESCRIBIR - Direccion Fisica: %d - Valor: %s\n", pid_proceso, direccion_fisica, leido);
            free(leido);
        }else{
            printf("STR_CPY MO NO OK\n");
        }
        destruir_buffer_pro(buffer_recibido);
    }else{
        int bytes_escritos = 0;
        int bytes_a_escribir_en_actual = 0;
        int desplazamiento = 0;
        int num_pag = 0;
        int bytes_libres = 0;
        for (int i = 0; i < cant_pags_escribir; i++)
        {
            direccion_fisica = traducir(dir_log_dest + bytes_escritos);
            
            num_pag = floor((dir_log_dest + bytes_escritos)/ tamanio_pagina);
            desplazamiento = (dir_log_dest + bytes_escritos) - (num_pag * tamanio_pagina);
            bytes_libres = tamanio_pagina - desplazamiento;
            if(bytes_libres < tamanio_pagina){
                bytes_a_escribir_en_actual = bytes_libres;
            }else if ((tamanio - bytes_escritos) > tamanio_pagina){
                bytes_a_escribir_en_actual = tamanio_pagina;
            }else{
                bytes_a_escribir_en_actual = tamanio - bytes_escritos;
            }

            void* valor_para_memoria = malloc(bytes_a_escribir_en_actual + 1);
            memcpy(valor_para_memoria, dato_reconstruido + bytes_escritos, bytes_a_escribir_en_actual);

            t_buffer* buffer_a_enviar = crear_buffer_pro();
            cargar_int_al_buffer_pro(buffer_a_enviar, pid_proceso);
            cargar_int_al_buffer_pro(buffer_a_enviar, direccion_fisica);
            cargar_int_al_buffer_pro(buffer_a_enviar,bytes_a_escribir_en_actual);
            cargar_stream_al_buffer_pro(buffer_a_enviar, valor_para_memoria, bytes_a_escribir_en_actual);

            t_paquete* paquete_a_enviar = crear_paquete_pro(PEDIDO_DE_ESCRITURA, buffer_a_enviar);
            enviar_paquete_pro(paquete_a_enviar, fd_memoria);
            destruir_paquete_pro(paquete_a_enviar);


            int rta = recibir_operacion(fd_memoria);
            t_buffer* buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
            int rta_final = extraer_int_del_buffer_pro(buffer_recibido);
            if(rta_final == OK){
                printf("STR_CPY MO OK: %d\n", rta);
            }else{
                printf("STR_CPY MO NO OK\n");
            }
            destruir_buffer_pro(buffer_recibido);
            bytes_escritos += bytes_a_escribir_en_actual;

            char* leido = malloc(bytes_a_escribir_en_actual + 2*sizeof(char));
            snprintf(leido,bytes_a_escribir_en_actual + 2*sizeof(char),"%s\n",(char*) valor_para_memoria);
            leido[bytes_a_escribir_en_actual] = '\0';

            log_info(logger_cpu,"PID: %d - Accion: LEER - Direccion Fisica: %d - Valor: %s\n", pid_proceso, direccion_fisica, leido);
            free(leido);
            free(valor_para_memoria);

        }  
    }
    free(dato_reconstruido);
}

// Por ejemplo: IO_STDIN_READ Int2 REGDIR REGTAM
void stdin_read(t_instruccion* instruccion){

    int direccion_logica = get_valor_registro(instruccion->parametros[1]);
    int tamanio_total_a_mostrar = get_valor_registro(instruccion->parametros[2]);
    int cant_paginas = cantidad_paginas_a_leer(direccion_logica, tamanio_total_a_mostrar);
    int direccion_fisica = 0;
    
    if(cant_paginas == 1){
        direccion_fisica = traducir(direccion_logica);
        t_buffer* buffer_a_enviar = crear_buffer_pro();
        
        t_proceso_desalojado* proceso = malloc(sizeof(t_proceso_desalojado));
        proceso->motivo = SYSCALL_IO;
        proceso->contexto = registros_cpu;
        

        t_instruccion instruccion_a_enviar;
        instruccion_a_enviar.identificador = IO_STDIN_READ;
        instruccion_a_enviar.parametros = malloc(sizeof(char*)*6);
        
        instruccion_a_enviar.parametros[0] = instruccion->parametros[0];

        char* aux_cant_pag = string_itoa(cant_paginas);
        char* aux_tamanio = string_itoa(tamanio_total_a_mostrar);
        char* aux_df = string_itoa(direccion_fisica);

        instruccion_a_enviar.parametros[1] = aux_cant_pag;
        instruccion_a_enviar.parametros[2] = aux_tamanio;
        instruccion_a_enviar.parametros[3] = aux_df;
        instruccion_a_enviar.parametros[4] = aux_tamanio;
        instruccion_a_enviar.parametros[5] = NULL;

        proceso->syscall = instruccion_a_enviar;
        cargar_proceso_desalojado_al_buffer_pro(buffer_a_enviar, proceso);
        t_paquete* paquete_a_enviar = crear_paquete_pro(INSTRUCCION, buffer_a_enviar);
        enviar_paquete_pro(paquete_a_enviar, fd_kernel_dispatch);
        destruir_paquete_pro(paquete_a_enviar);
        for (int i = 1; i < 4; i++) {
            free(proceso->syscall.parametros[i]);
        }
        free(proceso->syscall.parametros);
        free(proceso);

    }else{
        int bytes_leidos = 0;
        int bytes_a_leer_en_actual = 0;
        int desplazamiento = 0;
        int num_pag = 0;
        int bytes_libres = 0;

        t_buffer* buffer_a_enviar = crear_buffer_pro();
        
        t_proceso_desalojado* proceso = malloc(sizeof(t_proceso_desalojado));
        proceso->motivo = SYSCALL_IO;
        proceso->contexto = registros_cpu;
        

        t_instruccion instruccion_a_enviar;
        instruccion_a_enviar.identificador = IO_STDIN_READ;
        instruccion_a_enviar.parametros = malloc(sizeof(char*)*(cant_paginas *2) + sizeof(char*)*4);

        char* aux_cant_pag = string_itoa(cant_paginas);
        char* aux_tamanio = string_itoa(tamanio_total_a_mostrar);
        
        instruccion_a_enviar.parametros[0] = instruccion->parametros[0];
        instruccion_a_enviar.parametros[1] = aux_cant_pag;
        instruccion_a_enviar.parametros[2] = aux_tamanio;

        int k = 0;
        for(int i=0; i<cant_paginas; i++){
            
            direccion_fisica = traducir(direccion_logica + bytes_leidos);
            
            num_pag = floor((direccion_logica + bytes_leidos)/ tamanio_pagina);
            desplazamiento = (direccion_logica + bytes_leidos) - (num_pag * tamanio_pagina);

            bytes_libres = tamanio_pagina - desplazamiento;

            if(bytes_libres < tamanio_pagina){
                bytes_a_leer_en_actual = bytes_libres;
            }else if ((tamanio_total_a_mostrar - bytes_leidos) > tamanio_pagina){
                bytes_a_leer_en_actual = tamanio_pagina;
            }else{
                bytes_a_leer_en_actual = tamanio_total_a_mostrar - bytes_leidos;
            }

            bytes_leidos += bytes_a_leer_en_actual;
            
            char* aux_df = string_itoa(direccion_fisica);
            char* aux_bytes = string_itoa(bytes_a_leer_en_actual);

            instruccion_a_enviar.parametros[k+3] = aux_df;
            instruccion_a_enviar.parametros[k+4] = aux_bytes;

            k += 2;
        }
        instruccion_a_enviar.parametros[3 + cant_paginas*2] = NULL;

        proceso->syscall = instruccion_a_enviar;
        cargar_proceso_desalojado_al_buffer_pro(buffer_a_enviar,proceso);
        t_paquete* paquete_a_enviar = crear_paquete_pro(INSTRUCCION, buffer_a_enviar);
        enviar_paquete_pro(paquete_a_enviar, fd_kernel_dispatch);
        destruir_paquete_pro(paquete_a_enviar);

        for (int i = 1; i < 3 + cant_paginas * 2; i++) {
            free(instruccion_a_enviar.parametros[i]);
        }
        free(instruccion_a_enviar.parametros);
        free(proceso);
    }
}

// Por ejemplo: STDOUT_WRITE REGDIR REGTAM
void stdout_write(t_instruccion* instruccion) {
    int direccion_logica = get_valor_registro(instruccion->parametros[1]);
    int tamanio_total_a_mostrar = get_valor_registro(instruccion->parametros[2]);
    int cant_paginas = cantidad_paginas_a_leer(direccion_logica, tamanio_total_a_mostrar);
    int direccion_fisica = 0;

    t_instruccion instruccion_a_enviar;
    instruccion_a_enviar.parametros = NULL;

    if (cant_paginas == 1) {
        direccion_fisica = traducir(direccion_logica);
        t_buffer* buffer_a_enviar = crear_buffer_pro();

        t_proceso_desalojado* proceso = malloc(sizeof(t_proceso_desalojado));
        proceso->motivo = SYSCALL_IO;
        proceso->contexto = registros_cpu;

        instruccion_a_enviar.identificador = IO_STDOUT_WRITE;
        instruccion_a_enviar.parametros = malloc(sizeof(char*) * 6);

        instruccion_a_enviar.parametros[0] = instruccion->parametros[0];
        instruccion_a_enviar.parametros[1] = string_itoa(cant_paginas);
        instruccion_a_enviar.parametros[2] = string_itoa(tamanio_total_a_mostrar);
        instruccion_a_enviar.parametros[3] = string_itoa(direccion_fisica);
        instruccion_a_enviar.parametros[4] = string_itoa(tamanio_total_a_mostrar);
        instruccion_a_enviar.parametros[5] = NULL;

        proceso->syscall = instruccion_a_enviar;
        cargar_proceso_desalojado_al_buffer_pro(buffer_a_enviar, proceso);
        t_paquete* paquete_a_enviar = crear_paquete_pro(INSTRUCCION, buffer_a_enviar);
        enviar_paquete_pro(paquete_a_enviar, fd_kernel_dispatch);
        destruir_paquete_pro(paquete_a_enviar);

        for (int i = 1; i < 5; i++) {
            free(proceso->syscall.parametros[i]);
        }
        free(proceso->syscall.parametros);
        free(proceso);
    } else {
        int bytes_leidos = 0;
        int bytes_a_leer_en_actual = 0;
        int desplazamiento = 0;
        int num_pag = 0;
        int bytes_libres = 0;

        t_buffer* buffer_a_enviar = crear_buffer_pro();

        t_proceso_desalojado* proceso = malloc(sizeof(t_proceso_desalojado));
        proceso->motivo = SYSCALL_IO;
        proceso->contexto = registros_cpu;

        instruccion_a_enviar.identificador = IO_STDOUT_WRITE;
        instruccion_a_enviar.parametros = malloc(sizeof(char*) * (cant_paginas * 2 + 4));

        instruccion_a_enviar.parametros[0] = instruccion->parametros[0];
        instruccion_a_enviar.parametros[1] = string_itoa(cant_paginas);
        instruccion_a_enviar.parametros[2] = string_itoa(tamanio_total_a_mostrar);

        int k = 0;
        for (int i = 0; i < cant_paginas; i++) {
            direccion_fisica = traducir(direccion_logica + bytes_leidos);

            num_pag = (direccion_logica + bytes_leidos) / tamanio_pagina;
            desplazamiento = (direccion_logica + bytes_leidos) - (num_pag * tamanio_pagina);

            bytes_libres = tamanio_pagina - desplazamiento;

            if (bytes_libres < tamanio_pagina) {
                bytes_a_leer_en_actual = bytes_libres;
            } else if ((tamanio_total_a_mostrar - bytes_leidos) > tamanio_pagina) {
                bytes_a_leer_en_actual = tamanio_pagina;
            } else {
                bytes_a_leer_en_actual = tamanio_total_a_mostrar - bytes_leidos;
            }

            bytes_leidos += bytes_a_leer_en_actual;

            instruccion_a_enviar.parametros[k + 3] = string_itoa(direccion_fisica);
            instruccion_a_enviar.parametros[k + 4] = string_itoa(bytes_a_leer_en_actual);
            k += 2;
        }
        instruccion_a_enviar.parametros[3 + cant_paginas * 2] = NULL;

        proceso->syscall = instruccion_a_enviar;
        cargar_proceso_desalojado_al_buffer_pro(buffer_a_enviar, proceso);
        t_paquete* paquete_a_enviar = crear_paquete_pro(INSTRUCCION, buffer_a_enviar);
        enviar_paquete_pro(paquete_a_enviar, fd_kernel_dispatch);
        destruir_paquete_pro(paquete_a_enviar);

        for (int i = 1; i < 3 + cant_paginas * 2; i++) {
            free(instruccion_a_enviar.parametros[i]);
        }
        free(instruccion_a_enviar.parametros);
        free(proceso);
    }
}

void io_fs_create(t_instruccion* instruccion){

        t_buffer* buffer_a_enviar = crear_buffer_pro();
        t_proceso_desalojado* proceso = malloc(sizeof(t_proceso_desalojado));

        proceso->motivo = SYSCALL_IO;
        proceso->contexto = registros_cpu;
        
        t_instruccion instruccion_a_enviar;
        instruccion_a_enviar.identificador = IO_FS_CREATE;
        instruccion_a_enviar.parametros = malloc(sizeof(char*)*6);
    
        instruccion_a_enviar.parametros[0] = instruccion->parametros[0];
        instruccion_a_enviar.parametros[1] = instruccion->parametros[1];
        instruccion_a_enviar.parametros[2] = NULL;

        proceso->syscall = instruccion_a_enviar;
        cargar_proceso_desalojado_al_buffer_pro(buffer_a_enviar, proceso);
        t_paquete* paquete_a_enviar = crear_paquete_pro(INSTRUCCION, buffer_a_enviar);
        enviar_paquete_pro(paquete_a_enviar, fd_kernel_dispatch);
        destruir_paquete_pro(paquete_a_enviar);
        free(proceso->syscall.parametros);
        free(proceso);
}

void io_fs_delete(t_instruccion* instruccion){
        t_buffer* buffer_a_enviar = crear_buffer_pro();
        t_proceso_desalojado* proceso = malloc(sizeof(t_proceso_desalojado));

        proceso->motivo = SYSCALL_IO;
        proceso->contexto = registros_cpu;
        
        t_instruccion instruccion_a_enviar;
        instruccion_a_enviar.identificador = IO_FS_DELETE;
        instruccion_a_enviar.parametros = malloc(sizeof(char*)*3);
    
        instruccion_a_enviar.parametros[0] = instruccion->parametros[0];
        instruccion_a_enviar.parametros[1] = instruccion->parametros[1];
        instruccion_a_enviar.parametros[2] = NULL;

        proceso->syscall = instruccion_a_enviar;
        cargar_proceso_desalojado_al_buffer_pro(buffer_a_enviar, proceso);
        t_paquete* paquete_a_enviar = crear_paquete_pro(INSTRUCCION, buffer_a_enviar);
        enviar_paquete_pro(paquete_a_enviar, fd_kernel_dispatch);
        destruir_paquete_pro(paquete_a_enviar);
        free(proceso->syscall.parametros);
        free(proceso);
}

void io_fs_truncate(t_instruccion* instruccion){
        t_buffer* buffer_a_enviar = crear_buffer_pro();
        t_proceso_desalojado* proceso = malloc(sizeof(t_proceso_desalojado));

        proceso->motivo = SYSCALL_IO;
        proceso->contexto = registros_cpu;
        
        t_instruccion instruccion_a_enviar;
        instruccion_a_enviar.identificador = IO_FS_TRUNCATE;
        instruccion_a_enviar.parametros = malloc(sizeof(char*)*4);
    
        instruccion_a_enviar.parametros[0] = instruccion->parametros[0];
        instruccion_a_enviar.parametros[1] = instruccion->parametros[1];
        char* aux = string_itoa(get_valor_registro(instruccion->parametros[2]));
        instruccion_a_enviar.parametros[2] = aux;
        instruccion_a_enviar.parametros[3] = NULL;

        proceso->syscall = instruccion_a_enviar;
        cargar_proceso_desalojado_al_buffer_pro(buffer_a_enviar, proceso);
        t_paquete* paquete_a_enviar = crear_paquete_pro(INSTRUCCION, buffer_a_enviar);
        enviar_paquete_pro(paquete_a_enviar, fd_kernel_dispatch);
        destruir_paquete_pro(paquete_a_enviar);
        free(aux);
        free(proceso->syscall.parametros);
        free(proceso);
}

void io_fs_write(t_instruccion* instruccion){

    // ENVIO: INT - ARCH - PTRO ARCH - CANT PAGS - TAM TOTAL - DF - TAM - DF - TAM

    int tamanio = get_valor_registro(instruccion->parametros[3]);
    int direccion_logica = get_valor_registro(instruccion->parametros[2]);
    int cant_paginas = cantidad_paginas_a_leer(direccion_logica, tamanio);

    t_buffer* buffer_a_enviar = crear_buffer_pro();
    t_proceso_desalojado* proceso = malloc(sizeof(t_proceso_desalojado));

    proceso->motivo = SYSCALL_IO;
    proceso->contexto = registros_cpu;
        
    t_instruccion instruccion_a_enviar;
    instruccion_a_enviar.identificador = IO_FS_WRITE;
    instruccion_a_enviar.parametros = malloc(sizeof(char*)*(3 + 2*cant_paginas)*2);
    
    instruccion_a_enviar.parametros[0] = instruccion->parametros[0];
    instruccion_a_enviar.parametros[1] = instruccion->parametros[1];
    instruccion_a_enviar.parametros[2] = string_itoa(get_valor_registro(instruccion->parametros[4]));
    instruccion_a_enviar.parametros[3] = string_itoa(cant_paginas);
    instruccion_a_enviar.parametros[4] = string_itoa(tamanio);

    if(cant_paginas == 1){
        
        instruccion_a_enviar.parametros[5] = string_itoa(traducir(direccion_logica));
        instruccion_a_enviar.parametros[6] = string_itoa(tamanio);
        instruccion_a_enviar.parametros[7] = NULL;
    }
    else{

        int bytes_leidos = 0;
        int bytes_a_leer_en_actual = 0;
        int desplazamiento = 0;
        int num_pag = 0;
        int bytes_libres = 0;
        int direccion_fisica = 0;

        int k = 0;
        for(int i=0; i<cant_paginas; i++){
            
            direccion_fisica = traducir(direccion_logica + bytes_leidos);
            
            num_pag = floor((direccion_logica + bytes_leidos)/ tamanio_pagina);
            desplazamiento = (direccion_logica + bytes_leidos) - (num_pag * tamanio_pagina);

            bytes_libres = tamanio_pagina - desplazamiento;

            if(bytes_libres < tamanio_pagina){
                bytes_a_leer_en_actual = bytes_libres;
            }else if ((tamanio - bytes_leidos) > tamanio_pagina){
                bytes_a_leer_en_actual = tamanio_pagina;
            }else{
                bytes_a_leer_en_actual = tamanio - bytes_leidos;
            }

            bytes_leidos += bytes_a_leer_en_actual;

            instruccion_a_enviar.parametros[k+5] = string_itoa(direccion_fisica);
            instruccion_a_enviar.parametros[k+6] = string_itoa(bytes_a_leer_en_actual);
            k += 2;
        }
        instruccion_a_enviar.parametros[5 + cant_paginas*2] = NULL;
    }

    proceso->syscall = instruccion_a_enviar;
    cargar_proceso_desalojado_al_buffer_pro(buffer_a_enviar, proceso);
    t_paquete* paquete_a_enviar = crear_paquete_pro(INSTRUCCION, buffer_a_enviar);
    enviar_paquete_pro(paquete_a_enviar, fd_kernel_dispatch);
    destruir_paquete_pro(paquete_a_enviar);
    int z = 2;
    while (instruccion_a_enviar.parametros[z]!= NULL){
        free(instruccion_a_enviar.parametros[z]);
        z++;
    }
    free(proceso->syscall.parametros);
    free(proceso);
}

void io_fs_read(t_instruccion* instruccion){

    // ENVIO: INT - ARCH - PTRO ARCH - CANT PAGS - TAM TOTAL - DF - TAM - DF - TAM

    int tamanio = get_valor_registro(instruccion->parametros[3]);
    int direccion_logica = get_valor_registro(instruccion->parametros[2]);
    int cant_paginas = cantidad_paginas_a_leer(direccion_logica, tamanio);

    t_buffer* buffer_a_enviar = crear_buffer_pro();
    t_proceso_desalojado* proceso = malloc(sizeof(t_proceso_desalojado));

    proceso->motivo = SYSCALL_IO;
    proceso->contexto = registros_cpu;
        
    t_instruccion instruccion_a_enviar;
    instruccion_a_enviar.identificador = IO_FS_READ;
    instruccion_a_enviar.parametros = malloc(sizeof(char*)*(3 + 2*cant_paginas)*2);
    
    instruccion_a_enviar.parametros[0] = instruccion->parametros[0]; 
    instruccion_a_enviar.parametros[1] = instruccion->parametros[1];

    char* ptro_arch = string_itoa(get_valor_registro(instruccion->parametros[4]));
    instruccion_a_enviar.parametros[2] = ptro_arch;


    char* cant_pag = string_itoa(cant_paginas);
    instruccion_a_enviar.parametros[3] = cant_pag;
   
    char* tam = string_itoa(tamanio);
    instruccion_a_enviar.parametros[4] = tam;

    printf("Cant pags: %s \n",instruccion_a_enviar.parametros[3]);
    printf("Tamanio: %s \n",instruccion_a_enviar.parametros[4]);
    char* df ;
    char* tam_df;
    if(cant_paginas == 1){
        
        df = string_itoa(traducir(direccion_logica));
        instruccion_a_enviar.parametros[5] = df;
        tam_df = string_itoa(tamanio);
        instruccion_a_enviar.parametros[6] = tam_df;
  
        instruccion_a_enviar.parametros[7] = NULL;

        printf("Dir logica: %s \n",instruccion_a_enviar.parametros[5]);
        printf("Tamanio: %s \n",instruccion_a_enviar.parametros[6]);
    }
    else{

        int bytes_leidos = 0;
        int bytes_a_leer_en_actual = 0;
        int desplazamiento = 0;
        int num_pag = 0;
        int bytes_libres = 0;
        int direccion_fisica = 0;

        int k = 0;
        for(int i=0; i<cant_paginas; i++){
            
            direccion_fisica = traducir(direccion_logica + bytes_leidos);
            
            num_pag = floor((direccion_logica + bytes_leidos)/ tamanio_pagina);
            desplazamiento = (direccion_logica + bytes_leidos) - (num_pag * tamanio_pagina);

            bytes_libres = tamanio_pagina - desplazamiento;

            if(bytes_libres < tamanio_pagina){
                bytes_a_leer_en_actual = bytes_libres;
            }else if ((tamanio - bytes_leidos) > tamanio_pagina){
                bytes_a_leer_en_actual = tamanio_pagina;
            }else{
                bytes_a_leer_en_actual = tamanio - bytes_leidos;
            }

            bytes_leidos += bytes_a_leer_en_actual;

            df = string_itoa(direccion_fisica);
            instruccion_a_enviar.parametros[k+5] = df;
         
            tam_df = string_itoa(bytes_a_leer_en_actual);
            instruccion_a_enviar.parametros[k+6] = tam_df;

            printf("Dir logica: %s \n",instruccion_a_enviar.parametros[5+k]);
            printf("Tamanio: %s \n",instruccion_a_enviar.parametros[6+k]);
            
            k += 2;
        }
        instruccion_a_enviar.parametros[5 + cant_paginas*2] = NULL;
    }

    proceso->syscall = instruccion_a_enviar;

    cargar_proceso_desalojado_al_buffer_pro(buffer_a_enviar, proceso);
    t_paquete* paquete_a_enviar = crear_paquete_pro(INSTRUCCION, buffer_a_enviar);
    enviar_paquete_pro(paquete_a_enviar, fd_kernel_dispatch);
    destruir_paquete_pro(paquete_a_enviar);

    free(cant_pag);
    free(ptro_arch);
    free(tam);
    free(df);
    free(tam_df);
    free(proceso->syscall.parametros);
    free(proceso);
    
}

// Otras funciones ---------------------------------------------------------------------

void enviar_contexto(t_motivo_desalojo motivo,t_instruccion* syscall){

    t_buffer *buffer = crear_buffer_pro();
    t_proceso_desalojado *proceso = malloc(sizeof(t_proceso_desalojado));
    proceso->motivo = motivo;
    proceso->contexto = registros_cpu;
    if(syscall != NULL){
    t_instruccion sys;
    // Contar la cantidad de parámetros en syscall->parametros
        int param_count = 0;
        while (syscall->parametros[param_count] != NULL) {
            param_count++;
        }
        // Copiar los parámetros
        sys.parametros = malloc((param_count + 1) * sizeof(char*)); // +1 para el NULL terminador
        for (int i = 0; i < param_count; ++i) {
            char* aux = strdup(syscall->parametros[i]);
            sys.parametros[i] = aux;
        }
        sys.parametros[param_count] = NULL; // Asegurar que el último elemento es NULL
        sys.identificador = syscall->identificador;
        proceso->syscall = sys;
    }

    cargar_proceso_desalojado_al_buffer_pro(buffer,proceso);
    t_paquete *paquete = crear_paquete_pro(INSTRUCCION, buffer);
    enviar_paquete_pro(paquete,fd_kernel_dispatch);
    destruir_paquete_pro(paquete);
    int i = 0;
    while(proceso->syscall.parametros[i]!=NULL){
        free(proceso->syscall.parametros[i]);
        i++;
    }
    free(proceso->syscall.parametros);
    free(proceso);
}

int cantidad_paginas_a_leer(int direccion_logica,int tamanio){
    
    int valor_inicial = floor(direccion_logica / tamanio_pagina);
    int valor_final = floor((direccion_logica+tamanio-1)/ tamanio_pagina);

    int cant_paginas = valor_final - valor_inicial + 1;
    return cant_paginas;
}

void loggear_exec(t_instruccion* instruccion_act, int cant_params, char* inst){

        if(cant_params == 0){
            log_info(logger_cpu, "PID: %d - Ejecutando: %s ", a_ejecutar->pid, inst);
        }else if(cant_params == 1){
            log_info(logger_cpu, "PID: %d - Ejecutando: %s - %s", a_ejecutar->pid, inst, instruccion_act->parametros[0]);

        }else if(cant_params == 2){
            log_info(logger_cpu, "PID: %d - Ejecutando: %s - %s | %s", a_ejecutar->pid, inst, instruccion_act->parametros[0], instruccion_act->parametros[1]);

        }else if(cant_params == 3){
            log_info(logger_cpu, "PID: %d - Ejecutando: %s - %s | %s | %s", a_ejecutar->pid, inst, instruccion_act->parametros[0], instruccion_act->parametros[1], instruccion_act->parametros[2]);

        }else if(cant_params == 4){
            log_info(logger_cpu, "PID: %d - Ejecutando: %s - %s | %s | %s | %s", a_ejecutar->pid, inst, instruccion_act->parametros[0], instruccion_act->parametros[1], instruccion_act->parametros[2], instruccion_act->parametros[3]);

        }else if(cant_params == 5){
            log_info(logger_cpu, "PID: %d - Ejecutando: %s - %s | %s | %s | %s | %s", a_ejecutar->pid, inst, instruccion_act->parametros[0], instruccion_act->parametros[1], instruccion_act->parametros[2], instruccion_act->parametros[3], instruccion_act->parametros[4]);
        }
        //free(a_ejecutar);
}

bool lista_interrupciones_esta_vacia(){
    pthread_mutex_lock(&mutex_lista_interrupciones);
    bool resultado = list_is_empty(lista_interrupciones);
    pthread_mutex_unlock(&mutex_lista_interrupciones);
    return resultado;
}

t_interrupcion* proxima_interrupcion_protegido(){
    pthread_mutex_lock(&mutex_lista_interrupciones);
    t_interrupcion* interrupcion = list_remove(lista_interrupciones, 0);
    pthread_mutex_unlock(&mutex_lista_interrupciones); 
    return interrupcion;
}

void destruir_instruccion(t_instruccion* instruccion, int comienzo){
    int i = comienzo;
    while(instruccion->parametros[i]!=NULL){
        free(instruccion->parametros[i]);
        i++;
    }
    free(instruccion->parametros);
    free(instruccion);
}