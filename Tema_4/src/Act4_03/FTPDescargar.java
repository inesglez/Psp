package Act4_03;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FTPDescargar {

    private static FTPClient clienteFTP = new FTPClient();

    public static void main(String[] args) throws IOException {

        // Crear la ventana principal
        JFrame ventanaPrincipal = new JFrame("DESCARGAR FICHEROS");
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setSize(600, 400);
        ventanaPrincipal.setLayout(new BorderLayout());

        // Panel para ingresar credenciales de usuario
        JPanel panelCredenciales = new JPanel();
        panelCredenciales.setLayout(new GridLayout(3, 2));

        JLabel etiquetaUsuario = new JLabel("Usuario:");
        JTextField campoUsuario = new JTextField("");  // Campo de texto para usuario
        JLabel etiquetaContraseña = new JLabel("Contraseña:");
        JPasswordField campoContraseña = new JPasswordField("");  // Campo de texto para contraseña
        JButton botonConectar = new JButton("Conectar");

        panelCredenciales.add(etiquetaUsuario);
        panelCredenciales.add(campoUsuario);
        panelCredenciales.add(etiquetaContraseña);
        panelCredenciales.add(campoContraseña);
        panelCredenciales.add(new JLabel()); // Espacio vacío
        panelCredenciales.add(botonConectar);

        ventanaPrincipal.add(panelCredenciales, BorderLayout.NORTH);

        // Lista de archivos y botones
        DefaultListModel<String> modeloArchivos = new DefaultListModel<>();
        JList<String> listaArchivos = new JList<>(modeloArchivos);
        JScrollPane panelDesplazamiento = new JScrollPane(listaArchivos);

        JButton botonDescargar = new JButton("Descargar");
        JButton botonSalir = new JButton("Salir");

        JPanel panelAcciones = new JPanel();
        panelAcciones.add(botonDescargar);
        panelAcciones.add(botonSalir);

        ventanaPrincipal.add(panelDesplazamiento, BorderLayout.CENTER);
        ventanaPrincipal.add(panelAcciones, BorderLayout.SOUTH);

        // Acción para conectarse al servidor FTP
        botonConectar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuario.getText();
                String contraseña = new String(campoContraseña.getPassword());
                try {
                    clienteFTP.connect("localhost", 21); // Conectar al servidor FTP en el puerto 21
                    boolean conexionExitosa = clienteFTP.login(usuario, contraseña);
                    clienteFTP.changeWorkingDirectory("/home/usuario"); // Cambiar al directorio de trabajo en el servidor

                    if (conexionExitosa) {
                        JOptionPane.showMessageDialog(ventanaPrincipal, "Conexión exitosa.");
                        clienteFTP.enterLocalPassiveMode(); // Modo pasivo para conexiones FTP

                        // Listar solo archivos (sin directorios)
                        modeloArchivos.clear();
                        FTPFile[] archivosRemotos = clienteFTP.listFiles();
                        for (FTPFile archivoRemoto : archivosRemotos) {
                            if (archivoRemoto.getType() == FTPFile.FILE_TYPE) {
                                modeloArchivos.addElement(archivoRemoto.getName()); // Solo ficheros, no directorios
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(ventanaPrincipal, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(ventanaPrincipal, "Error al conectar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para descargar el archivo seleccionado
        botonDescargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String archivoSeleccionado = listaArchivos.getSelectedValue();
                if (archivoSeleccionado == null) {
                    JOptionPane.showMessageDialog(ventanaPrincipal, "Seleccione un archivo para descargar.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Abrir un selector de directorios para elegir donde guardar el archivo
                JFileChooser selectorDirectorio = new JFileChooser();
                selectorDirectorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int opcionUsuario = selectorDirectorio.showSaveDialog(ventanaPrincipal);

                if (opcionUsuario == JFileChooser.APPROVE_OPTION) {
                    File directorioDestino = selectorDirectorio.getSelectedFile();
                    File archivoLocal = new File(directorioDestino, archivoSeleccionado);

                    // Descargar el archivo del servidor FTP
                    try (FileOutputStream flujoSalida = new FileOutputStream(archivoLocal)) {
                        boolean descargaExitosa = clienteFTP.retrieveFile(archivoSeleccionado, flujoSalida);
                        if (descargaExitosa) {
                            JOptionPane.showMessageDialog(ventanaPrincipal, "Archivo descargado correctamente en: " + archivoLocal.getAbsolutePath());
                        } else {
                            JOptionPane.showMessageDialog(ventanaPrincipal, "Error al descargar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ventanaPrincipal, "Error al guardar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Acción para salir y desconectar del servidor FTP
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (clienteFTP.isConnected()) {
                        clienteFTP.logout();
                        clienteFTP.disconnect();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ventanaPrincipal.dispose(); // Cerrar la ventana
            }
        });

        // Hacer visible la ventana
        ventanaPrincipal.setVisible(true);
    }
}
