package com.example.tema3_2.Actividad3_9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class Cliente {
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static JTextField textField;
    private static JTextArea textArea;

    public static void main(String[] args) {
        // Configuración de la interfaz gráfica (ventana del cliente)
        JFrame frame = new JFrame("VENTANA DEL CLIENTE - EJERCICIO 5");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        textField = new JTextField(20);  // Campo de texto para ingresar mensajes
        textArea = new JTextArea(5, 20);  // Área de texto para mostrar respuestas
        textArea.setEditable(false);  // No permitir editar el área de texto

        // Botón para enviar mensajes
        JButton enviarButton = new JButton("Enviar");
        enviarButton.addActionListener(new ActionListener() {  // Acción al hacer clic en Enviar
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
            }
        });

        // Botón para limpiar los campos
        JButton limpiarButton = new JButton("Limpiar");
        limpiarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        // Configuración del panel con los componentes
        JPanel panel = new JPanel();
        panel.add(new JLabel("Escribe texto:"));
        panel.add(textField);
        panel.add(enviarButton);
        panel.add(limpiarButton);
        panel.add(new JScrollPane(textArea));  // Agrega un scroll a textArea

        // Añade el panel al marco principal de la ventana
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);  // Muestra la ventana

        conectarAlServidor();
    }

    private static void conectarAlServidor() {
        try {
            socket = new Socket("127.0.0.1", 44444);  // Se conecta al servidor en la dirección y puerto especificado
            out = new PrintWriter(socket.getOutputStream(), true);  // Se prepara para enviar datos al servidor
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // Se prepara para recibir datos del servidor
        } catch (IOException e) {
            e.printStackTrace();  // En caso de error, imprime la excepción
        }
    }

    private static void enviarMensaje() {
        String mensaje = textField.getText();  // Obtiene el mensaje del campo de texto
        if (mensaje.equals("*")) {  // Si el mensaje es "*", termina la ejecución
            out.println(mensaje);
            System.exit(0);
        } else {
            out.println(mensaje);
            try {
                String respuesta = in.readLine();
                textArea.append("Servidor: " + respuesta + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            textField.setText("");

            }
        }
    private static void limpiarCampos() {
        textField.setText("");
        textArea.setText("");
    }
}
