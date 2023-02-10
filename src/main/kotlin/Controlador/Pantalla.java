package Controlador;

import java.util.Scanner;

public class Pantalla { // CLASSE FETA AMB JAVA

    public enum Escenas {ENTRADA, MENU, INGAME, EXIT} // ENUM CLASS DE LES DIFERENTS ESCENES
    public Escenas escenas;

    public Pantalla(){
        escenas = Escenas.ENTRADA;
    }

    public void escenaEntrada() { // PRINT ESCENA ENTRADA Y READLN PAR PODER AVANÇAR SEGUENT ESCENA
        Scanner input = new Scanner(System.in);
        String nextEscena;
        do {
            System.out.println("===================================");
            System.out.println("|¡BIENVENIDO AL JUEGO DEL AJEDREZ!|");
            System.out.println("===================================");
            System.out.print("ESCRIBA 'C' PARA CONTINUAR: ");
            nextEscena = input.nextLine();
        } while (!nextEscena.equalsIgnoreCase("c"));
    }

    public int escenaMenu() { // PRINT ESCENA MENU Y READLN PAR PODER AVANÇAR SEGUENT ESCENA
        Scanner input = new Scanner(System.in);
        int nextEscena;
        do {
            System.out.println("====================================");
            System.out.println("|  ¡BIENVENIDO AL MENÚ PRINCIPAL!  |");
            System.out.println("|----------------------------------|");
            System.out.println("| OPCIONES:                        |");
            System.out.println("| 1. JUGAR PARTIDA DE AJEDREZ      |");
            System.out.println("| 2. SALIR DEL JUEGO               |");
            System.out.println("====================================");
            System.out.print("DIGITE EL NÚMERO QUE DESEE:  ");
            try {
                nextEscena = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                nextEscena = 0;
            }
        } while (nextEscena != 1 && nextEscena != 2);
        return nextEscena;
    }
}
