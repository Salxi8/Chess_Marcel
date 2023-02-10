package Modelo

import Controlador.Posicion
import Controlador.Tablero

class Caballo(blancaONegra: Boolean, posicion: Posicion, seleccionada: Boolean) : Ficha(blancaONegra, posicion, seleccionada) {
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

        tablero.tablero[posFinalY][posFinalX] = Caballo(blancaONegra, Posicion(posFinalY, posFinalX), false) // INVADIMOS LA CASILLA
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

    fun posibilidadesMovimientoCaballo(posFinalY: Int, posFinalX: Int,  tablero : Tablero): Boolean {

        if(blancaONegra){ // CABALLO BLANCO
            if ((tablero[posFinalY, posFinalX]?.blancaONegra == false || tablero[posFinalY, posFinalX] == null) && ((posicionFicha.fila -1 == posFinalY && posicionFicha.columna - 2 == posFinalX) || (posicionFicha.fila - 2 == posFinalY && posicionFicha.columna - 1 == posFinalX) || (posicionFicha.fila - 2 == posFinalY && posicionFicha.columna + 1 == posFinalX) || (posicionFicha.fila - 1 == posFinalY && posicionFicha.columna + 2 == posFinalX) || (posicionFicha.fila + 1 == posFinalY && posicionFicha.columna + 2 == posFinalX) || (posicionFicha.fila + 2 == posFinalY && posicionFicha.columna + 1 == posFinalX) || (posicionFicha.fila + 2 == posFinalY && posicionFicha.columna - 1 == posFinalX) || (posicionFicha.fila + 1 == posFinalY && posicionFicha.columna - 2 == posFinalX)))
                return true
            // AQUEST MOVIMENT ENS PERMET AVANÇAR AL CABALL BLANC
            // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
            // 1. ELS DIFERENTS MOVIMENTS DEL CABALL (POT SALTAR PECES)
            // 2. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
        }
        else{ // CABALLO NEGRO
            if ((tablero[posFinalY, posFinalX]?.blancaONegra == true || tablero[posFinalY, posFinalX] == null) && ((posicionFicha.fila -1 == posFinalY && posicionFicha.columna - 2 == posFinalX) || (posicionFicha.fila - 2 == posFinalY && posicionFicha.columna - 1 == posFinalX) || (posicionFicha.fila - 2 == posFinalY && posicionFicha.columna + 1 == posFinalX) || (posicionFicha.fila - 1 == posFinalY && posicionFicha.columna + 2 == posFinalX) || (posicionFicha.fila + 1 == posFinalY && posicionFicha.columna + 2 == posFinalX) || (posicionFicha.fila + 2 == posFinalY && posicionFicha.columna + 1 == posFinalX) || (posicionFicha.fila + 2 == posFinalY && posicionFicha.columna - 1 == posFinalX) || (posicionFicha.fila + 1 == posFinalY && posicionFicha.columna - 2 == posFinalX)))
                return true
            // AQUEST MOVIMENT ENS PERMET AVANÇAR AL CABALL NEGRE
            // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
            // 1. ELS DIFERENTS MOVIMENTS DEL CABALL (POT SALTAR PECES)
            // 2. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
        }
        return false
    }

}