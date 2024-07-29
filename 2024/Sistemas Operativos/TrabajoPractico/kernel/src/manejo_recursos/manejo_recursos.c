#include "../../include/manejo_recursos/manejo_recursos.h"

void inicializar_manejo_recursos(){
    dic_registro_recursos = dictionary_create();

    for(int i = 0; i < string_array_size(nombres_recursos); i++){
        t_registro_recurso* registro_recurso = crear_registro_recurso(atoi(instancias_recursos[i]), nombres_recursos[i]);

        dictionary_put(dic_registro_recursos, nombres_recursos[i], registro_recurso);
    }
}


t_registro_recurso* crear_registro_recurso(int instancias, char* nombre){
    t_registro_recurso* registro_recurso = malloc(sizeof(t_registro_recurso));
    
    registro_recurso->cola_bloqueados = list_create();
    pthread_mutex_init(&registro_recurso->mutex_cola, NULL);
    registro_recurso->instancias_disponibles = instancias;
    registro_recurso->nombre = strdup(nombre);

    return registro_recurso; 
}


bool existe_recurso(char* nombre_recurso){
    return dictionary_has_key(dic_registro_recursos, nombre_recurso);
}


void atender_wait(t_pcb* pcb, char* nombre_recurso){
    char* nombre_recurso_limpio = sanitizar_nombre_recurso(nombre_recurso);

    if(!existe_recurso(nombre_recurso_limpio)){
        finalizar_proceso_por_recurso_invalido(pcb);
        free(nombre_recurso_limpio);
        return;
    }

    t_registro_recurso* registro_recurso = dictionary_get(dic_registro_recursos, nombre_recurso_limpio);

    registro_recurso->instancias_disponibles--;
    
    if(registro_recurso->instancias_disponibles<0){
        enviar_a_blocked_recurso(pcb, registro_recurso->nombre, registro_recurso);
        free(nombre_recurso_limpio);
        return;
    }
     
    marcar_recurso_asignado(pcb, registro_recurso->nombre);
    algoritmo_planificador_corto_plazo.devolver_a_cpu_por_algoritmo(pcb);
    free(nombre_recurso_limpio);
}


void enviar_a_blocked_recurso(t_pcb* pcb, char* nombre_recurso, t_registro_recurso* registro_recurso){
    validar_planificacion_prendida();
    
    encolar_protegido(registro_recurso->cola_bloqueados, pcb, &registro_recurso->mutex_cola);
    pcb->recurso_bloqueado = (struct t_registro_recurso*) registro_recurso;
    
    actualizar_estado_loggeando(pcb, BLOCKED_RECURSO); 
    loggear_bloqueo(pcb->pid, nombre_recurso);  
}


void atender_signal(t_pcb* pcb, char* nombre_recurso){
    char* nombre_recurso_limpio = sanitizar_nombre_recurso(nombre_recurso);

    if(!existe_recurso(nombre_recurso_limpio)){
        finalizar_proceso_por_recurso_invalido(pcb);
        return;
    }

    liberar_recurso(nombre_recurso_limpio);
     
    marcar_recurso_desasignado(pcb, nombre_recurso_limpio);
    algoritmo_planificador_corto_plazo.devolver_a_cpu_por_algoritmo(pcb);

    free(nombre_recurso_limpio);
}


void desbloquear_proximo_proceso(t_registro_recurso* registro_recurso){
    t_pcb* proceso = desencolar_protegido(registro_recurso->cola_bloqueados, &registro_recurso->mutex_cola); 
    marcar_recurso_asignado(proceso, registro_recurso->nombre);
    proceso->recurso_bloqueado = NULL;
    
    enviar_a_ready(proceso);
}


void finalizar_proceso_por_recurso_invalido(t_pcb* proceso){
    enviar_a_exit(proceso, INVALID_RESOURCE);
    sem_post(&disponibilidad_multiprogramacion);
}


void marcar_recurso_asignado(t_pcb* pcb, char* recurso){
    list_add(pcb->recursos_asignados, recurso);
}


void marcar_recurso_desasignado(t_pcb* pcb, char* recurso){
    list_remove_element(pcb->recursos_asignados, (void*)recurso);
}


void liberar_recursos_asignados(t_pcb* pcb){
    pthread_mutex_lock(&mutex_liberacion_recursos);
    proceso_a_liberar_recursos = pcb;
    list_iterate(pcb->recursos_asignados, (void*) liberar_recurso);
    pthread_mutex_unlock(&mutex_liberacion_recursos);
    list_destroy(pcb->recursos_asignados);
}


void liberar_recurso(char* recurso){
    t_registro_recurso* registro_recurso = dictionary_get(dic_registro_recursos, recurso);

    registro_recurso->instancias_disponibles++;

    if(registro_recurso->instancias_disponibles<=0){
        desbloquear_proximo_proceso(registro_recurso);
    }
}


void eliminar_proceso_bloqueado_en_recurso(t_pcb* pcb){
    t_registro_recurso* recurso_asociado = (t_registro_recurso*) pcb->recurso_bloqueado;

    pthread_mutex_lock(&recurso_asociado->mutex_cola);
    list_remove_element(recurso_asociado->cola_bloqueados, pcb);
    recurso_asociado->instancias_disponibles++;
    pthread_mutex_unlock(&recurso_asociado->mutex_cola);

    pcb->recurso_bloqueado = NULL;
    liberar_recursos_asignados(pcb);
    enviar_a_exit(pcb, CONSOLA_KERNEL);
    sem_post(&disponibilidad_multiprogramacion);
}