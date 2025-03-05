package Act3_10;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class JugadorAdivina extends JFrame implements ActionListener {
    private JTextField numeroInput;
    private JLabel idJugadorLabel, intentosLabel, resultadoLabel;
    private JButton enviarBtn, salirBtn;
    private Socket cliente;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private Datos datos;

    public JugadorAdivina() {
        configurarInterfaz();
        configurarConexion();
    }

    private void configurarInterfaz() {
        setTitle("Jugador - Adivina un Número");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel idLabel = new JLabel("ID JUGADOR:");
        idLabel.setBounds(20, 20, 100, 20);
        add(idLabel);

        idJugadorLabel = new JLabel("2"); // ID por defecto
        idJugadorLabel.setBounds(120, 20, 50, 20);
        add(idJugadorLabel);

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

        // Registrar ActionListener
        enviarBtn.addActionListener(this);
        salirBtn.addActionListener(e -> System.exit(0));
    }

    private void configurarConexion() {
        try {
            cliente = new Socket("localhost", 6001);
            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());

            datos = (Datos) entrada.readObject();
            idJugadorLabel.setText(String.valueOf(datos.getIdentificador()));
            resultadoLabel.setText(datos.getCadena());
        } catch (IOException | ClassNotFoundException e) {
            resultadoLabel.setText("Error al conectar con el servidor.");
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enviarBtn) {
            enviarNumero();
        }
    }

    private void enviarNumero() {
        String numeroTexto = numeroInput.getText().trim();

        if (!esNumeroValido(numeroTexto)) {
            resultadoLabel.setText("Por favor, introduce un número entre 1 y 25.");
            return;
        }

        try {
            Datos nuevoDato = new Datos(numeroTexto, datos.getIntentos(), datos.getIdentificador());
            salida.reset();
            salida.writeObject(nuevoDato);

            datos = (Datos) entrada.readObject();
            intentosLabel.setText(String.valueOf(datos.getIntentos()));
            resultadoLabel.setText(datos.getCadena());

            if (datos.isGana()) {
                resultadoLabel.setText("¡Felicidades! Has ganado.");
                bloquearEntrada();
            } else if (!datos.isJuega() || datos.getIntentos() >= 5) {
                resultadoLabel.setText("El juego ha terminado.");
                bloquearEntrada();
            }

        } catch (IOException | ClassNotFoundException e) {
            resultadoLabel.setText("Error al comunicarse con el servidor.");
            e.printStackTrace();
        }
    }

    private boolean esNumeroValido(String numeroTexto) {
        try {
            int numero = Integer.parseInt(numeroTexto);
            return numero >= 1 && numero <= 25;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void bloquearEntrada() {
        numeroInput.setEnabled(false);
        enviarBtn.setEnabled(false);
    }

    public static void main(String[] args) {
        JugadorAdivina app = new JugadorAdivina();
        app.setVisible(true);
    }
}
