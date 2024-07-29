#include "../../include/comunicacion/memoria_interfaz.h"

void atender_memoria_interfaz(void* fd_escucha){
    
	t_buffer* buffer;
    bool conectado = 1;
	int fd_interfaz = *(int*)fd_escucha;

	while (conectado) {
		int cod_op = recibir_operacion(fd_interfaz);
		switch (cod_op) {
		case MENSAJE:
			
			break;
		case PAQUETE:
			break;
		case PEDIDO_DE_LECTURA: 
			usleep(retardo_peticiones * 1000);
			buffer = recibir_todo_el_buffer_pro(fd_interfaz);

    		int direc_fisica = extraer_int_del_buffer_pro(buffer);
   			int cant_bytes_a_leer = extraer_int_del_buffer_pro(buffer);

			void* valor_leido= atender_ped_lectura_io(direc_fisica, cant_bytes_a_leer);


			t_buffer* buffer_l = crear_buffer_pro();
			cargar_stream_al_buffer_pro(buffer_l, valor_leido, cant_bytes_a_leer);

			t_paquete* paquete_l = crear_paquete_pro(PEDIDO_DE_LECTURA, buffer_l); 
			enviar_paquete_pro(paquete_l, fd_interfaz);

			destruir_buffer_pro(buffer);
			destruir_paquete_pro(paquete_l);
			free(valor_leido);
			break;
		case PEDIDO_DE_ESCRITURA: 
			usleep(retardo_peticiones * 1000);
			buffer = recibir_todo_el_buffer_pro(fd_interfaz);
			atender_ped_escritura_io(buffer);

			t_buffer* buffer_e = crear_buffer_pro();
			cargar_int_al_buffer_pro(buffer_e, OK);
			t_paquete* paquete_e = crear_paquete_pro(PEDIDO_DE_ESCRITURA, buffer_e); //ver como lo recibe realmente
			enviar_paquete_pro(paquete_e, fd_interfaz);
			destruir_buffer_pro(buffer);
			destruir_paquete_pro(paquete_e);
			break;

		case -1:
			log_error(logger_memoria, "Desconexion de INTERFACES-IO");
			conectado = 0;
            break;
		default:
			log_warning(logger_memoria,"Operacion desconocida de INTERFACES-IO");
			break;
		}
	}
	liberar_conexion(fd_interfaz);
	
}


void* atender_ped_lectura_io(int direc_fisica, int cant_bytes_a_leer){

    if (direc_fisica < 0 || direc_fisica + cant_bytes_a_leer > tam_memoria) {
        fprintf(stderr, "Error: la dirección física o los bytes a leer están fuera de los límites\n");
        return NULL;
    }

    void* inicio_lectura = (unsigned char*)espacio_usuario + direc_fisica;
    void* valor_leido = malloc(cant_bytes_a_leer);

    if (valor_leido == NULL) {
        fprintf(stderr, "Error al asignar memoria para valor_leido\n");
        return NULL;
    }

    memcpy(valor_leido, inicio_lectura, cant_bytes_a_leer);

    return valor_leido;
}

void atender_ped_escritura_io(t_buffer* unBuffer) {
    int direc_fisica = extraer_int_del_buffer_pro(unBuffer);
    int tam = extraer_int_del_buffer_pro(unBuffer);
	void* valor_a_escribir = extraer_algo_del_buffer_pro(unBuffer);
	
    if (direc_fisica < 0 || direc_fisica + tam > tam_memoria) {
        fprintf(stderr, "Error: la dirección física o el tamaño del dato a escribir están fuera de los límites\n");
        return; //no debería entrar acá
    }

    void* inicio_escritura = (unsigned char*)espacio_usuario + direc_fisica;

    memcpy(inicio_escritura, valor_a_escribir, tam);

    //ver si no rompe esto
    free(valor_a_escribir);
}