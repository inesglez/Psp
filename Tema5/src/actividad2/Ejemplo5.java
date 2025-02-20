package Act_02;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Ejemplo5 {

    public static void main(String[] args) {

        try {
            FileOutputStream out = new FileOutputStream("src/Act_02/datos.dat");
            ObjectOutputStream oos = new ObjectOutputStream(out);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String datos = "En un lugar de la Mancha " + "de cuyo nombre no quiero acordarme, no ha mucho tiempo " + "que vivía un hidalgo de los de lanza en astillero, " + "adarga antigua, rocín flaco y galgo corredor.";

            byte[] dataByte = datos.getBytes();

            md.update(dataByte); // Texto a resumir
            byte[] resumen = md.digest(); // Se calcula el resumen
            oos.writeObject(datos);
            oos.writeObject(resumen);

            oos.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
