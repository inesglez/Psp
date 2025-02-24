package actividad3;

import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.io.IOException;

public class GenerarClavesSDA {

    public static void main(String[] args) {

        try {
            // Generador de claves DSA
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");

            // Se crea un generador de números aleatorios seguro
            SecureRandom numero = SecureRandom.getInstance("SHA1PRNG");

            // Se genera un par de claves con un tamaño de 2048 bits
            keyGen.initialize(2048, numero);

            // Se crea el par de claves (privada y pública)
            KeyPair par = keyGen.generateKeyPair();
            PrivateKey clavePriv = par.getPrivate(); // Clave privada
            PublicKey clavePubl = par.getPublic();  // Clave pública

            // Almacenar la clave PRIVADA en un archivo binario
            PKCS8EncodedKeySpec pk8Spec = new PKCS8EncodedKeySpec(clavePriv.getEncoded());
            FileOutputStream outPriv = new FileOutputStream("src/actividad3/Clave.privada");
            outPriv.write(pk8Spec.getEncoded()); // Se escribe la clave en el archivo
            outPriv.close();

            // Almacenar la clave PÚBLICA en un archivo binario
            X509EncodedKeySpec pkX509 = new X509EncodedKeySpec(clavePubl.getEncoded());
            FileOutputStream outPubl = new FileOutputStream("src/actividad3/Clave.publica");
            outPubl.write(pkX509.getEncoded());
            outPubl.close();

            // Crear un objeto de firma digital
            Signature dsa = Signature.getInstance("SHA256withDSA");
            dsa.initSign(clavePriv);

            // Mensaje que será firmado digitalmente
            String mensaje = "Este mensaje va a ser firmado";
            dsa.update(mensaje.getBytes()); // Se actualiza la firma con el contenido del mensaje

            byte[] firma = dsa.sign(); // Se genera la firma digital del mensaje

            // Ahora se verifica la firma usando la clave pública
            Signature verificaDSA = Signature.getInstance("SHA256withDSA");
            verificaDSA.initVerify(clavePubl); // Se inicializa con la clave pública
            verificaDSA.update(mensaje.getBytes()); // Se actualiza la verificación con el mensaje original

            boolean check = verificaDSA.verify(firma); // Se verifica la firma

            // Se muestra el resultado de la verificación
            if (check) {
                System.out.println("Firma verificada con clave pública");
            } else {
                System.out.println("Firma no verificada");
            }

        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException e) {
            e.printStackTrace(); // Manejo de errores: se imprimen los detalles de la excepción
        }
    }
}
