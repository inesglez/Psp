package actividad2_8.Ejercicio11;

public class Arbitro {
    private int totalJugadores;
    private int turno;
    private int numAdivinar;
    private boolean juegoAcabado;

    // Constructor: inicializa el juego con el número de jugadores
    public Arbitro(int totalJugadores) {
        this.totalJugadores = totalJugadores;
        this.turno = 1;              // El primer jugador inicia el juego
        this.numAdivinar = (int) (Math.random() * 10) + 1; // Número aleatorio entre 1 y 10
        this.juegoAcabado = false;   // El juego no ha terminado al principio

        System.out.println("Número a adivinar:  " + numAdivinar); // Para saber el número a adivinar
    }

    // Devuelve el turno actual del jugador
    public int obtenerTurno() {
        return this.turno;
    }

    // Devuelve si el juego ha terminado
    public boolean JuegoAcabado() {
        return this.juegoAcabado;
    }

    // Método sincronizado para que solo un jugador juegue a la vez
    public synchronized void jugar(int jugador, int numJugada) {
        // Si el juego ha terminado, no permite seguir jugando
        if (this.juegoAcabado) {
            System.out.println("El juego ha terminado, no puedes jugar.");
            return;
        }

        // Si el jugador acierta el número, termina el juego
        if (numJugada == numAdivinar) {
            System.out.println("El jugador " + jugador + " gana, adivinó el número!!");
            this.juegoAcabado = true; // Marca el juego como terminado
        } else {
            // Si no ha acertado, pasa al siguiente turno
            System.out.println("Le toca a: " + siguienteTurno());
        }
    }

    // Calcula el siguiente turno de forma cíclica
    private int siguienteTurno() {
        this.turno = (this.turno % totalJugadores) + 1; // Avanza al siguiente jugador, o vuelve al primero si se llega al último
        return this.turno;
    }
}
