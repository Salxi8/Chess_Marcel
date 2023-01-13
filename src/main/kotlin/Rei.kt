class Rei(blanca_o_negra: Boolean, posicion: Posicion): Ficha(blanca_o_negra, posicion, false) {

    fun movimientoRei(ficha: Rei,  tablero : Tablero): Rei {
        var posFinalY = -1
        var posFinalX = -1
        // COMPROBEM SI LA FILA Y COLUMNA ON VOLEM MOURE EL REI ESTA DINS DE L ARRAY DEL TABLERO
        // COMPROBEM QUE EL MOVIMENT ES PUGUI REALITZAR A TRAVES DE LA FUNCIÓ: POSIBILIDADESMOVIMIENTOREI()
        while ((posFinalX < 0 || posFinalX > 7) || (posFinalY < 0 || posFinalY > 7) || !(posibilidadesMovimientoRei(posFinalY, posFinalX, tablero))) { // PARA EVITAR QUE DE ERROR SI EL USUARIO ESCRIBE UNA CASILLA INVÁLIDA (POR EJEMPLO: P, o -1)

            print("En que FILA quiere mover la REI: ")
            posFinalY = readln().toIntOrNull() ?: -1
            print("En que COLUMNA quiere mover la REI: ")
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

    private fun posibilidadesMovimientoRei(posFinalY: Int, posFinalX: Int,  tablero : Tablero): Boolean {
        if(blanca_o_negra){ // REI BLANCO
            if(posFinalX == posicionFicha.columna && posFinalY == posicionFicha.fila + 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)){

                // MOVIMENT VERTICAL I CAP ABAIX DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR EL REI EN VERTICAL DE 0 --> 1 ...
                // !!!!!!!!!!!!!!!!!!!!!!!!
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA

            }else if(posFinalX == posicionFicha.columna && posFinalY == posicionFicha.fila - 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)){

                // MOVIMENT VERTICAL I CAP ADALT DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN VERTICAL DE 7 --> 0
                // !!!!!!!!!!!!!!!!!!!!!!!!
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }
            else if(posFinalY == posicionFicha.fila && posFinalX == posicionFicha.columna + 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)){

                // MOVIMENT HORITZONTAL I CAP A LA DRETA DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN HORITZONTAL DE 0 --> 7
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }
            else if(posFinalY == posicionFicha.fila && posFinalX == posicionFicha.columna - 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)){

                // MOVIMENT HORITZONTAL I CAP A L'ESQUERRA DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN HORITZONTAL DE 7 --> 0
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }
            else if (posFinalY == posicionFicha.fila - 1 && posFinalX == posicionFicha.columna - 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL AMUNT ESQUERRA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A L'ESQUERRA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA

            }
            else if (posFinalY == posicionFicha.fila - 1 && posFinalX == posicionFicha.columna + 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL AMUNT DRETA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA

            }
            else if (posFinalY == posicionFicha.fila + 1 && posFinalX == posicionFicha.columna + 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL ABAIX DRETA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }

            else if (posFinalY == posicionFicha.fila + 1 && posFinalX == posicionFicha.columna - 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL ABAIX ESQUERRA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }
        }
        else{ // REI NEGRO
            if(posFinalX == posicionFicha.columna && posFinalY == posicionFicha.fila + 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)){

                // MOVIMENT VERTICAL I CAP ABAIX DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR EL REI EN VERTICAL DE 0 --> 1 ...
                // !!!!!!!!!!!!!!!!!!!!!!!!
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA

            }else if(posFinalX == posicionFicha.columna && posFinalY == posicionFicha.fila - 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)){

                // MOVIMENT VERTICAL I CAP ADALT DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN VERTICAL DE 7 --> 0
                // !!!!!!!!!!!!!!!!!!!!!!!!
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. COLUMNA FINAL DE LA FICHA == COLUMNA INICIAL DE LA FICHA
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            }
            else if(posFinalY == posicionFicha.fila && posFinalX == posicionFicha.columna + 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)){

                // MOVIMENT HORITZONTAL I CAP A LA DRETA DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN HORITZONTAL DE 0 --> 7
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            }
            else if(posFinalY == posicionFicha.fila && posFinalX == posicionFicha.columna - 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)){

                // MOVIMENT HORITZONTAL I CAP A L'ESQUERRA DEL REI
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN HORITZONTAL DE 7 --> 0
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. FILA FINAL DE LA FICHA == FILA INICIAL DE LA FICHA
                // 2. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            }
            else if (posFinalY == posicionFicha.fila - 1 && posFinalX == posicionFicha.columna - 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL AMUNT ESQUERRA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A L'ESQUERRA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA

            }
            else if (posFinalY == posicionFicha.fila - 1 && posFinalX == posicionFicha.columna + 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL AMUNT DRETA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA

            }
            else if (posFinalY == posicionFicha.fila + 1 && posFinalX == posicionFicha.columna + 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)) {

                // MOVIMENT REI DIAGONAL ABAIX DRETA
                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR LA REINA EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            }

            else if (posFinalY == posicionFicha.fila + 1 && posFinalX == posicionFicha.columna - 1 && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)) {

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
}