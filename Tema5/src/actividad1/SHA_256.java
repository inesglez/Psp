package actividad1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Scanner;

<<<<<<< HEAD
public class SHA256Hasher {
=======
public class SHA_256 {
>>>>>>> 444a3dd9ac82ce51985fadaae17bd687d3663045

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Por favor, introduce un primer texto: ");
            String inputText1 = scanner.nextLine();

            System.out.print("Ahora, introduce un segundo texto: ");
            String inputText2 = scanner.nextLine();

            System.out.print("Por último, introduce la clave: ");
            String secretKey = scanner.next();

            // Concatenamos los textos con la clave
            String combinedText1 = inputText1 + secretKey;
            String combinedText2 = inputText2 + secretKey;

            // Inicializamos el algoritmo SHA-256
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            // Generación del hash para el primer texto
            byte[] bytesData1 = combinedText1.getBytes(); // Texto a Bytes
            messageDigest.update(bytesData1); // Introducir texto en bytes
            byte[] hashResult1 = messageDigest.digest(); // Calcular el hash

            System.out.println("=========== TEXTO 1 ==========");
            System.out.println("Texto original: " + inputText1);
            System.out.println("Hash en bytes: " + new String(hashResult1));
            System.out.println("Hash en formato hexadecimal: " + toHexadecimal(hashResult1));

            // Generación del hash para el segundo texto
            messageDigest.reset(); // Reiniciamos el MessageDigest
            byte[] bytesData2 = combinedText2.getBytes(); // Texto a Bytes
            messageDigest.update(bytesData2); // Introducir texto en bytes
            byte[] hashResult2 = messageDigest.digest(); // Calcular el hash

            System.out.println("=========== TEXTO 2 ==========");
            System.out.println("Texto original: " + inputText2);
            System.out.println("Hash en bytes: " + new String(hashResult2));
            System.out.println("Hash en formato hexadecimal: " + toHexadecimal(hashResult2));

            // Información del proveedor
            Provider provider = messageDigest.getProvider();
            System.out.println("Proveedor: " + provider.getName());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            scanner.close(); // Cerramos el Scanner
        }
    }

    // Convierte un array de bytes a hexadecimal
    static String toHexadecimal(byte[] hashResult) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashResult) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hexString.append('0'); // Agregar un cero si es necesario
            }
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase();
    }
}
