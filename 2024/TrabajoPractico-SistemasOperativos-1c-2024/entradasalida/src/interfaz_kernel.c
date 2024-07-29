#include "../include/interfaz_kernel.h"

uint32_t pid_actual = 0;

void atender_kernel_gen(){
    bool conectado = 1;
	while (conectado){

		reconfirmar_conexion_con_kernel();

		int cod_op = recibir_operacion(fd_kernel);

		switch (cod_op){
			case SOLICITUD_IO:
			t_buffer* buffer = recibir_todo_el_buffer_pro(fd_kernel);
            t_solicitud_ejecucion_io solicitud_ejecucion = extraer_solicitud_ejecucion_io_del_buffer_pro(buffer);
			char* mensaje = string_from_format("PID: %d - Operacion: IO_GEN_SLEEP %d", solicitud_ejecucion.pid, atoi(solicitud_ejecucion.instruccion->parametros[1]));
			log_info(logger_entradasalida, "%s" ,mensaje);
			usleep(1000*atoi(solicitud_ejecucion.instruccion->parametros[1])*tiempo_unidad_trabajo);
			destruir_buffer_pro(buffer);
			enviar_resultado_io(OK);
			free(mensaje);
			int i = 0;
			while(solicitud_ejecucion.instruccion->parametros[i]!=NULL){
				free(solicitud_ejecucion.instruccion->parametros[i]);
				i++;
			}
			free(solicitud_ejecucion.instruccion->parametros);
			free(solicitud_ejecucion.instruccion);
			break;
		case -1:
			log_error(logger_sec_io, "Desconexion de KERNEL");
			conectado = 0;
            break;
		default:
			log_warning(logger_sec_io,"Operacion desconocida de KERNEL");
			conectado = 0;
		}
	}
}

void atender_kernel_stdin(){

	bool conectado = 1;

	while (conectado){

		reconfirmar_conexion_con_kernel();
		
		int cod_op = recibir_operacion(fd_kernel);

		switch (cod_op){
		case SOLICITUD_IO: {
			
			t_buffer* buffer = recibir_todo_el_buffer_pro(fd_kernel);
            t_solicitud_ejecucion_io solicitud_ejecucion = extraer_solicitud_ejecucion_io_del_buffer_pro(buffer);

			char* mensaje = string_from_format("PID: %d - Operacion: IO_STDIN_READ", solicitud_ejecucion.pid);
			log_info(logger_entradasalida, "%s", mensaje);
			free(mensaje);

			int cant_pags_a_escribir = atoi(solicitud_ejecucion.instruccion->parametros[1]);
			int tamanio_total = atoi(solicitud_ejecucion.instruccion->parametros[2]);
			void* valor_ingresado = malloc(tamanio_total+sizeof(char));

			char* leido_cons;
			printf("Ingrese el texto a guardar en Memoria: \n");
			leido_cons = readline("> ");
			snprintf(valor_ingresado, tamanio_total + sizeof(char), "%s\n", leido_cons);
			
			int bytes_escritos = 0;
			int direccion_fisica;
			int tamanio_a_escribir;

			int k = 0;
			for(int i = 0; i < cant_pags_a_escribir; i++){
				direccion_fisica = atoi(solicitud_ejecucion.instruccion->parametros[k+3]);
				tamanio_a_escribir = atoi(solicitud_ejecucion.instruccion->parametros[k+4]);

				void* valor_para_memoria = malloc(tamanio_a_escribir+1);
            	memcpy(valor_para_memoria, valor_ingresado + bytes_escritos, tamanio_a_escribir);

				t_buffer* buffer_a_enviar = crear_buffer_pro();
				cargar_int_al_buffer_pro(buffer_a_enviar,direccion_fisica);
				cargar_int_al_buffer_pro(buffer_a_enviar,tamanio_a_escribir + 1);
				cargar_stream_al_buffer_pro(buffer_a_enviar, valor_para_memoria, tamanio_a_escribir);

				t_paquete* paquete_a_enviar = crear_paquete_pro(PEDIDO_DE_ESCRITURA, buffer_a_enviar);
            	enviar_paquete_pro(paquete_a_enviar, fd_memoria);
            	destruir_paquete_pro(paquete_a_enviar);

				int rta = recibir_operacion(fd_memoria);
				t_buffer* buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
				int rta_final = extraer_int_del_buffer_pro(buffer_recibido);
				if(rta_final == OK){
					printf("STDIN OK\n");
				}else{
					printf("STDIN NO OK\n");
				}
				destruir_buffer_pro(buffer_recibido);
				bytes_escritos += tamanio_a_escribir;
				free(valor_para_memoria);
				k +=2;
			}
			destruir_buffer_pro(buffer);
			free(valor_ingresado);
			enviar_resultado_io(OK);
			int i = 0;
			while(solicitud_ejecucion.instruccion->parametros[i]!=NULL){
				free(solicitud_ejecucion.instruccion->parametros[i]);
				i++;
			}
			free(solicitud_ejecucion.instruccion->parametros);
			free(solicitud_ejecucion.instruccion);
			free(leido_cons);
			break;
		}
		case -1:
			log_error(logger_sec_io, "Desconexion de KERNEL");
			conectado = 0;
            break;
		default:
			log_warning(logger_sec_io,"Operacion desconocida de KERNEL");
			conectado = 0;
		}
	}

}

void atender_kernel_stdout(){
	bool conectado = 1;

	while (conectado){

		reconfirmar_conexion_con_kernel();

		int cod_op = recibir_operacion(fd_kernel);

		switch (cod_op){
		case SOLICITUD_IO:
			t_buffer* buffer = recibir_todo_el_buffer_pro(fd_kernel);
            t_solicitud_ejecucion_io solicitud_ejecucion = extraer_solicitud_ejecucion_io_del_buffer_pro(buffer);

			char* mensaje = string_from_format("PID: %d - Operacion: IO_STDOUT_WRITE", solicitud_ejecucion.pid);
			log_info(logger_entradasalida, "%s", mensaje);
			free(mensaje);

			int cant_pags_a_leer = atoi(solicitud_ejecucion.instruccion->parametros[1]);
			int tamanio_total = atoi(solicitud_ejecucion.instruccion->parametros[2]);
			int direccion_fisica;
			int tamanio_a_leer;
			int bytes_leidos = 0;
			void* dato_reconstruido = malloc(tamanio_total + sizeof(char));
			t_buffer* buffer_recibido;

			int k = 0;
			for(int i = 0; i < cant_pags_a_leer; i++){
				direccion_fisica = atoi(solicitud_ejecucion.instruccion->parametros[k+3]);
				tamanio_a_leer = atoi(solicitud_ejecucion.instruccion->parametros[k+4]);

				t_buffer* buffer_a_enviar = crear_buffer_pro();
				cargar_int_al_buffer_pro(buffer_a_enviar,direccion_fisica);
				cargar_int_al_buffer_pro(buffer_a_enviar,tamanio_a_leer);

				t_paquete* paquete_a_enviar = crear_paquete_pro(PEDIDO_DE_LECTURA, buffer_a_enviar);
            	enviar_paquete_pro(paquete_a_enviar, fd_memoria);
            	destruir_paquete_pro(paquete_a_enviar);

				int lec_op = recibir_operacion(fd_memoria);
            	buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
            	void* resultado = extraer_algo_del_buffer_pro(buffer_recibido);
				memcpy(dato_reconstruido + bytes_leidos, resultado, tamanio_a_leer);
				free(resultado);
            	bytes_leidos += tamanio_a_leer;
				k += 2;
				destruir_buffer_pro(buffer_recibido);
			}

			char* leido = malloc(tamanio_total + 2*sizeof(char));
        	snprintf(leido,tamanio_total + 2*sizeof(char),"%s\n",(char*) dato_reconstruido);
			leido[tamanio_total] = '\0';
			printf("Datos recibidos: ");
			printf("%s\n", leido);
			destruir_buffer_pro(buffer);
			enviar_resultado_io(OK);
			int i = 0;
			while(solicitud_ejecucion.instruccion->parametros[i]!=NULL){
				free(solicitud_ejecucion.instruccion->parametros[i]);
				i++;
			}
			free(solicitud_ejecucion.instruccion->parametros);
			free(solicitud_ejecucion.instruccion);
			free(dato_reconstruido);
			free(leido);
			break;
		case -1:
			log_error(logger_sec_io, "Desconexion de KERNEL");
			conectado = 0;
            break;
		default:
			log_warning(logger_sec_io,"Operacion desconocida de KERNEL");
			conectado = 0;
		}
	}
}

void atender_kernel_dialfs(){
	bool conectado = 1;

	while (conectado){

        reconfirmar_conexion_con_kernel();

		int cod_op = recibir_operacion(fd_kernel);

		switch (cod_op){
		case SOLICITUD_IO:
			
			usleep(1000*tiempo_unidad_trabajo*1);
			t_buffer* buffer = recibir_todo_el_buffer_pro(fd_kernel);
            t_solicitud_ejecucion_io solicitud_ejecucion = extraer_solicitud_ejecucion_io_del_buffer_pro(buffer);
			pid_actual = solicitud_ejecucion.pid;

			switch(solicitud_ejecucion.instruccion->identificador){
			case IO_FS_CREATE:
				crear_archivo(solicitud_ejecucion.instruccion->parametros[1]);
				break;
			case IO_FS_DELETE:
				eliminar_archivo(solicitud_ejecucion.instruccion->parametros[1]);
				break;
			case IO_FS_TRUNCATE:
				truncar(solicitud_ejecucion.instruccion->parametros[1], atoi(solicitud_ejecucion.instruccion->parametros[2]));
				break;
			case IO_FS_WRITE: {// LEER MEM Y ESCRIBIR ARCHIVO || RECIBO: INT - ARCH - PTRO ARCH - CANT PAGS - TAM TOTAL - DF - TAM ...

				char* mensaje = string_from_format("PID: %d - Escribir Archivo: %s - Tamanio a leer: %i - Puntero Archivo: %i", solicitud_ejecucion.pid, solicitud_ejecucion.instruccion->parametros[1], atoi(solicitud_ejecucion.instruccion->parametros[4]), atoi(solicitud_ejecucion.instruccion->parametros[2]));
				log_info(logger_entradasalida, "%s", mensaje);
				free(mensaje);

				int cant_pags_a_leer = atoi(solicitud_ejecucion.instruccion->parametros[3]);
				int ptro = atoi(solicitud_ejecucion.instruccion->parametros[2]);

				int direccion_fisica;
				int tamanio_a_leer;
				int bytes_leidos = 0;
				int k = 0;

				char direccion_cfg[256];
    			snprintf(direccion_cfg, sizeof(direccion_cfg), "%s/%s/%s", path_base_dialfs, presentacion.nombre,solicitud_ejecucion.instruccion->parametros[1]);
				t_config* metadata = config_create(direccion_cfg);

				char* primer_bloque_char = config_get_string_value(metadata, "BLOQUE_INICIAL");
				int primer_bloque = atoi(primer_bloque_char);

				int byte_inicial = primer_bloque * block_size;

				for(int i = 0; i < cant_pags_a_leer; i++){
					direccion_fisica = atoi(solicitud_ejecucion.instruccion->parametros[k+5]);
					tamanio_a_leer = atoi(solicitud_ejecucion.instruccion->parametros[k+6]);

					t_buffer* buffer_a_enviar = crear_buffer_pro();
					cargar_int_al_buffer_pro(buffer_a_enviar,direccion_fisica);
					cargar_int_al_buffer_pro(buffer_a_enviar,tamanio_a_leer);

					t_paquete* paquete_a_enviar = crear_paquete_pro(PEDIDO_DE_LECTURA, buffer_a_enviar);
					enviar_paquete_pro(paquete_a_enviar, fd_memoria);
					destruir_paquete_pro(paquete_a_enviar);

					int lec_op = recibir_operacion(fd_memoria);
					t_buffer* buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
					void* resultado = extraer_algo_del_buffer_pro(buffer_recibido);

					memcpy(bloques_map + byte_inicial + ptro + bytes_leidos, resultado, tamanio_a_leer);
					
					msync(bloques_map, block_count*block_size, MS_SYNC);
					destruir_buffer_pro(buffer_recibido);
					k += 2;
					bytes_leidos += tamanio_a_leer;
					free(resultado);
				}
				config_destroy(metadata);
				break;
			}
			case IO_FS_READ: {// LEER ARCHIVO  Y ESCRIBIR MEMORIA || RECIBO: INT - ARCH - PTRO ARCH - CANT PAGS - TAM TOTAL - DF - TAM ...
				
				printf("TERMINE EL PRINTEO DE LA LSITA ----------------------------\n");
				char* mensaje = string_from_format("PID: %d - Leer Archivo: %s - Tamanio a leer: %i - Puntero Archivo: %i", solicitud_ejecucion.pid, solicitud_ejecucion.instruccion->parametros[1], atoi(solicitud_ejecucion.instruccion->parametros[4]), atoi(solicitud_ejecucion.instruccion->parametros[2]));
				log_info(logger_entradasalida, "%s", mensaje);

				int cant_pags_a_escribir = atoi(solicitud_ejecucion.instruccion->parametros[3]);
				int ptro = atoi(solicitud_ejecucion.instruccion->parametros[2]);
				
				int bytes_escritos = 0;
				int direccion_fisica;
				int tamanio_a_escribir;
				int k = 0;

				char direccion_cfg[256];
    			snprintf(direccion_cfg, sizeof(direccion_cfg), "%s/%s/%s", path_base_dialfs, presentacion.nombre,solicitud_ejecucion.instruccion->parametros[1]);
				t_config* metadata = config_create(direccion_cfg);

				char* primer_bloque_char = config_get_string_value(metadata, "BLOQUE_INICIAL");
				int primer_bloque = atoi(primer_bloque_char);

				int byte_inicial = primer_bloque * block_size;

				for(int i = 0; i < cant_pags_a_escribir; i++){
					direccion_fisica = atoi(solicitud_ejecucion.instruccion->parametros[k+5]);
					tamanio_a_escribir = atoi(solicitud_ejecucion.instruccion->parametros[k+6]);

					printf("DF: %i | TAM: %i\n", direccion_fisica, tamanio_a_escribir);

					void* valor_para_memoria = malloc(tamanio_a_escribir);
					memcpy(valor_para_memoria, bloques_map + byte_inicial + ptro + bytes_escritos, tamanio_a_escribir);

					t_buffer* buffer_a_enviar = crear_buffer_pro();
					cargar_int_al_buffer_pro(buffer_a_enviar,direccion_fisica);
					cargar_int_al_buffer_pro(buffer_a_enviar,tamanio_a_escribir + 1);
					cargar_stream_al_buffer_pro(buffer_a_enviar, valor_para_memoria, tamanio_a_escribir);

					t_paquete* paquete_a_enviar = crear_paquete_pro(PEDIDO_DE_ESCRITURA, buffer_a_enviar);
					enviar_paquete_pro(paquete_a_enviar, fd_memoria);
					destruir_paquete_pro(paquete_a_enviar);

					int rta = recibir_operacion(fd_memoria);
					t_buffer* buffer_recibido = recibir_todo_el_buffer_pro(fd_memoria);
					int rta_final = extraer_int_del_buffer_pro(buffer_recibido);
					if(rta_final == OK){
						printf("FS_READ OK\n");
					}else{
						printf("FS_READ NO OK\n");
					}
					destruir_buffer_pro(buffer_recibido);
					bytes_escritos += tamanio_a_escribir;
					free(valor_para_memoria);
					k +=2;
				}
				config_destroy(metadata);
				free(mensaje);
				break;
			}
			default:
				log_warning(logger_entradasalida,"Operacion desconocida de KERNEL");
				conectado = 0;
				break;
			}
		destruir_buffer_pro(buffer);
		int i = 0;
		while(solicitud_ejecucion.instruccion->parametros[i]!=NULL){
			free(solicitud_ejecucion.instruccion->parametros[i]);
			i++;
		}
		free(solicitud_ejecucion.instruccion->parametros);
		free(solicitud_ejecucion.instruccion);
		}
		enviar_resultado_io(OK);
	}
}

// FUNCIONES FILE SYSTEM-------------------------------------------------------------------------------------

void crear_archivo(char* nombre){

	char* nombre_real;
	nombre_real = string_substring(nombre,0,strlen(nombre)-1);
	char* mensaje = string_from_format("PID: %d - Crear Archivo: %s", pid_actual, nombre_real);
	log_info(logger_entradasalida, "%s", mensaje);
	free(mensaje);
// BITMAP-----------------------------------------------------------------------------------------------------

	int primer_bloque = buscar_cantidad_bloques_contiguo(1); //DEVUELVE EL NUMERO DEL PRIMER BLOQUE ENCONTRADO or -1
	
	if(primer_bloque == -1){
			printf("No hay mas espacio en el FS, no hay bloques libres\n");
	}else{
		printf("Creo el archivo en el bloque: %d\n",  primer_bloque);
		bitarray_set_bit(bitarray_memoria, primer_bloque);
		int tamanioBitmap = block_count / 8;

		printf("Sincronizi bitmap\n");
		if (msync(bitmap_memoria, tamanioBitmap, MS_SYNC) == -1) {
		printf("Error al sincronizar el archivo bitmap.\n");
		}
		
// METADATA-----------------------------------------------------------------------------------------------------------

		t_config* archivo;
		char direccion[256];
    	snprintf(direccion, sizeof(direccion), "%s/%s/%s", path_base_dialfs,presentacion.nombre, nombre_real);

		printf("Voy a crear el archivo!\n");
		FILE* archivoNuevo = fopen(direccion, "ab+");
		if(archivoNuevo == NULL){
			printf("ArchivoNuevo creado es NULL\n");
		}
		printf("Creo la config\n");
		archivo = config_create(direccion);

		char* primer_bloque_char = string_itoa(primer_bloque);
		config_set_value(archivo, "TAMANIO_ARCHIVO", "0");
		config_set_value(archivo, "BLOQUE_INICIAL", primer_bloque_char);
		config_save(archivo);

		printf("Termine de setear las configs del arch\n");
		config_destroy(archivo);

		free(primer_bloque_char);
		fclose(archivoNuevo);
		printf("Archivo creado: %s\n", nombre_real);
		list_add(lista_nombre_archivos,nombre_real);
	}
}

void eliminar_archivo(char* nombre){

	char* nombre_real;
	nombre_real = string_substring(nombre,0,strlen(nombre)-1);

	char* mensaje = string_from_format("PID: %d - Eliminar Archivo: %s", pid_actual, nombre_real);
	log_info(logger_entradasalida, "%s", mensaje);

	char direccion[256];
    snprintf(direccion, sizeof(direccion), "%s/%s/%s", path_base_dialfs, presentacion.nombre,nombre_real);

	printf("Abro las CFG del archivo\n");
	t_config* metadata = config_create(direccion);

	printf("Obtengo los valores de la CFG \n");
	char* primer_bloque_char = config_get_string_value(metadata, "BLOQUE_INICIAL");
	char* tamanio_char = config_get_string_value(metadata, "TAMANIO_ARCHIVO");

	int primer_bloque = atoi(primer_bloque_char);
	int tamanio = atoi(tamanio_char);

	int cantidad_bloques = ceil(tamanio/block_size);

	if(cantidad_bloques == 0){
		cantidad_bloques = 1;
	}

	printf("Voy a limpiar el bitarray\n");
	for(int i=0; i<cantidad_bloques; i++){
		bitarray_clean_bit(bitarray_memoria, primer_bloque + i);
	}
	int tamanioBitmap = block_count / 8;
	if (msync(bitmap_memoria, tamanioBitmap, MS_SYNC) == -1) {
		printf("Error al sincronizar el archivo bitmap.\n");
	}

	int index = buscar_arcivo_en_lista(nombre_real);
	if(index != -1){
		list_remove(lista_nombre_archivos,index);
	}

	free(mensaje);
	free(nombre_real);
	remove(direccion);
	config_destroy(metadata);

}

void truncar(char* nombre, int tam){
	
	char* mensaje = string_from_format("PID: %d - Truncar Archivo: %s - Tamanio: %i", pid_actual, nombre, tam);
	log_info(logger_entradasalida, "%s", mensaje);
	free(mensaje);

	int cantidad_bloques = (int)ceil(tam/(double)block_size);
	char direccion_cfg[256];
    snprintf(direccion_cfg, sizeof(direccion_cfg), "%s/%s/%s", path_base_dialfs,presentacion.nombre, nombre);
	t_config* metadata_archivo = config_create(direccion_cfg);
	t_config* metadata_archivo_actualizado;
	int tamanio_arch = config_get_int_value(metadata_archivo, "TAMANIO_ARCHIVO");
	int bloque_inicial = config_get_int_value(metadata_archivo, "BLOQUE_INICIAL");

	int cantidad_bloques_actual = (int)ceil(tamanio_arch/(double)block_size);
	if(cantidad_bloques_actual == 0){
		cantidad_bloques_actual = 1;
	}
	if(cantidad_bloques == cantidad_bloques_actual){ 

		char* tamanio_nuevo_char = string_itoa(tam);
		config_set_value(metadata_archivo, "TAMANIO_ARCHIVO", tamanio_nuevo_char);
		config_save(metadata_archivo);
		config_destroy(metadata_archivo);
		free(tamanio_nuevo_char);
	}else if(cantidad_bloques < cantidad_bloques_actual){

		int cantidad_a_sacar = cantidad_bloques_actual - cantidad_bloques;
		int bloque_final = bloque_inicial + cantidad_bloques_actual - 1;

		for(int i=0; i<cantidad_a_sacar; i++){
			bitarray_clean_bit(bitarray_memoria, bloque_final - i);
		}
		char* tamanio_nuevo_char = string_itoa(tam);
		config_set_value(metadata_archivo, "TAMANIO_ARCHIVO", tamanio_nuevo_char);
		config_save(metadata_archivo);
		config_destroy(metadata_archivo);
	}else{// SI TENGO QUE AGRANDAR EL ARCHIVO

		int indice_inicial = buscar_bloques_contiguos_archivo(cantidad_bloques, bloque_inicial + cantidad_bloques_actual - 1);
		printf("INDICE INICIAl: %i\n", indice_inicial);
		if(indice_inicial == -1){ // CUANDO DEVUELVE -1 NO HAY ESPACIO CONTIGUO

			int cant_total_contiguos = buscar_cantidad_bloques_contiguo(cantidad_bloques);

			if(cant_total_contiguos !=-1){ // PERO SI HAY ESPACIO

				log_info(logger_entradasalida, "PID: %i - Inicio Compactacion", pid_actual);
				usleep(1000*retraso_compactacion);

				compactar(bloques_map, bitarray_memoria);

				int cant_actual = (int)ceil(tamanio_arch/(double)block_size);
				if(cant_actual == 0){
					cant_actual = 1;
				}

				config_destroy(metadata_archivo);
				metadata_archivo_actualizado = config_create(direccion_cfg);

				int nuevo_inicial = config_get_int_value(metadata_archivo_actualizado, "BLOQUE_INICIAL");

				int bloque_final = nuevo_inicial + cant_actual - 1;

				compactar_hacia_derecha(bloques_map, bitarray_memoria, bloque_final);

				for(int i=1; i<=cantidad_bloques-cantidad_bloques_actual; i++){
				bitarray_set_bit(bitarray_memoria, nuevo_inicial + i);			
				}
				char* tamanio_nuevo_char = string_itoa(tam);	
				config_set_value(metadata_archivo_actualizado, "TAMANIO_ARCHIVO", tamanio_nuevo_char);
				
				compactar(bloques_map, bitarray_memoria);

				config_save(metadata_archivo_actualizado);
				config_destroy(metadata_archivo_actualizado);
				free(tamanio_nuevo_char);

				log_info(logger_entradasalida, "PID: %i - Fin Compactacion", pid_actual);

			}else{//NO HAY ESPACIO
				printf("No hay ese espacio\n");
				exit(-1);
			}
		}else if(indice_inicial == 0){//HAY ESPACIO CONTIGUO
			indice_inicial = bloque_inicial + cantidad_bloques_actual;
			for(int i=0; i<cantidad_bloques-cantidad_bloques_actual; i++){
			bitarray_set_bit(bitarray_memoria, indice_inicial + i);			
			}
			char* tamanio_nuevo_char = string_itoa(tam);	
			config_set_value(metadata_archivo, "TAMANIO_ARCHIVO", tamanio_nuevo_char);
			free(tamanio_nuevo_char);
			msync(bitmap_memoria,block_count/8,MS_SYNC);
			config_save(metadata_archivo);
			config_destroy(metadata_archivo);
		}
	}
}

// FUNCIONES AUXILIARES----------------------------------------------------------------------------------------------------------

int buscar_cantidad_bloques_contiguo(int cantidad){
	int cant_contiguos = 0;
	int cantidad_total_libres = 0;
	for(int i = 0; i < bitarray_get_max_bit(bitarray_memoria);i++){
		if(bitarray_test_bit(bitarray_memoria,i) == 0){
			cant_contiguos++;
			cantidad_total_libres++;
			if(cant_contiguos == cantidad){
				return i - cant_contiguos + 1;
			}
		}else{
			cant_contiguos = 0;
		}
	}
	if(cantidad_total_libres >= cantidad){
		return -2;
	}
	else{
		return -1;
	}
}

void compactar(void* bloques_map, t_bitarray* bitarray){

    int cant_bloques_libres_contiguos = 0;
    for (int i = 0; i < bitarray_get_max_bit(bitarray); i++){
        if(bitarray_test_bit(bitarray,i) == 0){
            cant_bloques_libres_contiguos++;
        }else if(cant_bloques_libres_contiguos > 0){
			char path[256];
			snprintf(path,sizeof(path),"%s/%s/%s", path_base_dialfs,presentacion.nombre,buscar_archivo_que_comienza_en(i));

			t_config* archivo_metadata = config_create(path);
			int b_ini_nuevo = i-cant_bloques_libres_contiguos;
			char* b_ini_nuevo_str = string_itoa(b_ini_nuevo);
			if (b_ini_nuevo_str == NULL) {
				printf("Error: No se pudo convertir b_ini_nuevo a string\n");
				exit(-1);
			}

			config_set_value(archivo_metadata, "BLOQUE_INICIAL", b_ini_nuevo_str);
			free(b_ini_nuevo_str);

			int tamani_archivo = config_get_int_value(archivo_metadata,"TAMANIO_ARCHIVO");
			int cantidad_bloques_ac = (int)ceil(tamani_archivo/(double)block_size);
			if(cantidad_bloques_ac == 0){
				cantidad_bloques_ac = 1;
			}

			config_save(archivo_metadata);
			config_destroy(archivo_metadata);

			memcpy(bloques_map + (b_ini_nuevo)*block_size, bloques_map + i*block_size, block_size*cantidad_bloques_ac); //SOURCE AND DESTINATION OVERLAP
			for(int z=0; z<cantidad_bloques_ac; z++){
                bitarray_set_bit(bitarray,b_ini_nuevo + z);
                bitarray_clean_bit(bitarray,i);
				i++;
            }
			i = b_ini_nuevo + cantidad_bloques_ac -1;
			int tamanio_bit_map = block_count/8;
			msync(bloques_map, (block_count*block_size)/8, MS_SYNC);
			msync(bitmap_memoria,tamanio_bit_map,MS_SYNC);
			cant_bloques_libres_contiguos = 0;
        }
    }
}

void compactar_hacia_derecha(void* bloques_map, t_bitarray* bitarray, int bloque_final){
	
    int cant_bloques_libres_contiguos = 0;
    for (int i = bitarray_get_max_bit(bitarray)-1; i > bloque_final; i--){
        if(bitarray_test_bit(bitarray,i) == 0){
            cant_bloques_libres_contiguos++;
        }else if(cant_bloques_libres_contiguos > 0){

			char path[256];
			snprintf(path,sizeof(path),"%s/%s/%s", path_base_dialfs,presentacion.nombre,buscar_archivo_que_finaliza_en(i));
			t_config* archivo_metadata = config_create(path);

			int tamani_archivo = config_get_int_value(archivo_metadata,"TAMANIO_ARCHIVO");
			int cantidad_bloques_actual = (int)ceil(tamani_archivo/(double)block_size);
			if(cantidad_bloques_actual == 0){
				cantidad_bloques_actual = 1;
			}

			int b_ini_nuevo = i + 1 + cant_bloques_libres_contiguos - cantidad_bloques_actual;
			char* b_ini_nuevo_str = string_itoa(b_ini_nuevo);
			if (b_ini_nuevo_str == NULL) {
				printf("Error: No se pudo convertir b_ini_nuevo a string\n");
				exit(-1);
			}

			config_set_value(archivo_metadata, "BLOQUE_INICIAL", b_ini_nuevo_str);
			free(b_ini_nuevo_str);

			config_save(archivo_metadata);
			config_destroy(archivo_metadata);

			memcpy(bloques_map + (b_ini_nuevo)*block_size, bloques_map + (i-cantidad_bloques_actual+1)*block_size, block_size*cantidad_bloques_actual); //Memecpy de todo el archivo de una
			for(int z=0; z<cantidad_bloques_actual; z++){
                bitarray_clean_bit(bitarray,i);
				i--;
            }
			for(int z=0; z<cantidad_bloques_actual; z++){
                bitarray_set_bit(bitarray,b_ini_nuevo + z);
            }
			i = b_ini_nuevo;
			msync(bloques_map, (block_count*block_size)/8, MS_SYNC);
			msync(bitmap_memoria,block_count/8,MS_SYNC);
			cant_bloques_libres_contiguos = 0;
        }
    }
}

void enviar_presentacion(t_presentacion_interfaz presentacion){

    t_buffer* buffer = crear_buffer_pro();
	cargar_presentacion_interfaz_al_buffer_pro(buffer, presentacion);
    
    t_paquete* paquete = crear_paquete_pro(PRESENTACION, buffer);
     
    enviar_paquete_pro(paquete, fd_kernel);
    
    destruir_paquete_pro(paquete);

}

t_solicitud_ejecucion_io recibir_solicitud_ejecucion(){
    
	t_buffer* buffer = recibir_todo_el_buffer_pro(fd_kernel);

	t_solicitud_ejecucion_io solicitud = extraer_solicitud_ejecucion_io_del_buffer_pro(buffer);

    destruir_buffer_pro(buffer);
    
	return solicitud;
}

void enviar_resultado_io(t_resultado_io resultado){

    t_buffer* buffer = crear_buffer_pro();
	cargar_resultado_io_al_buffer_pro(buffer, resultado);
    
    t_paquete* paquete = crear_paquete_pro(RESULTADO_IO, buffer);
     
    enviar_paquete_pro(paquete, fd_kernel);
    
    destruir_paquete_pro(paquete);

}

void reconfirmar_conexion_con_kernel(){
	    uint8_t validacion_conexion = 0;
		recv(fd_kernel, &validacion_conexion, sizeof(validacion_conexion), 0);
		recv(fd_kernel, &validacion_conexion, sizeof(validacion_conexion), 0);
}

void* buscar_archivo_que_comienza_en(int bloque_inicial){

	for (int i = 0; i < lista_nombre_archivos->elements_count; i++){
		char path[256];
		snprintf(path,sizeof(path),"%s/%s/%s", path_base_dialfs,presentacion.nombre,list_get(lista_nombre_archivos, i)); // INVALID READ SIZE 1
		t_config* archivo =  config_create(path);
		int bloque_archivo_meta = config_get_int_value(archivo,"BLOQUE_INICIAL");
		if(bloque_inicial == bloque_archivo_meta){
			config_destroy(archivo);
			return list_get(lista_nombre_archivos, i);
		}
		config_destroy(archivo);
	}
}

void* buscar_archivo_que_finaliza_en(int bloque_final){

	for (int i = 0; i < lista_nombre_archivos->elements_count; i++){
		char path[256];
		snprintf(path,sizeof(path),"%s/%s/%s", path_base_dialfs,presentacion.nombre,list_get(lista_nombre_archivos, i));
		t_config* archivo =  config_create(path);
		int bloque_archivo_meta = config_get_int_value(archivo,"BLOQUE_INICIAL");
		int tam_arch_meta = config_get_int_value(archivo,"TAMANIO_ARCHIVO");
		int cantidad_bloques_actual_arch = (int)ceil(tam_arch_meta/(double)block_size);
		if(cantidad_bloques_actual_arch == 0){
			cantidad_bloques_actual_arch = 1;
		}
		int bloque_final_meta = bloque_archivo_meta + cantidad_bloques_actual_arch - 1;
		if(bloque_final == bloque_final_meta){
			config_destroy(archivo);
			return list_get(lista_nombre_archivos, i);
		}
		config_destroy(archivo);
	}
}

int buscar_bloques_contiguos_archivo(int cantidad,int bloque_final){
	for(int i = 1; i <= cantidad; i++){
		if(bitarray_test_bit(bitarray_memoria,bloque_final+i)==1){
			return -1;
		}
	}
	return 0;
}

int buscar_arcivo_en_lista(char* nombre) {
    for (int i = 0; i < list_size(lista_nombre_archivos); i++) {
		if(strcmp(nombre, list_get(lista_nombre_archivos,i)) == 0){//INVALID READ SIZE 1 EN STRCMP
			return i;
		}
    }
    return -1;
}

void destroy_entry(void* data){
	free(data);
}