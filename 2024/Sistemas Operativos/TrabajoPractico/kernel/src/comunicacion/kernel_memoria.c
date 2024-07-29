#include "../../include/comunicacion/kernel_memoria.h"

void atender_kernel_memoria(){
    bool conectado = 1;

	while (conectado) {
		int cod_op = recibir_operacion(fd_k_memoria);
		switch (cod_op) {
		case MENSAJE:
			
			break;
		case PAQUETE:
			//

			break;
		case -1:
			log_error(logger_secundario_kernel, "Desconexion de MEMORIA");
			conectado = 0;
            break;
		default:
			log_warning(logger_secundario_kernel,"Operacion desconocida de MEMORIA");
			break;
		}
	}
}

uint32_t validar_creacion_proceso_en_memoria(){
    uint32_t confirmacion = 0;
	
    recv(fd_k_memoria, &confirmacion, sizeof(uint32_t), MSG_WAITALL); 
	if(!confirmacion){
		log_error(logger_secundario_kernel,"No se pudo crear la tabla de páginas del proceso.");
	}
    
    return confirmacion;
}


void solicitar_creacion_proceso_en_memoria(uint32_t pid, char* path){
    
	//Armar el buffer
	t_buffer* a_enviar = crear_buffer_pro();
	cargar_uint32_al_buffer_pro(a_enviar, pid);
	cargar_string_al_buffer_pro(a_enviar, path);
										
	//Armar paquete
	t_paquete* paquete = crear_paquete_pro(CREAR_PROCESO, a_enviar);
	
	//Enviar paquete
	pthread_mutex_lock(&mutex_comunicacion_memoria);
	enviar_paquete_pro(paquete, fd_k_memoria);
    pthread_mutex_unlock(&mutex_comunicacion_memoria);

	//Hacer frees
	destruir_paquete_pro(paquete);
}

void solicitar_eliminar_proceso_a_memoria(uint32_t pid){
	
	//Armar el buffer
	t_buffer* a_eliminar = crear_buffer_pro();
	cargar_uint32_al_buffer_pro(a_eliminar, pid);
										
	//Armar paquete
	t_paquete* paquete = crear_paquete_pro(FINALIZAR_PROCESO, a_eliminar);
	
	//Enviar paquete
	pthread_mutex_lock(&mutex_comunicacion_memoria);
	enviar_paquete_pro(paquete, fd_k_memoria);
    pthread_mutex_unlock(&mutex_comunicacion_memoria);

	//Hacer frees
	destruir_paquete_pro(paquete);
}
