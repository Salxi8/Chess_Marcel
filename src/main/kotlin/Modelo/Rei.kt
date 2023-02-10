package Modelo

import Controlador.Posicion
import Controlador.Tablero

class Rei(blancaONegra: Boolean, posicion: Posicion, seleccionada: Boolean) : Ficha(blancaONegra, posicion, seleccionada) {
    fun movimientoRei(tablero: Tablero){
        var posFinalY = -1
        var posFinalX = -1
        // COMPROBEM SI LA FILA Y COLUMNA ON VOLEM MOURE EL REI ESTA DINS DE L ARRAY DEL TABLERO
        // COMPROBEM QUE EL MOVIMENT ES PUGUI REALITZAR A TRAVES DE LA FUNCIÓ: POSIBILIDADESMOVIMIENTOREI()
        while ((posFinalX < 0 || posFinalX > 7) || (posFinalY < 0 || posFinalY > 7) || !(posibilidadesMovimientoRei(posFinalY, posFinalX, tablero))) { // PARA EVITAR QUE DE ERROR SI EL USUARIO ESCRIBE UNA CASILLA INVÁLIDA (POR EJEMPLO: P, o -1)
            // JAQUE ES IGUAL A FALSE
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
        // PARA PODER HACER QUE HAYA JAQUE YA QUE UN PEON PODRÍA ADELANTAR 2C YA QUE EL MOVIMIENTO DEL REI AUN NO SE HA ECHO
        val auxReiComJaque = Rei(blancaONegra, Posicion(posFinalY, posFinalX), false)
        tablero.tablero[posFinalY][posFinalX] = auxReiComJaque
        tablero.tablero[posicionFicha.fila][posicionFicha.columna] = null

        if (isKingGoingToBeInCheck(posFinalY, posFinalX, tablero) || tablero.isKingInCheck(auxReiComJaque)){
            tablero.tablero[posFinalY][posFinalX] = elementoCasillaQueInvadimos
            return
        }

        posicionFicha.fila = posFinalY
        posicionFicha.columna = posFinalX

        return
    }

    fun posibilidadesMovimientoRei(posFinalY: Int, posFinalX: Int, tablero: Tablero): Boolean {
        if (blancaONegra) { // REI BLANCO
            if (posFinalX == posicionFicha.columna && posFinalY == posicionFicha.fila + 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT VERTICAL I CAP ABAIX DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR EL REI EN VERTICAL DE 0 --> 1 ...
                // !!!!!!!!!!!!!!!!!!!!!!!!
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            } else if (posFinalX == posicionFicha.columna && posFinalY == posicionFicha.fila - 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT VERTICAL I CAP ADALT DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN VERTICAL DE 7 --> 0
                // !!!!!!!!!!!!!!!!!!!!!!!!
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            } else if (posFinalY == posicionFicha.fila && posFinalX == posicionFicha.columna + 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT HORITZONTAL I CAP A LA DRETA DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN HORITZONTAL DE 0 --> 7
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            } else if (posFinalY == posicionFicha.fila && posFinalX == posicionFicha.columna - 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT HORITZONTAL I CAP A L'ESQUERRA DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN HORITZONTAL DE 7 --> 0
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            } else if (posFinalY == posicionFicha.fila - 1 && posFinalX == posicionFicha.columna - 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL AMUNT ESQUERRA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A L'ESQUERRA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA

            } else if (posFinalY == posicionFicha.fila - 1 && posFinalX == posicionFicha.columna + 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL AMUNT DRETA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA

            } else if (posFinalY == posicionFicha.fila + 1 && posFinalX == posicionFicha.columna + 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL ABAIX DRETA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            } else if (posFinalY == posicionFicha.fila + 1 && posFinalX == posicionFicha.columna - 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL ABAIX ESQUERRA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }
        } else {
            // REI NEGRO
            if (posFinalX == posicionFicha.columna && posFinalY == posicionFicha.fila + 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT VERTICAL I CAP ABAIX DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR EL REI EN VERTICAL DE 0 --> 1 ...
                // !!!!!!!!!!!!!!!!!!!!!!!!
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            } else if (posFinalX == posicionFicha.columna && posFinalY == posicionFicha.fila - 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT VERTICAL I CAP ADALT DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN VERTICAL DE 7 --> 0
                // !!!!!!!!!!!!!!!!!!!!!!!!
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            } else if (posFinalY == posicionFicha.fila && posFinalX == posicionFicha.columna + 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT HORITZONTAL I CAP A LA DRETA DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN HORITZONTAL DE 0 --> 7
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            } else if (posFinalY == posicionFicha.fila && posFinalX == posicionFicha.columna - 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT HORITZONTAL I CAP A L'ESQUERRA DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN HORITZONTAL DE 7 --> 0
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            } else if (posFinalY == posicionFicha.fila - 1 && posFinalX == posicionFicha.columna - 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL AMUNT ESQUERRA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A L'ESQUERRA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA

            } else if (posFinalY == posicionFicha.fila - 1 && posFinalX == posicionFicha.columna + 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL AMUNT DRETA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA

            } else if (posFinalY == posicionFicha.fila + 1 && posFinalX == posicionFicha.columna + 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL ABAIX DRETA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            } else if (posFinalY == posicionFicha.fila + 1 && posFinalX == posicionFicha.columna - 1 && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL ABAIX ESQUERRA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            }
        }
        return false
    }


    fun buscarPosicionRei(tablero: Tablero): Rei { // SERVIRÁ PARA RETORNAR LA POSICIÓN DEL REI PARA LUEGO PODER SABER SI ESTÁ EN JAQUE O NO

        var posYRei = 0
        var posXRei = 0
        if (tablero.turnoBlancoONegro) {
            for (i in 0 until tablero.numFilas) {
                for (j in 0 until tablero.numColumnas) {
                    if (tablero.tablero[i][j] is Rei && tablero.tablero[i][j]?.blancaONegra == true) {
                        posYRei = i
                        posXRei = j
                    }
                }
            }
        } else {
            for (i in 0 until tablero.numFilas) {
                for (j in 0 until tablero.numColumnas) {
                    if (tablero.tablero[i][j] is Rei && tablero.tablero[i][j]?.blancaONegra == false) {
                        posYRei = i
                        posXRei = j
                    }
                }
            }
        }

        return Rei(tablero.turnoBlancoONegro, Posicion(posYRei, posXRei), false)
    }


    fun printarJaque(tablero: Tablero){
        if (tablero.turnoBlancoONegro && tablero.isKingInCheck(buscarPosicionRei(tablero))){

            println("\u001B[41m\u001B[30m\u001B[4m[¡JAQUE AL REY BLANCO!]\u001B[0m ")

        }else if(!tablero.turnoBlancoONegro && tablero.isKingInCheck(buscarPosicionRei(tablero))){
            println("\u001B[41m\u001B[30m\u001B[4m[¡JAQUE AL REI NEGRO!]\u001B[0m ")
        }
    }

    fun jaqueMate(tablero: Tablero): Boolean {
        val movimientosPosiblesDeLasPiezas = tablero.retornarMovimientosPosiblesTodasLasPiezas(piezasQueQuedanEnElTablero(tablero), this)

        if (movimientosPosiblesDeLasPiezas.isEmpty() && tablero.turnoBlancoONegro && tablero.isKingInCheck(this)) {
            println("\u001B[42m\u001B[30m\u001B[4m[¡JAQUE MATE A LAS BLANCAS!]\u001B[0m ")
            return true
        } else if (movimientosPosiblesDeLasPiezas.isEmpty() && !tablero.turnoBlancoONegro && tablero.isKingInCheck(this)) {
            println("\u001B[42m\u001B[30m\u001B[4m[¡JAQUE MATE A LAS NEGRAS!]\u001B[0m ")
            return true
        }
        return false
    }

    fun ahogado(tablero: Tablero): Boolean {
        val movimientosPosiblesDeLasPiezas = tablero.retornarMovimientosPosiblesTodasLasPiezas(piezasQueQuedanEnElTablero(tablero), this)

        if (movimientosPosiblesDeLasPiezas.isEmpty() && tablero.turnoBlancoONegro && !tablero.isKingInCheck(this)) {
            println("\u001B[44m\u001B[30m\u001B[4m[¡REI BLANCO AHOGADO!]\u001B[0m ")
            return true
        } else if (movimientosPosiblesDeLasPiezas.isEmpty() && !tablero.turnoBlancoONegro && !tablero.isKingInCheck(this)) {
            println("\u001B[44m\u001B[30m\u001B[4m[¡REI NEGRO AHOGADO!]\u001B[0m ")
            return true
        }
        return false
    }

    // ES POT SIMPLIFICAR EL BUCLE
    private fun isKingGoingToBeInCheck(movimentYFinal: Int, movimentXFinal: Int, tablero: Tablero): Boolean { // PER SI EL MOVIMENT QUE FARÁ EL REI NO ES POSIBLE PQ ES JAQUE
        if (tablero.turnoBlancoONegro) { // CUANDO AL REI BLANCO HACE EL MOVIMIENTO COMPROBAR QUE EN LA POSICIÓN QUE VA NO LO HACEN JAQUE
            for (i in 0 until tablero.numFilas) { // RECORRO ALL EL TABLERO
                for (j in 0 until tablero.numColumnas) {
                    val ficha = tablero.tablero[i][j]
                    if (ficha != null) { // SI LA POSICIÓN DEL TABLERO NO ESTA NULA NI HAY UN REI (COMPROBAR PQ CREO QUE EVITO EL JAQUE DEL OTRO REI)
                        if (ficha is Peon && !ficha.blancaONegra && ficha.posibilidadesMovimientoPeon(movimentYFinal, movimentXFinal, tablero)) {

                            return true // JAQUE
                        } else if (ficha is Torre && !ficha.blancaONegra && ficha.posibilidadesMovimientoTorre(movimentYFinal, movimentXFinal, tablero)) {

                            return true
                        } else if (ficha is Alfil && !ficha.blancaONegra && ficha.posibilidadesMovimientoAlfil(movimentYFinal, movimentXFinal, tablero)) {

                            return true
                        } else if (ficha is Caballo && !ficha.blancaONegra && ficha.posibilidadesMovimientoCaballo(movimentYFinal, movimentXFinal, tablero)) {

                            return true
                        } else if (ficha is Reina && !ficha.blancaONegra && ficha.posibilidadesMovimientoReina(movimentYFinal, movimentXFinal, tablero)) {

                            return true
                        } else if (ficha is Rei && !ficha.blancaONegra && ficha.posibilidadesMovimientoRei(movimentYFinal, movimentXFinal, tablero)) {

                            return true
                        }
                    }
                }
            }
        } else {
            for (i in 0 until tablero.numFilas) { // RECORRO ALL EL TABLERO
                for (j in 0 until tablero.numColumnas) {
                    val ficha = tablero.tablero[i][j]
                    if (ficha != null) { // SI LA POSICIÓN DEL TABLERO NO ESTA NULA NI HAY UN REI (COMPROBAR PQ CREO QUE EVITO EL JAQUE DEL OTRO REI)
                        if (ficha is Peon && ficha.blancaONegra && ficha.posibilidadesMovimientoPeon(movimentYFinal, movimentXFinal, tablero)) {

                            return true
                        } else if (ficha is Torre && ficha.blancaONegra && ficha.posibilidadesMovimientoTorre(movimentYFinal, movimentXFinal, tablero)) {

                            return true
                        } else if (ficha is Alfil && ficha.blancaONegra && ficha.posibilidadesMovimientoAlfil(movimentYFinal, movimentXFinal, tablero)) {

                            return true
                        } else if (ficha is Caballo && ficha.blancaONegra && ficha.posibilidadesMovimientoCaballo(movimentYFinal, movimentXFinal, tablero)) {

                            return true
                        } else if (ficha is Reina && ficha.blancaONegra && ficha.posibilidadesMovimientoReina(movimentYFinal, movimentXFinal, tablero)) {

                            return true
                        } else if (ficha is Rei && ficha.blancaONegra && ficha.posibilidadesMovimientoRei(movimentYFinal, movimentXFinal, tablero)) {

                            return true
                        }
                    }
                }
            }
        }

        return false // NO JAQUE
    }


}