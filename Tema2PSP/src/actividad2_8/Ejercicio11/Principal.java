package actividad2_8.Ejercicio11;

public class Principal {
    public static void main(String[] args) {

        // Crear el juego con 3 jugadores
        Arbitro juego = new Arbitro(5);

        // Crear los hilos para los jugadores (cada uno con su identificador y el árbitro)
        Jugador jugador1 = new Jugador(1, juego);
        Jugador jugador2 = new Jugador(2, juego);
        Jugador jugador3 = new Jugador(3, juego);
        Jugador jugador4 = new Jugador(4, juego);
        Jugador jugador5 = new Jugador(5, juego);

        // Iniciar los hilos de los jugadores, lo que hace que cada jugador empiece a jugar en paralelo
        jugador1.start();
        jugador2.start();
        jugador3.start();
        jugador4.start();
        jugador5.start();

        try {
            // Esperar a que todos los hilos terminen antes de continuar
            jugador1.join();
            jugador2.join();
            jugador3.join();
            jugador4.join();
            jugador5.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Manejo de la interrupción
        }

        // Después de que los jugadores terminen, mostrar el estado final del juego
        if (juego.JuegoAcabado()) {
            System.out.println("¡El juego ha terminado!"); // Si el juego terminó, lo notificamos
        }
    }

}
