fun main(){

    var turnoBlancoNegro: Boolean = true
    val tablero = Tablero()
    var contador = 0;


    do {
        tablero.printarTabero(turnoBlancoNegro)

        tablero.actualizarTablero(turnoBlancoNegro)

        for (elem in tablero.tablero)
            println(elem)


        turnoBlancoNegro = !turnoBlancoNegro // CAMBIAR TURNO
        contador++ // POR AHORA

        // COMO LIMPIO PANTALLA?
    }while(contador < 10)

}