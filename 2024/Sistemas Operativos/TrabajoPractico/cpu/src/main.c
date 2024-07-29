#include <../include/main.h>

t_registros registros_cpu = {0,0,0,0,0,0,0,0,0,0,0};
int main(int argc, char *argv[]){
    tlb = list_create();
	char* path = argv[1];
	printf("El path a las config es: %s\n", path);
	levantar_log_y_config(path);

	fd_memoria = levantar_conexion_como_cliente(ip_memoria, puerto_memoria, logger_sec_cpu);
	handshake_cliente(handshake_cpu, fd_memoria, logger_sec_cpu, "MEMORIA");

	int tam_op = recibir_operacion(fd_memoria);

	switch (tam_op)
	{
	case TAM_PAG:
		t_buffer* buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
		tamanio_pagina = extraer_uint32_del_buffer_pro(buffer_recibido);
		destruir_buffer_pro(buffer_recibido);
		break;
	default:
		break;
	}
	
	fd_servidor_dispatch = iniciar_servidor(logger_sec_cpu, "CPU_DISPATCH - KERNEL ", ip_cpu, puerto_escucha_dispatch);
	fd_servidor_interrupt = iniciar_servidor(logger_sec_cpu, "CPU_INTERRUPT - KERNEL", ip_cpu, puerto_escucha_interrupt);

	fd_kernel_dispatch = esperar_cliente(fd_servidor_dispatch, logger_sec_cpu);
	handshake_servidor(fd_kernel_dispatch, logger_sec_cpu);
	log_info(logger_sec_cpu, "Se conecto un cliente (KERNEL DISPATCH), handshake correcto.");

	fd_kernel_interrupt = esperar_cliente(fd_servidor_interrupt, logger_sec_cpu);
	handshake_servidor(fd_kernel_interrupt, logger_sec_cpu);
	log_info(logger_sec_cpu, "Se conecto un cliente (KERNEL INTERRUPT), handshake correcto.");

	pthread_t hilo_kernel_interrupt;
	pthread_create(&hilo_kernel_interrupt, NULL, (void*)atender_cpu_kernel_interrupt, NULL);
	pthread_detach(hilo_kernel_interrupt);

	pthread_t hilo_kernel_dispatch;
	pthread_create(&hilo_kernel_dispatch, NULL, (void*)atender_cpu_kernel_dispatch, NULL);

    pthread_join(hilo_kernel_dispatch, NULL);
	terminar_programa_cpu();
	return EXIT_SUCCESS;
}