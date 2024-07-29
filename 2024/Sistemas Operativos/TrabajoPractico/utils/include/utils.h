#ifndef UTILS_H
#define UTILS_H

#include <stdio.h> // Funciones de entrada y salida
#include <stdlib.h> // Funciones de manejo de memoria
#include <string.h> // Funciones de manejo de cadenas

#include <commons/string.h>
#include <commons/collections/list.h>
#include <commons/log.h>

#include <sys/socket.h>
#include <semaphore.h>

#include <pthread.h>

#include "inicioYCierre.h"

struct t_registro_interfaz;
struct t_registro_recurso;

typedef enum{
    MENSAJE,
    PAQUETE,
	CONTEXTO_EJECUCION,
	INSTRUCCION,
	CREAR_PROCESO,
	MARCO,
	PEDIDO_DE_LECTURA,
	INTERRUPCION,
	IO_GEN_SLEEP,
	SET,
    SUM,
    SUB,
    MOV_IN,
    MOV_OUT,
    RESIZE,
    JNZ,
	FIN,
	FINALIZAR_PROCESO,
	PRESENTACION,
	SOLICITUD_IO,
	RESULTADO_IO,
	PEDIDO_DE_ESCRITURA,
	RESULTADO_RESIZE,
	TAM_PAG,
	WAIT,
	SIGNAL,
	COPY_STRING,
	IO_STDIN_READ,
	IO_STDOUT_WRITE,
	IO_FS_CREATE,
	IO_FS_DELETE,
	IO_FS_TRUNCATE,
	IO_FS_WRITE,
	IO_FS_READ
}op_code;

typedef struct {
    int size; 
    void* stream; // Payload
} t_buffer;

typedef struct
{
	op_code codigo_operacion;
	t_buffer* buffer;
}t_paquete;

typedef struct
{
	uint32_t PC;
    uint8_t  AX, BX, CX, DX;
 	uint32_t EAX, EBX, ECX, EDX, SI, DI;
}t_registros;

typedef struct{
	op_code identificador; // esto no me parece que este del todo bien, yo considero que podriamos cambiar esto por otro enum donde solo esten las instrucciones
	char **parametros;
}t_instruccion;

typedef enum {
    NEW,
    READY,
    RUNNING,
    BLOCKED_IO,
	BLOCKED_RECURSO,
	EXIT,
	READY_PLUS
}t_estados;

typedef enum {
    SUCCESS,
    ERROR,
    CLOCK,
	SYSCALL,
	CONSOLA_KERNEL,
	OUT_OF_MEMORY,
	INVALID_IO,
	INVALID_RESOURCE,
	INVALID_OPERATION_IO,
	SYSCALL_IO
}t_motivo_desalojo;

typedef enum{
	INTERRUPT_CONSOLA,
	INTERRUPT_CLOCK
}t_tipo_interrupcion;

typedef struct{
	uint32_t pid_proceso_interrumpido;
	t_tipo_interrupcion tipo;
}t_interrupcion;

typedef enum{
	TLB_HIT,
	TLB_MISS
}t_respuesta_tlb;

typedef struct{
	uint32_t pid;
	uint32_t quantum;
	t_estados estado;
	t_registros registros;
	struct t_registro_interfaz* interfaz_bloqueado;
	struct t_registro_recurso* recurso_bloqueado;
	t_list* recursos_asignados;
}t_pcb;

typedef struct{
 	bool disponible;
}t_frame;

typedef struct{
	u_int32_t nroFrame; //Frame de la memoria en el que está almacenada la página
	u_int32_t nroPagina;
}t_pagina;

typedef struct{
	u_int32_t pid;
	u_int32_t cant_pags;
	t_pagina *tabla_paginas; //Cada proceso tendrá su propia tabla de páginas
	t_list* instrucciones; //Sería como cargar en memoria el segmento de código del proceso
}t_proceso_en_memoria;

typedef struct{
	t_motivo_desalojo motivo;
	t_registros contexto;
	t_instruccion syscall;
}t_proceso_desalojado;

typedef struct{
	uint32_t pid;
	t_registros contexto;
}t_proceso_a_ejecutar;

typedef enum{
	GENERICA,
	STDIN,
	STDOUT,
	DIALFS
} t_tipo_interfaz;

typedef struct{
     int fd;
     char* nombre;
     t_tipo_interfaz tipo;
     t_list *cola_bloqueados;
     sem_t semaforo_disponibilidad;
	 pthread_mutex_t mutex_cola;
	 t_pcb* proceso_atendiendo;
	 bool flag_eliminar_proceso; 
} t_registro_interfaz;

typedef struct{
     t_list *cola_bloqueados;
	 pthread_mutex_t mutex_cola;
	 int instancias_disponibles;
	 char* nombre;
} t_registro_recurso;

typedef struct{
	char* nombre;
	t_tipo_interfaz tipo;
} t_presentacion_interfaz;

typedef struct{
	t_pcb* pcb;
	t_instruccion instruccion_io;
}t_proceso_pendiente_io;

typedef enum{
	OK,
}t_resultado_io;

typedef struct{
	uint32_t pid;
	t_instruccion* instruccion;
}t_solicitud_ejecucion_io;

typedef struct{
	void (*planificar_por_algoritmo)(void);
	void (*devolver_a_cpu_por_algoritmo)(t_pcb*);
	void (*retornar_a_ready_correspondiente_por_algoritmo)(t_pcb*);
}t_algoritmo_planificador_corto_plazo;

typedef struct{
	u_int32_t pid;
	int pagina;
	u_int32_t marco;
	int entrada;
}t_tlb;

typedef struct{
	int respuesta;
	u_int32_t marco;
}t_tlb_rta;

typedef struct{
  int flag_interrupcion_realizada;
  uint32_t pid_actual;
}t_control_clock;


//////
void loggear_protegido(char* mensaje, t_log* logger, pthread_mutex_t* mutex);

/////
t_list* obtener_primeros_elementos(t_list* lista, int n);

////

void liberar_parametros_instruccion(void *parametro);

void liberar_instruccion(void *instruccion);

void liberar_instrucciones_en_lista(t_list *instrucciones);

#endif