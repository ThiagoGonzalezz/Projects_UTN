#include "../../include/comunicacion/kernel_cpu_interrupt.h"

void enviar_interrupcion(uint32_t pid_a_interrumpir, t_tipo_interrupcion tipo){
    
    pthread_mutex_lock(&mutex_interrupciones);

    t_interrupcion interrupcion = {pid_a_interrumpir, tipo};

	//Armo el buffer
    t_buffer* unBuffer = crear_buffer_pro();
	cargar_interrupcion_al_buffer_pro(unBuffer, interrupcion);

    
	//Lo serializo
    t_paquete* paquete_interrupcion = crear_paquete_pro(INTERRUPCION, unBuffer);
     
	//Lo envio
    enviar_paquete_pro(paquete_interrupcion, fd_cpu_interrupt);
    

    //Lo libero
    destruir_paquete_pro(paquete_interrupcion);

    pthread_mutex_unlock(&mutex_interrupciones);

}


