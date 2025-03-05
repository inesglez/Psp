package Act3_01;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Act3_01 {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Por favor, proporcione un nombre de máquina o dirección IP como argumento.");
            System.out.println("Ejemplo de uso: java Act3_01.TestInetAddress <nombre_maquina_o_direccion_IP>");
            return;
        }

        String input = args[0]; // Obtener el primer argumento de línea de comandos
        System.out.println("=======================================================");
        System.out.println("PROGRAMA DE INFORMACIÓN DE DIRECCIONES IP");
        System.out.println("Información sobre: " + input);
        System.out.println("=======================================================");

        InetAddress dir;

        try {
            // Obtener información sobre el nombre de máquina o dirección IP proporcionada
            dir = InetAddress.getByName(input);
            pruebaMetodos(dir);

            // Si hay múltiples direcciones IP asociadas, las mostramos
            InetAddress[] direcciones = InetAddress.getAllByName(dir.getHostName());
            if (direcciones.length > 1) {
                System.out.println("\tDIRECCIONES IP PARA: " + dir.getHostName());
                for (InetAddress direccion : direcciones) {
                    System.out.println("\t\t" + direccion.toString());
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("No se pudo obtener información para: " + input);
            e.printStackTrace();
        }
        System.out.println("=======================================================");
    }

    private static void pruebaMetodos(InetAddress dir) {
        System.out.println("\tMétodo getByName(): " + dir);
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("\tMétodo getLocalHost(): " + localHost);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // Métodos para mostrar información sobre la dirección
        System.out.println("\tMétodo getHostName(): " + dir.getHostName());
        System.out.println("\tMétodo getHostAddress(): " + dir.getHostAddress());
        System.out.println("\tMétodo toString(): " + dir.toString());
        System.out.println("\tMétodo getCanonicalHostName(): " + dir.getCanonicalHostName());
    }
}
