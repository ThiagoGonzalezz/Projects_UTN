#include "../../include/comunicacion/memoria_cpu.h"

void atender_memoria_cpu(void* fd_escucha){
	t_buffer* buffer;
    bool conectado = 1;
	int fd_cpu = *(int*)fd_escucha;
	while (conectado) {
		int cod_op = recibir_operacion(fd_cpu);
		switch (cod_op) {
		case MENSAJE:
			
			break;
		case PAQUETE:
			break;

		case MARCO: //recibe un pid y un nroDePagina
			usleep(retardo_peticiones * 1000);
			buffer = recibir_todo_el_buffer_pro(fd_cpu);
			u_int32_t nroFrameAEnviar = atender_acceso_a_tabla(buffer);

			t_buffer* a_enviar = crear_buffer_pro();
			cargar_uint32_al_buffer_pro(a_enviar, nroFrameAEnviar);
			t_paquete* paquete = crear_paquete_pro(MARCO, a_enviar);
			enviar_paquete_pro(paquete, fd_cpu);

			destruir_buffer_pro(buffer);
			destruir_paquete_pro(paquete);
			break;

		case INSTRUCCION: //recibo un pid y pc, devuelvo una instruccion
			usleep(retardo_peticiones * 1000);
			buffer = recibir_todo_el_buffer_pro(fd_cpu);
			t_instruccion* instruccion_a_enviar = atender_pc(buffer);

			t_buffer* a_enviar_ = crear_buffer_pro();
			cargar_instruccion_al_buffer_pro(a_enviar_, instruccion_a_enviar);
			t_paquete* paquete_ = crear_paquete_pro(INSTRUCCION, a_enviar_);
			enviar_paquete_pro(paquete_, fd_cpu);

			destruir_buffer_pro(buffer);
			destruir_paquete_pro(paquete_);

			break;

		case PEDIDO_DE_LECTURA: 
			usleep(retardo_peticiones * 1000);
			buffer = recibir_todo_el_buffer_pro(fd_cpu);
			
			int pid = extraer_int_del_buffer_pro(buffer);
    		int direc_fisica = extraer_int_del_buffer_pro(buffer);
   			int cant_bytes_a_leer = extraer_int_del_buffer_pro(buffer);

			void* valor_leido = atender_ped_lectura(pid, direc_fisica, cant_bytes_a_leer);
			
			t_buffer* buffer_l = crear_buffer_pro();
			cargar_stream_al_buffer_pro(buffer_l, valor_leido, cant_bytes_a_leer);
			
			t_paquete* paquete_l = crear_paquete_pro(PEDIDO_DE_LECTURA, buffer_l); 
			enviar_paquete_pro(paquete_l, fd_cpu);

			destruir_buffer_pro(buffer);
			destruir_paquete_pro(paquete_l);
			free(valor_leido);
			break;

		case PEDIDO_DE_ESCRITURA: 
			usleep(retardo_peticiones * 1000);
			buffer = recibir_todo_el_buffer_pro(fd_cpu);
			atender_ped_escritura(buffer);

			t_buffer* buffer_e = crear_buffer_pro();
			cargar_int_al_buffer_pro(buffer_e, OK);
			t_paquete* paquete_e = crear_paquete_pro(PEDIDO_DE_ESCRITURA, buffer_e); //ver como lo recibe realmente
			enviar_paquete_pro(paquete_e, fd_cpu);
			destruir_buffer_pro(buffer);
			destruir_paquete_pro(paquete_e);
			break;
		case RESIZE:
			usleep(retardo_peticiones * 1000);
			buffer = recibir_todo_el_buffer_pro(fd_cpu);
			atender_resize(buffer);
			destruir_buffer_pro(buffer);
			break;
		case -1:
			log_error(logger_memoria, "Desconexion de CPU");
			conectado = 0;
            break;
		default:
			log_warning(logger_memoria,"Operacion desconocida de CPU");
			break;
		}
	}

	liberar_conexion(fd_cpu);
	
	
}

u_int32_t atender_acceso_a_tabla(t_buffer* unBuffer){
	u_int32_t pid = extraer_uint32_del_buffer_pro(unBuffer);
	u_int32_t nroDePagina = extraer_uint32_del_buffer_pro(unBuffer);

	u_int32_t nroDeFrame = buscarFrame(nroDePagina, pid);
	log_info(logger_memoria, "PID: %d - Pagina: %d - Marco: %d", pid, nroDePagina, nroDeFrame); //Log obligatorio
	return nroDeFrame;
}

t_instruccion* buscar_instruccion_pc(int pid, uint32_t pc){
    t_proceso_en_memoria* proceso = buscarProcesoPorId(pid);
	 if (proceso)
	 {
		t_instruccion* instruccion = list_get(proceso->instrucciones, pc);
		return instruccion;
	 }
    return NULL; // no se encontró el proceso con el pid dado
}

t_instruccion* atender_pc(t_buffer* unBuffer){ //VER

	int pid = extraer_int_del_buffer_pro(unBuffer);

	uint32_t pc = extraer_uint32_del_buffer_pro(unBuffer);

	t_instruccion* instruccion_a_enviar = buscar_instruccion_pc(pid,pc); 

	return instruccion_a_enviar;

}


void atender_resize(t_buffer* unBuffer){
	int pid = extraer_int_del_buffer_pro(unBuffer);

	int tam_nuevo = extraer_int_del_buffer_pro(unBuffer);

	t_proceso_en_memoria* proceso = buscarProcesoPorId(pid);

	uint32_t cant_pags_actual = proceso->cant_pags;
	uint32_t cant_pags_nueva = calcular_cantPaginasNecesarias(tam_nuevo);

	if(cant_pags_nueva > cant_pags_actual){
		ampliar_proceso(proceso, cant_pags_nueva);
		log_info(logger_memoria, "PID: %d - Tamaño Actual: %d - Tamaño a Ampliar: %d",pid, cant_pags_actual*tam_pagina, tam_nuevo);
	}
	else{
		if(cant_pags_nueva < cant_pags_actual){
			reducir_proceso(proceso, cant_pags_nueva);
			log_info(logger_memoria, "PID: %d - Tamaño Actual: %d - Tamaño a Reducir: %d",pid, cant_pags_actual*tam_pagina, tam_nuevo);

			//mandar a cpu que el resize se realizó correctamente
			t_buffer* buffer = crear_buffer_pro();
			cargar_int_al_buffer_pro(buffer, SUCCESS);
			t_paquete* paquete = crear_paquete_pro(SUCCESS, buffer);
			enviar_paquete_pro(paquete, fd_cpu);
			destruir_paquete_pro(paquete);
		}
	}
	return;
}

void atender_ped_escritura(t_buffer* unBuffer) {
    int pid = extraer_int_del_buffer_pro(unBuffer);
    int direc_fisica = extraer_int_del_buffer_pro(unBuffer);
    int tam = extraer_int_del_buffer_pro(unBuffer);
	void* valor_a_escribir = extraer_algo_del_buffer_pro(unBuffer);
	
    if (direc_fisica < 0 || direc_fisica + tam > tam_memoria) {
        fprintf(stderr, "Error: la dirección física o el tamaño del dato a escribir están fuera de los límites\n");
        return; //no debería entrar acá
    }

    void* inicio_escritura = (unsigned char*)espacio_usuario + direc_fisica;

    memcpy(inicio_escritura, valor_a_escribir, tam);

    log_info(logger_memoria, "PID: %d - Acción: ESCRIBIR - Dirección Física: %d - Tamaño: %d", pid, direc_fisica,tam);

    //ver si no rompe esto
    free(valor_a_escribir);
}

void* atender_ped_lectura(int pid,int direc_fisica, int cant_bytes_a_leer){

    if (direc_fisica < 0 || direc_fisica + cant_bytes_a_leer > tam_memoria) {
        fprintf(stderr, "Error: la dirección física o los bytes a leer están fuera de los límites\n");
        return NULL;
    }

    void* inicio_lectura = (unsigned char*)espacio_usuario + direc_fisica;
    void* valor_leido = malloc(cant_bytes_a_leer);

    if (valor_leido == NULL) {
        fprintf(stderr, "Error al asignar memoria para valor_leido\n");
        return NULL;
    }

    memcpy(valor_leido, inicio_lectura, cant_bytes_a_leer);

	log_info(logger_memoria, "PID: %d - Acción: LEER - Dirección Física: %d - Tamaño: %d", pid, direc_fisica, cant_bytes_a_leer);
    return valor_leido;
} 