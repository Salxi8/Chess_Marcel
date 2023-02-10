package Controlador

class Pantallas(var escenas: Escenas) {
    enum class Escenas {ENTRADA, MENU, INGAME, EXIT} // ENUM CLASS DE LES DIFERENTS ESCENES

    fun escenaEntrada(){ // PRINT ESCENA ENTRADA Y READLN PAR PODER AVANÇAR SEGUENT ESCENA
        do {
            println("===================================")
            println("|¡BIENVENIDO AL JUEGO DEL AJEDREZ!|")
            println("===================================")
            print("ESCRIBA 'C' PARA CONTINUAR: ")
            val nextEscene = readln()

        }while (nextEscene != "c" && nextEscene != "C")

    }

    fun escenaMenu(): Int{ // PRINT ESCENA MENU Y READLN PAR PODER AVANÇAR SEGUENT ESCENA
        var nextEscene: Int
        do {
            println("====================================")
            println("|  ¡BIENVENIDO AL MENÚ PRINCIPAL!  |")
            println("|----------------------------------|")
            println("| OPCIONES:                        |")
            println("| 1. JUGAR PARTIDA DE AJEDREZ      |")
            println("| 2. SALIR DEL JUEGO               |")
            println("====================================")
            print("DIGITE EL NÚMERO QUE DESEE:  ")
            nextEscene = readln().toIntOrNull()?:0

        }while (nextEscene != 1 && nextEscene != 2)

        return nextEscene
    }

}