#ifndef MMU_H_
#define MMU_H_

#include <math.h>

#include "c_gestor.h"
#include <commons/collections/list.h>

extern u_int32_t pid_proceso;
extern t_list* tlb;
extern char* algoritmo_tlb;
extern int cantidad_entradas_tlb;


int traducir ( int );
u_int32_t pedir_y_recibir_marco(int, u_int32_t);
u_int32_t recibir_marco();
t_tlb_rta buscar_en_tlb(u_int32_t, int);
bool buscar_entrada_en_tlb(void*);
int find_lru_index(t_list*);

#endif