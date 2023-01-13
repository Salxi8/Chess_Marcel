class Torre(blanca_o_negra: Boolean, posicion: Posicion): Ficha(blanca_o_negra, posicion, false) {

    fun movimientoTorre(ficha: Torre,  tablero : Tablero): Torre {
        var posFinalY = -1
        var posFinalX = -1
        // COMPROBEM SI LA FILA Y COLUMNA ON VOLEM MOURE LA TORRE ESTA DINS DE L ARRAY DEL TABLERO
        // COMPROBEM QUE EL MOVIMENT ES PUGUI REALITZAR A TRAVES DE LA FUNCIÓ: POSIBILIDADESMOVIMIENTOTORRE()
        while ((posFinalX < 0 || posFinalX > 7) || (posFinalY < 0 || posFinalY > 7) || !(posibilidadesMovimientoTorre(posFinalY, posFinalX, tablero))) { // PARA EVITAR QUE DE ERROR SI EL USUARIO ESCRIBE UNA CASILLA INVÁLIDA (POR EJEMPLO: P, o -1)

            print("En que FILA quiere mover la TORRE: ")
            posFinalY = readln().toIntOrNull() ?: -1
            print("En que COLUMNA quiere mover la TORRE: ")
            posFinalX = readln().toIntOrNull() ?: -1

            if ((posFinalX < 0 || posFinalX > 7) || (posFinalY < 0 || posFinalY > 7)) { // WARNING DE QUE LA FICHA NO SE PUEDE MOVER AHI
                println("La casilla [${posFinalY}, ${posFinalX}] no es una casilla válida dentro del tablero de ajedrez")
            }
            // SI LAS CORDENADAS DEL TABLERO NO SON VÁLIDAS COMPROBAR CON LA FUNCIÓN PRINTAR QUE ESA PIEZA NO PUEDE AVANZAR AHÍ
        }

        ficha.posicionFicha.fila = posFinalY
        ficha.posicionFicha.columna = posFinalX

        return ficha // NOSE SI CAL RETORNAR LA FICHA AQUÍ
    }

    private fun posibilidadesMovimientoTorre(posFinalY: Int, posFinalX: Int,  tablero : Tablero): Boolean {

        if(blanca_o_negra){
            if(posFinalX == posicionFicha.columna && posFinalY > posicionFicha.fila && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)){
                for(i in posicionFicha.fila ..posFinalY){
                    if(tablero[i,posicionFicha.columna] != null && i != posicionFicha.fila && i != posFinalY){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA TORRE INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 0 --> 7 EN VERTICAL DE LA TORRE
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA TORRE EN VERTICAL DE 0 --> 7
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA

            }else if(posFinalX == posicionFicha.columna && posFinalY < posicionFicha.fila && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)){
                for(i in posFinalY ..posicionFicha.fila){
                    if(tablero[i,posicionFicha.columna] != null && i != posicionFicha.fila && i != posFinalY){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA TORRE INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 7 --> 0 EN VERTICAL DE LA TORRE
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA TORRE EN VERTICAL DE 7 --> 0
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }
            else if(posFinalY == posicionFicha.fila && posFinalX > posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)){
                for(i in posicionFicha.columna ..posFinalX){
                    if(tablero[posicionFicha.fila, i] != null && i != posicionFicha.columna && i != posFinalX){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA TORRE INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 0 --> 7 EN HORITZONTAL DE LA TORRE
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA TORRE EN HORITZONTAL DE 0 --> 7
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }
            else if(posFinalY == posicionFicha.fila && posFinalX < posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)){
                for(i in posFinalX .. posicionFicha.columna){
                    if(tablero[posicionFicha.fila, i] != null && i != posicionFicha.columna && i != posFinalX){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA TORRE INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 7 --> 0 EN HORITZONTAL DE LA TORRE
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA TORRE EN HORITZONTAL DE 7 --> 0
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }
        }
        else{
            if(posFinalX == posicionFicha.columna && posFinalY > posicionFicha.fila && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)){
                for(i in posicionFicha.fila ..posFinalY){
                    if(tablero[i,posicionFicha.columna] != null && i != posicionFicha.fila && i != posFinalY){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA TORRE INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 0 --> 7 EN VERTICAL DE LA TORRE
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA TORRE EN VERTICAL DE 0 --> 7
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA

            }
            else if(posFinalX == posicionFicha.columna && posFinalY < posicionFicha.fila && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)){
                for(i in posFinalY ..posicionFicha.fila){
                    if(tablero[i,posicionFicha.columna] != null && i != posicionFicha.fila && i != posFinalY){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA TORRE INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 7 --> 0 EN VERTICAL DE LA TORRE
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA TORRE EN VERTICAL DE 7 --> 0
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            }
            else if(posFinalY == posicionFicha.fila && posFinalX > posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)){
                for(i in posicionFicha.columna ..posFinalX){
                    if(tablero[posicionFicha.fila, i] != null && i != posicionFicha.columna && i != posFinalX){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA TORRE INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 0 --> 7 EN HORITZONTAL DE LA TORRE
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA TORRE EN HORITZONTAL DE 0 --> 7
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            }
            else if(posFinalY == posicionFicha.fila && posFinalX < posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)){
                for(i in posFinalX .. posicionFicha.columna){
                    if(tablero[posicionFicha.fila, i] != null && i != posicionFicha.columna && i != posFinalX){
                        return false
                        // AQUÍ COMPROBAMOS QUE DE LA POSICIÓN EN LA QUE SE ENCUENTRA LA TORRE INICIALMENTE HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                }
                // MOVIMENT DE 7 --> 0 EN HORITZONTAL DE LA TORRE
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA TORRE EN HORITZONTAL DE 7 --> 0
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            }
        }



        return false
    }

}