package Actividad1_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjemploLecturaInterno {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String texto = br.readLine();
            
            System.out.println("Cadena leída: " + texto);
            
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}