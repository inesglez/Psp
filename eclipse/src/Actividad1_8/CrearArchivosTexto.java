package Actividad1_8;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CrearArchivosTexto {
	public static void main(String[] args) {
		// Archivos necesarios
		String archivoEntrada = "entrada.txt";
		String archivoSalida = "salida.txt";
		String archivoError = "error.log";

		try {
			// Crear archivo de entrada con contenido
			FileWriter fwEntrada = new FileWriter(archivoEntrada);
			PrintWriter pwEntrada = new PrintWriter(fwEntrada);
			pwEntrada.println("Este es el texto de entrada."); // Contenido del archivo de entrada
			pwEntrada.close();
			System.out.println("Archivo 'entrada.txt' creado con contenido.");

			// Crear archivo de salida vacío
			FileWriter fwSalida = new FileWriter(archivoSalida);
			fwSalida.close();
			System.out.println("Archivo 'salida.txt' creado vacío.");

			// Crear archivo de error vacío
			FileWriter fwError = new FileWriter(archivoError);
			fwError.close();
			System.out.println("Archivo 'error.log' creado vacío.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
