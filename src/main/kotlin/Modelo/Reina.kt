package Modelo

import Controlador.Posicion
import Controlador.Tablero
import kotlin.math.absoluteValue

class Reina(blancaONegra: Boolean, posicion: Posicion, seleccionada: Boolean) : Ficha(blancaONegra, posicion, seleccionada) {
    fun movimientoReina(tablero : Tablero, reiEncontrado: Rei){
        var posFinalY = -1
        var posFinalX = -1
        // COMPROBEM SI LA FILA Y COLUMNA ON VOLEM MOURE LA REINA ESTA DINS DE L ARRAY DEL TABLERO
        // COMPROBEM QUE EL MOVIMENT ES PUGUI REALITZAR A TRAVES DE LA FUNCIÓ: POSIBILIDADESMOVIMIENTOREINA()
        while ((posFinalX < 0 || posFinalX > 7) || (posFinalY < 0 || posFinalY > 7) || !(posibilidadesMovimientoReina(posFinalY, posFinalX, tablero))) { // PARA EVITAR QUE DE ERROR SI EL USUARIO ESCRIBE UNA CASILLA INVÁLIDA (POR EJEMPLO: P, o -1)

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
            val posFinalXAux = readln().singleOrNull() ?: '-'

            posFinalY = convertorLetraNumeroANumero(posFinalYAux, posFinalXAux).first // CONVERTIM EL NUMERO QUE DIGITA EL JUGADOR AL NÚMERO CORRESPONENT A LA MATRIU TABLERO
            posFinalX = convertorLetraNumeroANumero(posFinalYAux, posFinalXAux).second // CONVERTIM LA LLETRA QUE DIGITA EL JUGADOR AL NÚMERO CORRESPONENT A LA MATRIU TABLERO

            if ((posFinalX < 0 || posFinalX > 7) || (posFinalY < 0 || posFinalY > 7)) { // WARNING DE QUE LA FICHA NO SE PUEDE MOVER AHI
                println("La casilla [${posFinalYAux}, ${posFinalXAux}] no es una casilla válida dentro del tablero de ajedrez")
            }
            // SI LAS CORDENADAS DEL TABLERO NO SON VÁLIDAS COMPROBAR CON LA FUNCIÓN PRINTAR QUE ESA PIEZA NO PUEDE AVANZAR AHÍ
        }

        val elementoCasillaQueInvadimos = tablero[posFinalY, posFinalX] // GUARDAMOS EL ELEMENTO QUE HABÍA EN LA CASILLA ANTES DE INVADIRLA

        tablero.tablero[posFinalY][posFinalX] = Reina(blancaONegra, Posicion(posFinalY, posFinalX), false) // INVADIMOS LA CASILLA
        tablero.tablero[posicionFicha.fila][posicionFicha.columna] = null // LA CASELLA EN LA QUE ES TROBAVA LA FICHA AL INICI QUEDA NULA

        // SI AMB EL MOVIMENT REALITZAT ES POSA AMB JAQUE AL TEU REI NO PERMETEM EL MOVIMENT I TORNEM TOT COM AL PRINCIPI
        if (tablero.isKingInCheck(reiEncontrado)){
            tablero.tablero[posFinalY][posFinalX] = elementoCasillaQueInvadimos
            return
        }

        // SI NO HI HA JAQUE I EL MOVIMENT ES POSIBLE PERMETEM EL MOVIMENT CAMBIANT LA POSICIÓ DE LA FILA I COLUMNA DE LA FICHA
        posicionFicha.fila = posFinalY
        posicionFicha.columna = posFinalX

        return
    }

    // PREGUNTAR SI ES BUENO CREAR UNA FUNCION IDÉNTICA PARA LA REINA CUANDO PODRIAMOS RECICLAR LA DEL ALFIL Y TORRE
    fun posibilidadesMovimientoReina(posFinalY: Int, posFinalX: Int,  tablero : Tablero): Boolean {

        if(blancaONegra){
            if(posFinalX == posicionFicha.columna && posFinalY > posicionFicha.fila && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)){
                for(i in posicionFicha.fila ..posFinalY){
                    if(tablero[i,posicionFicha.columna] != null && i != posicionFicha.fila && i != posFinalY){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA REINA INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 0 --> 7 EN VERTICAL DE LA REINA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN VERTICAL DE 0 --> 7
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA

            }else if(posFinalX == posicionFicha.columna && posFinalY < posicionFicha.fila && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)){
                for(i in posFinalY ..posicionFicha.fila){
                    if(tablero[i,posicionFicha.columna] != null && i != posicionFicha.fila && i != posFinalY){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA REINA INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 7 --> 0 EN VERTICAL DE LA REINA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN VERTICAL DE 7 --> 0
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }
            else if(posFinalY == posicionFicha.fila && posFinalX > posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)){
                for(i in posicionFicha.columna ..posFinalX){
                    if(tablero[posicionFicha.fila, i] != null && i != posicionFicha.columna && i != posFinalX){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA REINA INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 0 --> 7 EN HORITZONTAL DE LA REINA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN HORITZONTAL DE 0 --> 7
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }
            else if(posFinalY == posicionFicha.fila && posFinalX < posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)){
                for(i in posFinalX .. posicionFicha.columna){
                    if(tablero[posicionFicha.fila, i] != null && i != posicionFicha.columna && i != posFinalX){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA REINA INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 7 --> 0 EN HORITZONTAL DE LA REINA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN HORITZONTAL DE 7 --> 0
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }
            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY < posicionFicha.fila && posFinalX < posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posFinalX
                for (i in posFinalY..posicionFicha.fila) {
                    if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                        return false
                        // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA LA REINA HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                    auxPosicionFichaColumnas++
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A L'ESQUERRA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA

            }
            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY < posicionFicha.fila && posFinalX > posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posFinalX

                for (i in posFinalY..posicionFicha.fila) { // BIEN
                    if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                        return false
                        // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA LA REINA HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                    auxPosicionFichaColumnas--
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA

            }
            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY > posicionFicha.fila && posFinalX > posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posicionFicha.columna
                for (i in posicionFicha.fila .. posFinalY) {
                    if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                        return false
                        // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA LA REINA HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                    auxPosicionFichaColumnas++
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }

            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY > posicionFicha.fila && posFinalX < posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posicionFicha.columna
                for (i in posicionFicha.fila .. posFinalY) {
                    if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                        return false
                        // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA LA REINA HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                    auxPosicionFichaColumnas--
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA

            }
        }
        else{ // REINA NEGRA
            if(posFinalX == posicionFicha.columna && posFinalY > posicionFicha.fila && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)){
                for(i in posicionFicha.fila ..posFinalY){
                    if(tablero[i,posicionFicha.columna] != null && i != posicionFicha.fila && i != posFinalY){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA REINA INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 0 --> 7 EN VERTICAL DE LA REINA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN VERTICAL DE 0 --> 7
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA

            }else if(posFinalX == posicionFicha.columna && posFinalY < posicionFicha.fila && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)){
                for(i in posFinalY ..posicionFicha.fila){
                    if(tablero[i,posicionFicha.columna] != null && i != posicionFicha.fila && i != posFinalY){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA REINA INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 7 --> 0 EN VERTICAL DE LA REINA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN VERTICAL DE 7 --> 0
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            }
            else if(posFinalY == posicionFicha.fila && posFinalX > posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)){
                for(i in posicionFicha.columna ..posFinalX){
                    if(tablero[posicionFicha.fila, i] != null && i != posicionFicha.columna && i != posFinalX){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA REINA INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 0 --> 7 EN HORITZONTAL DE LA REINA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN HORITZONTAL DE 0 --> 7
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            }
            else if(posFinalY == posicionFicha.fila && posFinalX < posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)){
                for(i in posFinalX .. posicionFicha.columna){
                    if(tablero[posicionFicha.fila, i] != null && i != posicionFicha.columna && i != posFinalX){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA REINA INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 7 --> 0 EN HORITZONTAL DE LA REINA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN HORITZONTAL DE 7 --> 0
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            }
            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY < posicionFicha.fila && posFinalX < posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posFinalX
                for (i in posFinalY..posicionFicha.fila) {
                    if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                        return false
                        // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA LA REINA HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                    auxPosicionFichaColumnas++
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A L'ESQUERRA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA

            }
            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY < posicionFicha.fila && posFinalX > posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posFinalX

                for (i in posFinalY..posicionFicha.fila) { // BIEN
                    if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                        return false
                        // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA LA REINA HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                    auxPosicionFichaColumnas--
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA

            }
            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY > posicionFicha.fila && posFinalX > posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posicionFicha.columna
                for (i in posicionFicha.fila .. posFinalY) {
                    if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                        return false
                        // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA LA REINA HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                    auxPosicionFichaColumnas++
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            }

            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY > posicionFicha.fila && posFinalX < posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posicionFicha.columna
                for (i in posicionFicha.fila .. posFinalY) {
                    if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                        return false
                        // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA LA REINA HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                    auxPosicionFichaColumnas--
                }

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
}