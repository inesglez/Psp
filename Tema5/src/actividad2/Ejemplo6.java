/*
* Si cambiamos una letra del fichero .dat no sale este error:
* java.io.StreamCorruptedException: invalid stream header: EFBFBDEF
	at java.base/java.io.ObjectInputStream.readStreamHeader(ObjectInputStream.java:977)
	at java.base/java.io.ObjectInputStream.<init>(ObjectInputStream.java:404)
	at Act_02.Ejemplo6.main(Ejemplo6.java:13)
* Esto signfinica que .dat ha sido alterado y su contenido ya no coincide con el formato binario
* esperado por ObjectInputStream. Este flujo espera leer un encabezado específico generado por
* ObjectOutputStream, y al cambiar una letra (o cualquier byte), el encabezado se corrompe,
* provocando el error invalid stream header. Esto ocurre porque el archivo ya no representa un
* objeto serializado válido en Java.
*/

package Act_02;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.MessageDigest;

public class Ejemplo6 {

    public static void main(String[] args) {

        try {
            FileInputStream in = new FileInputStream("src/Act_02/datos.dat");
            ObjectInputStream ois = new ObjectInputStream(in);
            Object o = ois.readObject();

            // Primera lectura, se obtiene el String
            String datos = (String) o;
            System.out.println("Datos: " + datos);

            // Segunda lectura
            o = ois.readObject();
            byte[] resumenOriginal = (byte[]) o;

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Se calcula el resumen del String leído del fichero
            md.update(datos.getBytes()); // Texto a resumir
            byte[] resumenActual = md.digest(); // Se calcula el resumen

            // Se comprueban los dos resúmenes
            if (MessageDigest.isEqual(resumenOriginal, resumenActual)) {
                System.out.println("Datos válidos");
            } else {
                System.out.println("Datos no válidos");
            }

            ois.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
