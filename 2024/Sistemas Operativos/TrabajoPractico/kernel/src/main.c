#include <../include/main.h>

int main(int argc, char *argv[]){
    
	char* path = argv[1];
	printf("El path a las config es: %s\n", path);

    levantar_log_y_config(path);

	iniciar_listas_y_colas();

	iniciar_semaforos_y_mutex();
	
	inicializar_manejo_recursos();

	fd_server_interfaces = iniciar_servidor(logger_secundario_kernel, "KERNEL", ip_kernel, puerto_escucha);  
    
	conectarse_a_cpu_dispatch();

	conectarse_a_cpu_interrupt();

	conectarse_a_memoria();

	crear_hilo_atendedor_de_nuevas_interfaces();

    setear_planificacion();

    iniciar_hilos_planificacion();
	
	consola();

	terminar_programa_kernel();

    return 0;
}

