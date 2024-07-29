#ifndef UTILS_CPU_H
#define UTILS_CPU_H

#include <stdlib.h>

#include "c_gestor.h"
#include "cpu_kernel_interrupt.h"
#include "mmu.h"

extern t_registros registros_cpu;
extern char* algoritmo_tlb;
extern int cantidad_entradas_tlb;

void levantar_log_y_config(char* path);
void terminar_programa_cpu();
void imprimir_registros_por_pantalla(t_registros);
void set(t_instruccion*);
void *get_registro(char*);
void asignarValorARegistro(char*, char*);
void sum(t_instruccion*);
int get_valor_registro(char*);
void sub(t_instruccion*);
t_buffer* fetch(t_proceso_a_ejecutar*);
t_buffer* recibir_instruccion();
t_instruccion* decode(t_buffer*);
t_instruccion* execute(t_instruccion*);
void check_interrupt(t_instruccion*);
void jnz(t_instruccion*);
void enviar_contexto(t_motivo_desalojo, t_instruccion*);
void mov_in(t_instruccion*);
void mov_out(t_instruccion*);
void resize(t_instruccion*);
void copy_string(t_instruccion*);
void stdin_read(t_instruccion*);
void stdout_write(t_instruccion*);
int cantidad_paginas_a_leer(int ,int);
void loggear_exec(t_instruccion*, int, char*);
bool lista_interrupciones_esta_vacia();
t_interrupcion* proxima_interrupcion_protegido();
void io_fs_create(t_instruccion*);
void io_fs_delete(t_instruccion*);
void io_fs_truncate(t_instruccion*);
void io_fs_write(t_instruccion*);
void io_fs_read(t_instruccion*);
void destruir_instruccion(t_instruccion* , int );

#endif