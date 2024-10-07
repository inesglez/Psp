package Actividad1_5;

import java.io.*;

public class Act1_5 {

    public static void main(String[] args) throws IOException {

        // Cambiamos el formato para que sea compatible
        File directorio = new File("/home/usuario/Documentos/PSP/T1/out/production/Tema_1");  
        
        //ProcessBuilder para ejecutar Ejemplo2
        ProcessBuilder pb = new ProcessBuilder("java", "Ejemplos.ProgNoExis");

        // Directorio de trabajo
        pb.directory(directorio);
        System.out.printf("Directorio de trabajo: %s%n", pb.directory());

        // Inicizar el proceso
        Process p = pb.start();

        try {
            //salida del proceso
            InputStream is = p.getInputStream();
            int c;
            while ((c = is.read()) != -1) {
                System.out.print((char) c);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  error
        try {
            InputStream er = p.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(er));
            String liner = null;
            while ((liner = br.readLine()) != null) {
                System.out.println(liner);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*El error que aparece indica que la clase Ejemplos.ProgNoExis no se encuentra en el sistema 
         * o no es accesible desde la ubicación donde Java está buscando, lo que impide que el programa 
         * se ejecute correctamente.*/

    }

}