package actividad2_12;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends Applet implements Runnable, ActionListener {
    private int x = 1; // Posición inicial en x
    private int y = 100; // Posición fija en y
    private int deltaX = 5; // Velocidad del movimiento
    private boolean ejecutando = true; // Control del hilo
    private Thread hilo; // Hilo para el movimiento
    private Button botonControl; // Botón para pausar/reanudar

    @Override
    public void init() {
        // Configuración del applet
        setLayout(new FlowLayout());
        botonControl = new Button("Finalizar Hilo");
        botonControl.addActionListener(this);
        add(botonControl);

        // Inicializar y arrancar el hilo
        hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        while (true) {
            if (ejecutando) {
                // Actualizar la posición de la letra
                x += deltaX;

                // Comprobar si hay que rebotar
                if (x > getSize().width || x < 1) {
                    deltaX = -deltaX; // Invertir dirección
                }

                // Repaint para actualizar la pantalla
                repaint();
            }

            // Pausa del hilo para suavizar el movimiento
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        // Dibujar la letra en su posición actual
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("A", x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Alternar entre pausar y reanudar
        if (ejecutando) {
            ejecutando = false;
            botonControl.setLabel("Reanudar Hilo");
        } else {
            ejecutando = true;
            botonControl.setLabel("Finalizar Hilo");
        }
    }

    @Override
    public void stop() {
        // Detener el hilo cuando se cierra el applet
        hilo = null;
    }
}

