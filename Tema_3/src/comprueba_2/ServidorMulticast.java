package comprueba_2;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;

public class ServidorMulticast extends JFrame implements ActionListener {
    JTextField texto;
    JTextArea mensajes;
    JButton enviar, salir;
    MulticastSocket socket;
    InetAddress grupo;
    int puerto = 12345;

    public ServidorMulticast() throws IOException {
        setLayout(null);
        texto = new JTextField();
        texto.setBounds(10, 10, 200, 30);
        add(texto);
        mensajes = new JTextArea();
        mensajes.setBounds(10, 50, 300, 200);
        add(mensajes);
        enviar = new JButton("Enviar");
        enviar.setBounds(220, 10, 90, 30);
        add(enviar);
        enviar.addActionListener(this);
        salir = new JButton("Salir");
        salir.setBounds(220, 260, 90, 30);
        add(salir);
        salir.addActionListener(e -> System.exit(0));

        socket = new MulticastSocket(puerto);
        grupo = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(grupo);

        setSize(350, 350);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String mensaje = texto.getText();
            byte[] buffer = mensaje.getBytes();
            DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, grupo, puerto);
            socket.send(paquete);
            mensajes.append("Enviado: " + mensaje + "\n");
            texto.setText("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new ServidorMulticast();
    }
}