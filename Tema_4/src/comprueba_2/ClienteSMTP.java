package comprueba_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.net.SocketException;

public class ClienteSMTP extends JFrame {
    private JTextField txtServidor, txtPuerto, txtUsuario, txtRemitente, txtDestinatario, txtAsunto;
    private JPasswordField txtClave;
    private JTextArea txtMensaje;
    private JButton btnConectar, btnEnviar;
    private AuthenticatingSMTPClient smtpClient;

    public ClienteSMTP() {
        setTitle("Cliente SMTP");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 2));

        add(new JLabel("Servidor SMTP:"));
        txtServidor = new JTextField();
        add(txtServidor);

        add(new JLabel("Puerto:"));
        txtPuerto = new JTextField("25");
        add(txtPuerto);

        add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        add(txtUsuario);

        add(new JLabel("Clave:"));
        txtClave = new JPasswordField();
        add(txtClave);

        add(new JLabel("Remitente:"));
        txtRemitente = new JTextField();
        add(txtRemitente);

        add(new JLabel("Destinatario:"));
        txtDestinatario = new JTextField();
        add(txtDestinatario);

        add(new JLabel("Asunto:"));
        txtAsunto = new JTextField();
        add(txtAsunto);

        add(new JLabel("Mensaje:"));
        txtMensaje = new JTextArea(5, 20);
        add(new JScrollPane(txtMensaje));

        btnConectar = new JButton("Conectar");
        btnConectar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conectarSMTP();
            }
        });
        add(btnConectar);

        btnEnviar = new JButton("Enviar mensaje");
        btnEnviar.setEnabled(false);
        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarCorreo();
            }
        });
        add(btnEnviar);
    }

    private void conectarSMTP() {
        smtpClient = new AuthenticatingSMTPClient();
        try {
            smtpClient.connect(txtServidor.getText(), Integer.parseInt(txtPuerto.getText()));
            smtpClient.login();
            if (smtpClient.auth(AuthenticatingSMTPClient.AUTH_METHOD.LOGIN, txtUsuario.getText(), new String(txtClave.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Conexión realizada.");
                btnEnviar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error de autenticación.");
            }
        } catch (SocketException ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error de comunicación: " + ex.getMessage());
        }
    }

    private void enviarCorreo() {
        try {
            smtpClient.setSender(txtRemitente.getText());
            smtpClient.addRecipient(txtDestinatario.getText());
            Writer writer = new OutputStreamWriter(smtpClient.sendMessageData());
            writer.write("Subject: " + txtAsunto.getText() + "\n");
            writer.write(txtMensaje.getText() + "\n");
            writer.close();
            smtpClient.completePendingCommand();
            JOptionPane.showMessageDialog(this, "Mensaje enviado.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al enviar el mensaje: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteSMTP().setVisible(true));
    }
}
