package com.example.tema3_2.Actividad3_10;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Cliente extends JFrame {
    private JTextField idJugadorInput, numeroInput;
    private JLabel intentosLabel, resultadoLabel;
    private JButton enviarBtn, salirBtn;
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;

    int puerto = 6000;
    String host = "localhost";

    private int intentos = 0;

    public Cliente() {
        // Configuración de la ventana
        setTitle("Jugador - Adivina un Número");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Componentes
        JLabel idLabel = new JLabel("ID JUGADOR:");
        idLabel.setBounds(20, 20, 100, 20);
        add(idLabel);

        idJugadorInput = new JTextField("1"); // ID por defecto
        idJugadorInput.setBounds(120, 20, 50, 20);
        idJugadorInput.setEditable(false);
        add(idJugadorInput);

        JLabel intentosTexto = new JLabel("INTENTOS:");
        intentosTexto.setBounds(200, 20, 100, 20);
        add(intentosTexto);

        intentosLabel = new JLabel("0");
        intentosLabel.setBounds(280, 20, 50, 20);
        add(intentosLabel);

        JLabel numeroLabel = new JLabel("NÚMERO A ADIVINAR:");
        numeroLabel.setBounds(20, 60, 150, 20);
        add(numeroLabel);

        numeroInput = new JTextField();
        numeroInput.setBounds(180, 60, 100, 20);
        add(numeroInput);

        enviarBtn = new JButton("Enviar");
        enviarBtn.setBounds(300, 60, 80, 20);
        add(enviarBtn);

        resultadoLabel = new JLabel("Adivina un NÚMERO ENTRE 1 Y 25");
        resultadoLabel.setBounds(20, 100, 360, 20);
        add(resultadoLabel);

        salirBtn = new JButton("Salir");
        salirBtn.setBounds(150, 150, 80, 25);
        add(salirBtn);

        // Configuración del socket
        try {
            socket = new Socket(host, puerto);
            salida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Acción del botón "Enviar"
        enviarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numero = Integer.parseInt(numeroInput.getText());
                    salida.println(numero); // Envía el número al servidor
                    intentos++;
                    intentosLabel.setText(String.valueOf(intentos));

                    String respuesta = entrada.readLine(); // Recibe la respuesta del servidor
                    resultadoLabel.setText(respuesta);

                    if (respuesta.contains("¡Correcto!")) {
                        System.out.println("¡Felicidades, adivinaste el número!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingresa un número válido.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al comunicar con el servidor.");
                }
            }
        });

        // Acción del botón "Salir"
        salirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        Cliente app = new Cliente();
        app.setVisible(true);
    }
}