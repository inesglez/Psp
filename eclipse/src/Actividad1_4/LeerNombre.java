package Actividad1_4;

public class LeerNombre {
    public static void main(String[] args) {
        // Verificar si se han pasado dos argumentos
        if (args.length != 2) {
            System.out.println("Error: Debes introducir un nombre y un apellido.");
            System.exit(1); // Terminar el programa con código de error
        } else {
            // Imprimir el nombre y apellido
            System.out.println("Nombre: " + args[0] + ", Apellido: " + args[1]);
            System.exit(0); // Terminar el programa correctamente
        }
    } 
}
