#include "../../include/comunicacion/kernel_cpu_dispatch.h"

void enviar_a_ejecutar(t_pcb* proceso){

    t_proceso_a_ejecutar* proceso_contexto = malloc(sizeof(t_proceso_a_ejecutar));
    proceso_contexto->pid= proceso->pid;
    proceso_contexto->contexto= proceso->registros;

	//Armo el buffer
    t_buffer* buffer_contexto = crear_buffer_pro();
	cargar_proceso_a_ejecutar_al_buffer_pro(buffer_contexto, proceso_contexto);
    free(proceso_contexto);
    
	//Lo serializo
    t_paquete* paquete_contexto = crear_paquete_pro(CONTEXTO_EJECUCION, buffer_contexto);
     
	//Lo envio
    enviar_paquete_pro(paquete_contexto, fd_cpu_dispatch);
    

    //Lo libero
    destruir_paquete_pro(paquete_contexto);

}

t_proceso_desalojado* recibir_retorno_de_proceso_cpu(){
	
	recibir_operacion(fd_cpu_dispatch);

	t_buffer* buffer = recibir_todo_el_buffer_pro(fd_cpu_dispatch);

	t_proceso_desalojado* desalojo = extraer_proceso_desalojado_del_buffer_pro(buffer);

    destruir_buffer_pro(buffer);
    
	return desalojo;
}