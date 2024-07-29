#include "../../include/consola/consola_utils.h"

bool atender_instruccion_consola(char* leido){
  bool validacion_leido = validar_instruccion_consola(leido);
	bool flag_exit = 0;

  if(validacion_leido==0){
		printf("Comando no reconocido. Si desea ver los comandos disponibles ingrese \"HELP\" \n");
	}else{
	  flag_exit = ejecutar_instruccion_consola(leido);
	}

return flag_exit;
}

bool validar_instruccion_consola(char* leido){
	bool resultado_validacion = 0;
  
    if(string_is_empty(leido)){ //para permitir el ingreso vacio por consola 
      resultado_validacion = true;  //NO ANDA EL MUY PUTO
    }

	char** comando_consola = string_split(leido, " ");//divide cadena de texto
    
    if(strcmp(comando_consola[0], "EJECUTAR_SCRIPT") == 0){
      if(contarParametros(comando_consola)==1){
        resultado_validacion = true;
    }
	  }else if(strcmp(comando_consola[0], "INICIAR_PROCESO") == 0){
      if(contarParametros(comando_consola)==1){
        resultado_validacion = true;
    }
  	}else if(strcmp(comando_consola[0], "FINALIZAR_PROCESO") == 0){
      if(contarParametros(comando_consola)==1){
        resultado_validacion = true;
    }
	  }else if(strcmp(comando_consola[0], "DETENER_PLANIFICACION") == 0){
      if(contarParametros(comando_consola)==0){
        resultado_validacion = true;
    }
    }else if(strcmp(comando_consola[0], "INICIAR_PLANIFICACION") == 0){
      if(contarParametros(comando_consola)==0){
        resultado_validacion = true;
    }
    }else if(strcmp(comando_consola[0], "MULTIPROGRAMACION") == 0){
      if(contarParametros(comando_consola)==1){
        resultado_validacion = true;
    }
    }else if(strcmp(comando_consola[0], "PROCESO_ESTADO") == 0){
      if(contarParametros(comando_consola)==0){
        resultado_validacion = true;
    }
    }else if(strcmp(comando_consola[0], "HELP") == 0){
      if(contarParametros(comando_consola)==0){
        resultado_validacion = true;
    }
    }else if(strcmp(comando_consola[0], "EXIT") == 0){
      if(contarParametros(comando_consola)==0){
        resultado_validacion = true;
    }
    }
    
	string_array_destroy(comando_consola);
    
	return resultado_validacion;
}

bool ejecutar_instruccion_consola(char* leido){
  bool flagExit = 0;
	
	char** comando_consola = string_split(leido, " ");//divide cadena de texto

    if(strcmp(comando_consola[0], "EJECUTAR_SCRIPT") == 0){
      char* script = comando_consola[1];
      ejecutar_script(script);

	  }else if(strcmp(comando_consola[0], "INICIAR_PROCESO") == 0){
      //Derivo en un hilo para que no se trabe la consola por la comunicacion con memoria
      iniciar_proceso(comando_consola[1]);

  	}else if(strcmp(comando_consola[0], "FINALIZAR_PROCESO") == 0){
      char* pid_string = comando_consola[1];
      intptr_t pid = atoi(pid_string);
      //Derivo a un hilo para que si se queda bloqueado por temas de sincronizacion, no se bloquee la consola;
      hilo_finalizar_proceso(pid); 

	  }else if(strcmp(comando_consola[0], "DETENER_PLANIFICACION") == 0){
      detener_planificacion();

    }else if(strcmp(comando_consola[0], "INICIAR_PLANIFICACION") == 0){
      iniciar_planificacion();

    }else if(strcmp(comando_consola[0], "MULTIPROGRAMACION") == 0){
      char* nuevo_grado_string = comando_consola[1];
      intptr_t nuevo_grado = atoi(nuevo_grado_string);
      //Derivo a un hilo para que si se queda bloqueado por temas de sincronizacion, no se bloquee la consola;
      hilo_actualizar_grado_multiprogramacion(nuevo_grado);

    }else if(strcmp(comando_consola[0], "PROCESO_ESTADO") == 0){
      listar_procesos_por_estado();

    }else if(strcmp(comando_consola[0], "HELP") == 0){
      help();

    }else if(strcmp(comando_consola[0], "EXIT") == 0){
      flagExit = 1;
      cerrar_consola();
    }
	

	string_array_destroy(comando_consola);

  return flagExit;
}

//Parametros

int contarParametros(char **array){
    int contador = 0;
    while (*array != NULL) {
        contador++;
        array++;
    }

    return contador - 1; //se le resta el comando
}
