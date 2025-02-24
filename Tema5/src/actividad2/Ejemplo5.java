package actividad2;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

public class Ejemplo5 {

    public static void main(String[] args) {
        try (FileOutputStream fos = new FileOutputStream("src/actividad2/DATOS.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // Texto que vamos a almacenar
            String texto = "En un lugar de la Mancha " +
                    "de cuyo nombre no quiero acordarme, no ha mucho tiempo " +
                    "que vivía un hidalgo de los de lanza en astillero, " +
                    "adarga antigua, rocín flaco y galgo corredor.";

            // Calculamos el hash SHA-256 del texto
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(texto.getBytes());
            byte[] hash = md.digest();

            // Guardamos el texto y su hash en el archivo
            oos.writeObject(texto);
            oos.writeObject(hash);

            System.out.println("Archivo generado correctamente.");

        } catch (Exception e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
