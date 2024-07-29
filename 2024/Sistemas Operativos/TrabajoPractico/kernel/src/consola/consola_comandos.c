#include "../../include/consola/consola_comandos.h"

void help(){
printf("╔═════════════════════════════════╗ \n");
printf("║  LISTA DE COMANDOS DISPONIBLES  ║ \n");
printf("╠═════════════════════════════════╣ \n");
printf("║-EJECUTAR_SCRIPT [PATH]          ║ \n");
printf("╠═════════════════════════════════╣ \n");
printf("║-INICIAR_PROCESO [PATH]          ║ \n");
printf("╠═════════════════════════════════╣ \n");
printf("║-FINALIZAR_PROCESO [PID]         ║ \n");
printf("╠═════════════════════════════════╣ \n");
printf("║-DETENER_PLANIFICACION           ║ \n");
printf("╠═════════════════════════════════╣ \n");
printf("║-INICIAR_PLANIFICACION           ║ \n");
printf("╠═════════════════════════════════╣ \n");
printf("║-MULTIPROGRAMACION [VALOR]       ║ \n");
printf("╠═════════════════════════════════╣ \n");
printf("║-PROCESO_ESTADO                  ║ \n");
printf("╠═════════════════════════════════╣ \n");
printf("║-HELP                            ║ \n");
printf("╠═════════════════════════════════╣ \n");
printf("║-EXIT                            ║ \n");
printf("╚═════════════════════════════════╝ \n");
}



void ejecutar_script(char* path){
    FILE* archivo_instrucciones = fopen(path, "rt"); 
    char linea[150]; 
    bool flagExit = 0;

    if (archivo_instrucciones == NULL) {
        log_error(logger_secundario_kernel, "No fue posible abrir el script"); 
        return;
    }

    // Ejecutar línea por línea hasta llegar al final del script
    while (fgets(linea, sizeof(linea), archivo_instrucciones) != NULL && !flagExit) {

        char *pos;
        if ((pos=strchr(linea, '\n')) != NULL)
            *pos = '\0'; //eliminar el carácter de nueva línea al final de la cadena linea, si está presente

        if (!string_is_empty(linea)) {
            flagExit = atender_instruccion_consola(linea);
            printf("Comando leido: %s \n", linea);
        }
    }
  
    printf("Se finalizo la ejecucion del script. \n");
    fclose(archivo_instrucciones);
}



void cerrar_consola(){
  printf("Apagando consola... \n");
}

//Hacer mas facherito
void listar_procesos_por_estado(){
printf("╔═════════════════════════════════╗ \n");
printf("║ LISTADO DE PROCESOS POR ESTADO  ║ \n");
printf("╠═════════════════════════════════╣ \n");
printf("║     PROCESOS EN ESTADO NEW      ║ \n");
printf("╠═════════════════════════════════╣ \n");
mostrar_procesos_del_estado(cola_new);
printf("╠═════════════════════════════════╣ \n");
printf("║    PROCESOS EN ESTADO READY     ║\n");
printf("╠═════════════════════════════════╣ \n");
mostrar_procesos_del_estado(cola_ready);
printf("╠═════════════════════════════════╣ \n");
printf("║  PROCESOS EN ESTADO READY PLUS  ║\n");
printf("╠═════════════════════════════════╣ \n");
mostrar_procesos_del_estado(cola_ready_plus);
printf("╠═════════════════════════════════╣ \n");
printf("║    PROCESOS EN ESTADO BLOCKED   ║\n");
printf("╠═════════════════════════════════╣ \n");
mostrar_procesos_bloqueados();
printf("╠═════════════════════════════════╣ \n");
printf("║    PROCESO EN ESTADO RUNNING    ║\n");
printf("╠═════════════════════════════════╣ \n");
mostrar_proceso_running();
printf("╠═════════════════════════════════╣ \n");
printf("║     PROCESOS EN ESTADO EXIT     ║\n");
mostrar_procesos_del_estado(lista_exit);
printf("╚═════════════════════════════════╝ \n");
}


void hilo_actualizar_grado_multiprogramacion(intptr_t nuevo_grado){
    pthread_t hilo_actualizar_multiprogramacion;
	pthread_create(&hilo_actualizar_multiprogramacion, NULL, (void*)actualizar_grado_multiprogramacion, (void*)nuevo_grado);
    pthread_detach(hilo_actualizar_multiprogramacion);

}


void iniciar_proceso(char* path){
    crear_proceso(path);
}


void hilo_finalizar_proceso(intptr_t pid){
    pthread_t finalizar_proceso_hilo;
	pthread_create(&finalizar_proceso_hilo, NULL, (void*)eliminar_proceso, (void*)pid);
    pthread_detach(finalizar_proceso_hilo);
}