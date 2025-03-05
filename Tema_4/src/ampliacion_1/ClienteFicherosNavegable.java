package ampliacion_1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.List;

public class ClienteFicherosNavegable extends JFrame {
    private JTextField txtServidor;
    private JButton btnConectar, btnDesconectar, btnActualizar;
    private JList<String> listaFicheros;
    private DefaultListModel<String> modeloLista;
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    public ClienteFicherosNavegable() {
        setTitle("Cliente de Ficheros Navegable");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        txtServidor = new JTextField();
        btnConectar = new JButton("Conectar");
        btnDesconectar = new JButton("Desconectar");
        btnActualizar = new JButton("Actualizar");
        modeloLista = new DefaultListModel<>();
        listaFicheros = new JList<>(modeloLista);

        add(txtServidor);
        add(btnConectar);
        add(btnActualizar);
        add(new JScrollPane(listaFicheros));

        btnDesconectar.setEnabled(false);
        btnActualizar.setEnabled(false);

        btnConectar.addActionListener(e -> conectarServidor());
        btnDesconectar.addActionListener(e -> desconectarServidor());
        btnActualizar.addActionListener(e -> actualizarListaFicheros());

        listaFicheros.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    cambiarDirectorio(listaFicheros.getSelectedValue());
                }
            }
        });
    }

    private void conectarServidor() {
        try {
            socket = new Socket(txtServidor.getText(), 12345);
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());

            JOptionPane.showMessageDialog(this, "Conexión realizada.");
            btnConectar.setEnabled(false);
            btnDesconectar.setEnabled(true);
            btnActualizar.setEnabled(true);
            actualizarListaFicheros();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
        }
    }

    private void desconectarServidor() {
        try {
            if (socket != null) socket.close();
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

    private void cambiarDirectorio(String directorio) {
        try {
            salida.writeObject("CambiarDirectorio:" + directorio);
            actualizarListaFicheros();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cambiar de directorio: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteFicherosNavegable().setVisible(true));
    }
}
