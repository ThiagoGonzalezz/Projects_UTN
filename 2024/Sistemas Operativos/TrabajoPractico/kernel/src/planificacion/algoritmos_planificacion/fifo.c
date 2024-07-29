#include "../../../include/planificacion/algoritmos_planificacion/fifo.h"

void planificar_corto_plazo_por_fifo(){
    
    t_pcb* proceso_a_ejecutar = proximo_a_ejecutar_fifo();

    enviar_a_running(proceso_a_ejecutar);

    ultimo_desalojo = recibir_retorno_de_proceso_cpu();

    atender_devolucion(proceso_a_ejecutar, ultimo_desalojo);

    free(ultimo_desalojo);
    proceso_running = NULL;
}


t_pcb* proximo_a_ejecutar_fifo(){
   return primero_en_ready();
}


void devolver_a_cpu_por_fifo(t_pcb* pcb){

    free(ultimo_desalojo);

    enviar_a_ejecutar(pcb);

    ultimo_desalojo = recibir_retorno_de_proceso_cpu();

    atender_devolucion(pcb, ultimo_desalojo);
}