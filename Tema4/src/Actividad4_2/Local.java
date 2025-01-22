package Actividad4_2;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Local {

    public static void main(String[] args) {
        // Configuración del servidor FTP
        String servidorFTP = "localhost"; // Dirección del servidor (localhost en este caso)
        int puertoFTP = 21; // Puerto predeterminado para FTP
        String usuarioFTP = "usuario"; // Usuario autorizado
        String claveFTP = "usuario"; // Contraseña del usuario
        String carpetaRemota = "/home/usuario"; // Carpeta en el servidor para subir archivos

        // Selector de archivo
        JFileChooser selectorArchivo = new JFileChooser();
        selectorArchivo.setDialogTitle("Selecciona un archivo para cargar en el servidor FTP");
        int seleccion = selectorArchivo.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = selectorArchivo.getSelectedFile();

            // Crear cliente FTP
            FTPClient clienteFTP = new FTPClient();
            try {
                clienteFTP.connect(servidorFTP, puertoFTP); // Conexión al servidor
                boolean inicioSesion = clienteFTP.login(usuarioFTP, claveFTP); // Inicio de sesión

                if (inicioSesion) {
                    clienteFTP.enterLocalPassiveMode(); // Activar modo pasivo
                    clienteFTP.setFileType(FTP.BINARY_FILE_TYPE); // Configurar tipo de archivo binario

                    // Cambiar a la carpeta remota
                    if (!clienteFTP.changeWorkingDirectory(carpetaRemota)) {
                        // Crear carpeta si no existe
                        if (clienteFTP.makeDirectory(carpetaRemota)) {
                            clienteFTP.changeWorkingDirectory(carpetaRemota);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo acceder ni crear la carpeta en el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Subir el archivo seleccionado
                    String nombreArchivoRemoto = archivoSeleccionado.getName(); // Nombre del archivo en el servidor
                    try (FileInputStream flujoEntrada = new FileInputStream(archivoSeleccionado)) {
                        boolean exitoSubida = clienteFTP.storeFile(nombreArchivoRemoto, flujoEntrada); // Subir archivo
                        if (exitoSubida) {
                            JOptionPane.showMessageDialog(null, "Archivo " + nombreArchivoRemoto + " subido correctamente.");

                            // Mostrar contenido de la carpeta en el servidor
                            System.out.println("Archivos en la carpeta actual del servidor:");
                            for (String nombre : clienteFTP.listNames()) {
                                System.out.println(nombre);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo subir el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo iniciar sesión en el servidor FTP.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                clienteFTP.logout(); // Cerrar sesión
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } finally {
                try {
                    if (clienteFTP.isConnected()) {
                        clienteFTP.disconnect(); // Desconectar al finalizar
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No seleccionaste ningún archivo.");
        }
    }
}
