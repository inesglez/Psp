package Act3_09;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Cliente extends JFrame implements ActionListener {
    private JTextField textoInput; // Campo de entrada para el mensaje
    private JLabel textoOutput; // Campo de salida para el mensaje recibido
    private JButton enviarBtn; // Botón para enviar
    private JButton salirBtn; // Botón para salir
    private JButton limpiarBtn; // Botón para limpiar

    private Socket socket;
    private DataOutputStream salida;
    private DataInputStream entrada;

    int puerto = 44444;
    String host = "localhost";

    public Cliente() {
        // Configuración de la ventana
        setTitle("VENTANA DEL CLIENTE - EJERCICIO 5");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Etiqueta para el texto de entrada
        JLabel labelEntrada = new JLabel("Escribe texto:");
        labelEntrada.setBounds(20, 20, 100, 20);
        add(labelEntrada);

        // Campo de texto para entrada
        textoInput = new JTextField();
        textoInput.setBounds(120, 20, 250, 20);
        add(textoInput);

        // Campo de texto para salida
        textoOutput = new JLabel();
        textoOutput.setBounds(20, 60, 350, 20);
        add(textoOutput);

        // Botones
        enviarBtn = new JButton("Enviar");
        enviarBtn.setBounds(20, 100, 100, 25);
        enviarBtn.addActionListener(this);
        add(enviarBtn);

        salirBtn = new JButton("Salir");
        salirBtn.setBounds(150, 100, 100, 25);
        salirBtn.addActionListener(this);
        add(salirBtn);

        limpiarBtn = new JButton("Limpiar");
        limpiarBtn.setBounds(280, 100, 100, 25);
        limpiarBtn.addActionListener(this);
        add(limpiarBtn);

        // Conectar al servidor
        try {
            socket = new Socket(host, puerto);
            salida = new DataOutputStream(socket.getOutputStream());
            entrada = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con el servidor: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == enviarBtn) {
            String texto = textoInput.getText().trim();
            if (!texto.isEmpty()) {
                try {
                    salida.writeUTF(texto); // Envía el texto al servidor
                    String respuesta = entrada.readUTF(); // Recibe respuesta del servidor
                    textoOutput.setText(respuesta); // Muestra la respuesta en el campo de salida
                } catch (IOException ex) {
                    textoOutput.setText("Error al enviar el mensaje.");
                }
            }
        } else if (source == salirBtn) {
            try {
                salida.writeUTF("*"); // Envía * para cerrar la conexión
                socket.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al cerrar la conexión.");
            }
            System.exit(0);
        } else if (source == limpiarBtn) {
            textoInput.setText(""); // Limpia el campo de entrada
            textoOutput.setText(""); // Limpia el campo de salida
        }
    }

    public static void main(String[] args) {
        Cliente app = new Cliente(); // Crea la ventana
        app.setVisible(true); // Hace visible la ventana
    }
}
