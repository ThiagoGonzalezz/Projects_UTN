#ifndef MANEJO_INTERFACES_H_
#define MANEJO_INTERFACES_H_

#include "../k_gestor.h"
#include "../planificacion/planificacion.h"

t_registro_interfaz* crear_registro_interfaz(char* nombre, t_tipo_interfaz tipo_interfaz, int fd);

void gestionar_interfaz(t_registro_interfaz* registro_interfaz);

void crear_hilo_gestionador_de_interfaz(t_registro_interfaz* registro_interfaz);

bool tiene_el_nombre_buscado(void* registro_interfaz);

t_registro_interfaz* interfaz_por_nombre(char* nombre);

void destruir_registro_interfaz(t_registro_interfaz* registro);

void finalizar_proceso_pendiente(void* proceso_con_instruccion);

void finalizar_procesos_pendientes(t_registro_interfaz* registro_interfaz);

bool io_puede_ejecutar(t_tipo_interfaz tipo, op_code instruccion_id);

bool interfaz_sigue_conectada(int fd_interfaz);

t_motivo_desalojo obtener_motivo_finalizacion_segun_resultado_io(t_resultado_io resultado);

void registrar_desconexion_interfaz(t_registro_interfaz* registro_interfaz);

void eliminar_proceso_en_io(t_pcb* pcb);

bool es_el_proceso_en_io_buscado(t_proceso_pendiente_io* proceso_con_instruccion);

void enviar_a_blockedo_io(t_pcb* pcb, t_registro_interfaz* interfaz,  t_instruccion instruccion_io);

void atender_syscall_io(t_pcb* pcb, t_instruccion instruccion_io);

#endif