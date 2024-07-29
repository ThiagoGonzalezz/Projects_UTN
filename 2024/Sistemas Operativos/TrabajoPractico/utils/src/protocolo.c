#include "../include/protocolo.h"


// ------------------------------------------------------------------------------------------
// -- Recibir --
// ------------------------------------------------------------------------------------------

//GENERICAS

int recibir_operacion(int socket_cliente)
{
	int cod_op;
	if(recv(socket_cliente, &cod_op, sizeof(int), MSG_WAITALL) > 0)
		return cod_op;
	else
	{
		liberar_conexion(socket_cliente);
		return -1;
	}
}

void destruir_buffer_pro(t_buffer* buffer){
	if(buffer->stream != NULL){
		free(buffer->stream);
	}
	free(buffer);
}

t_buffer* recibir_todo_el_buffer_pro(int conexion){
	t_buffer* buffer = malloc(sizeof(t_buffer));

	if(recv(conexion, &(buffer->size), sizeof(int), MSG_WAITALL) > 0){
		buffer->stream = malloc(buffer->size);
		if(recv(conexion, buffer->stream, buffer->size, MSG_WAITALL) > 0){
			return buffer;
		}else{
			printf("Error al recibir el buffer de la conexion\n");
			exit(EXIT_FAILURE);
		}
	}else{
		printf("Error al recibir el tamaño del buffer de la conexion");
        exit(EXIT_FAILURE);
	}

	return buffer;
}

void* extraer_algo_del_buffer_pro(t_buffer* buffer){
	if(buffer->size==0){
		printf("[ERROR]Buffer vacío \n" );
	}
	if(buffer->size<0){
		printf("[ERROR]Buffer negativo(RIP) \n");
	}
	int size_stream;
	memcpy(&size_stream, buffer->stream, sizeof(int));
	void* stream = malloc(size_stream);
	memcpy(stream, buffer->stream + sizeof(int), size_stream);
	
	int nuevo_size = buffer->size - sizeof(int) - size_stream;
	if(nuevo_size==0){
		buffer->size=0;
		free(buffer->stream);
		buffer->stream=NULL;
		return stream;
	}
	if(nuevo_size<0){
		printf("[ERROR]Buffer negativo(RIP) \n");
		exit(EXIT_FAILURE);
	}


    void* nuevo_stream = malloc(nuevo_size);
	memcpy(nuevo_stream, buffer->stream + sizeof(int) + size_stream, nuevo_size);
	free(buffer->stream);
	buffer->size = nuevo_size;
	buffer->stream = nuevo_stream;
	
	return stream;
}


int extraer_int_del_buffer_pro(t_buffer* buffer){
	int* entero = extraer_algo_del_buffer_pro(buffer);
	int valor = *entero;
	free(entero);
	return valor;
}

char* extraer_string_del_buffer_pro(t_buffer* buffer){
	char* string = extraer_algo_del_buffer_pro(buffer);
	return string;
}

uint32_t extraer_uint32_del_buffer_pro(t_buffer* buffer){
	uint32_t* entero = extraer_algo_del_buffer_pro(buffer);
	uint32_t valor = *entero;
	free(entero);
	return valor;
}

uint8_t extraer_uint8_del_buffer_pro(t_buffer* buffer){
	uint8_t* entero = extraer_algo_del_buffer_pro(buffer);
	uint8_t valor = *entero;
	free(entero);
	return valor;
}

t_instruccion* extraer_instruccion_del_buffer_pro(t_buffer* buffer){
    t_instruccion* instruccion = malloc(sizeof(t_instruccion)); // en algun lado hay que hacer el free

    instruccion->identificador = extraer_int_del_buffer_pro(buffer);
    int cantidad_parametros = extraer_int_del_buffer_pro(buffer);
    instruccion->parametros = malloc((cantidad_parametros + 1) * sizeof(char*)); // en algun lado hay que hacer el free

    for (int i = 0; i < cantidad_parametros; i++) {
        instruccion->parametros[i] = extraer_string_del_buffer_pro(buffer);
    }
    instruccion->parametros[cantidad_parametros] = NULL; //final del array

    return instruccion;
}

//KERNEL_CPU

t_proceso_a_ejecutar* extraer_proceso_a_ejecutar_del_buffer_pro(t_buffer* buffer){
	t_proceso_a_ejecutar* a_ejecutar = malloc(sizeof(t_proceso_a_ejecutar));
    a_ejecutar->pid = extraer_uint32_del_buffer_pro(buffer);
    a_ejecutar->contexto = extraer_registros_del_buffer_pro(buffer);
    return a_ejecutar;
}

t_registros extraer_registros_del_buffer_pro(t_buffer* buffer){
	t_registros registros;
    registros.PC = extraer_uint32_del_buffer_pro(buffer);
    registros.AX = extraer_uint8_del_buffer_pro(buffer);
	registros.BX = extraer_uint8_del_buffer_pro(buffer);
	registros.CX = extraer_uint8_del_buffer_pro(buffer);
	registros.DX = extraer_uint8_del_buffer_pro(buffer);
	registros.EAX = extraer_uint32_del_buffer_pro(buffer);
	registros.EBX = extraer_uint32_del_buffer_pro(buffer);
	registros.ECX = extraer_uint32_del_buffer_pro(buffer);
	registros.EDX = extraer_uint32_del_buffer_pro(buffer);
	registros.SI = extraer_uint32_del_buffer_pro(buffer);
	registros.DI = extraer_uint32_del_buffer_pro(buffer);
	
    return registros;
}

t_proceso_desalojado* extraer_proceso_desalojado_del_buffer_pro(t_buffer* buffer){
	t_proceso_desalojado* proceso_desalojado = malloc(sizeof(t_proceso_desalojado));
    proceso_desalojado->motivo = extraer_int_del_buffer_pro(buffer); //Es un enum, esta bien asi??
    proceso_desalojado->contexto = extraer_registros_del_buffer_pro(buffer);
	if(proceso_desalojado->motivo == SYSCALL || proceso_desalojado->motivo == SYSCALL_IO){
	t_instruccion* syscall = extraer_instruccion_del_buffer_pro(buffer);
	proceso_desalojado->syscall = *(syscall);
	free(syscall);
	}
	return proceso_desalojado;
}

t_interrupcion* extraer_interrupcion_del_buffer_pro(t_buffer* buffer){
	t_interrupcion* interrupcion = malloc(sizeof(t_interrupcion));
	interrupcion->pid_proceso_interrumpido = extraer_uint32_del_buffer_pro(buffer);
	interrupcion->tipo =  extraer_int_del_buffer_pro(buffer);
	return interrupcion;
}

//KERNEL_IO

t_presentacion_interfaz extraer_presentacion_interfaz_del_buffer_pro(t_buffer* buffer){
	t_presentacion_interfaz presentacion;
	presentacion.nombre = extraer_string_del_buffer_pro(buffer);
	presentacion.tipo = extraer_int_del_buffer_pro(buffer);

	return presentacion;
}

t_solicitud_ejecucion_io extraer_solicitud_ejecucion_io_del_buffer_pro(t_buffer* buffer){
	t_solicitud_ejecucion_io solicitud;
	solicitud.pid = extraer_uint32_del_buffer_pro(buffer);
	solicitud.instruccion = extraer_instruccion_del_buffer_pro(buffer);

	return solicitud;
}

t_resultado_io extraer_resultado_io_del_buffer_pro(t_buffer* buffer){
	t_resultado_io resultado = extraer_uint32_del_buffer_pro(buffer);
    return resultado;
}



// ------------------------------------------------------------------------------------------
// -- Enviar --
// ------------------------------------------------------------------------------------------

//GENERICAS

void cargar_stream_al_buffer_pro(t_buffer* buffer, void* algo, int tamAlgo){
	if(buffer->size == 0){
		buffer->stream = malloc(sizeof(int)+tamAlgo);
		memcpy(buffer->stream, &tamAlgo, sizeof(int));
		memcpy(buffer->stream + sizeof(int), algo, tamAlgo);
	}
	else{
		buffer->stream = realloc(buffer->stream, buffer->size + sizeof(int) + tamAlgo);
		memcpy(buffer->stream + buffer->size, &tamAlgo, sizeof(int));
		memcpy(buffer->stream + buffer->size + sizeof(int), algo, tamAlgo);
	}
	buffer->size+=sizeof(int);
	buffer->size+=tamAlgo;
}

void cargar_uint32_al_buffer_pro(t_buffer* buffer, uint32_t intACargar){
	cargar_stream_al_buffer_pro(buffer, &intACargar ,sizeof(uint32_t));
}

void cargar_uint8_al_buffer_pro(t_buffer* buffer, uint8_t intACargar){
	cargar_stream_al_buffer_pro(buffer, &intACargar ,sizeof(uint8_t));
}

void cargar_int_al_buffer_pro(t_buffer* buffer, int un_valor_int){
	cargar_stream_al_buffer_pro(buffer, &un_valor_int, sizeof(int));
}


void cargar_string_al_buffer_pro(t_buffer* buffer, char* un_valor_string){
	cargar_stream_al_buffer_pro(buffer, un_valor_string, strlen(un_valor_string)+1);
}


t_buffer* crear_buffer_pro(){
	t_buffer* buffer = malloc(sizeof(t_buffer));
	buffer->size = 0;
	buffer->stream = NULL;
	return buffer;
}


t_paquete* crear_paquete_pro(op_code cod_op, t_buffer* un_buffer){
	t_paquete* un_paquete = malloc(sizeof(t_paquete));
	un_paquete->codigo_operacion = cod_op;
	un_paquete->buffer=un_buffer;
	return un_paquete;
	
}

void destruir_paquete_pro(t_paquete* paquete){
	destruir_buffer_pro(paquete->buffer);
	free(paquete);
}

void* serializar_paquete_pro(t_paquete* paquete){
    int size_algo = paquete -> buffer -> size + 2*sizeof(int);
	void* algo = malloc(size_algo);
	int desplazamiento = 0;

	memcpy(algo + desplazamiento, &(paquete->codigo_operacion), sizeof(int));
	desplazamiento += sizeof(int);
	memcpy(algo + desplazamiento, &(paquete->buffer->size), sizeof(int));
	desplazamiento += sizeof(int);
    memcpy(algo + desplazamiento, paquete->buffer->stream, paquete->buffer->size);
	desplazamiento += paquete->buffer->size;

	return algo;
}

void enviar_paquete_pro(t_paquete* paquete, int conexion){
	void* stream_a_enviar = serializar_paquete_pro(paquete);
	int bytes = paquete->buffer->size + 2* sizeof(int);
	send(conexion, stream_a_enviar, bytes, 0);
	free(stream_a_enviar);
}

//MEMORIA - CPU
void cargar_instruccion_al_buffer_pro(t_buffer* buffer, t_instruccion* instruccion) {
    if (buffer == NULL) {
        fprintf(stderr, "Error: buffer nulo\n");
        return;
    }
    if (instruccion == NULL) {
        //fprintf(stderr, "Error: instrucción nula\n");
        return;
    }

    cargar_int_al_buffer_pro(buffer, instruccion->identificador); // Carga el identificador

    // Identifica y carga cantidad de parámetros
    int cantidad_parametros = 0;
    while (instruccion->parametros[cantidad_parametros] != NULL) {
        cantidad_parametros++;
    }
    cargar_int_al_buffer_pro(buffer, cantidad_parametros);

    // Carga los parámetros
    for (int i = 0; i < cantidad_parametros; i++) {
        if (instruccion->parametros[i] != NULL) {
            cargar_string_al_buffer_pro(buffer, instruccion->parametros[i]);
        } else {
            fprintf(stderr, "Error: parámetro nulo en la posición %d\n", i);
        }
    }
}

//KERNEL-CPU

void cargar_proceso_a_ejecutar_al_buffer_pro(t_buffer* buffer, t_proceso_a_ejecutar* a_ejecutar){
	cargar_uint32_al_buffer_pro(buffer, a_ejecutar->pid);
	cargar_registro_al_buffer_pro(buffer, a_ejecutar->contexto);
}

void cargar_registro_al_buffer_pro(t_buffer* buffer, t_registros registro){
	cargar_uint32_al_buffer_pro(buffer, registro.PC);
	cargar_uint8_al_buffer_pro(buffer, registro.AX);
	cargar_uint8_al_buffer_pro(buffer, registro.BX);
	cargar_uint8_al_buffer_pro(buffer, registro.CX);
	cargar_uint8_al_buffer_pro(buffer, registro.DX);
	cargar_uint32_al_buffer_pro(buffer, registro.EAX);
	cargar_uint32_al_buffer_pro(buffer, registro.EBX);
	cargar_uint32_al_buffer_pro(buffer, registro.ECX);
	cargar_uint32_al_buffer_pro(buffer, registro.EDX);
	cargar_uint32_al_buffer_pro(buffer, registro.SI);
	cargar_uint32_al_buffer_pro(buffer, registro.DI);
}

void cargar_proceso_desalojado_al_buffer_pro(t_buffer* buffer, t_proceso_desalojado* proceso){
	cargar_int_al_buffer_pro(buffer, proceso->motivo);
	cargar_registro_al_buffer_pro(buffer, proceso->contexto);
	if(proceso->motivo == SYSCALL||proceso->motivo == SYSCALL_IO){
	cargar_instruccion_al_buffer_pro(buffer, &(proceso->syscall)); //En caso de no ser syscall se puede mandar esto vacio???
	}
}

void cargar_interrupcion_al_buffer_pro(t_buffer* buffer, t_interrupcion interrupcion){
	cargar_uint32_al_buffer_pro(buffer, interrupcion.pid_proceso_interrumpido);
	cargar_int_al_buffer_pro(buffer, interrupcion.tipo);
}

//KERNEL_IO

void cargar_presentacion_interfaz_al_buffer_pro(t_buffer* buffer, t_presentacion_interfaz presentacion){
    cargar_string_al_buffer_pro(buffer, presentacion.nombre);
    cargar_int_al_buffer_pro(buffer, presentacion.tipo);
}

void cargar_solicitud_ejecucion_io_al_buffer_pro(t_buffer* buffer, t_solicitud_ejecucion_io solicitud){
	cargar_uint32_al_buffer_pro(buffer, solicitud.pid);
	cargar_instruccion_al_buffer_pro(buffer, solicitud.instruccion);
}

void cargar_resultado_io_al_buffer_pro(t_buffer* buffer, t_resultado_io resultado){
	cargar_uint32_al_buffer_pro(buffer, resultado);
}