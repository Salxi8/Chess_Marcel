import Controlador.Pantallas
import Controlador.Tablero

fun main(){
    val pantalla = Pantallas(Pantallas.Escenas.ENTRADA)
    val tablero = Tablero(true)
    var jaqueMate: Boolean


    do { // BUCLE DE DENTRO DEL JUEGO

        if (pantalla.escenas == Pantallas.Escenas.ENTRADA) { // ESCENA ENTRADA

            pantalla.escenaEntrada()
            pantalla.escenas = Pantallas.Escenas.MENU
        }
        else if (pantalla.escenas == Pantallas.Escenas.MENU){ // ESCENA MENU

            when (pantalla.escenaMenu()) {
                1 -> {
                    pantalla.escenas = Pantallas.Escenas.INGAME
                }
                2 -> {
                    pantalla.escenas = Pantallas.Escenas.EXIT
                }
            }

        }
        if (pantalla.escenas == Pantallas.Escenas.INGAME) {
            tablero.printarTabero()

            jaqueMate = tablero.actualizarTablero()


            /*for (elem in tablero.tablero)
                println(elem)*/

            tablero.turnoBlancoONegro = !tablero.turnoBlancoONegro // CAMBIAR TURNO

            if (jaqueMate){
                pantalla.escenas = Pantallas.Escenas.EXIT
            }
        }
    }while(pantalla.escenas != Pantallas.Escenas.EXIT)
    println("=============================")
    println("¡ESPERAMOS QUE VUELVA PRONTO!")
    println("=============================")

}