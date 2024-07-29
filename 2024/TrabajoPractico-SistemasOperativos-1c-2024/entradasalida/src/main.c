#include <../include/main.h>

char* path_base_dialfs;
int block_size;
int block_count;
int retraso_compactacion;

FILE* bloques = NULL;
FILE* archivo_bitmap = NULL;

void* bloques_map = NULL;
t_bitarray* bitarray_memoria = NULL;
void* bitmap_memoria = NULL;

t_presentacion_interfaz presentacion;

t_list* lista_nombre_archivos;

int main(int argc, char *argv[]) {

    if (argc < 3) {
        fprintf(stderr, "Uso: %s <nombre_interfaz> <path>\n", argv[0]);
        return 1;
    }

    printf("El nombre de la interfaz es: %s\n", argv[1]);
    char* path = argv[2];
    printf("El path es: %s\n", path);
    
    levantar_log_y_config(path);

    presentacion.nombre = argv[1];

    t_tipo_interfaz tipo;
    if (strcmp(tipo_interfaz, "GENERICA") == 0) {
        tipo = GENERICA;
    } else if (strcmp(tipo_interfaz, "STDIN") == 0) {
        tipo = STDIN;
    } else if (strcmp(tipo_interfaz, "STDOUT") == 0) {
        tipo = STDOUT;
    } else if (strcmp(tipo_interfaz, "DIALFS") == 0) {
        tipo = DIALFS;
    }

    presentacion.tipo = tipo;

    fd_kernel = levantar_conexion_como_cliente(ip_kernel, puerto_kernel, logger_entradasalida);
    
    handshake_cliente(handshake_io, fd_kernel, logger_entradasalida, "KERNEL");
    
    enviar_presentacion(presentacion);

    lista_nombre_archivos = list_create();

    if(tipo == DIALFS){
        printf("ENTRE A LA VERIFICACION DEL DIRECTORIO \n");

        char pathFs[256];
        snprintf(pathFs, sizeof(pathFs), "dialfs/%s", presentacion.nombre);
        
        DIR* folder = opendir(pathFs);
        if (folder == NULL) {
            DIR* directorioBase = opendir("dialfs");
            int dfd = dirfd(directorioBase);
            mkdirat(dfd, presentacion.nombre, 0777);
            char path_abs[256];
            snprintf(path_abs, sizeof(path_abs), "%s/%s",path_base_dialfs, presentacion.nombre);
            printf("Path al FS absoluto: %s\n", path_base_dialfs);
        } else {
            char path_abs[256];
            snprintf(path_abs, sizeof(path_abs), "%s/%s", path_base_dialfs,presentacion.nombre);
            printf("Path al FS absoluto: %s\n", path_abs);
            struct dirent* directorio;
            while ((directorio = readdir(folder))) {
                if (!esNombreProhibido(directorio->d_name)) {
                    list_add(lista_nombre_archivos, directorio->d_name);
                    printf("%s\n", directorio->d_name);
                }
            }
            closedir(folder);
        }
    }

    printf("PRINTEO LA LISTA DESPUES DEL HANDSHAKE----------------------------\n");
    for(int i=0; i<list_size(lista_nombre_archivos); i++){
        printf("%s\n", list_get(lista_nombre_archivos, i)); //INVALID READ SIZE 1 (en la primer iteracion??)
    }
    printf("TERMINE EL PRINTEO DE LA LSITA ----------------------------\n");

    fd_memoria = levantar_conexion_como_cliente(ip_memoria, puerto_memoria, logger_entradasalida);
    handshake_cliente(handshake_io, fd_memoria, logger_entradasalida, "MEMORIA");
    
    pthread_t hilo_kernel;
    pthread_create(&hilo_kernel, NULL, atender_segun_interfaz, NULL);
   
    pthread_join(hilo_kernel, NULL);
    terminar_programa_entradasalida();

    return 0;
}

void atender_segun_interfaz(){

    printf("ENTRE A ATENDER SEGUN INTERFAZ\n");

    char* tipo_gen = "GENERICA";
    char* tipo_stdin = "STDIN";
    char* tipo_stdout = "STDOUT";
    char* tipo_fs = "DIALFS";

        if(strcmp(tipo_interfaz, tipo_gen) == 0){
            printf("ENTRE AL TIPO GEN\n");
            atender_kernel_gen();
        }
        if(strcmp(tipo_interfaz, tipo_stdin) == 0){
            printf("ENTRE AL TIPO STDIN\n");
            atender_kernel_stdin();
        }
        if(strcmp(tipo_interfaz, tipo_stdout) == 0){
            printf("ENTRE AL TIPO STDOUT\n");
            atender_kernel_stdout();
        }
        if(strcmp(tipo_interfaz, tipo_fs) == 0){
            printf("ENTRE AL TIPO DIALFS\n");

            levantar_archivos_base();

            atender_kernel_dialfs();
        }
        else{
            log_error(logger_entradasalida, "TIPO DE INTERFAZ DESCONOCIDA");
        }
}

void levantar_archivos_base(){

    if (path_base_dialfs == NULL) {
        fprintf(stderr, "Error: path_base_dialfs es NULL\n");
        return;
    }

    size_t base_len = strlen(path_base_dialfs);
    if (base_len + strlen("/bloques.dat") >= 256) {
        fprintf(stderr, "Error: path_base_dialfs es demasiado largo\n");
        return;
    }

    char path_bloques[256];
    int written = snprintf(path_bloques, sizeof(path_bloques), "%s/%s/%s", path_base_dialfs, presentacion.nombre,"bloques.dat");

    if (written < 0 || written >= sizeof(path_bloques)) {
        fprintf(stderr, "Error: el buffer es demasiado pequeño\n");
        return;
    }

    printf("PATH BASE DIALFS: %s\n", path_base_dialfs);
    printf("PATH BLOQUES: %s\n", path_bloques);


    // BLOQUES --------------------------------------------------------------------------

    bloques = fopen(path_bloques, "ab+");
    if(bloques == NULL){
        printf("No se pudo abrir el archivo bloques\n");
    }

    if(ftruncate(fileno(bloques), block_count*block_size) == -1){
        printf("No se pudo establecer el tamanio de bloques \n");
    }

    bloques_map = mmap(NULL, block_count/8, PROT_READ | PROT_WRITE, MAP_SHARED, fileno(bloques), 0);

    // BITMAP -------------------------------------------------------------------------------

    char path_bitmap[256];
    snprintf(path_bitmap, sizeof(path_bitmap), "%s/%s/%s", path_base_dialfs,presentacion.nombre, "bitmap.dat");

    printf("PATH BITMAP: %s\n", path_bitmap);

    archivo_bitmap = fopen(path_bitmap, "ab+");
    if (archivo_bitmap == NULL){
        log_error(logger_entradasalida, "Error al abrir/crear el archivo bitmap de bloques.");
        exit(EXIT_FAILURE);
    }

    int tamanioBitmap = block_count / 8;
    ftruncate(fileno(archivo_bitmap), tamanioBitmap);
    
    bitmap_memoria = mmap(NULL, tamanioBitmap, PROT_READ | PROT_WRITE, MAP_SHARED, fileno(archivo_bitmap), 0);
    if (bitmap_memoria == MAP_FAILED) {
        log_error(logger_entradasalida, "Error al mapear el archivo bitmap.");
        fclose(archivo_bitmap);
        free(bitmap_memoria);
    }

    memset(archivo_bitmap, 0, tamanioBitmap);
    bitarray_memoria = bitarray_create_with_mode(bitmap_memoria, tamanioBitmap, MSB_FIRST);   

    msync(bitmap_memoria, tamanioBitmap, MS_SYNC);

    printf("FIN DE LA CREACION DE LOS ARCHIVOS BASE\n");

    fclose(bloques);
    fclose(archivo_bitmap);
}

bool esNombreProhibido(void* string){
    return strcmp(string,"bloques.dat") == 0 || strcmp(string,"bitmap.dat") == 0 || strcmp(string,".") == 0 || strcmp(string,"..") == 0;
}