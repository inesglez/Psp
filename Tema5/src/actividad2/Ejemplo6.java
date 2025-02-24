package actividad2;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.MessageDigest;

public class Ejemplo6 {

    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("src/actividad2/DATOS.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            // Leemos el primer objeto (String) del archivo
            String contenido = (String) ois.readObject();
            System.out.println("Contenido leído: " + contenido);

            // Leemos el segundo objeto (hash original)
            byte[] hashGuardado = (byte[]) ois.readObject();

            // Calculamos el hash del contenido leído
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(contenido.getBytes());
            byte[] hashCalculado = md.digest();

            // Verificamos si los hashes coinciden
            if (MessageDigest.isEqual(hashGuardado, hashCalculado)) {
                System.out.println("Los datos son correctos.");
            } else {
                System.out.println("Advertencia: los datos han sido alterados.");
            }

        } catch (Exception e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
