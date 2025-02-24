package actividad1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static void main(String[] args) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String message = "Este es un texto diferente."; // Texto modificado

            byte[] dataBytes = message.getBytes(); // Convertir texto a bytes
            md.update(dataBytes); // Introducir bytes en el digestor
            byte[] hashBytes = md.digest(); // Calcular el hash

            System.out.println("Texto original: " + message);
            System.out.println("Longitud del hash: " + md.getDigestLength());
            System.out.println("Algoritmo utilizado: " + md.getAlgorithm());
            System.out.println("Hash en bytes: " + new String(hashBytes));
            System.out.println("Hash en formato hexadecimal: " + toHexadecimal(hashBytes));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Convierte un array de bytes a su representación hexadecimal
    static String toHexadecimal(byte[] hashBytes) {
        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hexBuilder.append('0'); // Agrega un cero si es necesario
            }
            hexBuilder.append(hex);
        }
        return hexBuilder.toString().toUpperCase();
    }
}
