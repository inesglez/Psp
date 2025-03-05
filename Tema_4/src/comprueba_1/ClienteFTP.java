package comprueba_1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

public class ClienteFTP extends JFrame {
    private JTextField txtServidor, txtUsuario;
    private JPasswordField txtClave;
    private JButton btnConectar, btnDesconectar;
    private FTPClient clienteFTP;

    public ClienteFTP() {
        setTitle("Cliente FTP");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Servidor FTP:"));
        txtServidor = new JTextField();
        add(txtServidor);

        add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        add(txtUsuario);

        add(new JLabel("Clave:"));
        txtClave = new JPasswordField();
        add(txtClave);

        btnConectar = new JButton("Conectar");
        btnConectar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conectarFTP();
            }
        });
        add(btnConectar);

        btnDesconectar = new JButton("Desconectar");
        btnDesconectar.setEnabled(false);
        btnDesconectar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                desconectarFTP();
            }
        });
        add(btnDesconectar);
    }

    private void conectarFTP() {
        clienteFTP = new FTPClient();
        try {
            clienteFTP.connect(txtServidor.getText());
            if (clienteFTP.login(txtUsuario.getText(), new String(txtClave.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Conexión realizada con éxito.");
                btnConectar.setEnabled(false);
                btnDesconectar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error de autenticación.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
        }
    }

    private void desconectarFTP() {
        try {
            if (clienteFTP.isConnected()) {
                clienteFTP.logout();
                clienteFTP.disconnect();
                JOptionPane.showMessageDialog(this, "Desconectado del servidor.");
                btnConectar.setEnabled(true);
                btnDesconectar.setEnabled(false);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al desconectar: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteFTP().setVisible(true));
    }
}
