package comprueba_4;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClienteFicheros extends JFrame {
    private JTextField txtServidor, txtUsuario;
    private JButton btnConectar, btnDesconectar, btnActualizar;
    private JList<String> listaFicheros;
    private DefaultListModel<String> modeloLista;
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    public ClienteFicheros() {
        setTitle("Cliente de Ficheros");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Servidor:"));
        txtServidor = new JTextField();
        add(txtServidor);

        add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        add(txtUsuario);

        btnConectar = new JButton("Conectar");
        btnConectar.addActionListener(e -> conectarServidor());
        add(btnConectar);

        btnDesconectar = new JButton("Desconectar");
        btnDesconectar.setEnabled(false);
        btnDesconectar.addActionListener(e -> desconectarServidor());
        add(btnDesconectar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setEnabled(false);
        btnActualizar.addActionListener(e -> actualizarListaFicheros());
        add(btnActualizar);

        modeloLista = new DefaultListModel<>();
        listaFicheros = new JList<>(modeloLista);
        add(new JScrollPane(listaFicheros));
    }

    private void conectarServidor() {
        try {
            socket = new Socket(txtServidor.getText(), 12345);
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());
            salida.writeObject(txtUsuario.getText());

            JOptionPane.showMessageDialog(this, "Conexión realizada.");
            btnConectar.setEnabled(false);
            btnDesconectar.setEnabled(true);
            btnActualizar.setEnabled(true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
        }
    }

    private void desconectarServidor() {
        try {
            if (socket != null) {
                socket.close();
            }
            JOptionPane.showMessageDialog(this, "Desconectado.");
            btnConectar.setEnabled(true);
            btnDesconectar.setEnabled(false);
            btnActualizar.setEnabled(false);
            modeloLista.clear();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al desconectar: " + ex.getMessage());
        }
    }

    private void actualizarListaFicheros() {
        try {
            salida.writeObject("PideDirectorio");
            List<String> ficheros = (List<String>) entrada.readObject();
            modeloLista.clear();
            for (String fichero : ficheros) {
                modeloLista.addElement(fichero);
            }
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteFicheros().setVisible(true));
    }
}
