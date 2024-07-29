#ifndef PROTOCOLO_H_
#define PROTOCOLO_H_

#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 

#include <sys/socket.h>
#include "utils.h"



// ------------------------------------------------------------------------------------------
// -- Recibir --
// ------------------------------------------------------------------------------------------

//GENERICAS

int recibir_operacion(int socket_cliente);

t_buffer* recibir_todo_el_buffer_pro(int conexion);

void* extraer_algo_del_buffer_pro(t_buffer* buffer);

int extraer_int_del_buffer_pro(t_buffer* buffer);

char* extraer_string_del_buffer_pro(t_buffer* buffer);

uint32_t extraer_uint32_del_buffer_pro(t_buffer* buffer);

uint8_t extraer_uint8_del_buffer_pro(t_buffer* buffer);

t_instruccion* extraer_instruccion_del_buffer_pro(t_buffer* buffer);

void destruir_buffer_pro(t_buffer* buffer);


//KERNEL_CPU

t_proceso_a_ejecutar* extraer_proceso_a_ejecutar_del_buffer_pro(t_buffer* buffer);

t_registros extraer_registros_del_buffer_pro(t_buffer* buffer);

t_proceso_desalojado* extraer_proceso_desalojado_del_buffer_pro(t_buffer* buffer);

t_interrupcion* extraer_interrupcion_del_buffer_pro(t_buffer* buffer);

//KERNEL_IO

t_presentacion_interfaz extraer_presentacion_interfaz_del_buffer_pro(t_buffer* buffer);

t_solicitud_ejecucion_io extraer_solicitud_ejecucion_io_del_buffer_pro(t_buffer* buffer);

t_resultado_io extraer_resultado_io_del_buffer_pro(t_buffer* buffer);

// ------------------------------------------------------------------------------------------
// -- Enviar --
// ------------------------------------------------------------------------------------------

//GENERICAS

void cargar_stream_al_buffer_pro(t_buffer* buffer, void* algo, int tamAlgo);

void cargar_uint32_al_buffer_pro(t_buffer* buffer, uint32_t intACargar);

void cargar_uint8_al_buffer_pro(t_buffer* buffer, uint8_t intACargar);

void cargar_int_al_buffer_pro(t_buffer* buffer, int un_valor_int);

void cargar_string_al_buffer_pro(t_buffer* buffer, char* un_valor_string);

void cargar_instruccion_al_buffer_pro(t_buffer* buffer, t_instruccion* instruccion);

t_buffer* crear_buffer_pro();

t_paquete* crear_paquete_pro(op_code cod_op, t_buffer* un_buffer);

void destruir_paquete_pro(t_paquete* paquete);

void* serializar_paquete_pro(t_paquete* paquete);

void enviar_paquete_pro(t_paquete* paquete, int conexion);


//KERNEL-CPU

void cargar_proceso_a_ejecutar_al_buffer_pro(t_buffer* buffer, t_proceso_a_ejecutar* a_ejecutar);

void cargar_registro_al_buffer_pro(t_buffer* buffer, t_registros registro);

void cargar_proceso_desalojado_al_buffer_pro(t_buffer* buffer, t_proceso_desalojado* proceso);

void cargar_interrupcion_al_buffer_pro(t_buffer* buffer, t_interrupcion interrupcion);

//KERNEL_IO

void cargar_presentacion_interfaz_al_buffer_pro(t_buffer* buffer, t_presentacion_interfaz presentacion);

void cargar_solicitud_ejecucion_io_al_buffer_pro(t_buffer* buffer, t_solicitud_ejecucion_io solicitud);

void cargar_resultado_io_al_buffer_pro(t_buffer* buffer, t_resultado_io resultado);

#endif