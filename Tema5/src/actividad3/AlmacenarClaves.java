package Act_03;

import java.io.FileOutputStream;
import java.security.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class AlmacenarClaves {

    public static void main(String[] args) {

        try {
            // Generador de par de claves DSA
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            SecureRandom numero = SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(2048, numero);

            // Se crea el par de claves PRIVADA y PÚBLICA
            KeyPair par = keyGen.generateKeyPair();
            PrivateKey clavePriv = par.getPrivate();
            PublicKey clavePubl = par.getPublic();

            // Almacenar clave PRIVADA en un fichero binario
            PKCS8EncodedKeySpec pk8Spec = new PKCS8EncodedKeySpec(clavePriv.getEncoded());
            FileOutputStream outPriv = new FileOutputStream("src/Act_03/Clave.privada");
            outPriv.write(pk8Spec.getEncoded());
            outPriv.close();

            // Almacenar clave PÚBLICA en un fichero binario
            X509EncodedKeySpec pkX509 = new X509EncodedKeySpec(clavePubl.getEncoded());
            FileOutputStream outPubl = new FileOutputStream("src/Act_03/Clave.publica");
            outPubl.write(pkX509.getEncoded());
            outPubl.close();

            // FIRMA CON LA CLAVE PRIVADA
            Signature dsa = Signature.getInstance("SHA256withDSA");
            dsa.initSign(clavePriv);
            String mensaje = "Este mensaje va a ser firmado";
            dsa.update(mensaje.getBytes());

            byte[] firma = dsa.sign(); // Mensaje firmado

            // Verificación de la firma con la clave pública
            Signature verificaDSA = Signature.getInstance("SHA256withDSA");
            verificaDSA.initVerify(clavePubl);
            verificaDSA.update(mensaje.getBytes());
            boolean check = verificaDSA.verify(firma);

            if (check) {
                System.out.println("Firma verificada con clave pública");
            } else {
                System.out.println("Firma no verificada");
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException e) {
            e.printStackTrace();
        }
    }

}
