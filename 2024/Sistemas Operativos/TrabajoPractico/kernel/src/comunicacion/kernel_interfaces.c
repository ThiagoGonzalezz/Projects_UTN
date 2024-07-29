#include "../../include/comunicacion/kernel_interfaces.h"

void atender_interfaces_entrantes(){

    while(1){

    int fd_interfaz = esperar_cliente(fd_server_interfaces, logger_secundario_kernel); 
        
        handshake_servidor(fd_interfaz, logger_secundario_kernel);

        recibir_operacion(fd_interfaz);
        
        t_presentacion_interfaz presentacion = recibir_presentacion(fd_interfaz);

        t_registro_interfaz* registro = crear_registro_interfaz(presentacion.nombre, presentacion.tipo, fd_interfaz);

        free(presentacion.nombre);
        
        encolar_protegido(lista_interfaces_en_sistema, registro, &mutex_lista_interfaces);
        
        crear_hilo_gestionador_de_interfaz(registro);
    }
}


t_presentacion_interfaz recibir_presentacion(int fd_interfaz){

	t_buffer* buffer = recibir_todo_el_buffer_pro(fd_interfaz);

	t_presentacion_interfaz presentacion = extraer_presentacion_interfaz_del_buffer_pro(buffer);

    destruir_buffer_pro(buffer);
    
	return presentacion;
}


void enviar_solicitud_ejecutar_instruccion(int fd_interfaz, t_instruccion* instruccion_io, uint32_t pid){
    t_solicitud_ejecucion_io solicitud_ejecucion;
    solicitud_ejecucion.instruccion = instruccion_io;
    solicitud_ejecucion.pid = pid;

	//Armo el buffer
    t_buffer* buffer = crear_buffer_pro();
	cargar_solicitud_ejecucion_io_al_buffer_pro(buffer, solicitud_ejecucion);
    
	//Lo serializo
    t_paquete* paquete = crear_paquete_pro(SOLICITUD_IO, buffer);
     
	//Lo envio
    enviar_paquete_pro(paquete, fd_interfaz);
    
    //Lo libero
    destruir_paquete_pro(paquete);
}


t_resultado_io recibir_resultado_io(int fd_interfaz){
    
    recibir_operacion(fd_interfaz);

	t_buffer* buffer = recibir_todo_el_buffer_pro(fd_interfaz);

	t_resultado_io resultado = extraer_resultado_io_del_buffer_pro(buffer);

    destruir_buffer_pro(buffer);
    
	return resultado;
}