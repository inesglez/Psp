import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Ejemplo3 {
    public static void main(String[] args) throws IOException {
        // Verifica que el directorio apunte al lugar correcto
        File directorio = new File("/home/usuario/IdeaProjects/PSP/Psp_ProgramarTm1/out/production/Psp_ProgramarTm1");

        // Ejecutar LeerNombre.class
        ProcessBuilder pb = new ProcessBuilder("/home/usuario/.jdks/openjdk-23/bin/java", "LeerNombre", "Juan", "Perez");

        // Establecer el directorio donde se encuentra el ejecutable
        pb.directory(directorio);

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
        } catch (Exception e) {
            e.printStackTrace();

        }
        try{
            // Esperar a que el proceso termine
            int exitCode = p.waitFor();
            System.out.println("\nEl proceso terminó con código: " + exitCode);

        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
