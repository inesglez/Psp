package Act4_04.Prop4_02;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Writer;

public class Cliente {
    private JFrame frame;
    private JTextField txtServer, txtPort, txtUser, txtRecipient, txtSubject;
    private JPasswordField txtPassword;
    private JTextArea txtMessage;
    private JRadioButton rbtnTLS, rbtnNoTLS;
    private JButton btnConnect, btnDisconnect, btnSend;
    private AuthenticatingSMTPClient client;

    public Cliente() {
        frame = new JFrame("CLIENTE SMTP BÁSICO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(625, 450);
        frame.setLayout(null);

        JLabel lblServer = new JLabel("Servidor SMTP:");
        lblServer.setBounds(20, 20, 100, 20);
        frame.add(lblServer);

        txtServer = new JTextField("smtp.gmail.com");
        txtServer.setBounds(130, 20, 150, 20);
        frame.add(txtServer);

        JLabel lblPort = new JLabel("Puerto:");
        lblPort.setBounds(300, 20, 50, 20);
        frame.add(lblPort);

        txtPort = new JTextField("587");
        txtPort.setBounds(350, 20, 50, 20);
        frame.add(txtPort);

        rbtnTLS = new JRadioButton("Con TLS", true);
        rbtnTLS.setBounds(420, 20, 80, 20);
        frame.add(rbtnTLS);

        rbtnNoTLS = new JRadioButton("Sin TLS");
        rbtnNoTLS.setBounds(500, 20, 80, 20);
        frame.add(rbtnNoTLS);

        ButtonGroup group = new ButtonGroup();
        group.add(rbtnTLS);
        group.add(rbtnNoTLS);

        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setBounds(20, 50, 100, 20);
        frame.add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(130, 50, 150, 20);
        frame.add(txtUser);

        JLabel lblPassword = new JLabel("Clave:");
        lblPassword.setBounds(300, 50, 50, 20);
        frame.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(350, 50, 150, 20);
        frame.add(txtPassword);

        btnConnect = new JButton("Conectar");
        btnConnect.setBounds(510, 50, 100, 20);
        frame.add(btnConnect);

        btnDisconnect = new JButton("Desconectar");
        btnDisconnect.setBounds(510, 80, 100, 20);
        btnDisconnect.setEnabled(false);
        frame.add(btnDisconnect);

        JLabel lblRecipient = new JLabel("Remitente:");
        lblRecipient.setBounds(20, 80, 100, 20);
        frame.add(lblRecipient);

        txtRecipient = new JTextField();
        txtRecipient.setBounds(130, 80, 370, 20);
        frame.add(txtRecipient);

        JLabel lblSubject = new JLabel("Asunto:");
        lblSubject.setBounds(20, 110, 100, 20);
        frame.add(lblSubject);

        txtSubject = new JTextField();
        txtSubject.setBounds(130, 110, 480, 20);
        frame.add(txtSubject);

        JLabel lblMessage = new JLabel("Redacta el cuerpo del mensaje:");
        lblMessage.setBounds(20, 140, 200, 20);
        frame.add(lblMessage);

        txtMessage = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(txtMessage);
        scrollPane.setBounds(20, 160, 560, 150);
        frame.add(scrollPane);

        btnSend = new JButton("Enviar mensaje");
        btnSend.setBounds(220, 330, 150, 30);
        btnSend.setEnabled(false);
        frame.add(btnSend);

        btnConnect.addActionListener(e -> conectar());
        btnDisconnect.addActionListener(e -> desconectar());
        btnSend.addActionListener(e -> enviarCorreo());

        frame.setVisible(true);
    }

    private void conectar() {
        try {
            client = new AuthenticatingSMTPClient();
            client.connect(txtServer.getText(), Integer.parseInt(txtPort.getText()));
            if (!SMTPReply.isPositiveCompletion(client.getReplyCode())) {
                client.disconnect();
                JOptionPane.showMessageDialog(frame, "Error al conectar");
                return;
            }
            client.ehlo(txtServer.getText());
            if (rbtnTLS.isSelected() && !client.execTLS()) {
                JOptionPane.showMessageDialog(frame, "Error en TLS");
                return;
            }
            if (!client.auth(AuthenticatingSMTPClient.AUTH_METHOD.PLAIN, txtUser.getText(), new String(txtPassword.getPassword()))) {
                JOptionPane.showMessageDialog(frame, "Error de autenticación");
                return;
            }
            btnConnect.setEnabled(false);
            btnDisconnect.setEnabled(true);
            btnSend.setEnabled(true);
            JOptionPane.showMessageDialog(frame, "Conexión realizada");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void desconectar() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                JOptionPane.showMessageDialog(frame, "Desconectado");
                btnConnect.setEnabled(true);
                btnDisconnect.setEnabled(false);
                btnSend.setEnabled(false);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void enviarCorreo() {
        try {
            SimpleSMTPHeader header = new SimpleSMTPHeader(txtUser.getText(), txtRecipient.getText(), txtSubject.getText());
            client.setSender(txtUser.getText());
            client.addRecipient(txtRecipient.getText());

            Writer writer = client.sendMessageData();
            if (writer != null) {
                writer.write(header.toString());
                writer.write(txtMessage.getText());
                writer.close();
            }

            if (client.completePendingCommand()) {
                JOptionPane.showMessageDialog(frame, "Mensaje enviado con éxito");
            } else {
                JOptionPane.showMessageDialog(frame, "Error al enviar mensaje");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Cliente();
    }
}
