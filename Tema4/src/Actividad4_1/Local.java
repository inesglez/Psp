package Actividad4_1;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

public class Local {
    public static void main(String[] args) {
        // Dirección del servidor FTP
        String direccionServidor = "localhost"; // Servidor FTP
        int puertoServidor = 21; // Puerto FTP por defecto
        String usuarioFTP = "usuario"; // Usuario
        String contrasenaFTP = "usuario"; // Contraseña

        // Cliente FTP para realizar la conexión
        FTPClient clienteFTP = new FTPClient();

        try {
            // Conexión al servidor FTP
            clienteFTP.connect(direccionServidor, puertoServidor);
            if (!clienteFTP.login(usuarioFTP, contrasenaFTP)) {
                System.out.println("Error: No se pudo iniciar sesión en el servidor FTP.");
                return;
            }

            System.out.println("Conexión establecida con el servidor FTP.");

            // Listar los archivos y directorios en el directorio especificado
            System.out.println("Contenido del directorio:");
            var listaArchivos = clienteFTP.listFiles("/home/usuario"); // Ruta del directorio a listar
            for (var archivo : listaArchivos) {
                System.out.println(archivo.getName()); // Nombre del archivo o directorio
            }

            // Cerrar la sesión del cliente FTP
            clienteFTP.logout();
            System.out.println("Sesión finalizada correctamente.");
        } catch (IOException excepcion) {
            // Manejo de errores en caso de fallo de conexión o comandos
            System.out.println("Error: " + excepcion.getMessage());
            excepcion.printStackTrace();
        } finally {
            try {
                // Verificar si la conexión sigue activa y cerrarla
                if (clienteFTP.isConnected()) {
                    clienteFTP.disconnect();
                    System.out.println("Conexión al servidor FTP cerrada.");
                }
            } catch (IOException excepcion) {
                // Manejo de errores al cerrar la conexión
                excepcion.printStackTrace();
            }
        }
    }
}