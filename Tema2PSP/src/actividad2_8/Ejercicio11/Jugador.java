package actividad2_8.Ejercicio11;

public class Jugador extends Thread {

    private int idJugador;
    private Arbitro arbitro;

    // Constructor que recibe el identificador del jugador y el árbitro
    public Jugador(int idJugador, Arbitro arbitro) {
        this.idJugador = idJugador;
        this.arbitro = arbitro;
    }

    @Override
    public void run() {
        // El jugador jugará hasta que el juego termine
        while (!arbitro.JuegoAcabado()) {
            // Comprobar si es el turno del jugador
            if (arbitro.obtenerTurno() == idJugador) {
                // Si es el turno del jugador, genera un número aleatorio entre 1 y 10
                int numeroJugada = (int) (Math.random() * 10) + 1;
                System.out.println("Jugador " + idJugador + " dice: " + numeroJugada);

                // Hacer la jugada, usando el método sincronizado de la clase arbitro
                arbitro.jugar(idJugador, numeroJugada);
            }

            // Esperar un poco antes de la siguiente jugada
            try {
                Thread.sleep(1500); // Simula el tiempo de espera entre turnos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
