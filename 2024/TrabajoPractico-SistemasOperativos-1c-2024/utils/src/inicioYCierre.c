#include "../include/inicioYCierre.h"

// ------------------------------------------------------------------------------------------
// -- Funciones de conexion con sockets entre modulos --
// ------------------------------------------------------------------------------------------

int iniciar_servidor(t_log *logger, const char *name, char *ip, char *puerto)
{
    struct addrinfo hints, *servinfo;

    memset(&hints, 0, sizeof(hints));
    hints.ai_family = AF_UNSPEC;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_flags = AI_PASSIVE;

    getaddrinfo(NULL, puerto, &hints, &servinfo);

    // Creamos el socket de escucha del servidor.
    int socket_servidor =0;
    socket_servidor = socket(servinfo->ai_family, servinfo->ai_socktype, servinfo->ai_protocol);
    if(socket_servidor == -1){
        log_error(logger, "Error al crear el socket de escucha del servidor.\n");
        close(socket_servidor);
        return -1;
    }

    // Asociamos el socket a un puerto.
    bind(socket_servidor, servinfo->ai_addr, servinfo->ai_addrlen);
        
    // Escuchamos las conexiones entrantes
    if(listen(socket_servidor, SOMAXCONN) == -1){
        log_error(logger, "Error a la hora de escuchar las conexiones con LISTEN.");
        close(socket_servidor);
        return -1;
    }

    log_info(logger, "Servidor listo! Esperando en modo escucha...\n");
    freeaddrinfo(servinfo); 
    return socket_servidor;
}

int crear_conexion(char* ip, char* puerto, t_log* logger)
{
    struct addrinfo hints;
    struct addrinfo* server_info;

    memset(&hints, 0, sizeof(hints));
    hints.ai_family = AF_UNSPEC;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_flags = AI_PASSIVE;

    if (getaddrinfo(ip, puerto, &hints, &server_info) != 0) {
        log_error(logger, "Error al resolver la dirección y puerto.");
        return -1;
    }
    
    // Se crea el socket del cliente
    int socket_cliente = socket(server_info->ai_family,
                                server_info->ai_socktype,
                                server_info->ai_protocol);

    if (socket_cliente == -1) {
        log_error(logger, "Error al crear el socket.");
        freeaddrinfo(server_info);
        return -1;
    }
    
    // Conectamos el socket del cliente con el servidor (El servidor tiene que estar iniciado)
    if (connect(socket_cliente, server_info->ai_addr, server_info->ai_addrlen) == -1) {
        log_error(logger, "Error al conectar el socket.");
        close(socket_cliente);
        freeaddrinfo(server_info);
        return -1;
    }

    freeaddrinfo(server_info);
    return socket_cliente;
}

int esperar_cliente(int socket_servidor, t_log* logger){
    int socket_cliente = accept(socket_servidor, NULL, NULL);
    return socket_cliente;
}

void liberar_conexion(int socket_cliente)
{
    close(socket_cliente);
    socket_cliente = -1;
}

void terminar_programa(int conexion, t_log *logger, t_config *config)
{
    
    log_info(logger, "Se cerraran las conexiones, los archivos y finaliza el programa.\n");

    if (logger != NULL)
    {
        log_destroy(logger);
    }
    if (config != NULL)
    {
        config_destroy(config);
    }
    liberar_conexion(conexion);

}

int levantar_conexion_como_cliente(char* ip, char* puerto, t_log* logger){

	int conexion = crear_conexion(ip, puerto, logger);
	return conexion;
}

// HANDSHAKE

void handshake_cliente(uint32_t handshake, int conexion, t_log* logger, char* nombre_servidor){
    send(conexion, &handshake, sizeof(uint32_t), 0);
    uint32_t respuesta;
    recv(conexion, &respuesta, sizeof(uint32_t), 0);
    if(respuesta == 0) {
        log_info(logger, "Handshake exitoso con el servidor %s\n", nombre_servidor);
    } else {
        log_error(logger, "Error en el handshake con el servidor %s\n", nombre_servidor);
    }
}

int32_t handshake_servidor(int socket_cliente, t_log* logger){
    int32_t handshake;
    uint32_t resultOk = 0;
    uint32_t resultError = -1; 

    recv(socket_cliente, &handshake, sizeof(uint32_t), MSG_WAITALL);

    if(handshake == 1 || handshake == 2 || handshake == 3 || handshake == 4 || handshake == 5 || handshake == 6){

        send(socket_cliente, &resultOk, sizeof(uint32_t), 0);
        return handshake;
    }
    else{
        
        send(socket_cliente, &resultError, sizeof(uint32_t), 0);
        log_error(logger, "Error en el handshake con el cliente");
        close(socket_cliente); // Cerrar el socket_cliente si el handshake falla
        return -1;
    }
}