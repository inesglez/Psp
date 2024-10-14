package actividad2_1;

class Tic extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("TIC");
            try {
                // Pausa de medio segundo
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}