<<<<<<< HEAD
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
=======
package actividad2;
>>>>>>> 444a3dd9ac82ce51985fadaae17bd687d3663045

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.MessageDigest;

public class Ejemplo6 {

    public static void main(String[] args) {
<<<<<<< HEAD

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

=======
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
>>>>>>> 444a3dd9ac82ce51985fadaae17bd687d3663045
}
