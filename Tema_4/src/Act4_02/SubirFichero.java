package Act4_02;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class SubirFichero {
    public static void main(String[] args) {
        FTPClient cliente = new FTPClient();
        String servidor = "localhost";
        String user = "usuario";
        String pasw = "usuario";

        try {
            System.out.println("Conectándose a " + servidor);
            cliente.connect(servidor);
            cliente.enterLocalPassiveMode();
            boolean login = cliente.login(user, pasw);

            cliente.setFileType(FTP.BINARY_FILE_TYPE);
            String direc = "/home/usuario/";

            if (login) {
                System.out.println("Login correcto");

                // Cambiar al directorio raíz del usuario
                if (!cliente.changeWorkingDirectory(direc)) {
                    JOptionPane.showMessageDialog(null, "No se ha podido acceder al directorio raíz.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }

                System.out.println("Directorio actual: " + cliente.printWorkingDirectory());

                // Seleccionar el archivo a subir utilizando JFileChooser
                JFileChooser fileChooser = new JFileChooser();
                int resultado = fileChooser.showOpenDialog(null);
                if (resultado == JFileChooser.APPROVE_OPTION) {
                    String archivoSeleccionado = fileChooser.getSelectedFile().getAbsolutePath();
                    System.out.println("Archivo seleccionado: " + archivoSeleccionado);

                    // Subir el archivo seleccionado
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(archivoSeleccionado));
                    String nombreArchivo = fileChooser.getSelectedFile().getName();

                    boolean subidoCorrectamente = cliente.storeFile(nombreArchivo, in);
                    in.close(); // Cerrar flujo

                    // Mostrar mensaje en consola y cuadro de diálogo según si el archivo fue subido o no
                    if (subidoCorrectamente) {
                        JOptionPane.showMessageDialog(null, "Archivo subido correctamente: " + nombreArchivo, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("Archivo subido correctamente: " + nombreArchivo);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha podido subir el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                        System.out.println("No se ha podido subir el archivo.");
                    }

                    // Mostrar contenido del directorio raíz
                    System.out.println("Contenido del directorio raíz después de la subida:");
                    FTPFile[] archivos = cliente.listFiles();
                    String[] tipos = {"Fichero", "Directorio", "Enlace simb."}; // Tipos de archivos posibles para mostrar
                    for (FTPFile archivo : archivos) {
                        System.out.println("\t" + archivo.getName() + " => " + tipos[archivo.getType()]);
                    }

                    // Cerrar sesión y desconectar
                    cliente.logout();
                    cliente.disconnect();
                    System.out.println("Desconectado del servidor FTP...");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún archivo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar o al subir el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
