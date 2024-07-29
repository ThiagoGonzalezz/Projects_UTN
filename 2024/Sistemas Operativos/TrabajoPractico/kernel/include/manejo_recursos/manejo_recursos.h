#ifndef MANEJO_RECURSOS_H_
#define MANEJO_RECURSOS_H_

#include "../k_gestor.h"

void inicializar_manejo_recursos();

t_registro_recurso* crear_registro_recurso(int instancias, char* nombre);

bool existe_recurso(char* nombre_recurso);

void enviar_a_blocked_recurso(t_pcb* pcb, char* nombre_recurso, t_registro_recurso* registro_recurso);

void atender_wait(t_pcb* pcb, char* nombre_recurso);

void atender_signal(t_pcb* pcb, char* nombre_recurso);

void desbloquear_proximo_proceso(t_registro_recurso* registro_recurso);

void finalizar_proceso_por_recurso_invalido(t_pcb* proceso);

void marcar_recurso_asignado(t_pcb* pcb, char* recurso);

void marcar_recurso_desasignado(t_pcb* pcb, char* recurso);

void liberar_recursos_asignados(t_pcb* pcb);

void liberar_recurso(char* recurso);

void eliminar_proceso_bloqueado_en_recurso(t_pcb* pcb);

#endif