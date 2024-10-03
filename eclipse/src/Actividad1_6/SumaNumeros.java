package Actividad1_6;

import java.util.Scanner;

public class SumaNumeros {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
        double numero1 = 0;
        double numero2 = 0;

        System.out.print("Introduce el primer número: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Por favor, introduce un número válido.");
            scanner.next();  // Limpiar la entrada inválida
			/*
			 * Si no se hace el paso anterior, el programa sigue encontrando 
			 * el mismo valor inválido una y otra vez, lo que hace que 
			 * el bucle se quede atrapado repitiendo la misma instrucción
			 */
        }
        numero1 = scanner.nextDouble();

        // Validación para el segundo número
        System.out.print("Introduce el segundo número: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Por favor, introduce un número válido.");
            scanner.next();  // Limpiar la entrada inválida
        }
        numero2 = scanner.nextDouble();

        // Mostrar la suma
        System.out.println("La suma de los números es: " + (numero1 + numero2));
    }
}