package ampliacion_1;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorFicheros {
    private static final int PUERTO = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor de ficheros en ejecución...");

            while (true) {
                Socket socket = serverSocket.accept();
                new ManejadorCliente(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ManejadorCliente extends Thread {
        private Socket socket;
        private ObjectOutputStream salida;
        private ObjectInputStream entrada;
        private File directorioActual;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
            this.directorioActual = new File(System.getProperty("user.dir")); // Directorio inicial
        }

        public void run() {
            try {
                salida = new ObjectOutputStream(socket.getOutputStream());
                entrada = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    Object peticion = entrada.readObject();

                    if (peticion instanceof String) {
                        String comando = (String) peticion;

                        if (comando.equals("PideDirectorio")) {
                            enviarListaArchivos();
                        } else if (comando.startsWith("CambiarDirectorio:")) {
                            cambiarDirectorio(comando.split(":")[1]);
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Cliente desconectado.");
            }
        }

        private void enviarListaArchivos() throws IOException {
            File[] archivos = directorioActual.listFiles();
            List<String> nombres = new ArrayList<>();
            nombres.add("[..]"); // Para regresar al directorio anterior

            if (archivos != null) {
                for (File archivo : archivos) {
                    nombres.add(archivo.getName() + (archivo.isDirectory() ? "/" : ""));
                }
            }
            salida.writeObject(nombres);
        }

        private void cambiarDirectorio(String nuevoDirectorio) throws IOException {
            if (nuevoDirectorio.equals("..")) {
                directorioActual = directorioActual.getParentFile();
            } else {
                File nuevoDir = new File(directorioActual, nuevoDirectorio);
                if (nuevoDir.isDirectory()) {
                    directorioActual = nuevoDir;
                }
            }
            enviarListaArchivos();
        }
    }
}
