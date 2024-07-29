#include "../include/cpu_kernel_dispatch.h"

void atender_cpu_kernel_dispatch(){
    bool conectado = 1;
	while (conectado) {
		int cod_op = recibir_operacion(fd_kernel_dispatch);
		switch (cod_op) {
		case PAQUETE:
			break;
		case CONTEXTO_EJECUCION:
		  	t_buffer* buffer = recibir_todo_el_buffer_pro(fd_kernel_dispatch);
			atender_ejecutar_proceso(buffer);
			//destruir_buffer_pro(buffer);
		    break;
		case -1:
			log_error(logger_cpu, "Desconexion de KERNEL - Dispatch");
			conectado = 0;
            break;
		default:
			log_warning(logger_cpu,"Operacion desconocida de KERNEL - Dispatch");
			break;
		}
	}
}

void atender_ejecutar_proceso(t_buffer* buffer){
	a_ejecutar = extraer_proceso_a_ejecutar_del_buffer_pro(buffer);
     
	printf("Me llego la solicitud de ejecutar el proceso de Pid: %d con el PC: %d \n", a_ejecutar->pid, a_ejecutar->contexto.PC);
	printf("Registros---------------------------------------------------------------------------------------------------------------------\n");
	printf("AX: %d   |BX: %d    |CX: %d   |DX: %d   \n", a_ejecutar->contexto.AX, a_ejecutar->contexto.BX, a_ejecutar->contexto.CX, a_ejecutar->contexto.DX);
	printf("EAX: %d  |EBX: %d   |ECX: %d  |EDX: %d   \n", a_ejecutar->contexto.EAX, a_ejecutar->contexto.EBX, a_ejecutar->contexto.ECX, a_ejecutar->contexto.EDX);
	printf("SI: %d   |DI: %d    \n", a_ejecutar->contexto.SI, a_ejecutar->contexto.DI);
	printf("------------------------------------------------------------------------------------------------------------------------------\n");

	registros_cpu = a_ejecutar->contexto;
	check_interrupt(execute(decode(fetch(a_ejecutar))));
	destruir_buffer_pro(buffer);
}