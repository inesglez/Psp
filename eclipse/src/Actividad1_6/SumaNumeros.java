package Actividad1_6;

import java.util.Scanner;

public class SumaNumeros {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
        double numero1 = 0;
        double numero2 = 0;

        // Validación para el primer número
        System.out.print("Introduce el primer número: ");
        while (!scanner.hasNextDouble()) { 
            System.out.println("Por favor, introduce un número válido.");
        }
        numero1 = scanner.nextDouble(); //guarda el numero
        
      //El !scanner.hasNextDouble() anterior indica que si lo introducido por 
      //pantalla es diferente a Double quiere decir que no ha introducido un num

        // Validación para el segundo número
        System.out.print("Introduce el segundo número: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Por favor, introduce un número válido.");
        }
        numero2 = scanner.nextDouble();

     // Mostrar la suma
        System.out.println("La suma de los números es: " + (numero1 + numero2));
    }
}
