#include "../../include/planificacion/planificacion.h"

void detener_planificacion(){

    pthread_mutex_lock(&mutex_gestion_planificador);
    
    if(flag_planificacion_iniciada != 0){ //Para controlar si ya estaba detenida y no romper el sistema de mutex
        flag_planificacion_iniciada = 0;

        pausar_cambios_de_estado();
    }

    pthread_mutex_unlock(&mutex_gestion_planificador);
}


void iniciar_planificacion(){ 
    
    pthread_mutex_lock(&mutex_gestion_planificador);
    
    if(flag_planificacion_iniciada != 1){ //Para controlar si ya estaba iniciada y no romper el sistema de mutex
        flag_planificacion_iniciada = 1;
   
        despausar_cambios_de_estado();
    }

    pthread_mutex_unlock(&mutex_gestion_planificador);
    
}

void iniciar_hilos_planificacion(){
    iniciar_hilo_planificador_largo_plazo();
    iniciar_hilo_finalizador_de_procesos();
    iniciar_hilo_planificador_corto_plazo();
}


t_pcb* pcb_por_pid(intptr_t pid){ //SOLO USAR PARA ELIMINAR PROCESOS PORQUE LO SACA DE LA LISTA DEL SISTEMA
    pthread_mutex_lock(&mutex_lista_procesos_en_sistema);

    pid_buscado = pid;
    t_pcb* pcb = (t_pcb*) list_remove_by_condition(lista_procesos_en_sistema, tiene_el_pid_buscado);

    pthread_mutex_unlock(&mutex_lista_procesos_en_sistema);
    return pcb;
}

bool tiene_el_pid_buscado(void* pcb){
    t_pcb* proceso = (t_pcb*) pcb;   
    return (proceso->pid == pid_buscado);
}

void enviar_a_exit(t_pcb* pcb, t_motivo_desalojo motivo){ //Falta agregar el motivo
    validar_planificacion_prendida();


    encolar_protegido(lista_exit, pcb, &mutex_lista_exit);
    encolar_protegido(cola_eliminacion, pcb, &mutex_cola_eliminacion);
    sem_post(&proceso_a_eliminar_disponible);

    //solicitar a memoria que libere todo el espacio que ocupaba el proceso
    actualizar_estado_loggeando(pcb, EXIT);
    char* mensaje = string_from_format("Finaliza el proceso %d - Motivo: %s.", pcb->pid, motivo_to_string(motivo));
    loggear_protegido(mensaje, logger_kernel, &mutex_logger);
    free(mensaje);
}

void actualizar_estado_loggeando(t_pcb* pcb, t_estados nuevo_estado){

    char* mensaje = string_from_format("PID: %d - Estado Anterior: %s - Estado Actual: %s", pcb->pid, estado_to_string(pcb->estado), estado_to_string(nuevo_estado));
    loggear_protegido(mensaje, logger_kernel, &mutex_logger);
    pcb->estado = nuevo_estado;
    free(mensaje);
}

void loggear_lista_pids_en_ready(){
    char* lista_pids = string_new();
    t_pcb* pcb;
    char* pid;

    pthread_mutex_lock(&mutex_cola_ready);
        
        //Para el primer elemento
        pcb = list_get(cola_ready, 0);
        pid = string_itoa(pcb->pid);
        string_append(&lista_pids, pid);
        free(pid);

        //Para el resto de elementos
     for(int i = 1; i < list_size(cola_ready); i++){
        pcb = list_get(cola_ready, i);
        pid = string_itoa(pcb->pid);
        string_append(&lista_pids, ", ");
        string_append(&lista_pids, pid);
        free(pid);
        
    }

    pthread_mutex_unlock(&mutex_cola_ready);
    
    char* mensaje = string_from_format("Cola Ready: [%s]", lista_pids);
    loggear_protegido(mensaje, logger_kernel, &mutex_logger);
    
    free(mensaje);
    free(lista_pids);
}


void enviar_a_ready(t_pcb* pcb){
    validar_planificacion_prendida();

    encolar_protegido(cola_ready, pcb, &mutex_cola_ready);

    actualizar_estado_loggeando(pcb, READY);

    loggear_lista_pids_en_ready();
    
    sem_post(&ready_disponible);
}

char* estado_to_string(t_estados estado){
	 switch (estado)
    {
    case NEW:
        return "NEW";        
    case READY:
        return "READY";
    case RUNNING:
        return "RUNNING";        
    case BLOCKED_IO:
        return "BLOCKED";
    case BLOCKED_RECURSO:
        return "BLOCKED";
    case EXIT:
        return "EXIT";
	case READY_PLUS:
	    return "READY PLUS";
    default:
        return "ERROR";
    }
}

t_pcb* extraer_especifico_protegido(t_list* lista, pthread_mutex_t* mutex, uint32_t pid){
    pthread_mutex_lock(mutex);
	
    pid_buscado = pid;
    t_pcb* proceso = (t_pcb*) list_remove_by_condition(lista, tiene_el_pid_buscado); 
	
    pthread_mutex_unlock(mutex);
    
    return proceso;
}

char* motivo_to_string(t_motivo_desalojo motivo){
	 switch (motivo)
    {
    case SUCCESS:
        return "SUCCESS";        
    case ERROR:
        return "ERROR";
    case CLOCK:
        return "CLOCK";        
    case SYSCALL:
        return "SYSCALL";
	case CONSOLA_KERNEL:
	    return "INTERRUMPED BY USER";
    case OUT_OF_MEMORY:
	    return "OUT OF MEMORY";
	case INVALID_IO:
	    return "INVALID INTERFACE";
	case INVALID_RESOURCE:
	    return "INVALID RESOURCE";
	case INVALID_OPERATION_IO:
	    return "INVALID OPERATION IO";
	default:
        return "ERROR MOTIVO TO STRING";
    }
}

void validar_planificacion_prendida(){
    sem_wait(&sem_planificacion);
    sem_post(&sem_planificacion);
}

void pausar_cambios_de_estado(){
    sem_wait(&sem_planificacion);
}

void despausar_cambios_de_estado(){
    sem_post(&sem_planificacion);
}
