package Actividad1_6;

import java.io.IOException;

public class EjecutarSumaNumeros {

	public static void main(String[] args) {
		 try {
	            // Ejecuta el comando para correr el programa SumaNumeros
	            Process proceso = Runtime.getRuntime().exec("java -cp . Actividad1_6.SumaNumeros");
	            proceso.waitFor();  // Esperar a que termine
	            System.out.println("Programa SumaNumeros ejecutado correctamente.");

	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}