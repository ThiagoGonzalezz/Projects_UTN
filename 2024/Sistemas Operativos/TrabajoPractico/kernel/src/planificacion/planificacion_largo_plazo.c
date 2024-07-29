#include "../../include/planificacion/planificacion_largo_plazo.h"


void crear_proceso(char* path){
    t_pcb* pcb = crear_pcb();
    
    //solicitar creacion de proceso en memoria
    solicitar_creacion_proceso_en_memoria(pcb->pid, path);

    //esperar OK
    uint32_t proceso_creado = validar_creacion_proceso_en_memoria();
    
     // Si la memoria pudo hacer todo, mandar a new
    if(proceso_creado == 1){
        encolar_protegido(lista_procesos_en_sistema, pcb, &mutex_lista_procesos_en_sistema);
        mandar_proceso_a_new(pcb);   
    }
}


t_pcb* crear_pcb()
{
	t_pcb* pcb = malloc(sizeof(t_pcb));
    pcb->pid = nuevo_pid();
	pcb->estado = NEW;
    pcb->registros = iniciar_registros();
	pcb->quantum = quantum;
    pcb->recurso_bloqueado = NULL;
    pcb->interfaz_bloqueado = NULL;
    pcb->recursos_asignados = list_create();

	return pcb;
}

uint32_t nuevo_pid(){
    pthread_mutex_lock(&mutex_contador_pids);
    uint32_t pid = contador_pids;
    contador_pids ++;
    pthread_mutex_unlock(&mutex_contador_pids);
    return pid;
} 


t_registros iniciar_registros(){
    t_registros registros;
    registros.PC = 0;
    registros.AX = 0;
    registros.BX = 0;
    registros.CX = 0;
    registros.DX = 0;
    registros.EAX = 0;
    registros.EBX = 0;
    registros.ECX = 0;
    registros.EDX = 0;
    registros.SI = 0;
    registros.DI = 0;
    
    return registros;
}

void mandar_proceso_a_new(t_pcb* pcb){
    
    encolar_protegido(cola_new, pcb, &mutex_cola_new);

    char* mensaje = string_from_format("Se crea el proceso %d en NEW", pcb->pid);
    loggear_protegido(mensaje, logger_kernel, &mutex_logger);
    free(mensaje);

	sem_post(&new_disponible);
}


//funcion invocada por consola
void eliminar_proceso(intptr_t pid){
    t_pcb* pcb = pcb_por_pid(pid);
    
    if(pcb != NULL){
        ver_como_eliminar(pcb);
    }else{
        log_error(logger_secundario_kernel, "El proceso indicado no existe");
    }
}


void ver_como_eliminar(t_pcb* pcb){
    switch(pcb->estado){
        case RUNNING: 
           if(!controlador_clock.flag_interrupcion_realizada){
           enviar_interrupcion(pcb->pid, INTERRUPT_CONSOLA);
           }else{
            ver_como_eliminar(pcb);
           }

           break;

        case BLOCKED_IO:
           eliminar_proceso_en_io(pcb);
           break;

        case BLOCKED_RECURSO:
           eliminar_proceso_bloqueado_en_recurso(pcb);
           break;

        case READY:
           //sacar de ready, decremento el contador de procesos en ready y libero un espacio multiprogramacion
           sem_wait(&ready_disponible);
           pcb = extraer_especifico_protegido(cola_ready, &mutex_cola_ready, pcb->pid);
           liberar_recursos_asignados(pcb);
           enviar_a_exit(pcb, CONSOLA_KERNEL);
           sem_post(&disponibilidad_multiprogramacion);
           break;

        case READY_PLUS:
           //sacar de ready plus, decrementar su contador y liberar un espacio multiprogramacion
           sem_wait(&ready_disponible);
           pcb = extraer_especifico_protegido(cola_ready, &mutex_cola_ready_plus, pcb->pid);
           liberar_recursos_asignados(pcb);
           enviar_a_exit(pcb, CONSOLA_KERNEL);
           sem_post(&disponibilidad_multiprogramacion);
           break;

        case NEW:
           //saco de cola new y decremento el contador de procesos en new
           sem_wait(&new_disponible);
           pcb = extraer_especifico_protegido(cola_new, &mutex_cola_new, pcb->pid);
           enviar_a_exit(pcb, CONSOLA_KERNEL);
           break;
        
        case EXIT: //Esto no lo aclaraba el enunciado pero me parece lo correcto

           log_error(logger_secundario_kernel, "El proceso ya se encuentra finalizado");
           break; 
    }
}


void planificar_largo_plazo(){ 

    while(1){
    
    pthread_mutex_lock(&mutex_cambio_en_multiprogamacion); //Para poder agilizar el cambio en la multiprogramacion, sino van alternando y tarda mucho
    pthread_mutex_unlock(&mutex_cambio_en_multiprogamacion); 
    
    //Lo vuelvo a habilitar aca para que el otro hilo tengo mas tiempo para detener la planificacion
    
    sem_wait(&disponibilidad_multiprogramacion); //para esperar a que haya espacio segun el grado de multiprogramacion
        
    sem_wait(&new_disponible); //para esperar a que haya al menos un proceso en New disponible para llevar a ready

    
    t_pcb* pcb = desencolar_protegido(cola_new, &mutex_cola_new);
    enviar_a_ready(pcb);
    
    }
}

void iniciar_hilo_planificador_largo_plazo(){
    pthread_t hiloPlanificadorLargoPlazo;
    pthread_create(&hiloPlanificadorLargoPlazo, NULL, (void*) planificar_largo_plazo, NULL);
    pthread_detach(hiloPlanificadorLargoPlazo);
}

void iniciar_hilo_finalizador_de_procesos(){
    pthread_t hiloEliminadorDeProcesos;
    pthread_create(&hiloEliminadorDeProcesos, NULL, (void*) eliminador_de_procesos, NULL);
    pthread_detach(hiloEliminadorDeProcesos);
}

void actualizar_grado_multiprogramacion(intptr_t  nuevoGrado){
    intptr_t diferencia_grado = nuevoGrado - grado_multiprogramacion;
    
    //Para poder agilizar el cambio en la multiprogramacion, sino van alternando con el hilo planificador y tarda mucho
    pthread_mutex_lock(&mutex_cambio_en_multiprogamacion); 


    //si el nuevo grado de multiprogramacion es mayor al anterior, libero tantos espacios como haya de diferencia
    if(diferencia_grado > 0){        
        
        for(int i = 0; i < diferencia_grado; i++){
            sem_post(&disponibilidad_multiprogramacion);
        }
    } 

    //si el nuevo grado de multiprogramacion es menor al anterior, bloqueo tantos espacios como haya de diferencia
    else{
        for(int i = 0; i > diferencia_grado; i--){
            sem_wait(&disponibilidad_multiprogramacion);
        } 
    }
    
    grado_multiprogramacion = nuevoGrado;
    pthread_mutex_unlock(&mutex_cambio_en_multiprogamacion); 
}

void eliminador_de_procesos(){
   
    while(1){
    
    sem_wait(&proceso_a_eliminar_disponible); //para esperar a que haya al menos un proceso en Exit disponible para eliminar en memoria
    
    t_pcb* pcb = desencolar_protegido(cola_eliminacion, &mutex_cola_eliminacion);
    
    solicitar_eliminar_proceso_a_memoria(pcb->pid);
    
    }
}