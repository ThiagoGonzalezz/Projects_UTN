#include "../../include/utils/utilsKernel.h"

void levantar_log_y_config(char* path)
{
	config_kernel = config_create(path);
	logger_kernel = log_create(LOG_FILE_PATH_KERNEL, "KERNEL", false, LOG_LEVEL_INFO);
	logger_secundario_kernel = log_create(LOG_FILE_PATH_SECUNDARIO_KERNEL, "KERNEL", true, LOG_LEVEL_INFO);

	ip_cpu = config_get_string_value(config_kernel, "IP_CPU");
	puerto_cpu_dispatch = config_get_string_value(config_kernel, "PUERTO_CPU_DISPATCH");
	puerto_cpu_interrupt = config_get_string_value(config_kernel, "PUERTO_CPU_INTERRUPT");

	ip_memoria = config_get_string_value(config_kernel, "IP_MEMORIA");
	puerto_memoria = config_get_string_value(config_kernel, "PUERTO_MEMORIA");

	ip_kernel = config_get_string_value(config_kernel, "IP_KERNEL");
  	puerto_escucha = config_get_string_value(config_kernel, "PUERTO_ESCUCHA");

	quantum = atoi(config_get_string_value(config_kernel, "QUANTUM"));
	grado_multiprogramacion = atoi(config_get_string_value(config_kernel, "GRADO_MULTIPROGRAMACION"));
    algoritmo_planificacion = config_get_string_value(config_kernel, "ALGORITMO_PLANIFICACION");
    
    
	nombres_recursos = config_get_array_value(config_kernel, "RECURSOS");
	instancias_recursos = config_get_array_value(config_kernel, "INSTANCIAS_RECURSOS");

    flag_planificacion_iniciada = 1;
    contador_pids = 1;

	controlador_clock.flag_interrupcion_realizada = 0;

	log_info(logger_secundario_kernel, "IP_KERNEL: %s | PUERTO_ESCUCHA: %s \n", ip_kernel, puerto_escucha);
    log_info(logger_secundario_kernel, "IP_CPU: %s | PUERTO_CPU_DISPATCH: %s | PUERTO_CPU_INTERRUPT: %s\n", ip_cpu, puerto_cpu_dispatch, puerto_cpu_interrupt);
	log_info(logger_secundario_kernel, "IP_MEMORIA: %s | PUERTO_MEMORIA: %s \n", ip_memoria, puerto_memoria);
}

void terminar_programa_kernel(){
	liberar_conexion(fd_cpu_dispatch);
	liberar_conexion(fd_k_memoria);
	liberar_conexion(fd_cpu_interrupt);
	liberar_conexion(fd_interfaz);
	terminar_programa(fd_server_interfaces, logger_secundario_kernel, config_kernel);
}


void iniciar_semaforos_y_mutex(){
   
   //Mutex
   pthread_mutex_init(&mutex_cola_new, NULL);
   pthread_mutex_init(&mutex_cola_ready, NULL);
   pthread_mutex_init(&mutex_cola_eliminacion, NULL);
   pthread_mutex_init(&mutex_contador_pids, NULL);
   pthread_mutex_init(&mutex_lista_procesos_en_sistema, NULL);
   pthread_mutex_init(&mutex_lista_exit, NULL);
   pthread_mutex_init(&mutex_logger, NULL);
   pthread_mutex_init(&mutex_gestion_planificador, NULL);
   pthread_mutex_init(&mutex_cambio_en_multiprogamacion, NULL);
   pthread_mutex_init(&mutex_comunicacion_memoria, NULL);
   pthread_mutex_init(&mutex_lista_interfaces, NULL);
   pthread_mutex_init(&mutex_liberacion_recursos, NULL);
   pthread_mutex_init(&mutex_clock, NULL);
   pthread_mutex_init(&mutex_interrupciones, NULL);


   //Semaforos 
   sem_init(&new_disponible, 0, 0);
   sem_init(&ready_disponible, 0, 0);
   sem_init(&disponibilidad_multiprogramacion, 0, grado_multiprogramacion);
   sem_init(&sem_planificacion, 0, 1);
   sem_init(&proceso_a_eliminar_disponible, 0, 0); 
}

void iniciar_listas_y_colas(){
	cola_new = list_create(); 
    cola_ready = list_create();
	cola_ready_plus = list_create();
	cola_eliminacion = list_create();
	lista_exit = list_create();
	lista_procesos_en_sistema = list_create();
	lista_interfaces_en_sistema = list_create();
	
}

void destruir_listas_y_colas(){
	//toDo es una paja

}

void destruir_semaforos_y_mutex(){
	//Semaforos
	sem_destroy(&new_disponible);
	sem_destroy(&ready_disponible);
	sem_destroy(&disponibilidad_multiprogramacion);
    sem_destroy(&sem_planificacion);
	sem_destroy(&proceso_a_eliminar_disponible);
	
	
    //Mutex
	pthread_mutex_destroy(&mutex_cola_new);
	pthread_mutex_destroy(&mutex_cola_ready);
	pthread_mutex_destroy(&mutex_cola_eliminacion);
	pthread_mutex_destroy(&mutex_contador_pids);
	pthread_mutex_destroy(&mutex_lista_procesos_en_sistema);
    pthread_mutex_destroy(&mutex_gestion_planificador);
	pthread_mutex_destroy(&mutex_cambio_en_multiprogamacion);
	pthread_mutex_destroy(&mutex_comunicacion_memoria);
	pthread_mutex_destroy(&mutex_lista_procesos_en_sistema);
	pthread_mutex_destroy(&mutex_liberacion_recursos);
	pthread_mutex_destroy(&mutex_clock);
	pthread_mutex_destroy(&mutex_interrupciones);
}


void conectarse_a_cpu_dispatch(){
	fd_cpu_dispatch = levantar_conexion_como_cliente(ip_cpu, puerto_cpu_dispatch, logger_secundario_kernel);
	handshake_cliente(HANDSHAKE_KERNEL_DISPATCH, fd_cpu_dispatch, logger_secundario_kernel, "CPU");
}


void conectarse_a_cpu_interrupt(){
	fd_cpu_interrupt = levantar_conexion_como_cliente(ip_cpu, puerto_cpu_interrupt, logger_secundario_kernel);
	handshake_cliente(HANDSHAKE_KERNEL_INTERRUPT, fd_cpu_interrupt, logger_secundario_kernel, "CPU");
}


void conectarse_a_memoria(){
    fd_k_memoria = levantar_conexion_como_cliente(ip_memoria, puerto_memoria, logger_secundario_kernel);
	handshake_cliente(HANDSHAKE_KERNEL, fd_k_memoria, logger_secundario_kernel, "MEMORIA");
}


void crear_hilo_atendedor_de_nuevas_interfaces(){
	pthread_t hilo_atender_interfaces;
    pthread_create(&hilo_atender_interfaces, NULL, (void*)atender_interfaces_entrantes, NULL);
	pthread_detach(hilo_atender_interfaces);
}


void encolar_protegido(t_list* cola, void* elemento, pthread_mutex_t* mutex){
	pthread_mutex_lock(mutex);
	list_add(cola, elemento);   //Lo agrega al final de la lista asi que funciona como cola
	pthread_mutex_unlock(mutex);
}

void* desencolar_protegido(t_list* cola, pthread_mutex_t* mutex){
    pthread_mutex_lock(mutex);
	void* elemento = list_remove(cola, 0); //Te retorna el primer elemento y lo elimina de la lista, ya funciona como desencolar :)
	pthread_mutex_unlock(mutex);
    return elemento;
}

void liberar_parametros(t_instruccion instruccion){
    char **parametros = instruccion.parametros;
    for (int i = 0; parametros[i] != NULL; i++) {
        free(parametros[i]);
    }
    free(parametros);
}