package Actividad4_3;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Local {

    private static FTPClient gestorFTP = new FTPClient();

    public static void main(String[] args) throws IOException {

        // Crear ventana principal
        JFrame ventanaPrincipal = new JFrame("Gestor de Archivos FTP");
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setSize(700, 500);
        ventanaPrincipal.setLayout(new BorderLayout());

        // Panel para ingresar credenciales
        JPanel panelCredenciales = new JPanel();
        panelCredenciales.setLayout(new GridLayout(3, 2));
        JLabel etiquetaUsuario = new JLabel("Usuario:");
        JTextField campoUsuario = new JTextField("usuario"); // Usuario predeterminado
        JLabel etiquetaClave = new JLabel("Contraseña:");
        JPasswordField campoClave = new JPasswordField("usuario"); // Contraseña predeterminada
        JButton botonConectar = new JButton("Conectar");
        panelCredenciales.add(etiquetaUsuario);
        panelCredenciales.add(campoUsuario);
        panelCredenciales.add(etiquetaClave);
        panelCredenciales.add(campoClave);
        panelCredenciales.add(new JLabel());
        panelCredenciales.add(botonConectar);

        ventanaPrincipal.add(panelCredenciales, BorderLayout.NORTH);

        // Lista para mostrar archivos y botones de acción
        DefaultListModel<String> modeloListaArchivos = new DefaultListModel<>();
        JList<String> listaArchivos = new JList<>(modeloListaArchivos);
        JScrollPane panelScroll = new JScrollPane(listaArchivos);

        JButton botonDescargar = new JButton("Descargar");
        JButton botonSalir = new JButton("Salir");

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonDescargar);
        panelBotones.add(botonSalir);

        ventanaPrincipal.add(panelScroll, BorderLayout.CENTER);
        ventanaPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Acción para conectar al servidor FTP
        botonConectar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuario.getText();
                String clave = new String(campoClave.getPassword());
                try {
                    gestorFTP.connect("localhost", 21); // Dirección y puerto del servidor FTP
                    boolean acceso = gestorFTP.login(usuario, clave);
                    gestorFTP.changeWorkingDirectory("/home/usuario");

                    if (acceso) {
                        JOptionPane.showMessageDialog(ventanaPrincipal, "Conexión establecida correctamente.");
                        gestorFTP.enterLocalPassiveMode();

                        // Mostrar archivos disponibles en el servidor
                        modeloListaArchivos.clear();
                        for (String archivo : gestorFTP.listNames()) {
                            modeloListaArchivos.addElement(archivo);
                        }
                    } else {
                        JOptionPane.showMessageDialog(ventanaPrincipal, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(ventanaPrincipal, "Error de conexión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para descargar un archivo
        botonDescargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String archivoSeleccionado = listaArchivos.getSelectedValue();
                if (archivoSeleccionado == null) {
                    JOptionPane.showMessageDialog(ventanaPrincipal, "Seleccione un archivo para descargar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Seleccionar carpeta de destino
                JFileChooser selectorCarpeta = new JFileChooser();
                selectorCarpeta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int seleccion = selectorCarpeta.showSaveDialog(ventanaPrincipal);

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File carpetaDestino = selectorCarpeta.getSelectedFile();
                    File archivoLocal = new File(carpetaDestino, archivoSeleccionado);

                    try (FileOutputStream flujoSalida = new FileOutputStream(archivoLocal)) {
                        boolean exitoDescarga = gestorFTP.retrieveFile(archivoSeleccionado, flujoSalida);
                        if (exitoDescarga) {
                            JOptionPane.showMessageDialog(ventanaPrincipal, "Archivo descargado en: " + archivoLocal.getAbsolutePath());
                        } else {
                            JOptionPane.showMessageDialog(ventanaPrincipal, "No se pudo descargar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ventanaPrincipal, "Error al guardar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Acción para salir
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (gestorFTP.isConnected()) {
                        gestorFTP.logout();
                        gestorFTP.disconnect();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ventanaPrincipal.dispose();
            }
        });

        ventanaPrincipal.setVisible(true);
    }
}
