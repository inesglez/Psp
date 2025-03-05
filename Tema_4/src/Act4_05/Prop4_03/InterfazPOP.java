package Act4_05.Prop4_03;

import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;

public class InterfazPOP extends JFrame {
    private JTextField txtServer, txtPort, txtUser;  // Campos para el servidor, puerto y usuario
    private JPasswordField txtPassword;  // Campo para la contraseña
    private JRadioButton rbImplicit, rbNoImplicit;  // Botones de opción para modo implícito o no implícito
    private JButton btnConnect, btnRetrieve;  // Botones para conectar y recuperar mensajes
    private JList<String> messageList;  // Lista de mensajes en el servidor
    private DefaultListModel<String> listModel;  // Modelo de datos para la lista de mensajes
    private JTextArea txtMessage;  // Área de texto para mostrar el contenido de un mensaje
    private ButtonGroup group;  // Grupo para los botones de opción
    private POP3Client pop3Client;  // Cliente POP3 para manejar la conexión
    private boolean conectado = false;  // Estado de la conexión (conectado o no)

    public InterfazPOP() {
        setTitle("CLIENTE POP BÁSICO");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con los campos para introducir la información de conexión
        JPanel panelTop = new JPanel(new GridLayout(2, 4));
        txtServer = new JTextField("localhost");
        txtPort = new JTextField("110");
        txtUser = new JTextField("");
        txtPassword = new JPasswordField("");
        rbImplicit = new JRadioButton("Modo implícito");
        rbNoImplicit = new JRadioButton("Modo no implícito", true);
        group = new ButtonGroup();
        group.add(rbImplicit);
        group.add(rbNoImplicit);

        // Añadimos los componentes al panel superior
        panelTop.add(new JLabel("Servidor POP:"));
        panelTop.add(txtServer);
        panelTop.add(new JLabel("Puerto:"));
        panelTop.add(txtPort);
        panelTop.add(new JLabel("Usuario:"));
        panelTop.add(txtUser);
        panelTop.add(new JLabel("Clave:"));
        panelTop.add(txtPassword);

        // Panel con los botones de opción y el botón de conectar
        JPanel panelOptions = new JPanel();
        panelOptions.add(rbImplicit);
        panelOptions.add(rbNoImplicit);
        panelOptions.add(btnConnect = new JButton("Conectar"));

        // Botón para recuperar los mensajes del servidor
        btnRetrieve = new JButton("Recuperar mensajes del servidor");
        btnRetrieve.setEnabled(false);  // Inicialmente está deshabilitado
        btnRetrieve.setPreferredSize(new Dimension(300, 25));

        // Modelo de lista para mostrar los mensajes
        listModel = new DefaultListModel<>();
        messageList = new JList<>(listModel);
        JScrollPane scrollList = new JScrollPane(messageList);

        // Área de texto para mostrar el contenido de un mensaje
        txtMessage = new JTextArea();
        txtMessage.setEditable(false);  // No editable
        JScrollPane scrollMessage = new JScrollPane(txtMessage);

        // Panel para mostrar los mensajes y el área de texto
        JPanel panelMessages = new JPanel(new BorderLayout());
        panelMessages.add(btnRetrieve, BorderLayout.NORTH);
        panelMessages.add(scrollList, BorderLayout.CENTER);
        panelMessages.add(scrollMessage, BorderLayout.SOUTH);

        // Añadimos los paneles a la ventana principal
        add(panelTop, BorderLayout.NORTH);
        add(panelOptions, BorderLayout.CENTER);
        add(panelMessages, BorderLayout.SOUTH);

        // Acción del botón de conectar
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarConexion();
            }
        });

        // Acción del botón para recuperar mensajes
        btnRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recuperarMensajes();
            }
        });

        // Listener para mostrar el contenido del mensaje seleccionado
        messageList.addListSelectionListener(e -> mostrarMensaje());
    }

    // Método para manejar la conexión y desconexión del servidor POP
    private void manejarConexion() {
        if (!conectado) {  // Si no estamos conectados, intentamos conectar
            String server = txtServer.getText();
            int port = Integer.parseInt(txtPort.getText());
            String user = txtUser.getText();
            String password = new String(txtPassword.getPassword());

            try {
                conectar(server, port, user, password);
                conectado = true;
                JOptionPane.showMessageDialog(this, "Conexión realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                btnConnect.setText("Desconectar");
                btnRetrieve.setEnabled(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al conectar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {  // Si ya estamos conectados, intentamos desconectar
            try {
                desconectar();
                conectado = false;
                JOptionPane.showMessageDialog(this, "Desconexión realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                btnConnect.setText("Conectar");
                btnRetrieve.setEnabled(false);
                listModel.clear();  // Limpiamos la lista de mensajes
                txtMessage.setText("");  // Limpiamos el área de texto del mensaje
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al desconectar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para conectar al servidor POP3
    private void conectar(String server, int port, String user, String password) throws IOException {
        pop3Client = new POP3Client();
        pop3Client.connect(server, port);  // Conectamos al servidor POP
        if (!pop3Client.login(user, password)) {  // Intentamos iniciar sesión con las credenciales
            throw new IOException("Error de autenticación.");
        }
    }

    // Método para desconectar del servidor POP3
    private void desconectar() throws IOException {
        if (pop3Client != null && pop3Client.isConnected()) {
            pop3Client.logout();  // Cerramos la sesión
            pop3Client.disconnect();  // Desconectamos
        }
    }

    // Método para recuperar los mensajes del servidor POP3
    private void recuperarMensajes() {
        try {
            POP3MessageInfo[] mensajes = pop3Client.listMessages();  // Recuperamos los mensajes
            if (mensajes == null) {
                JOptionPane.showMessageDialog(this, "No se pudieron recuperar los mensajes.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            listModel.clear();  // Limpiamos la lista antes de agregar los nuevos mensajes
            for (POP3MessageInfo mensaje : mensajes) {
                listModel.addElement("Mensaje " + mensaje.number + " (" + mensaje.size + " bytes)");  // Agregamos la info del mensaje
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar mensajes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para mostrar el contenido del mensaje seleccionado
    private void mostrarMensaje() {
        int selectedIndex = messageList.getSelectedIndex();  // Obtenemos el índice del mensaje seleccionado
        if (selectedIndex != -1) {  // Si hay un mensaje seleccionado
            try {
                BufferedReader reader = (BufferedReader) pop3Client.retrieveMessage(selectedIndex + 1);  // Recuperamos el mensaje
                if (reader == null) {
                    txtMessage.setText("No se pudo recuperar el mensaje.");
                    return;
                }
                StringBuilder mensaje = new StringBuilder();
                String linea;
                while ((linea = reader.readLine()) != null) {
                    mensaje.append(linea).append("\n");  // Leemos y concatenamos las líneas del mensaje
                }
                reader.close();
                txtMessage.setText(mensaje.toString());  // Mostramos el mensaje en el área de texto
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al leer mensaje: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método principal para ejecutar la interfaz
    public static void main(String[] args) {
        new InterfazPOP().setVisible(true);  // Ejecutamos la interfaz en el hilo de la interfaz gráfica
    }
}
