package vianditasONG.modelos.servicios.seguridad;

import vianditasONG.modelos.servicios.seguridad.config.Config;
import vianditasONG.modelos.servicios.seguridad.encriptadores.Encriptador;
import lombok.Setter;

import java.io.*;


public class RegistroUsuarios {
    @Setter
    private ValidadorCreacionPassword validadorCreacionPassword;
    @Setter
    private Encriptador encriptador;

    private String filePath= Config.getInstancia().obtenerDelConfig("rutaArchivoContraseniasComunes");

    public void registrarUsuario(String nombreUsuario, String password) {

        if (this.usuarioExistente(nombreUsuario)) {
            System.out.println("El nombre de usuario ya esta en uso");
        } else {
            if (validadorCreacionPassword.validarCreacionContrasenia(password).esValido()) {
                this.almacenarUsuario(nombreUsuario, password);
            }
            else {
                System.out.println("Contraseña debil.");
            }

        }
    }

    public void almacenarUsuario(String nombreUsuario, String password) {
        try (FileWriter fw = new FileWriter(filePath, true);
             PrintWriter pw = new PrintWriter(fw)) {
            // Escribe el usuario y la contraseña en el archivo
            pw.println(nombreUsuario + ", " + encriptador.encriptar(password));
            System.out.println("Usuario y contraseña escritos en el archivo correctamente.");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }

    }

    public boolean usuarioExistente(String nombreUsuario) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = buffer.readLine()) != null) {
                String[] partes = linea.split(", ");
                if (partes.length == 2 && partes[0].trim().equals(nombreUsuario)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
