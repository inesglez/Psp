import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private int x = 1;
    private int y = 100;
    private int direccion = 1;
    private boolean enMovimiento = true;
    private JButton btnControl;
    private HiloMovimiento hilo;


    public Main() {
        // Configuración básica de la ventana (JFrame)
        setTitle("Movimiento de una Letra");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar al pulsar la X
        setBackground(Color.yellow); // Fondo para la ventana
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setLayout(null); // Desactivar el layout para controlar posiciones manualmente

        // Crear el panel donde se dibujará la letra
        JPanel fondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("A", x, y); // Dibujar la letra en las coordenadas (x, y)
            }
        };
        fondo.setBackground(Color.yellow);
        fondo.setLayout(null);
        setContentPane(fondo);

        // Crear el botón para controlar el hilo (pausar/reanudar)
        btnControl = new JButton("Finalizar Hilo");
        btnControl.setBounds(150, 130, 120, 30); // Posición y tamaño del botón
        btnControl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar el estado del hilo entre pausado y en movimiento
                if (enMovimiento) {
                    enMovimiento = false; // Pausar el movimiento
                    hilo.pausar();
                    btnControl.setText("Reanudar Hilo");
                } else {
                    enMovimiento = true; // Reanudar el movimiento
                    hilo.reanudar();
                    btnControl.setText("Finalizar Hilo");
                }
            }
        });
        add(btnControl);
        hilo = new HiloMovimiento();
        hilo.start();
    }

    // Método para pintar la letra en la pantalla
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("A", x, y);
    }

    // Clase interna que representa el hilo de movimiento
    private class HiloMovimiento extends Thread {
        private boolean pausado = false; // Estado del hilo (si está pausado o no)

        // Método para pausar el hilo
        public synchronized void pausar() {
            pausado = true; // Marcar el hilo como pausado
        }

        // Método para reanudar el hilo
        public synchronized void reanudar() {
            pausado = false; // Marcar el hilo como en movimiento
            notify();
        }

        @Override
        public void run() {
            // Bucle infinito para mover la letra continuamente
            while (true) {
                synchronized (this) {
                    while (pausado) {
                        try {
                            wait(); // Esperar si el hilo está pausado
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Actualizar la posición de la letra según la dirección
                x += direccion * 5; // Aumentar o disminuir la posición en x

                // Detectar los bordes de la ventana para hacer rebotar la letra
                if (x >= getWidth() - 15) { // Si la letra toca el borde derecho
                    direccion = -1; // Cambiar la dirección hacia la izquierda
                } else if (x <= 1) { // Si la letra toca el borde izquierdo
                    direccion = 1; // Cambiar la dirección hacia la derecha
                }
                repaint();

                try {
                    Thread.sleep(50); // Controlar la velocidad del movimiento (50 ms entre actualizaciones)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        Main ventana = new Main(); // Crear la ventana
        ventana.setVisible(true); // Hacer visible la ventana
    }
}