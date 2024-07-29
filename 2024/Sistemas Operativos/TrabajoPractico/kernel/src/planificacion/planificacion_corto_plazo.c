#include "../../include/planificacion/planificacion_corto_plazo.h"

// Función para crear el hilo del planificador a corto plazo
void iniciar_hilo_planificador_corto_plazo(){
    pthread_t hilo_planificador;
    pthread_create(&hilo_planificador, NULL, (void*) planificar_corto_plazo, NULL);
    pthread_detach(hilo_planificador);
}


// Función del planificador a corto plazo
void planificar_corto_plazo(){
    while (1) {

        sem_wait(&ready_disponible);
        
        algoritmo_planificador_corto_plazo.planificar_por_algoritmo();

    }
}


void atender_devolucion(t_pcb* proceso, t_proceso_desalojado* contexto_desalojo){
    
    //Actualizar contexto
    actualizar_contexto(proceso, contexto_desalojo->contexto);
     
    switch (contexto_desalojo->motivo){
        case SUCCESS:
            liberar_recursos_asignados(proceso);
            enviar_a_exit(proceso, SUCCESS); 
            sem_post(&disponibilidad_multiprogramacion);
            break;

        case CLOCK:
            enviar_a_ready(proceso);
    
            break;

        case CONSOLA_KERNEL:
            liberar_recursos_asignados(proceso);
            enviar_a_exit(proceso, CONSOLA_KERNEL); 
            sem_post(&disponibilidad_multiprogramacion);
            break;

        case OUT_OF_MEMORY:
            liberar_recursos_asignados(proceso);
            enviar_a_exit(proceso, OUT_OF_MEMORY); 
            sem_post(&disponibilidad_multiprogramacion);
            break;

        case SYSCALL: 
            atender_syscall(proceso, contexto_desalojo->syscall);
            break;

        case SYSCALL_IO:
            atender_syscall_io(proceso, contexto_desalojo->syscall);

            break;
        default:
            break;
    }
}


void activar_clock(bool* flag_volvio_proceso){
    controlador_clock.flag_interrupcion_realizada = 0;
    controlador_clock.pid_actual = proceso_running->pid;

    pthread_t hilo_clock;
    pthread_create(&hilo_clock, NULL, (void*) funcion_clock, (void*)flag_volvio_proceso);
    pthread_detach(hilo_clock);
}


void funcion_clock(bool* flag_volvio_proceso){

    uint32_t pid_a_interrumpir = proceso_running->pid;
    
    usleep(proceso_running->quantum*1000);

    pthread_mutex_lock(&mutex_clock);

    if(!*flag_volvio_proceso){
    controlador_clock.flag_interrupcion_realizada = 1;
	interrumpir_por_clock(pid_a_interrumpir);
    }
    
    free(flag_volvio_proceso);

    pthread_mutex_unlock(&mutex_clock);
}


void actualizar_contexto(t_pcb* proceso, t_registros contexto){
    proceso->registros = contexto;
}


void setear_planificacion(){ 
    if(string_equals_ignore_case(algoritmo_planificacion, "FIFO")){
        algoritmo_planificador_corto_plazo.planificar_por_algoritmo = planificar_corto_plazo_por_fifo;
        algoritmo_planificador_corto_plazo.devolver_a_cpu_por_algoritmo = devolver_a_cpu_por_fifo;
        algoritmo_planificador_corto_plazo.retornar_a_ready_correspondiente_por_algoritmo = enviar_a_ready;
    }
    else if(string_equals_ignore_case(algoritmo_planificacion, "RR")){
        algoritmo_planificador_corto_plazo.planificar_por_algoritmo = planificar_corto_plazo_rr;
        algoritmo_planificador_corto_plazo.devolver_a_cpu_por_algoritmo = devolver_a_cpu_por_rr;
        algoritmo_planificador_corto_plazo.retornar_a_ready_correspondiente_por_algoritmo = enviar_a_ready;    
    }
    else if(string_equals_ignore_case(algoritmo_planificacion, "VRR")){
        algoritmo_planificador_corto_plazo.planificar_por_algoritmo = planificar_corto_plazo_vrr;
        algoritmo_planificador_corto_plazo.devolver_a_cpu_por_algoritmo = devolver_a_cpu_por_vrr;
        algoritmo_planificador_corto_plazo.retornar_a_ready_correspondiente_por_algoritmo = enviar_a_ready_plus;

    }
}

t_pcb* primero_en_ready(){
    return desencolar_protegido(cola_ready, &mutex_cola_ready);
}


void enviar_a_running(t_pcb* pcb){
    validar_planificacion_prendida();

    proceso_running = pcb;
    actualizar_estado_loggeando(pcb, RUNNING);

    enviar_a_ejecutar(pcb);
}

void atender_syscall(t_pcb* proceso, t_instruccion syscall){
    switch (syscall.identificador){
    case WAIT:
        atender_wait(proceso, syscall.parametros[0]);
        break;
    
    case SIGNAL:
        atender_signal(proceso, syscall.parametros[0]);
        break;
        
    default:
        break;
    }

    

    liberar_parametros(syscall);
}

void mutear_clock(){
    pthread_mutex_lock(&mutex_clock);
}

void desmutear_clock(){
    pthread_mutex_unlock(&mutex_clock);
}

void interrumpir_por_clock(uint32_t pid){
    enviar_interrupcion(pid, INTERRUPT_CLOCK);
    char* mensaje = string_from_format("PID: %d - Desalojado por fin de Quantum", proceso_running->pid);
    loggear_protegido(mensaje, logger_kernel, &mutex_logger);
    free(mensaje);
}

bool* crear_flag_controlador_de_clock(){
    bool* flag_controlador = malloc(sizeof(bool));
    *flag_controlador = false;
    flag_termino_de_ejecutar_actual = flag_controlador;

    return flag_controlador;
}

void loggear_bloqueo(uint32_t pid, char* nombre_bloqueador){
    char* mensaje = string_from_format("PID: %d - Bloqueado por: %s", pid, nombre_bloqueador);
    loggear_protegido(mensaje, logger_kernel, &mutex_logger);
    free(mensaje);
}

char* sanitizar_nombre_recurso(char* nombre){
    size_t longitud = strlen(nombre);
    char* nombre_sanitizado = malloc(longitud);
    strncpy(nombre_sanitizado, nombre, longitud - 1);
    
    nombre_sanitizado[longitud - 1] = '\0';

    return nombre_sanitizado; 
}