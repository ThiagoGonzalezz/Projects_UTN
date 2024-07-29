#ifndef UTILS_MEMORIA_H_
#define UTILS_MEMORIA_H_

#include "../comunicacion/memoria_cpu.h"
#include "../comunicacion/memoria_interfaz.h"
#include "../comunicacion/memoria_kernel.h"

void levantar_log_y_config(char*);
void terminar_programa_memoria();
void escuchar_conexiones_entrantes();
void inicializar_frames();
void inicializar_memoria();
void finalizar_memoria(); 
void crearProcesoEnMemoria(uint32_t pid, char* path);
u_int32_t calcular_cantPaginasNecesarias(u_int32_t bytes); 
t_pagina* asignaFrames_y_crearTabla(t_list* framesAOcupar);
void almacenar_en_espacioDeUsuario(char* path, t_list* framesAOcupar);
t_list* obtener_instrucciones_del_path(char *path_relativo);
void liberar_parametros_instruccion(void *parametro);
void liberar_instruccion(void *instruccion);
void liberar_instrucciones_en_lista(t_list *instrucciones);
t_proceso_en_memoria* buscarProcesoPorId(uint32_t pid);
void marcarFrameComoOcupado(u_int32_t nro_frame);
void marcarFrameComoLibre(u_int32_t nro_frame);
void finalizarProceso(uint32_t pid);
u_int32_t buscarFrame(u_int32_t nroDePagina, u_int32_t pid);
int obtenerPosicionEnListaDeProcesos(uint32_t pid);
void destruirTablaDePaginas(t_proceso_en_memoria *proceso);
void ampliar_proceso(t_proceso_en_memoria* proceso, uint32_t cant_pags_nueva);
void reducir_proceso(t_proceso_en_memoria* proceso, uint32_t cant_pags_nueva);
int obtener_frame_libre();
void agregar_paginas(t_proceso_en_memoria* proceso, int paginas_a_agregar);
void sacar_paginas(t_proceso_en_memoria* proceso, int paginas_a_sacar);

#endif