#include "../../../include/planificacion/algoritmos_planificacion/vrr.h"

void planificar_corto_plazo_vrr() {
   
        proceso_running = seleccionar_proximo_proceso_vrr();
    
        bool* nuevo_flag_volvio = crear_flag_controlador_de_clock();
       
        controlar_vrr(nuevo_flag_volvio);

        enviar_a_running(proceso_running);

        ultimo_desalojo = recibir_retorno_de_proceso_cpu();

        mutear_clock();

        if(!controlador_clock.flag_interrupcion_realizada){*nuevo_flag_volvio = true;}

        atender_devolucion(proceso_running, ultimo_desalojo);

        desmutear_clock();

        controlar_nuevo_quantum();

        proceso_running = NULL;

        free(ultimo_desalojo);
}


t_pcb* seleccionar_proximo_proceso_vrr() {
    
    if (!cola_ready_plus_esta_vacia()) {
        return desencolar_protegido(cola_ready_plus, &mutex_cola_ready_plus);
    }else{    
        return desencolar_protegido(cola_ready, &mutex_cola_ready);
    }
}


void controlar_vrr(bool* flag_volvio_proceso) {
    timer = temporal_create();
    activar_clock(flag_volvio_proceso);
}


void enviar_a_ready_plus(t_pcb* pcb){
    validar_planificacion_prendida();

    encolar_protegido(cola_ready_plus, pcb, &mutex_cola_ready_plus);

    actualizar_estado_loggeando(pcb, READY_PLUS);

    loggear_lista_pids_en_ready_plus();

    sem_post(&ready_disponible);
}


void loggear_lista_pids_en_ready_plus(){
    char* lista_pids = string_new();
    t_pcb* pcb;
    char* pid;

    pthread_mutex_lock(&mutex_cola_ready_plus);
        
    // Para el primer elemento
        pcb = list_get(cola_ready_plus, 0);
        pid = string_itoa(pcb->pid);
        string_append(&lista_pids, pid);
        free(pid);

    // Para el resto de elementos
    for(int i = 1; i < list_size(cola_ready_plus); i++){
        pcb = list_get(cola_ready_plus, i);
        pid = string_itoa(pcb->pid);
        string_append(&lista_pids, ", ");
        string_append(&lista_pids, pid);
        free(pid);
    }

    pthread_mutex_unlock(&mutex_cola_ready_plus);
    
    char* mensaje = string_from_format("Cola Ready Prioridad: [%s]", lista_pids);
    loggear_protegido(mensaje, logger_kernel, &mutex_logger);
    
    free(mensaje);
    free(lista_pids);
}


void devolver_a_cpu_por_vrr(t_pcb* pcb) {
    free(ultimo_desalojo);

    enviar_a_ejecutar(pcb);

    if(!controlador_clock.flag_interrupcion_realizada){*flag_termino_de_ejecutar_actual = false;}

    desmutear_clock();

    ultimo_desalojo = recibir_retorno_de_proceso_cpu();
    
    mutear_clock();

    if(!controlador_clock.flag_interrupcion_realizada){*flag_termino_de_ejecutar_actual = true;}
    
    atender_devolucion(pcb, ultimo_desalojo);
}


void controlar_nuevo_quantum(){
    temporal_stop(timer);
    int64_t quantum_consumido = temporal_gettime(timer);

    if(ultimo_desalojo->motivo == SYSCALL_IO &&  quantum_consumido < proceso_running->quantum){
        quantum_sobrante = proceso_running->quantum - quantum_consumido;
        proceso_running->quantum = quantum_sobrante;
    }else{
        proceso_running->quantum = quantum;
    }

    temporal_destroy(timer);
}


bool cola_ready_plus_esta_vacia(){
    pthread_mutex_lock(&mutex_cola_ready_plus);
    bool resultado = list_is_empty(cola_ready_plus);
    pthread_mutex_unlock(&mutex_cola_ready_plus);
    return resultado;
}