#include "../../include/consola/consola_comandos_utils.h"

void mostrar_proceso(t_pcb* pcb){
  printf("║ PID: %-4d                       ║\n", pcb->pid);
}

void mostrar_procesos_del_estado(t_list* estado){
    list_iterate(estado, (void *)mostrar_proceso);
}

void mostrar_proceso_running(){
  if(proceso_running != NULL){
    printf("║ PID: %-4d                       ║\n", proceso_running->pid);
}
}

void mostrar_procesos_bloqueados(){
  pthread_mutex_lock(&mutex_lista_procesos_en_sistema);
  t_list* lista_bloqueados = list_filter(lista_procesos_en_sistema, (void *)esta_bloqueado);
  pthread_mutex_unlock(&mutex_lista_procesos_en_sistema);
  list_iterate(lista_bloqueados, (void *)mostrar_proceso);
  list_destroy(lista_bloqueados);
}

bool esta_bloqueado(t_pcb* pcb){
  return (pcb->estado == BLOCKED_IO || pcb->estado == BLOCKED_RECURSO);
}