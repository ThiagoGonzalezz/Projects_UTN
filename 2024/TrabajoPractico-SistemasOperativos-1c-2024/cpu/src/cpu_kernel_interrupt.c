#include "../include/cpu_kernel_interrupt.h"

int motivo_int;

void atender_cpu_kernel_interrupt(){
    bool conectado = 1;

	while (conectado) {
		int cod_op = recibir_operacion(fd_kernel_interrupt);
		switch (cod_op) {
		case INTERRUPCION:
			t_buffer *buffer_recibido = recibir_todo_el_buffer_pro(fd_kernel_interrupt);
			t_interrupcion* interrupcion = extraer_interrupcion_del_buffer_pro(buffer_recibido);
			pthread_mutex_lock(&mutex_lista_interrupciones);
			list_add(lista_interrupciones, interrupcion);
			pthread_mutex_unlock(&mutex_lista_interrupciones);
			destruir_buffer_pro(buffer_recibido);
			break;
		case -1:
			log_error(logger_cpu, "Desconexion de KERNEL - Interrupt");
			conectado = 0;
            break;
		default:
			log_warning(logger_cpu,"Operacion desconocida de KERNEL - Interrupt");
			break;
		}
	}
}