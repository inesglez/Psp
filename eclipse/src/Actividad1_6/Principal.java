package Actividad1_6;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Especificamos el directorio donde se encuentra el programa a ejecutar
		File directorio = new File("/home/usuario/Documentos/psp/T1/out/production/T1");

		// Configuramos el proceso que queremos ejecutar, en este caso, la clase SumNums

		ProcessBuilder pb = new ProcessBuilder("java", "Actividad1_6.SumaNumeros"); // Comando para ejecutar el programa

		// Establecemos el directorio de trabajo donde se encuentra el archivo de la
		// clase
		pb.directory(directorio);

		try {
			// Iniciamos el proceso
			Process p = pb.start();

			// Preparamos la salida para enviar datos al proceso, simulando la entrada del
			// teclado
			OutputStream os = p.getOutputStream();
			os.write("7\n".getBytes()); // Enviamos el primer número
			os.write("8\n".getBytes()); // Enviamos el segundo número
			os.flush(); // Aseguramos que los datos se envíen

			// Leemos la salida del proceso que se ejecuta
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String linea;
			// Leemos cada línea de la salida del proceso y la mostramos en la consola
			while ((linea = br.readLine()) != null) {
				System.out.print(linea + "\n"); // Imprimimos la salida recibida
			}
			os.close(); // Cerramos el flujo de salida

			// Comprobamos el estado de salida del proceso: 0 indica éxito, 1 indica error
			int exiVal;
			try {
				exiVal = p.waitFor(); // Esperamos a que el proceso termine
				System.out.println("Valor de Salida: " + exiVal); // Mostramos el valor de salida del proceso
			} catch (InterruptedException e) {
				e.printStackTrace(); // Manejo de excepciones en caso de interrupción
			}

		} catch (IOException e) {
			e.printStackTrace(); // Manejo de excepciones relacionadas con la entrada/salida
		}
	}
}
