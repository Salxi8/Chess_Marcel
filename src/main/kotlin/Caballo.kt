class Caballo(blanca_o_negra: Boolean, posicion: Posicion, seleccionada: Boolean)
    : Ficha(blanca_o_negra, posicion, seleccionada) {

    fun movimientoCaballo(tablero : Tablero, reiEncontrado: Rei){
        var posFinalY = -1
        var posFinalX = -1
        // COMPROBEM SI LA FILA Y COLUMNA ON VOLEM MOURE EL CABALL ESTA DINS DE L ARRAY DEL TABLERO
        // COMPROBEM QUE EL MOVIMENT ES PUGUI REALITZAR A TRAVES DE LA FUNCIÓ: POSIBILIDADESMOVIMIENTOCABALLO()
        while ((posFinalX < 0 || posFinalX > 7) || (posFinalY < 0 || posFinalY > 7) || !(posibilidadesMovimientoCaballo(posFinalY, posFinalX, tablero))) { // PARA EVITAR QUE DE ERROR SI EL USUARIO ESCRIBE UNA CASILLA INVÁLIDA (POR EJEMPLO: P, o -1)

            println("------------------------------------------------------------------")
            print("|SI DESEA ELEJIR OTRA PIEZA PULSE [b] SINO PULSE CUALQUIER TECLA: ")
            val tirarAtras = readln()
            println("------------------------------------------------------------------")
            if (tirarAtras == "b"){
                return
            }

            print("En que FILA quiere mover la REINA [NUMERO]: ")
            val posFinalYAux = readln().toIntOrNull() ?: -1
            print("En que COLUMNA quiere mover la REINA [LETRA]: ")
            val posFinalXAux = readln().singleOrNull() ?: '-'.uppercaseChar()

            posFinalY = convertorLetraNumeroANumero(posFinalYAux, posFinalXAux).first
            posFinalX = convertorLetraNumeroANumero(posFinalYAux, posFinalXAux).second

            if ((posFinalX < 0 || posFinalX > 7) || (posFinalY < 0 || posFinalY > 7)) { // WARNING DE QUE LA FICHA NO SE PUEDE MOVER AHI
                println("La casilla [${posFinalY}, ${posFinalX}] no es una casilla válida dentro del tablero de ajedrez")
            }
            // SI LAS CORDENADAS DEL TABLERO NO SON VÁLIDAS COMPROBAR CON LA FUNCIÓN PRINTAR QUE ESA PIEZA NO PUEDE AVANZAR AHÍ
        }

        val elementoCasillaQueInvadimos = tablero[posFinalY, posFinalX]

        val auxCaballoJaque = Caballo(blanca_o_negra, Posicion(posFinalY, posFinalX), false)
        tablero.tablero[posFinalY][posFinalX] = auxCaballoJaque
        tablero.tablero[posicionFicha.fila][posicionFicha.columna] = null

        // PER EL JAQUE
        if (tablero.isKingInCheck(reiEncontrado)){

            tablero.tablero[posFinalY][posFinalX] = elementoCasillaQueInvadimos
            return

        }

        posicionFicha.fila = posFinalY
        posicionFicha.columna = posFinalX

        return
    }

    fun posibilidadesMovimientoCaballo(posFinalY: Int, posFinalX: Int,  tablero : Tablero): Boolean {

        if(blanca_o_negra){ // CABALLO BLANCO
            if ((tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null) && ((posicionFicha.fila -1 == posFinalY && posicionFicha.columna - 2 == posFinalX) || (posicionFicha.fila - 2 == posFinalY && posicionFicha.columna - 1 == posFinalX) || (posicionFicha.fila - 2 == posFinalY && posicionFicha.columna + 1 == posFinalX) || (posicionFicha.fila - 1 == posFinalY && posicionFicha.columna + 2 == posFinalX) || (posicionFicha.fila + 1 == posFinalY && posicionFicha.columna + 2 == posFinalX) || (posicionFicha.fila + 2 == posFinalY && posicionFicha.columna + 1 == posFinalX) || (posicionFicha.fila + 2 == posFinalY && posicionFicha.columna - 1 == posFinalX) || (posicionFicha.fila + 1 == posFinalY && posicionFicha.columna - 2 == posFinalX)))
                return true
        }
        else{ // CABALLO NEGRO
            if ((tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null) && ((posicionFicha.fila -1 == posFinalY && posicionFicha.columna - 2 == posFinalX) || (posicionFicha.fila - 2 == posFinalY && posicionFicha.columna - 1 == posFinalX) || (posicionFicha.fila - 2 == posFinalY && posicionFicha.columna + 1 == posFinalX) || (posicionFicha.fila - 1 == posFinalY && posicionFicha.columna + 2 == posFinalX) || (posicionFicha.fila + 1 == posFinalY && posicionFicha.columna + 2 == posFinalX) || (posicionFicha.fila + 2 == posFinalY && posicionFicha.columna + 1 == posFinalX) || (posicionFicha.fila + 2 == posFinalY && posicionFicha.columna - 1 == posFinalX) || (posicionFicha.fila + 1 == posFinalY && posicionFicha.columna - 2 == posFinalX)))
                return true
        }
        return false
    }

}