import Controlador.Pantalla
import Controlador.Tablero


fun main(){
    val pantalla = Pantalla()
    val tablero = Tablero(true)
    var jaqueMate: Boolean


    do { // BUCLE DE DENTRO DEL JUEGO

        if (pantalla.escenas == Pantalla.Escenas.ENTRADA) { // ESCENA ENTRADA

            pantalla.escenaEntrada()
            pantalla.escenas = Pantalla.Escenas.MENU
        }
        else if (pantalla.escenas == Pantalla.Escenas.MENU){ // ESCENA MENU

            when (pantalla.escenaMenu()) {
                1 -> {
                    pantalla.escenas = Pantalla.Escenas.INGAME
                }
                2 -> {
                    pantalla.escenas = Pantalla.Escenas.EXIT
                }
            }

        }
        if (pantalla.escenas == Pantalla.Escenas.INGAME) {
            tablero.printarTabero()

            jaqueMate = tablero.actualizarTablero()


            /*for (elem in tablero.tablero)
                println(elem)*/

            tablero.turnoBlancoONegro = !tablero.turnoBlancoONegro // CAMBIAR TURNO

            if (jaqueMate){
                pantalla.escenas = Pantalla.Escenas.EXIT
            }
        }
    }while(pantalla.escenas != Pantalla.Escenas.EXIT)
    println("=============================")
    println("¡ESPERAMOS QUE VUELVA PRONTO!")
    println("=============================")

}