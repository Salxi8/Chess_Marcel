package Modelo

import Controlador.Posicion
import Controlador.Tablero

class Peon(blancaONegra: Boolean, posicion: Posicion, seleccionada: Boolean) : Ficha(blancaONegra, posicion, seleccionada) {
    fun movimientoPeon(tablero : Tablero, reiEncontrado: Rei){
        //var tirarAtras: String
        var posFinalY = -1
        var posFinalX = -1
        // COMPROBEM SI LA FILA Y COLUMNA ON VOLEM MOURE EL PEÓN ESTA DINS DE L ARRAY DEL TABLERO
        // COMPROBEM QUE EL MOVIMENT ES PUGUI REALITZAR A TRAVES DE LA FUNCIÓ: POSIBILIDADESMOVIMIENTOPEON()
        while ((posFinalX < 0 || posFinalX > 7) || (posFinalY < 0 || posFinalY > 7) || !(posibilidadesMovimientoPeon(posFinalY, posFinalX, tablero))) { // PARA EVITAR QUE DE ERROR SI EL USUARIO ESCRIBE UNA CASILLA INVÁLIDA (POR EJEMPLO: P, o -1)

            println("------------------------------------------------------------------")
            print("|SI DESEA ELEJIR OTRA PIEZA PULSE [b] SINO PULSE CUALQUIER TECLA: ")
            val tirarAtras = readln()
            println("------------------------------------------------------------------")
            if (tirarAtras == "b"){
                return
            } // SI DECIDIM TORNAR ENRERE UN COP SELECCIONADA LA FICHA DEIXAREM EL MAPA EXACTAMENT COM ESTABA ABANS D ENTRAR A LA FUNCIÓ

            print("En que FILA quiere mover la REINA [NUMERO]: ")
            val posFinalYAux = readln().toIntOrNull() ?: -1
            print("En que COLUMNA quiere mover la REINA [LETRA]: ")
            val posFinalXAux = readln().singleOrNull() ?: '-'.uppercaseChar()

            posFinalY = convertorLetraNumeroANumero(posFinalYAux, posFinalXAux).first // CONVERTIM EL NUMERO QUE DIGITA EL JUGADOR AL NÚMERO CORRESPONENT A LA MATRIU TABLERO
            posFinalX = convertorLetraNumeroANumero(posFinalYAux, posFinalXAux).second // CONVERTIM LA LLETRA QUE DIGITA EL JUGADOR AL NÚMERO CORRESPONENT A LA MATRIU TABLERO

            if ((posFinalX < 0 || posFinalX > 7) || (posFinalY < 0 || posFinalY > 7)) { // WARNING DE QUE LA FICHA NO SE PUEDE MOVER AHI
                println("La casilla [${posFinalY}, ${posFinalX}] no es una casilla válida dentro del tablero de ajedrez")
            }
            // SI LAS CORDENADAS DEL TABLERO NO SON VÁLIDAS COMPROBAR CON LA FUNCIÓN PRINTAR QUE ESA PIEZA NO PUEDE AVANZAR AHÍ
        }

        val elementoCasillaQueInvadimos = tablero[posFinalY, posFinalX] // GUARDAMOS EL ELEMENTO QUE HABÍA EN LA CASILLA ANTES DE INVADIRLA

        tablero.tablero[posFinalY][posFinalX] = Peon(blancaONegra, Posicion(posFinalY, posFinalX), false) // INVADIMOS LA CASILLA
        tablero.tablero[posicionFicha.fila][posicionFicha.columna] = null // LA CASELLA EN LA QUE ES TROBAVA LA FICHA AL INICI QUEDA NULA

        // SI AMB EL MOVIMENT REALITZAT ES POSA AMB JAQUE AL TEU REI NO PERMETEM EL MOVIMENT I TORNEM TOT COM AL PRINCIPI
        if (tablero.isKingInCheck(reiEncontrado)){
            tablero.tablero[posFinalY][posFinalX] = elementoCasillaQueInvadimos
            return
        }

        // SI NO HI HA JAQUE I EL MOVIMENT ES POSIBLE PERMETEM EL MOVIMENT CAMBIANT LA POSICIÓ DE LA FILA I COLUMNA DE LA FICHA
        posicionFicha.fila = posFinalY
        posicionFicha.columna = posFinalX



        return // NOSE SI CAL RETORNAR LA FICHA AQUÍ
    }

    // RECORDATORI: DINTRE D'AQUESTA FUNCIÓ ANIRÁ LA SUMA DE PUNTS / FALTA PROHIBIR MOVIMIENTO SI CON EL MOVIMIENTO DEL PEÓN PONE EN JAQUE A SU PROPIO REY Y SI LLEGA AL OTRO LADO PODER CAMBIAR POR FICHA QUE QUIERAS
    fun posibilidadesMovimientoPeon(posFinalY: Int, posFinalX: Int, tablero : Tablero): Boolean {

        if (blancaONegra){ // MOVIMIENTO BLANCAS // TENS QUE FER PER SI ESTAN ALS BORDES
            if ((posicionFicha.fila + 1) == posFinalY) {
                if (tablero[posicionFicha.fila + 1, posicionFicha.columna] == null && posicionFicha.columna == posFinalX) {
                    // MOVIMENT UNA CASILLA CAP ENDEVANT: PER AVANÇAR
                    return true

                    // AQUEST MOVIMENT ENS PERMET AVANÇAR UNA CASELLA EN LA MATEIXA COLUMNA SI EL PEÓ NO TE CAP FIGURA DEVANT
                    // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                    // 1. (FILA EN LA QUE ES TROBA LA FICHA AL INICI + 1) == (FILA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 2. SI LA COLUMNA EN LA QUE ES TROBA LA FICHA ES IGUAL TAN INICIALMENT COM AL FINAL
                    // 3. SI LA POSICIÓ DE LA FICHA + 1 FILA NO HI HA CAP PEÇA (JA QUE EL PEÓ NO POT AVANÇAR NI MATAR UNA PEÇA QUE TE DEVANT)

                } else if (posicionFicha.columna != 7 && tablero[posicionFicha.fila + 1, posicionFicha.columna + 1] != null && posicionFicha.columna + 1 == posFinalX && tablero[posicionFicha.fila + 1, posicionFicha.columna + 1]?.blancaONegra == false){
                    // MOVIMENT UNA CASILLA CAP ENDEVANT I CAP A LA DRETA (DIAGONAL): PER MATAR FICHA NEGRA
                    return true

                    // AQUEST MOVIMENT ENS PERMET AVANÇAR UNA CASELLA EN DIAGONAL CAP A LA DRETA PER MATAR UNA FICHA DEL RIVAL
                    // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                    // 1. (FILA EN LA QUE ES TROBA LA FICHA AL INICI + 1) == (FILA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 2. SI LA CASELLA DE LA DIAGONAL CAP A LA DRETA HI HA UNA FICHA
                    // 3. SI (LA COLUMNA EN LA QUE ES TROBA LA FICHA + 1) == (COLUMNA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 4. SI LA FICHA QUE ES TROBA A LA DIAGONAL CAP A LA DRETA ES NEGRE

                } else if (posicionFicha.columna != 0 && tablero[posicionFicha.fila + 1, posicionFicha.columna - 1] /*O FUERA DE RANGO*/ is Ficha && posicionFicha.columna - 1 == posFinalX && tablero[posicionFicha.fila + 1, posicionFicha.columna - 1]?.blancaONegra == false){
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

                } else if (posicionFicha.columna != 7 && tablero[posicionFicha.fila - 1, posicionFicha.columna + 1] is Ficha && posicionFicha.columna + 1 == posFinalX && tablero[posicionFicha.fila - 1, posicionFicha.columna + 1]?.blancaONegra == true){
                    // MOVIMENT UNA CASILLA CAP ENDEVANT I CAP A LA DRETA (DIAGONAL): PER MATAR FICHA BLANCA
                    return true

                    // AQUEST MOVIMENT ENS PERMET AVANÇAR UNA CASELLA EN DIAGONAL CAP A LA DRETA PER MATAR UNA FICHA DEL RIVAL
                    // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                    // 1. (FILA EN LA QUE ES TROBA LA FICHA AL INICI - 1) == (FILA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 2. SI LA CASELLA DE LA DIAGONAL CAP A LA DRETA HI HA UNA FICHA
                    // 3. SI (LA COLUMNA EN LA QUE ES TROBA LA FICHA + 1) == (COLUMNA EN LA QUE ES TROBA LA FICHA AL FINAL)
                    // 4. SI LA FICHA QUE ES TROBA A LA DIAGONAL CAP A LA DRETA ES BLANCA

                } else if (posicionFicha.columna != 0 && tablero[posicionFicha.fila - 1, posicionFicha.columna - 1] is Ficha && posicionFicha.columna - 1 == posFinalX && tablero[posicionFicha.fila - 1, posicionFicha.columna - 1]?.blancaONegra == true){
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


    private fun intercambioPeonCoronacion(posicion: Posicion): Ficha? {

        println("==========================================")
        println("SU PEÓN HA LLEGADO A LA FILA DE CORONACIÓN")
        println("        ¿QUE FICHA DESEA ESCOJER?         ")
        println("A) REINA                                  ")
        println("B) CABALLO                                ")
        println("C) ALFIL                                  ")
        println("D) TORRE                                  ")
        println("==========================================")
        println("ELIJA SU OPCIÓN: ")
        var eleccionPieza = "X"
        while (eleccionPieza != "A" && eleccionPieza != "B" && eleccionPieza != "C" && eleccionPieza != "D") { // SELECCIONAMOS LA FICHA QUE DESEEMOS, SI NO SELECCIONAMOS UNA OPCIÓN
            eleccionPieza = readln().uppercase() // CORRECTA SIGUE PIDIENDO
        }

        return when (eleccionPieza) {
            "A" ->
                Reina(blancaONegra, Posicion(posicion.fila, posicion.columna), false) // RETORNAMOS UNA REINA

            "B" ->
                Caballo(blancaONegra, Posicion(posicion.fila, posicion.columna), false) // RETORNAMOS UN CABALLO

            "C" ->
                Alfil(blancaONegra, Posicion(posicion.fila, posicion.columna), false) // RETORNAMOS UN ALFIL

            "D" ->
                Torre(blancaONegra, Posicion(posicion.fila, posicion.columna), false) // RETORNAMOS UNA TORRE

            else ->
                null // ELSE RETORNAMOS NULL, AÚN ASÍ ES IMPOSIBLE LLEGAR A ESTA POSICIÓN
        }

    }

    fun peonCoronacion(tablero: Tablero){
        // CUÁNDO EL PEÓN LLEGA A LA ÚLTIMA FILA
        if (posicionFicha.fila == 7 && blancaONegra) { // PEON BLANCO
            // INTERCAMBIAMOS POR LA FICHA QUE HEMOS RETORNADO
            tablero.tablero[posicionFicha.fila][posicionFicha.columna] = intercambioPeonCoronacion(Posicion(posicionFicha.fila, posicionFicha.columna))
        }
        else if (posicionFicha.fila == 0 && !blancaONegra){ // PEON NEGRO
            // INTERCAMBIAMOS POR LA FICHA QUE HEMOS RETORNADO
            tablero.tablero[posicionFicha.fila][posicionFicha.columna] = intercambioPeonCoronacion(Posicion(posicionFicha.fila, posicionFicha.columna))
        }
    }



}