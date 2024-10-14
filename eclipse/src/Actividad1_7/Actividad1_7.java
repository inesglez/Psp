package Actividad1_7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Actividad1_7 {

	public static void main(String[] args) {
		String archivoEntrada = "entrada.txt";
		String archivoSalida = "salida.txt";

		try {
			FileReader fr = new FileReader(archivoEntrada);
//			leer datos desde el archivo 
//			especificado (entrada.txt en este caso).
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(archivoSalida);
			PrintWriter pw = new PrintWriter(fw);
//			escribir la salida en un archivo
			String texto;
			texto = br.readLine();

			pw.println("Cadena leída: " + texto);
			System.out.println("Cadena leída y almacenada en " + archivoSalida);

			// Cerrar todos los flujos
			br.close();
			pw.close();
		} catch (IOException e) {
			try {
				FileWriter fwError = new FileWriter("error.log");
				PrintWriter pwError = new PrintWriter(fwError);
				pwError.println("Error: " + e.getMessage());
				pwError.close();
				System.out.println("Error registrado en error.log");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
