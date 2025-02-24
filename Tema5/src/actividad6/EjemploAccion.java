package actividad6;

import java.io.*;
import java.security.PrivilegedAction;

public class EjemploAccion implements PrivilegedAction<Void> {
    @Override
    public Void run() {
        String archivo = "fichero.txt";

        try {
            // Intentar leer el archivo
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            System.out.println("Contenido de " + archivo + ":");
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            br.close();

            // Intentar escribir en el archivo
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
            bw.write("\nLínea añadida por el usuario.");
            bw.close();
            System.out.println("Se ha escrito en el archivo correctamente.");

        } catch (IOException e) {
            System.err.println("Error al acceder al archivo: " + e.getMessage());
        }

        return null;
    }
}
