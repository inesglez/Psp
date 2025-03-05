package comprueba_3;

import javax.swing.*;
import java.awt.*;

import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientePOP3 extends JFrame {
    private JTextField txtServidor, txtPuerto, txtUsuario;
    private JPasswordField txtClave;
    private JButton btnConectar, btnDesconectar, btnRecuperar;
    private JList<String> listaMensajes;
    private DefaultListModel<String> modeloLista;
    private JTextArea txtMensaje;
    private POP3Client pop3Client;

    public ClientePOP3() {
        setTitle("Cliente POP3");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Servidor POP3:"));
        txtServidor = new JTextField("localhost");
        add(txtServidor);

        add(new JLabel("Puerto:"));
        txtPuerto = new JTextField("110");
        add(txtPuerto);

        add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        add(txtUsuario);

        add(new JLabel("Clave:"));
        txtClave = new JPasswordField();
        add(txtClave);

        btnConectar = new JButton("Conectar");
        btnConectar.addActionListener(e -> conectarPOP3());
        add(btnConectar);

        btnDesconectar = new JButton("Desconectar");
        btnDesconectar.setEnabled(false);
        btnDesconectar.addActionListener(e -> desconectarPOP3());
        add(btnDesconectar);

        btnRecuperar = new JButton("Recuperar mensajes");
        btnRecuperar.setEnabled(false);
        btnRecuperar.addActionListener(e -> recuperarMensajes());
        add(btnRecuperar);

        modeloLista = new DefaultListModel<>();
        listaMensajes = new JList<>(modeloLista);
        listaMensajes.addListSelectionListener(e -> mostrarMensaje(listaMensajes.getSelectedIndex()));
        add(new JScrollPane(listaMensajes));

        txtMensaje = new JTextArea();
        txtMensaje.setEditable(false);
        add(new JScrollPane(txtMensaje));
    }

    private void conectarPOP3() {
        pop3Client = new POP3Client();
        try {
            pop3Client.connect(txtServidor.getText(), Integer.parseInt(txtPuerto.getText()));
            if (pop3Client.login(txtUsuario.getText(), new String(txtClave.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Conexión realizada.");
                btnConectar.setEnabled(false);
                btnDesconectar.setEnabled(true);
                btnRecuperar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error de autenticación.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
        }
    }

    private void desconectarPOP3() {
        try {
            if (pop3Client.isConnected()) {
                pop3Client.logout();
                pop3Client.disconnect();
                JOptionPane.showMessageDialog(this, "Desconectado.");
                btnConectar.setEnabled(true);
                btnDesconectar.setEnabled(false);
                btnRecuperar.setEnabled(false);
                modeloLista.clear();
                txtMensaje.setText("");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al desconectar: " + ex.getMessage());
        }
    }

    private void recuperarMensajes() {
        try {
            POP3MessageInfo[] mensajes = pop3Client.listMessages();
            if (mensajes == null) {
                JOptionPane.showMessageDialog(this, "No se pudieron recuperar los mensajes.");
                return;
            }
            modeloLista.clear();
            for (POP3MessageInfo mensaje : mensajes) {
                modeloLista.addElement("Mensaje " + mensaje.number);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar mensajes: " + ex.getMessage());
        }
    }

    private void mostrarMensaje(int index) {
        if (index == -1) return;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(pop3Client.retrieveMessage(index + 1)));
            StringBuilder contenido = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
            txtMensaje.setText(contenido.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al mostrar mensaje: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientePOP3().setVisible(true));
    }
}
