package actividad2_6.Ejercicio6;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame {
    private JProgressBar barra1, barra2, barra3;
    private JSlider slider1, slider2, slider3;
    private JTextField prioridad1, prioridad2, prioridad3;
    private JButton botonIniciar;

    public Principal() {
        // Configuración básica de la ventana
        setTitle("CARRERA DE HILOS");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Botón para comenzar la carrera
        botonIniciar = new JButton("Comenzar Carrera");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(botonIniciar, gbc);

        // Crear las filas para cada hilo con sus barras, sliders y prioridades
        agregarFila(gbc, 1, "HILO 1 ", barra1 = new JProgressBar(0, 100), slider1 = new JSlider(1, 10, 5), prioridad1 = new JTextField("5"));
        agregarFila(gbc, 2, "HILO 2 ", barra2 = new JProgressBar(0, 100), slider2 = new JSlider(1, 10, 5), prioridad2 = new JTextField("5"));
        agregarFila(gbc, 3, "HILO 3 ", barra3 = new JProgressBar(0, 100), slider3 = new JSlider(1, 10, 5), prioridad3 = new JTextField("5"));

        // Configurar los sliders para que actualicen los campos de prioridad
        configurarSlider(slider1, prioridad1);
        configurarSlider(slider2, prioridad2);
        configurarSlider(slider3, prioridad3);

        // Acción del botón de iniciar la carrera
        botonIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarCarrera();
            }
        });

        setVisible(true);
    }

    // Método para agregar una fila con los componentes de cada hilo (barra, slider, prioridad)
    private void agregarFila(GridBagConstraints gbc, int fila, String etiqueta, JProgressBar barra, JSlider slider, JTextField prioridad) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        add(new JLabel(etiqueta), gbc); // Etiqueta para cada hilo

        barra.setForeground(Color.GREEN);  // Color de la barra de progreso
        barra.setBackground(Color.GRAY);   // Color de fondo de la barra

        gbc.gridx = 1;
        add(barra, gbc);

        gbc.gridx = 2;
        add(slider, gbc); // Slider para cambiar la prioridad

        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Prioridad: "), gbc); // Etiqueta "Prioridad:"

        gbc.gridx = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(prioridad, gbc); // Campo de texto para mostrar la prioridad
    }


    private void configurarSlider(JSlider slider, JTextField prioridad) {
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                prioridad.setText(String.valueOf(slider.getValue())); // Actualizar el valor de prioridad
            }
        });
    }

    // Método para iniciar la carrera de hilos
    private void iniciarCarrera() {
        botonIniciar.setEnabled(false); // Desactivar el botón mientras corren los hilos

        Thread hilo1 = crearHilo(barra1, Integer.parseInt(prioridad1.getText()));
        Thread hilo2 = crearHilo(barra2, Integer.parseInt(prioridad2.getText()));
        Thread hilo3 = crearHilo(barra3, Integer.parseInt(prioridad3.getText()));

        // Establecer las prioridades de los hilos
        hilo1.setPriority(Integer.parseInt(prioridad1.getText()));
        hilo2.setPriority(Integer.parseInt(prioridad2.getText()));
        hilo3.setPriority(Integer.parseInt(prioridad3.getText()));

        hilo1.start();
        hilo2.start();
        hilo3.start();
    }

    // Método para crear un hilo que actualiza una barra de progreso
    private Thread crearHilo(JProgressBar barra, int prioridad) {
        return new Thread(() -> {
            int progreso = 0;
            while (progreso < 100) {
                try {
                    Thread.sleep((long) (Math.random() * 1000 + 1));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                progreso += 10;
                barra.setValue(progreso);
            }

            verificarGanador();
        });
    }

    // Método sincronizado para verificar si algún hilo ha ganado
    private synchronized void verificarGanador() {
        if (barra1.getValue() == 100 && barra2.getValue() < 100 && barra3.getValue() < 100) {
            JOptionPane.showMessageDialog(this, "¡HILO 1 GANA!");
        } else if (barra2.getValue() == 100 && barra1.getValue() < 100 && barra3.getValue() < 100) {
            JOptionPane.showMessageDialog(this, "¡HILO 2 GANA!");
        } else if (barra3.getValue() == 100 && barra1.getValue() < 100 && barra2.getValue() < 100) {
            JOptionPane.showMessageDialog(this, "¡HILO 3 GANA!");
        }

        // Si todos los hilos han llegado al 100%, reactivar el botón
        if (barra1.getValue() == 100 && barra2.getValue() == 100 && barra3.getValue() == 100) {
            botonIniciar.setEnabled(true);
        }
    }

    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        new Principal();
    }
}
