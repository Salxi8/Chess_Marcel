class Peon(blanca_o_negra: Boolean, posicion: Posicion, seleccionada: Boolean)
    : Ficha(blanca_o_negra, posicion, seleccionada) {


    fun movimientoPeon(tablero : Tablero, reiEncontrado: Rei){
        //var tirarAtras: String
        var posFinalY = -1
        var posFinalX = -1
        // COMPROBEM SI LA FILA Y COLUMNA ON VOLEM MOURE EL PEÓN ESTA DINS DE L ARRAY DEL TABLERO
        // COMPROBEM QUE EL MOVIMENT ES PUGUI REALITZAR A TRAVES DE LA FUNCIÓ: POSIBILIDADESMOVIMIENTOPEON()
        while ((posFinalX < 0 || posFinalX > 7) || (posFinalY < 0 || posFinalY > 7) || !(posibilidadesMovimientoPeon(posFinalY, posFinalX, tablero))) { // PARA EVITAR QUE DE ERROR SI EL USUARIO ESCRIBE UNA CASILLA INVÁLIDA (POR EJEMPLO: P, o -1)

            // PARA PODER TIRAR ATRAS
            println("------------------------------------------------------------------")
            print("|SI DESEA ELEJIR OTRA PIEZA PULSE [b] SINO PULSE CUALQUIER TECLA: ")
            val tirarAtras = readln()
            println("------------------------------------------------------------------")
            if (tirarAtras == "b"){
                println(posicionFicha.fila)
                println(posicionFicha.columna)
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

        val auxPeonJaque = Peon(blanca_o_negra, Posicion(posFinalY, posFinalX), false)
        tablero.tablero[posFinalY][posFinalX] = auxPeonJaque
        tablero.tablero[posicionFicha.fila][posicionFicha.columna] = null

        // PER EL JAQUE
        if (tablero.isKingInCheck(reiEncontrado)){

            tablero.tablero[posFinalY][posFinalX] = elementoCasillaQueInvadimos
            return

        }

        posicionFicha.fila = posFinalY
        posicionFicha.columna = posFinalX



        return // NOSE SI CAL RETORNAR LA FICHA AQUÍ
    }

    // RECORDATORI: DINTRE D'AQUESTA FUNCIÓ ANIRÁ LA SUMA DE PUNTS / FALTA PROHIBIR MOVIMIENTO SI CON EL MOVIMIENTO DEL PEÓN PONE EN JAQUE A SU PROPIO REY Y SI LLEGA AL OTRO LADO PODER CAMBIAR POR FICHA QUE QUIERAS
    fun posibilidadesMovimientoPeon(posFinalY: Int, posFinalX: Int, tablero : Tablero): Boolean {

        if (blanca_o_negra){ // MOVIMIENTO BLANCAS // TENS QUE FER PER SI ESTAN ALS BORDES
            if ((posicionFicha.fila + 1) == posFinalY) {
                if (tablero[posicionFicha.fila + 1, posicionFicha.columna] == null && posicionFicha.columna == posFinalX) {
                    // MOVIMENT UNA CASILLA CAP ENDEVANT: PER AVANÇAR
                    return true

                    // AQUEST MOVIMENT ENS PERMET AVANÇAR UNA CASELLA EN LA MATEIXA COLUMNA SI EL PEÓ NO TE CAP FIGURA DEVANT
                    // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                    // 1. (FILA EN LA QUE ES TROBA LA FICHA AL INICI + 1) == (FILA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 2. SI LA COLUMNA EN LA QUE ES TROBA LA FICHA ES IGUAL TAN INICIALMENT COM AL FINAL
                    // 3. SI LA POSICIÓ DE LA FICHA + 1 FILA NO HI HA CAP PEÇA (JA QUE EL PEÓ NO POT AVANÇAR NI MATAR UNA PEÇA QUE TE DEVANT)

                } else if (posicionFicha.columna != 7 && tablero[posicionFicha.fila + 1, posicionFicha.columna + 1] != null && posicionFicha.columna + 1 == posFinalX && tablero[posicionFicha.fila + 1, posicionFicha.columna + 1]?.blanca_o_negra == false){
                    // MOVIMENT UNA CASILLA CAP ENDEVANT I CAP A LA DRETA (DIAGONAL): PER MATAR FICHA NEGRA
                    return true

                    // AQUEST MOVIMENT ENS PERMET AVANÇAR UNA CASELLA EN DIAGONAL CAP A LA DRETA PER MATAR UNA FICHA DEL RIVAL
                    // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                    // 1. (FILA EN LA QUE ES TROBA LA FICHA AL INICI + 1) == (FILA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 2. SI LA CASELLA DE LA DIAGONAL CAP A LA DRETA HI HA UNA FICHA
                    // 3. SI (LA COLUMNA EN LA QUE ES TROBA LA FICHA + 1) == (COLUMNA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 4. SI LA FICHA QUE ES TROBA A LA DIAGONAL CAP A LA DRETA ES NEGRE

                } else if (posicionFicha.columna != 0 && tablero[posicionFicha.fila + 1, posicionFicha.columna - 1] /*O FUERA DE RANGO*/ is Ficha  && posicionFicha.columna - 1 == posFinalX && tablero[posicionFicha.fila + 1, posicionFicha.columna - 1]?.blanca_o_negra == false){
                    // MOVIMENT UNA CASILLA CAP ENDEVANT I CAP A L'ESQUERRA (DIAGONAL): PER MATAR FICHA NEGRA
                    return true

                    // AQUEST MOVIMENT ENS PERMET AVANÇAR UNA CASELLA EN DIAGONAL CAP A L'ESQUERRA PER MATAR UNA FICHA DEL RIVAL
                    // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                    // 1. QUE LA COLUMNA EN LA QUE ES TROBA LA FICHA NO SIGUI = 0 YA QUE SI TIRESIM A L'ESQUERRA SERÍA -1
                    // 2. (FILA EN LA QUE ES TROBA LA FICHA AL INICI + 1) == (FILA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 3. SI LA CASELLA DE LA DIAGONAL CAP A L'ESQUERRA HI HA UNA FICHA
                    // 4. SI (LA COLUMNA EN LA QUE ES TROBA LA FICHA - 1) == (COLUMNA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 5. SI LA FICHA QUE ES TROBA A LA DIAGONAL CAP A L'ESQUERRA ES NEGRE
                }

            } else if ((posicionFicha.fila + 2) == posFinalY && posicionFicha.fila == 1 && posicionFicha.columna == posFinalX && tablero[posicionFicha.fila + 2, posicionFicha.columna] !is Ficha && tablero[posicionFicha.fila + 1, posicionFicha.columna] !is Ficha) {
                // MOVIMENT DOS CASILLES CAP ENDEVANT: PER AVANÇAR
                return true

                // AQUEST MOVIMENT ENS PERMET AVANÇAR DOS CASELLA EN LA MATEIXA COLUMNA SI EL PEÓ ES TROBA A LA POSICIÓ INICIAL I A MÉS NO TE CAP FIGURA DEVANT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. (FILA EN LA QUE ES TROBA LA FICHA AL INICI + 2) == (FILA EN LA QUE ES TROBA LA FICHA AL FINAL)
                // 2. SI LA COLUMNA EN LA QUE ES TROBA LA FICHA ES IGUAL TAN INICIALMENT COM AL FINAL
                // 3. SI LA POSICIÓ DE LA FICHA + 1 FILA NO HI HA CAP PEÇA (JA QUE EL PEÓ NO POT AVANÇAR NI MATAR UNA PEÇA QUE TE DEVANT)
            }
        }
        else{ // MOVIMIENTO NEGRAS
            if ((posicionFicha.fila - 1) == posFinalY) {
                if (tablero[posicionFicha.fila - 1, posicionFicha.columna] !is Ficha && posicionFicha.columna == posFinalX) {
                    // MOVIMENT UNA CASILLA CAP ENDEVANT: PER AVANÇAR
                    return true

                    // AQUEST MOVIMENT ENS PERMET AVANÇAR UNA CASELLA EN LA MATEIXA COLUMNA SI EL PEÓ NO TE CAP FIGURA DEVANT
                    // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                    // 1. (FILA EN LA QUE ES TROBA LA FICHA AL INICI - 1) == (FILA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 2. SI LA COLUMNA EN LA QUE ES TROBA LA FICHA ES IGUAL TAN INICIALMENT COM AL FINAL
                    // 3. SI LA POSICIÓ DE LA FICHA - 2 FILA NO HI HA CAP PEÇA (JA QUE EL PEÓ NO POT AVANÇAR NI MATAR UNA PEÇA QUE TE DEVANT)

                } else if (posicionFicha.columna != 7 && tablero[posicionFicha.fila - 1, posicionFicha.columna + 1] is Ficha && posicionFicha.columna + 1 == posFinalX && tablero[posicionFicha.fila - 1, posicionFicha.columna + 1]?.blanca_o_negra == true){
                    // MOVIMENT UNA CASILLA CAP ENDEVANT I CAP A LA DRETA (DIAGONAL): PER MATAR FICHA BLANCA
                    return true

                    // AQUEST MOVIMENT ENS PERMET AVANÇAR UNA CASELLA EN DIAGONAL CAP A LA DRETA PER MATAR UNA FICHA DEL RIVAL
                    // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                    // 1. (FILA EN LA QUE ES TROBA LA FICHA AL INICI - 1) == (FILA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 2. SI LA CASELLA DE LA DIAGONAL CAP A LA DRETA HI HA UNA FICHA
                    // 3. SI (LA COLUMNA EN LA QUE ES TROBA LA FICHA + 1) == (COLUMNA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 4. SI LA FICHA QUE ES TROBA A LA DIAGONAL CAP A LA DRETA ES BLANCA

                } else if (posicionFicha.columna != 0 && tablero[posicionFicha.fila - 1, posicionFicha.columna - 1] is Ficha && posicionFicha.columna - 1 == posFinalX && tablero[posicionFicha.fila - 1, posicionFicha.columna - 1]?.blanca_o_negra == true){
                    // MOVIMENT UNA CASILLA CAP ENDEVANT I CAP A L'ESQUERRA (DIAGONAL): PER MATAR FICHA BLANCA
                    return true

                    // AQUEST MOVIMENT ENS PERMET AVANÇAR UNA CASELLA EN DIAGONAL CAP A L'ESQUERRA PER MATAR UNA FICHA DEL RIVAL
                    // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                    // 1. (FILA EN LA QUE ES TROBA LA FICHA AL INICI - 1) == (FILA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 2. SI LA CASELLA DE LA DIAGONAL CAP A L'ESQUERRA HI HA UNA FICHA
                    // 3. SI (LA COLUMNA EN LA QUE ES TROBA LA FICHA - 1) == (COLUMNA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 4. SI LA FICHA QUE ES TROBA A LA DIAGONAL CAP A L'ESQUERRA ES BLANCA
                }
            } else if ((posicionFicha.fila - 2) == posFinalY && posicionFicha.fila == 6 && posicionFicha.columna == posFinalX && tablero[posicionFicha.fila - 2, posicionFicha.columna] !is Ficha && tablero[posicionFicha.fila - 1, posicionFicha.columna] !is Ficha) {
                // MOVIMENT DOS CASILLES CAP ENDEVANT: PER AVANÇAR
                return true

                // AQUEST MOVIMENT ENS PERMET AVANÇAR DOS CASELLA EN LA MATEIXA COLUMNA SI EL PEÓ ES TROBA A LA POSICIÓ INICIAL I A MÉS NO TE CAP FIGURA DEVANT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. (FILA EN LA QUE ES TROBA LA FICHA AL INICI - 2) == (FILA EN LA QUE ES TROBA LA FICHA AL FINAL)
                // 2. SI LA COLUMNA EN LA QUE ES TROBA LA FICHA ES IGUAL TAN INICIALMENT COM AL FINAL
                // 3. SI LA POSICIÓ DE LA FICHA - 2 FILA NO HI HA CAP PEÇA (JA QUE EL PEÓ NO POT AVANÇAR NI MATAR UNA PEÇA QUE TE DEVANT)
            }
        }
        return false
    }


    fun intercambioPeonCoronacion(posicion: Posicion): Ficha? {

        println("==========================================") // ESTO LO PONGO MEJOR EN OTRA FUNCION?
        println("SU PEÓN HA LLEGADO A LA FILA DE CORONACIÓN")
        println("        ¿QUE FICHA DESEA ESCOJER?         ")
        println("A) REINA                                  ")
        println("B) CABALLO                                ")
        println("C) ALFIL                                  ")
        println("D) TORRE                                  ")
        println("==========================================")
        println("ELIJA SU OPCIÓN: ")
        var eleccionPieza = "X"
        while (eleccionPieza != "A" && eleccionPieza != "B" && eleccionPieza != "C" && eleccionPieza != "D") {
            eleccionPieza = readln().uppercase()
        }

        return when (eleccionPieza) {
            "A" ->
                Reina(blanca_o_negra, Posicion(posicion.fila, posicion.columna), false)

            "B" ->
                Caballo(blanca_o_negra, Posicion(posicion.fila, posicion.columna), false)

            "C" ->
                Alfil(blanca_o_negra, Posicion(posicion.fila, posicion.columna), false)

            "D" ->
                Torre(blanca_o_negra, Posicion(posicion.fila, posicion.columna), false)

            else ->
                null
        }

    }

    fun peonCoronacion(tablero: Tablero){
        // CUÁNDO EL PEÓN LLEGA A LA ÚLTIMA FILA
        if (posicionFicha.fila == 7 && blanca_o_negra) { // PEON BLANCO
            tablero.tablero[posicionFicha.fila][posicionFicha.columna] = intercambioPeonCoronacion(Posicion(posicionFicha.fila, posicionFicha.columna))
        }
        else if (posicionFicha.fila == 0 && !blanca_o_negra){ // PEON NEGRO
            tablero.tablero[posicionFicha.fila][posicionFicha.columna] = intercambioPeonCoronacion(Posicion(posicionFicha.fila, posicionFicha.columna))
        }
    }



}