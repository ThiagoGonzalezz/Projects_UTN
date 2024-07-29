#include "../../include/manejo_interfaces/manejo_interfaces.h"

t_registro_interfaz* crear_registro_interfaz(char* nombre, t_tipo_interfaz tipo_interfaz, int fd){
    t_registro_interfaz* registro_interfaz = malloc(sizeof(t_registro_interfaz));
    
    registro_interfaz->nombre = strdup(nombre);
    registro_interfaz->tipo = tipo_interfaz;
    registro_interfaz->fd = fd;    
    registro_interfaz->cola_bloqueados = list_create();
    registro_interfaz->proceso_atendiendo = NULL;
    registro_interfaz->flag_eliminar_proceso = false;
    
    pthread_mutex_init(&registro_interfaz->mutex_cola, NULL);    

    sem_init(&registro_interfaz->semaforo_disponibilidad, 0, 0); 

    return registro_interfaz;
}



void crear_hilo_gestionador_de_interfaz(t_registro_interfaz* registro_interfaz){
    pthread_t hilo_gestionador;
    pthread_create(&hilo_gestionador, NULL, (void* (*)(void*))gestionar_interfaz, (void*)registro_interfaz);
    pthread_detach(hilo_gestionador);
}



void gestionar_interfaz(t_registro_interfaz* registro_interfaz){
    
    while(1){
    sem_wait(&registro_interfaz->semaforo_disponibilidad);
    
    t_proceso_pendiente_io* proceso_con_instruccion = desencolar_protegido(registro_interfaz->cola_bloqueados, &registro_interfaz->mutex_cola);

    registro_interfaz->proceso_atendiendo = proceso_con_instruccion->pcb;
    
    //Validar que la IO siga conectada
    if(!interfaz_sigue_conectada(registro_interfaz->fd)){
        finalizar_proceso_pendiente(proceso_con_instruccion);
        registrar_desconexion_interfaz(registro_interfaz);
        break;
    }
    
    enviar_solicitud_ejecutar_instruccion(registro_interfaz->fd, &proceso_con_instruccion->instruccion_io, proceso_con_instruccion->pcb->pid);
    
    t_resultado_io resultado = recibir_resultado_io(registro_interfaz->fd);

    validar_planificacion_prendida();
    
    //Validar que la IO haya podido ejecutar correctamente la instruccion
    if(resultado != OK){
    t_motivo_desalojo motivo = obtener_motivo_finalizacion_segun_resultado_io(resultado); 
    
    registro_interfaz->proceso_atendiendo->interfaz_bloqueado = NULL;
    registro_interfaz->proceso_atendiendo = NULL;
    registro_interfaz->flag_eliminar_proceso = false;
    
    enviar_a_exit(proceso_con_instruccion->pcb, motivo);

    sem_post(&disponibilidad_multiprogramacion);
    liberar_parametros(proceso_con_instruccion->instruccion_io);
    free(proceso_con_instruccion);
    continue;
    }
    
    registro_interfaz->proceso_atendiendo->interfaz_bloqueado = NULL;
    registro_interfaz->proceso_atendiendo = NULL; 
    
    //Si la consola quiere eliminar el proceso 
    if(registro_interfaz->flag_eliminar_proceso){
        enviar_a_exit(proceso_con_instruccion->pcb, CONSOLA_KERNEL);     
        sem_post(&disponibilidad_multiprogramacion);
        registro_interfaz->flag_eliminar_proceso = false;
        continue;
    }
    
    algoritmo_planificador_corto_plazo.retornar_a_ready_correspondiente_por_algoritmo(proceso_con_instruccion->pcb);
    
    liberar_parametros(proceso_con_instruccion->instruccion_io);
    free(proceso_con_instruccion);
    }
    
}



t_registro_interfaz* interfaz_por_nombre(char* nombre){
    pthread_mutex_lock(&mutex_lista_interfaces);
    nombre_interfaz_buscada = strdup(nombre);

    t_registro_interfaz* interfaz = (t_registro_interfaz*) list_find(lista_interfaces_en_sistema, tiene_el_nombre_buscado);
    
    free(nombre_interfaz_buscada);

    pthread_mutex_unlock(&mutex_lista_interfaces);

    return interfaz;
}



bool tiene_el_nombre_buscado(void* registro_interfaz){
    t_registro_interfaz* interfaz = (t_registro_interfaz*) registro_interfaz;

    return (strcmp(interfaz->nombre, nombre_interfaz_buscada) == 0);
}



void registrar_desconexion_interfaz(t_registro_interfaz* registro_interfaz){

    pthread_mutex_lock(&mutex_lista_interfaces);
    list_remove_element(lista_interfaces_en_sistema, registro_interfaz);
    pthread_mutex_unlock(&mutex_lista_interfaces);

    finalizar_procesos_pendientes(registro_interfaz);

    destruir_registro_interfaz(registro_interfaz);
}



void destruir_registro_interfaz(t_registro_interfaz* registro){
    
    free(registro->nombre);
    list_destroy(registro->cola_bloqueados);
    sem_destroy(&registro->semaforo_disponibilidad);
    pthread_mutex_destroy(&registro->mutex_cola);
    
    free(registro);
}



void finalizar_procesos_pendientes(t_registro_interfaz* registro_interfaz){
    list_clean_and_destroy_elements(registro_interfaz->cola_bloqueados, finalizar_proceso_pendiente);
}



void finalizar_proceso_pendiente(void* proceso_con_instruccion){
    t_proceso_pendiente_io* proceso = (t_proceso_pendiente_io*) proceso_con_instruccion;
    
    proceso->pcb->interfaz_bloqueado = NULL;

    enviar_a_exit(proceso->pcb, INVALID_IO);
    
    liberar_parametros(proceso->instruccion_io);
    free(proceso);
    
    sem_post(&disponibilidad_multiprogramacion);
}



bool io_puede_ejecutar(t_tipo_interfaz tipo, op_code instruccion_id){ //CAMBIAR OP_CODE POR CODE_INSTRUCCION CUANDO ESTE IMPLEMENTADO
    switch(tipo){
        case GENERICA: 
        return instruccion_id == IO_GEN_SLEEP;
        
        case STDIN:
        return instruccion_id == IO_STDIN_READ;
        
        case STDOUT:
        return instruccion_id == IO_STDOUT_WRITE;

        case DIALFS:
        return instruccion_id == IO_FS_CREATE || instruccion_id == IO_FS_DELETE || instruccion_id == IO_FS_TRUNCATE || instruccion_id == IO_FS_WRITE || instruccion_id == IO_FS_READ;

    default:
        return false;
    }
}



bool interfaz_sigue_conectada(int fd_interfaz){
    uint8_t validacion_conexion_recv = 0; 
    int sigue_conectado = send(fd_interfaz, &validacion_conexion_recv, sizeof(validacion_conexion_recv), MSG_NOSIGNAL);
    sigue_conectado = send(fd_interfaz, &validacion_conexion_recv, sizeof(validacion_conexion_recv), MSG_NOSIGNAL);
    //Doble send porque el primero es en donde se habia bloqueado el io y el segundo reconfirma la conexion
    
    if(sigue_conectado == -1){
        return false;
    }else{
        return true;
    }
}



t_motivo_desalojo obtener_motivo_finalizacion_segun_resultado_io(t_resultado_io resultado){
    switch (resultado){
   
    default:
        return ERROR;
    }
}



void eliminar_proceso_en_io(t_pcb* pcb){
    t_registro_interfaz* interfaz_asociada = (t_registro_interfaz*) pcb->interfaz_bloqueado;

    if(interfaz_asociada->proceso_atendiendo == pcb){
        interfaz_asociada->flag_eliminar_proceso = true;
        return;
    }

    sem_wait(&interfaz_asociada->semaforo_disponibilidad);
    pthread_mutex_lock(&interfaz_asociada->mutex_cola);
    proceso_en_interfaz_buscado = pcb;
    list_remove_by_condition(interfaz_asociada->cola_bloqueados, (void *)es_el_proceso_en_io_buscado);
    pthread_mutex_unlock(&interfaz_asociada->mutex_cola);
    
    pcb->interfaz_bloqueado = NULL;
    liberar_recursos_asignados(pcb);
    enviar_a_exit(pcb, CONSOLA_KERNEL);
    sem_post(&disponibilidad_multiprogramacion);
}




bool es_el_proceso_en_io_buscado(t_proceso_pendiente_io* proceso_con_instruccion){
    return proceso_en_interfaz_buscado == proceso_con_instruccion->pcb;
}



void enviar_a_blockedo_io(t_pcb* pcb, t_registro_interfaz* interfaz,  t_instruccion instruccion_io){
    t_proceso_pendiente_io* proceso_con_instruccion = malloc(sizeof(t_proceso_pendiente_io));
    proceso_con_instruccion->pcb = pcb;
    proceso_con_instruccion->instruccion_io = instruccion_io;

    validar_planificacion_prendida();

    encolar_protegido(interfaz->cola_bloqueados, proceso_con_instruccion, &interfaz->mutex_cola);

    actualizar_estado_loggeando(pcb, BLOCKED_IO);
    loggear_bloqueo(pcb->pid, interfaz->nombre);
    pcb->interfaz_bloqueado = (struct t_registro_interfaz*) interfaz;

    sem_post(&interfaz->semaforo_disponibilidad);
}

void atender_syscall_io(t_pcb* pcb, t_instruccion instruccion_io){
    
    t_registro_interfaz* interfaz = interfaz_por_nombre(instruccion_io.parametros[0]);
    
    if(interfaz == NULL){
        enviar_a_exit(pcb, INVALID_IO);
        sem_post(&disponibilidad_multiprogramacion);
        liberar_parametros(instruccion_io);
        return;
    }

    if(!io_puede_ejecutar(interfaz->tipo, instruccion_io.identificador)){
        enviar_a_exit(pcb, INVALID_OPERATION_IO);
        sem_post(&disponibilidad_multiprogramacion);
        liberar_parametros(instruccion_io);
        return;
    }
    
    enviar_a_blockedo_io(pcb, interfaz, instruccion_io);
}         