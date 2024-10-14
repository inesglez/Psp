package Actividad1_8;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

public class EjemploLectura {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String archivoEntrada = "entrada.txt";
        String archivoSalida = "salida.txt";
        String archivoError = "error.log";
        
        try {
            // Configura ProcessBuilder para ejecutar el programa interno
            ProcessBuilder pb = new ProcessBuilder("java", "EjemploLecturaInterno");
            
            // Redirige la entrada desde archivoEntrada
            pb.redirectInput(ProcessBuilder.Redirect.from(new File(archivoEntrada)));
            
            // Redirige la salida estándar a archivoSalida
            pb.redirectOutput(ProcessBuilder.Redirect.to(new File(archivoSalida)));
            
            // Redirige la salida de error a archivoError
            pb.redirectError(ProcessBuilder.Redirect.to(new File(archivoError)));
            
            // Inicia el proceso
            Process proceso = pb.start();
            
            // Espera a que el proceso termine
            int exitCode = proceso.waitFor();
            
            System.out.println("Proceso completado con código de salida: " + exitCode);
            System.out.println("Revisa los archivos de salida y error.");
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
