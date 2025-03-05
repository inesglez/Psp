package Act4_01;

import java.io.*;

import org.apache.commons.net.ftp.*;

public class FTPLocal {

    public static void main(String[] args) {

        // Creamos una instancia del cliente FTP
        FTPClient cliente = new FTPClient();
        String servFTP = "localhost";
        System.out.println("Nos conectamos a: " + servFTP);

        // Datos para acceder al servidor FTP
        String usuario = "usuario";
        String clave = "usuario";

        try {
            cliente.connect(servFTP); // Conectar con el servidor FTP
            cliente.enterLocalPassiveMode(); // Habilitar el modo pasivo, por si servidor está detrás de un firewall

            boolean login = cliente.login(usuario, clave);
            if (login)
                System.out.println("Login correcto...");
            else {
                System.out.println("Login Incorrecto...");
                cliente.disconnect();
                System.exit(1);
            }

            // Mostramos el directorio actual en el servidor FTP
            System.out.println("Directorio actual: " + cliente.printWorkingDirectory());

            // Obtenemos la lista de archivos en el directorio actual
            FTPFile[] files = cliente.listFiles();
            System.out.println("Ficheros en el directorio actual: " + files.length);
            String tipos[] = {"Fichero", "Directorio", "Enlace simb."}; // Array con los tipos de archivos posibles para visualizarlos

            // Recorremos la lista de archivos y mostramos el nombre y tipo
            for (int i = 0; i < files.length; i++) {
                System.out.println("\t" + files[i].getName() + " => " + tipos[files[i].getType()]);
            }

            boolean logout = cliente.logout();
            if (logout)
                System.out.println("Logout del servidor FTP...");
            else
                System.out.println("Error al hacer Logout...");

            // Desconectamos del servidor FTP
            cliente.disconnect();
            System.out.println("Desconectado...");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
