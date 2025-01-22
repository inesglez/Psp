package Actividad4_1;
import org.apache.commons.net.ftp.*;
import java.io.IOException;

public class Remoto {

    public static void main(String[] args) {
        // Dirección del servidor FTP
        String direccionServidor = "ftp.rediris.es"; // Servidor FTP
        int puertoServidor = 21; // Puerto FTP por defecto
        String usuarioFTP = "anonimo"; // Usuario (anónimo)
        String contrasenaFTP = ""; // Contraseña (vacía para usuarios anónimos)

        // Cliente FTP para realizar la conexión
        FTPClient clienteFTP = new FTPClient();

        try {
            // Conexión al servidor FTP
            clienteFTP.connect(direccionServidor, puertoServidor);
            int codigoRespuesta = clienteFTP.getReplyCode();
            if (!clienteFTP.login(usuarioFTP, contrasenaFTP)) {
                System.out.println("Error: No se pudo iniciar sesión en el servidor FTP.");
                return;
            }

            System.out.println("Conexión establecida con el servidor FTP.");

            // Configurar el cliente FTP en modo pasivo y binario
            clienteFTP.enterLocalPassiveMode();
            clienteFTP.setFileType(FTP.BINARY_FILE_TYPE);

            // Listar los archivos y directorios del directorio raíz
            System.out.println("Contenido del directorio raíz:");
            FTPFile[] listaArchivos = clienteFTP.listFiles("/");
            if (listaArchivos != null && listaArchivos.length > 0) {
                for (FTPFile archivo : listaArchivos) {
                    System.out.println(archivo.getName()); // Nombre del archivo o directorio
                }
            } else {
                System.out.println("No se encontraron archivos o directorios.");
            }

            // Cerrar sesión y desconectar del servidor
            clienteFTP.logout();
            clienteFTP.disconnect();
            System.out.println("Desconexión del servidor FTP completada.");
        } catch (IOException excepcion) {
            // Manejo de errores en caso de fallo de conexión o comandos
            System.out.println("Error: " + excepcion.getMessage());
            excepcion.printStackTrace();
        }
    }
}
