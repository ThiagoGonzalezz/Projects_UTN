#include "../../include/comunicacion/memoria_kernel.h"

void atender_memoria_kernel(void* fd_escucha){

	int fd_kernel = *(int*)fd_escucha;
    bool conectado = 1;

	t_buffer* buffer;

	while (conectado) {
		int cod_op = recibir_operacion(fd_kernel);
		switch (cod_op) {
		case MENSAJE:
			
			break;
		case PAQUETE:
			break;
		//segun lo que entiendo no va a recibir nunca un MENSAJE o PAQUETE pero bueno por ahora lo dejo de onda
		case CREAR_PROCESO: 
			usleep(retardo_peticiones * 1000);
		  	buffer = recibir_todo_el_buffer_pro(fd_kernel);
			atender_crear_proceso(buffer);

			//Avisar al Kernel que llego bien
			uint32_t result = 1;
			send(fd_kernel, &result , sizeof(uint32_t), 0);

			destruir_buffer_pro(buffer);
			break;

		case FINALIZAR_PROCESO:
			usleep(retardo_peticiones * 1000);
			buffer = recibir_todo_el_buffer_pro(fd_kernel);
			atender_finalizar_proceso(buffer);
			destruir_buffer_pro(buffer);
			break;

		case -1:
			log_error(logger_memoria, "Desconexion de KERNEL");
			conectado = 0;
            break;
		default:
			log_warning(logger_memoria,"Operacion desconocida de KERNEL");
			break;
		}
	}
	liberar_conexion(fd_kernel);
	
}

void atender_crear_proceso(t_buffer* buffer){
	int pid = extraer_uint32_del_buffer_pro(buffer);
	char* path = extraer_string_del_buffer_pro(buffer);
	crearProcesoEnMemoria(pid, path);
	free(path);
}

void atender_finalizar_proceso(t_buffer* buffer){
	int pid = extraer_uint32_del_buffer_pro(buffer);
    finalizarProceso(pid);
}