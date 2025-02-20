package actividad4_4;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.apache.commons.net.smtp.*;
import java.io.IOException;

public class SMTPClientGUI extends JFrame {
    private JTextField serverField, portField, userField, passField, senderField, recipientField, subjectField;
    private JTextArea messageArea;
    private JRadioButton noTLS, withTLS;
    private JButton connectButton, sendButton;
    private AuthenticatingSMTPClient client;

    public SMTPClientGUI() {
        setTitle("Cliente SMTP");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 2));

        // Configuración de campos de entrada
        add(new JLabel("Servidor SMTP:"));
        serverField = new JTextField("localhost");
        add(serverField);

        add(new JLabel("Puerto:"));
        portField = new JTextField("25"); // Puerto predeterminado para SMTP
        add(portField);

        add(new JLabel("Usuario:"));
        userField = new JTextField();
        add(userField);

        add(new JLabel("Contraseña:"));
        passField = new JPasswordField();
        add(passField);

        add(new JLabel("Correo remitente:"));
        senderField = new JTextField();
        add(senderField);

        add(new JLabel("Correo destinatario:"));
        recipientField = new JTextField();
        add(recipientField);

        add(new JLabel("Asunto:"));
        subjectField = new JTextField();
        add(subjectField);

        add(new JLabel("Mensaje:"));
        messageArea = new JTextArea(5, 20);
        add(new JScrollPane(messageArea));

        // Selección de seguridad TLS
        noTLS = new JRadioButton("Sin TLS", true);
        withTLS = new JRadioButton("Con TLS");
        ButtonGroup group = new ButtonGroup();
        group.add(noTLS);
        group.add(withTLS);
        add(noTLS);
        add(withTLS);

        // Botones de conexión y envío
        connectButton = new JButton("Conectar");
        sendButton = new JButton("Enviar");
        sendButton.setEnabled(false);
        add(connectButton);
        add(sendButton);

        connectButton.addActionListener(e -> conectarSMTP());
        sendButton.addActionListener(e -> enviarCorreo());

        setVisible(true);
    }

    private void conectarSMTP() {
        try {
            client = new AuthenticatingSMTPClient();
            client.connect(serverField.getText(), Integer.parseInt(portField.getText()));

            if (!SMTPReply.isPositiveCompletion(client.getReplyCode())) {
                JOptionPane.showMessageDialog(this, "Conexión rechazada", "Error", JOptionPane.ERROR_MESSAGE);
                client.disconnect();
                return;
            }

            client.ehlo(serverField.getText());
            if (withTLS.isSelected()) {
                client.execTLS();
            }

            JOptionPane.showMessageDialog(this, "Conectado al servidor SMTP", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            sendButton.setEnabled(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enviarCorreo() {
        try {
            SimpleSMTPHeader header = new SimpleSMTPHeader(senderField.getText(), recipientField.getText(), subjectField.getText());
            client.setSender(senderField.getText());
            client.addRecipient(recipientField.getText());

            client.sendShortMessageData(messageArea.getText());
            JOptionPane.showMessageDialog(this, "Correo enviado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            client.logout();
            client.disconnect();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al enviar correo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new SMTPClientGUI();
    }
}
