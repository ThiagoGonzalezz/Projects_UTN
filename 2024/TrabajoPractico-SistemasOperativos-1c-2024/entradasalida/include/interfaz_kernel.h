#ifndef INTERFAZ_KERNEL_H_
#define INTERFAZ_KERNEL_H_

#include "e_s_gestor.h"

void atender_kernel_gen();
void atender_kernel_stdin();
void atender_kernel_stdout();
void atender_kernel_dialfs();

void enviar_presentacion(t_presentacion_interfaz presentacion);

t_solicitud_ejecucion_io recibir_solicitud_ejecucion();

void enviar_resultado_io(t_resultado_io resultado);

void reconfirmar_conexion_con_kernel();

void crear_archivo(char*);
void eliminar_archivo(char*);
void truncar(char*, int);
int buscar_cantidad_bloques_contiguo(int);
void compactar(void*, t_bitarray*);
void compactar_hacia_derecha(void*, t_bitarray*, int);
void asignar_bloque(int, char*);

extern t_list* lista_nombre_archivos;

void* buscar_archivo_que_comienza_en(int);
void* buscar_archivo_que_finaliza_en(int);
int buscar_arcivo_en_lista(char*);
int buscar_bloques_contiguos_archivo(int,int);
void destroy_entry(void*);
#endif