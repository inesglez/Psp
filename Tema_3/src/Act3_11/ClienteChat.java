package Act3_11;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClienteChat extends JFrame implements ActionListener, Runnable {
    private static final long serialVersionUID = 1L;
    Socket socket = null;

    // streams
    DataInputStream fentrada;
    DataOutputStream fsalida;

    String nombre;
    static JTextField mensaje = new JTextField();

    private JScrollPane scrollPane1;
    static JTextArea textArea1;
    JButton botonEnviar = new JButton("Enviar");
    JButton botonSalir = new JButton("Salir");
    boolean repetir = true;

    // Constructor
    public ClienteChat(Socket s, String nombre) {
        super("Conexión del cliente chat: " + nombre);
        setLayout(null);
        mensaje.setBounds(10, 10, 400, 30);
        add(mensaje);

        textArea1 = new JTextArea();
        scrollPane1 = new JScrollPane(textArea1);
        scrollPane1.setBounds(10, 50, 400, 300);
        add(scrollPane1);

        botonEnviar.setBounds(420, 10, 100, 30);
        add(botonEnviar);
        botonSalir.setBounds(420, 50, 100, 30);
        add(botonSalir);

        textArea1.setEditable(false);
        botonSalir.addActionListener(this);
        botonEnviar.addActionListener(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        socket = s;
        this.nombre = nombre;

        try {
            fentrada = new DataInputStream(socket.getInputStream());
            fsalida = new DataOutputStream(socket.getOutputStream());
            String texto = " > Entrada en el Chat ... " + nombre;
            fsalida.writeUTF(texto);
        } catch (IOException e) {
            System.out.println("ERROR DE E/S");
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonEnviar) {
            if (mensaje.getText().trim().length() == 0) return;
            String texto = nombre + " > " + mensaje.getText();

            try {
                mensaje.setText("");
                fsalida.writeUTF(texto);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (e.getSource() == botonSalir) {
            String texto = " > Abandona el Chat ... " + nombre;

            try {
                fsalida.writeUTF(texto);
                fsalida.writeUTF("*");
                repetir = false;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        String texto = "";
        while (repetir) {
            try {
                texto = fentrada.readUTF();
                textArea1.setText(texto);
            } catch (IOException e1) {
                // Este error sale cuando el servidor se cierra
                JOptionPane.showMessageDialog(null, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e1.getMessage(), "<<MENSAJE DE ERROR: 2>>", JOptionPane.ERROR_MESSAGE);
                repetir = false; // Salir del bucle

            }
        }
        try {
            socket.close(); // Cerrar el socket
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int puerto = 44444;
        String host = "localhost";
        Socket s = null;

        String nombre = JOptionPane.showInputDialog(null, "Introduce tu nombre o nick: ");

        if (nombre.trim().length() == 0) {
            System.out.println("El nombre está vacío...");
            return;
        }

        try {
            s = new Socket("172.17.0.1", puerto);
            ClienteChat cliente = new ClienteChat(s, nombre);
            cliente.setBounds(0, 0, 540, 400);
            cliente.setVisible(true);
            new Thread(cliente).start(); // Se lanza el hilo Cliente
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage(), "<<MENSAJE DE ERROR: 1>>", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
