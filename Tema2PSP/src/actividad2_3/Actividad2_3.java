package actividad2_3;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class Actividad2_3 extends Applet implements ActionListener {
    private int CONTADOR = 0; // contador
    private HiloContador h; // objeto del hilo
    private Button b1, b2; // botones de control
    private boolean parar; // control de estado
    private Font fuente; // tipo de letra

    // Inicialización
    public void init() {
        setBackground(Color.yellow); // color de fondo
        b1 = new Button("Iniciar contador");
        b2 = new Button("Parar contador");
        add(b1);
        add(b2);
        b1.addActionListener(this);
        b2.addActionListener(this);
        fuente = new Font("Verdana", Font.BOLD, 26); // tipo letra
    }

    // Método paint para dibujar el contador
    public void paint(Graphics g) {
        g.clearRect(0, 0, 400, 400);
        g.setFont(fuente);
        g.drawString(Integer.toString(CONTADOR), 80, 100);
    }

    // Acción para botones
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) { // Iniciar contador
            if (h == null || !h.isAlive()) { // Si no existe o ha terminado
                parar = false;
                h = new HiloContador(); // Se crea un nuevo hilo
                h.start(); // Se inicia el hilo
            }
        } else if (e.getSource() == b2) { // Parar contador
            parar = true; // Indicador para detener el hilo
        }
    }

    // Clase interna que define el hilo
    class HiloContador extends Thread {
        public void run() {
            while (!parar) { // Ejecutar mientras no se detenga
                try {
                    Thread.sleep(300); // Pausa de 300ms
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                CONTADOR++; // Incrementar contador
                repaint(); // Redibujar el contador
            }
        }
    }
}
