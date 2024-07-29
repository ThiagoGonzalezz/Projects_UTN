#include "../../include/consola/consola.h"

void consola(){

	char* leido;
	
	printf("Bienvenido a la consola interactiva del Kernel! \n");
	printf("Si desea conocer los comandos disponibles ingrese \"HELP\". \n");

	leido = readline("> ");
	
	while(strcmp(leido, "EXIT") != 0){
		atender_instruccion_consola(leido);
		
		free(leido);
		leido = readline("> ");
	}

	free(leido);
}