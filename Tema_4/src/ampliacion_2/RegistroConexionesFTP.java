package ampliacion_2;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RegistroConexionesFTP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Ingrese usuario (* para salir): ");
            String usuario = scanner.nextLine();

            if (usuario.equals("*")) break;

            System.out.print("Ingrese contraseña: ");
            String clave = scanner.nextLine();

            File logFile = Paths.get(usuario + "/LOG/LOG.TXT").toFile();

            if (!logFile.exists()) {
                System.out.println("Usuario o contraseña incorrectos.");
                continue;
            }

            registrarConexion(logFile);
            System.out.println("Conectado. Registro actualizado.");
        }

        scanner.close();
    }

    private static void registrarConexion(File logFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            String fecha = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy").format(new Date());
            writer.write("Hora de conexión: " + fecha);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al registrar la conexión.");
        }
    }
}

