package Actividad1_4;
import java.io.*;

public class Ejemplo3 {
    public static void main(String[] args) throws IOException {
        // Verifica que el directorio apunte al lugar correcto
        File directorio = new File("/home/usuario/personal/eclipse/src/Actividad1_4"); 
        
        // Ejecutar LeerNombre.class 
        ProcessBuilder pb = new ProcessBuilder("java", "LeerNombre", "Juan", "Perez");
       
        // Establecer el directorio donde se encuentra el ejecutable
        pb.directory(directorio);
        
        System.out.printf("Directorio de trabajo: %s%n", pb.directory());

        // Ejecutar el proceso
        Process p = pb.start();

        // Obtener la salida del proceso
        try {
            InputStream is = p.getInputStream();
            int c;
            while ((c = is.read()) != -1) {
                System.out.print((char) c);
            }
            is.close();

            // Esperar a que el proceso termine
            int exitCode = p.waitFor();
            System.out.println("\nEl proceso terminó con código: " + exitCode);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
