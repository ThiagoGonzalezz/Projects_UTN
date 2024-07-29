#include "../../../include/planificacion/algoritmos_planificacion/rr.h"

void planificar_corto_plazo_rr(){
    
    proceso_running = proximo_a_ejecutar_rr();

    bool* nuevo_flag_volvio = crear_flag_controlador_de_clock();
    
    controlar_rr(nuevo_flag_volvio);
    
    enviar_a_running(proceso_running);
    
    ultimo_desalojo = recibir_retorno_de_proceso_cpu();
    
    mutear_clock();
     
    if(!controlador_clock.flag_interrupcion_realizada){*nuevo_flag_volvio = true;}
    
    atender_devolucion(proceso_running, ultimo_desalojo);

    desmutear_clock();

    free(ultimo_desalojo);
    proceso_running = NULL;
}


void controlar_rr(bool* flag_volvio_proceso) {
    activar_clock(flag_volvio_proceso);
}


t_pcb* proximo_a_ejecutar_rr(){
    return primero_en_ready();
}

void devolver_a_cpu_por_rr(t_pcb* pcb){
    
    free(ultimo_desalojo);

    enviar_a_ejecutar(pcb);

    if(!controlador_clock.flag_interrupcion_realizada){*flag_termino_de_ejecutar_actual = false;}

    desmutear_clock();

    ultimo_desalojo = recibir_retorno_de_proceso_cpu();
    
    mutear_clock();

    if(!controlador_clock.flag_interrupcion_realizada){*flag_termino_de_ejecutar_actual = true;}
    
    atender_devolucion(pcb, ultimo_desalojo);
}