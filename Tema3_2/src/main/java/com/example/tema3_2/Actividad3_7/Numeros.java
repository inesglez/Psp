package com.example.tema3_2.Actividad3_7;

public class Numeros {
    int numero;
    long cuadrado;
    long cubo;

    // Constructor
    public Numeros(int numero) {
        this.numero = numero;
        this.cuadrado = (long) numero * numero;  // Calcula el cuadrado
        this.cubo = (long) numero * numero * numero;  // Calcula el cubo
    }

    // Métodos para obtener los valores
    public int getNumero() {
        return numero;
    }

    public long getCuadrado() {
        return cuadrado;
    }

    public long getCubo() {
        return cubo;
    }

    public void mostrarValores() {
        System.out.println("Número: " + numero);
        System.out.println("Cuadrado: " + cuadrado);
        System.out.println("Cubo: " + cubo);
    }

    // Método principal para probar la clase
    public static void main(String[] args) {
        Numeros n = new Numeros(5);  // Crear una instancia con el número 5
        n.mostrarValores();  // Mostrar los valores calculados
    }
}
