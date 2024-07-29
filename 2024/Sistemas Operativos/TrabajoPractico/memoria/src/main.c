#include <../include/main.h>

int main(int argc, char *argv[]){
    
	char* path_cfg = argv[1];
	printf("El path de las config es: %s\n", path_cfg);

	levantar_log_y_config(path_cfg);
    inicializar_memoria();
    fd_server = iniciar_servidor(logger_memoria, "MEMORIA", ip_memoria, puerto_memoria); 
    

	fd_cpu = esperar_cliente(fd_server, logger_memoria);
    handshake_servidor(fd_cpu, logger_memoria);
	
	t_buffer* buffer = crear_buffer_pro();
	cargar_int_al_buffer_pro(buffer, tam_pagina);
	t_paquete* paquete = crear_paquete_pro(TAM_PAG, buffer);
	enviar_paquete_pro(paquete, fd_cpu);
	destruir_paquete_pro(paquete);

	fd_kernel = esperar_cliente(fd_server, logger_memoria);
    handshake_servidor(fd_kernel, logger_memoria); 


	pthread_t hilo_escucha;
	pthread_create(&hilo_escucha, NULL, (void*)escuchar_conexiones_entrantes, NULL);
	pthread_detach(hilo_escucha);

	pthread_t hilo_cpu; 
	pthread_create(&hilo_cpu, NULL, (void*) atender_memoria_cpu, (void*) &fd_cpu);
	pthread_detach(hilo_cpu);

	pthread_t hilo_kernel; 
	pthread_create(&hilo_kernel, NULL, (void*) atender_memoria_kernel, (void*) &fd_kernel);
	pthread_join(hilo_kernel, NULL);

	
	terminar_programa_memoria();
	
	return EXIT_SUCCESS;
}


