#include "../include/mmu.h"

t_list* tlb = NULL;
int instante_actual = 0;
int a_reemplazar = 0;
t_tlb criterio = {0, 0, 0, 0};

int traducir(int direccion_logica){
    int nro_pagina = floor(direccion_logica / tamanio_pagina);
    int desplazamiento = direccion_logica - nro_pagina * tamanio_pagina;
    u_int32_t marco = 0;

    if(cantidad_entradas_tlb != 0){ // TLB ACTIVADA
        t_tlb_rta rta = buscar_en_tlb(pid_proceso, nro_pagina);

        if(rta.respuesta == TLB_HIT){
            marco = rta.marco;
            log_info(logger_cpu, "PID: %d - TLB HIT - Pagina: %d", pid_proceso, nro_pagina);

        }
        else if(rta.respuesta == TLB_MISS){
            log_info(logger_cpu, "PID: %d - TLB MISS - Pagina: %d", pid_proceso, nro_pagina);

            marco = pedir_y_recibir_marco(nro_pagina, pid_proceso);
            log_info(logger_cpu, "PID: %d - OBTENER MARCO - Pagina: %d - Marco: %d", pid_proceso, nro_pagina, marco);
            t_tlb* nueva_entrada = malloc(sizeof(t_tlb));
            nueva_entrada->pid = pid_proceso;
            nueva_entrada->pagina = nro_pagina;
            nueva_entrada->marco = marco;
            nueva_entrada->entrada = instante_actual;
            if(list_size(tlb) < cantidad_entradas_tlb){
                list_add(tlb,nueva_entrada);
                instante_actual++;
            }else{
                if(strcmp(algoritmo_tlb ,"FIFO")==0){
                    if(a_reemplazar < cantidad_entradas_tlb){
                    void* reemplazado = list_remove(tlb, a_reemplazar);
                    free(reemplazado);
                    list_add_in_index(tlb, a_reemplazar, nueva_entrada);
                    printf("Victima: %d\n", a_reemplazar);
                    a_reemplazar++;
                    instante_actual++;
                    }
                    else{
                        a_reemplazar = 0;
                        void* reemplazado = list_remove(tlb, a_reemplazar);
                        free(reemplazado);
                        list_add_in_index(tlb, a_reemplazar, nueva_entrada);
                        printf("Victima: %d\n", a_reemplazar);
                        a_reemplazar++;
                        instante_actual++;
                    }
                }else if(strcmp(algoritmo_tlb,"LRU")==0){ 
                    a_reemplazar = find_lru_index(tlb);
                    void* reemplazado = list_replace(tlb, a_reemplazar, nueva_entrada);
                    free(reemplazado);
                    printf("Victima: %d\n", a_reemplazar);
                    instante_actual++;
                }else{
                    log_info(logger_cpu, "Algoritmo de reemplazo invalido");
                }
            }
        }else{
            log_info(logger_cpu, "Respuesta desconocida de la TLB");
        }
    }else{
        marco = pedir_y_recibir_marco(nro_pagina, pid_proceso);
        log_info(logger_cpu, "PID: %d - OBTENER MARCO - Pagina: %d - Marco: %d", pid_proceso, nro_pagina, marco);
    }
    int direccion_fisica = marco * tamanio_pagina + desplazamiento;
    return direccion_fisica;
}

u_int32_t pedir_y_recibir_marco(int nro_pagina, u_int32_t pid_proc){
    t_buffer* buffer_a_enviar = crear_buffer_pro();
    cargar_uint32_al_buffer_pro(buffer_a_enviar, pid_proc);
    cargar_uint32_al_buffer_pro(buffer_a_enviar, (u_int32_t)nro_pagina);
    
    t_paquete* paquete_a_enviar = crear_paquete_pro(MARCO, buffer_a_enviar);
    enviar_paquete_pro(paquete_a_enviar, fd_memoria);
    destruir_paquete_pro(paquete_a_enviar);
    u_int32_t marco = recibir_marco();
    return marco;
}

u_int32_t recibir_marco(){
    t_buffer * buffer;
    int cod_op = recibir_operacion(fd_memoria);
    printf("OP CODE RECIBIR MARCO: %d \n", cod_op);
	buffer = recibir_todo_el_buffer_pro(fd_memoria);
    u_int32_t marco = extraer_uint32_del_buffer_pro(buffer);
    destruir_buffer_pro(buffer);
    return marco;
}

t_tlb_rta buscar_en_tlb(u_int32_t pid, int nro_pag){

    criterio.pid = pid;
    criterio.pagina = nro_pag;
    t_tlb* entrada_tlb = (t_tlb*) list_find(tlb, buscar_entrada_en_tlb);

    if(entrada_tlb == NULL){
        t_tlb_rta respuesta = {TLB_MISS, 0};
        return respuesta;
    }
    else{
        t_tlb_rta respuesta = {TLB_HIT, entrada_tlb->marco};
        entrada_tlb->entrada = instante_actual;
        return respuesta;
    }
}

bool buscar_entrada_en_tlb(void* data) {
    t_tlb* entrada = (t_tlb*)data;

    return entrada->pid == criterio.pid && entrada->pagina == criterio.pagina;
}

// Función para encontrar el índice del elemento menos recientemente utilizado
int find_lru_index(t_list* tlb) {
    int lru_index = 0;
    int lru_instante = ((t_tlb*)list_get(tlb, 0))->entrada;
    for (int i = 1; i < list_size(tlb); i++) {
        t_tlb* entry = (t_tlb*)list_get(tlb, i);
        if (entry->entrada < lru_instante) {
            lru_index = i;
            lru_instante = entry->entrada;
        }
    }
    return lru_index;
}