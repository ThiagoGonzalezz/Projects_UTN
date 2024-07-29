#include <../include/utils.h>


void loggear_protegido(char* mensaje, t_log* logger, pthread_mutex_t* mutex){
	pthread_mutex_lock(mutex);
    log_info(logger, "%s", mensaje);
	pthread_mutex_unlock(mutex);
}


t_list* obtener_primeros_elementos(t_list* lista, int n) { //no encontre una funcion del utils que lo haga
    t_list* primeros_elementos = list_create();
    if (primeros_elementos == NULL) {
        fprintf(stderr, "Error al crear la lista para los primeros elementos\n");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < n; i++) {
        void* elemento = list_get(lista, i);
        list_add(primeros_elementos, elemento);
    }

    return primeros_elementos;
}


void liberar_parametros_instruccion(void *parametro) { //creo que no es necesaria esta igual
    char *param = (char *)parametro;
    free(param);
}

void liberar_instruccion(void *instruccion) {
    t_instruccion *inst = (t_instruccion *)instruccion;
    char **parametros = inst->parametros;
    for (int i = 0; parametros[i] != NULL; i++) {
        free(parametros[i]);
    }
    free(parametros);
    free(inst);
}

void liberar_instrucciones_en_lista(t_list *instrucciones) {
    list_destroy_and_destroy_elements(instrucciones, liberar_instruccion);
}



