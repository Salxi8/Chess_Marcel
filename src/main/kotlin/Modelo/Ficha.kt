package Modelo

import Controlador.Posicion
import Controlador.Tablero

open class Ficha (val blancaONegra: Boolean, val posicionFicha: Posicion, var seleccionada: Boolean){

    fun piezasQueQuedanEnElTablero(tablero: Tablero): ArrayList<Pair<Int, Int>> {
        val posicionesPosibles = arrayListOf<Pair<Int, Int>>()

        if (tablero.turnoBlancoONegro) {
            for (i in 0 until tablero.numFilas) {
                for (j in 0 until tablero.numColumnas) {
                    if (tablero.tablero[i][j] is Ficha && tablero.tablero[i][j]?.blancaONegra == true) {
                        posicionesPosibles.add(Pair(i, j))
                    }
                }
            }
        } else {
            for (i in 0 until tablero.numFilas) {
                for (j in 0 until tablero.numColumnas) {
                    if (tablero.tablero[i][j] is Ficha && tablero.tablero[i][j]?.blancaONegra == false) {
                        posicionesPosibles.add(Pair(i, j))
                    }
                }
            }
        }
        return posicionesPosibles
    }


    // ESTAS DOS QUIZAS EN TABLERO MEJOR
    fun convertorLetraNumeroANumero(posicionY: Int, posicionX: Char): Pair<Int, Int>{

        val listaLetrasMay = listOf<Char>('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H')
        val listaLetrasMin = listOf<Char>('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h')

        val posicionYTransformada = posicionY - 1 //  FILAS
        var posicionXTransformada = -1

        for (i in listaLetrasMay.indices){
            if(listaLetrasMay[i] == posicionX || listaLetrasMin[i] == posicionX){
                posicionXTransformada = i
            }
        }

        return Pair(posicionYTransformada, posicionXTransformada)
    }

    fun convertorNumeroALetraNumero(posicionY: Int, posicionX: Int): Pair<Int, Char>{

        val listaLetras = listOf<Char>('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H')

        val posicionYTransformada = posicionY + 1 //  FILAS
        var posicionXTransformada = 'Z'

        for (i in listaLetras.indices){
            if(i == posicionX){
                posicionXTransformada = listaLetras[i]
            }
        }

        return Pair(posicionYTransformada, posicionXTransformada)
    }

    // =======================================================

    fun seleccionarFicha(tablero: Tablero): Pair<Int, Int>{

        var posInicialY: Int
        var posInicialX: Int

        do { // COMPRUEBO QUE HAYA UNA FICHA USABLE EN LA CASILLA SELECCIONADA

            print("En que FILA se encuentra la FICHA que desea MOVER [NUMERO]: ") // PIDO LA FILA EN QUE SE ENCUENTRA LA FICHA EN LA MATRIZ
            val posInicialYAux = readln().toIntOrNull() ?: -1
            print("En que COLUMNA se encuentra la FICHA que desea MOVER [LETRA]: ")
            val posInicialXAux = readln().singleOrNull() ?: '-'

            posInicialY = convertorLetraNumeroANumero(posInicialYAux, posInicialXAux).first
            posInicialX = convertorLetraNumeroANumero(posInicialYAux, posInicialXAux).second


            if ((posInicialX < 0 || posInicialX > 7) || (posInicialY < 0 || posInicialY > 7)) { // WARNING DE QUE LA POSICIÓN SELECCIONADA NO FORMA PARTE DEL TABLERO
                println("La casilla [${posInicialYAux}, ${posInicialXAux}] no es una casilla válida dentro del tablero de ajedrez")
            } else if (tablero.tablero[posInicialY][posInicialX] == null) { // WARNING DE QUE LA POSICIÓN SELECCIONADA FORMA PARTE DEL TABLERO PERO NO CONTIENE NINGUNA FICHA
                println("En la casilla [${posInicialYAux}, ${posInicialXAux}] no se encuentra ninguna ficha")
            } else if (tablero.tablero[posInicialY][posInicialX]?.blancaONegra != tablero.turnoBlancoONegro) { // WARNING DE QUE LA POSICIÓN SELECCIONADA FORMA PARTE DEL TABLERO,
                if (tablero.turnoBlancoONegro)                                                // PERO ESTA FICHA ES LA DEL OPONENTE
                    println("La casilla [${posInicialYAux}, ${posInicialXAux}] no corresponde a ninguna ficha BLANCA")
                else
                    println("La casilla [${posInicialYAux}, ${posInicialXAux}] no corresponde a ninguna ficha NEGRA")
            }
        } while ((posInicialX < 0 || posInicialX > 7) || (posInicialY < 0 || posInicialY > 7) || tablero.tablero[posInicialY][posInicialX] == null || (tablero.tablero[posInicialY][posInicialX]?.blancaONegra != tablero.turnoBlancoONegro))

        return Pair(posInicialY, posInicialX)
    }

    fun actualizarFicha(tablero: Tablero, reiEncontrado: Rei){

        // SELECCIONAR FICHA
        val posInicialPair = seleccionarFicha(tablero)
        val posInicialY = posInicialPair.first
        val posInicialX = posInicialPair.second

        if (tablero.tablero[posInicialY][posInicialX] is Peon) {

            if (tablero.turnoBlancoONegro) { // PEON BLANCO

                val peonAux = Peon(true, Posicion(posInicialY, posInicialX), true)

                tablero.tablero[posInicialY][posInicialX] = peonAux // PARA QUE SE VEA EL PEÓN MARCADO COMO SELECCIONADO
                tablero.printarTabero() // PARA QUE SE VEA EL PEÓN MARCADO COMO SELECCIONADO

                // PARA PRINTAR LA LETRA I EL NUMERO CUANDO RECOMENDAMOS UN MOVIMIENTO
                val pairPosicionesPrintar = tablero.retornarMovimientosPosiblesDos(posInicialY, posInicialX, reiEncontrado)
                print("TUS MOVIMIENTOS POSIBLES: ")
                for (elem in pairPosicionesPrintar) {
                    print("[${peonAux.convertorNumeroALetraNumero(elem.first, elem.second)}]")
                }
                println()

                peonAux.movimientoPeon(tablero, reiEncontrado)

                peonAux.seleccionada = false


                // CUÁNDO EL PEÓN LLEGA A LA ÚLTIMA FILA
                peonAux.peonCoronacion(tablero)

                if (posInicialY != peonAux.posicionFicha.fila || posInicialX != peonAux.posicionFicha.columna) {
                    tablero.tablero[posInicialY][posInicialX] = null
                } else {
                    // MANTENER TURNO BLANCASC YA QUE NO SE HA REALIZADO NINGUNA JUGADA PQ LA POSICIÓN SELECCIONADA ERA JAQUE Y
                    // POR LO TANTO EL REI NO PODÍA IR ALLI (LO PONGO EN FALSE (QUE ES TURNO DE LAS FICHAS NEGRAS) PORQUE POSTERIORMENTE
                    // EN EL MAIN SE HACE UN CAMBIO OTRA VEZ Y POR LO TANTO SERÁ TURNO DE LAS BLANCAS
                    tablero.turnoBlancoONegro = false
                }

            } else { // PEON NEGRO

                val peonAux = Peon(false, Posicion(posInicialY, posInicialX), true)

                tablero.tablero[posInicialY][posInicialX] = peonAux // PARA QUE SE VEA EL PEÓN MARCADO COMO SELECCIONADO
                tablero.printarTabero() // PARA QUE SE VEA EL PEÓN MARCADO COMO SELECCIONADO

                // PARA PRINTAR LA LETRA I EL NUMERO CUANDO RECOMENDAMOS UN MOVIMIENTO
                val pairPosicionesPrintar = tablero.retornarMovimientosPosiblesDos(posInicialY, posInicialX, reiEncontrado)
                print("TUS MOVIMIENTOS POSIBLES: ")
                for (elem in pairPosicionesPrintar) {
                    print("[${peonAux.convertorNumeroALetraNumero(elem.first, elem.second)}]")
                }
                println()

                peonAux.movimientoPeon(tablero, reiEncontrado)
                // PODER TIRAR ATRAS SI NOS HEMOS EQUIVOCADO DE FICHA O QUE DIRECTAMENTE EL SISTEMA TE DIGA QUE ESA FICHA

                peonAux.seleccionada = false

                // CUÁNDO EL PEÓN LLEGA A LA ÚLTIMA FILA
                peonAux.peonCoronacion(tablero)

                if (posInicialY != peonAux.posicionFicha.fila || posInicialX != peonAux.posicionFicha.columna) {
                    tablero.tablero[posInicialY][posInicialX] = null
                } else {
                    // MANTENER TURNO NEGRAS YA QUE NO SE HA REALIZADO NINGUNA JUGADA PQ LA POSICIÓN SELECCIONADA ERA JAQUE Y
                    // POR LO TANTO EL REI NO PODÍA IR ALLI (LO PONGO EN TRUE (QUE ES TURNO DE LAS FICHAS BLANCAS) PORQUE POSTERIORMENTE
                    // EN EL MAIN SE HACE UN CAMBIO OTRA VEZ Y POR LO TANTO SERÁ TURNO DE LAS NEGRAS
                    tablero.turnoBlancoONegro = true
                }


            }
        } else if (tablero.tablero[posInicialY][posInicialX] is Torre) {

            if (tablero.turnoBlancoONegro) { // TORRE BLANCA

                val torreAux = Torre(true, Posicion(posInicialY, posInicialX), true)


                tablero.tablero[posInicialY][posInicialX] = torreAux // PARA QUE SE VEA LA TORRE MARCADA COMO SELECCIONADA
                tablero.printarTabero() // PARA QUE SE VEA LA TORRE MARCADA COMO SELECCIONADA

                // PARA PRINTAR LA LETRA I EL NUMERO CUANDO RECOMENDAMOS UN MOVIMIENTO
                val pairPosicionesPrintar = tablero.retornarMovimientosPosiblesDos(posInicialY, posInicialX, reiEncontrado)
                print("TUS MOVIMIENTOS POSIBLES: ")
                for (elem in pairPosicionesPrintar) {
                    print("[${torreAux.convertorNumeroALetraNumero(elem.first, elem.second)}]")
                }
                println()

                torreAux.movimientoTorre(tablero, reiEncontrado)

                torreAux.seleccionada = false
                tablero.tablero[torreAux.posicionFicha.fila][torreAux.posicionFicha.columna] = torreAux

                if (posInicialY != torreAux.posicionFicha.fila || posInicialX != torreAux.posicionFicha.columna) {
                    tablero.tablero[posInicialY][posInicialX] = null
                } else {
                    // MANTENER TURNO BLANCASC YA QUE NO SE HA REALIZADO NINGUNA JUGADA PQ LA POSICIÓN SELECCIONADA ERA JAQUE Y
                    // POR LO TANTO EL REI NO PODÍA IR ALLI (LO PONGO EN FALSE (QUE ES TURNO DE LAS FICHAS NEGRAS) PORQUE POSTERIORMENTE
                    // EN EL MAIN SE HACE UN CAMBIO OTRA VEZ Y POR LO TANTO SERÁ TURNO DE LAS BLANCAS
                    tablero.turnoBlancoONegro = false
                }
            } else { // TORRE NEGRA

                val torreAux = Torre(false, Posicion(posInicialY, posInicialX), true)


                tablero.tablero[posInicialY][posInicialX] = torreAux // PARA QUE SE VEA LA TORRE MARCADA COMO SELECCIONADA
                tablero.printarTabero() // PARA QUE SE VEA LA TORRE MARCADA COMO SELECCIONADA

                // PARA PRINTAR LA LETRA I EL NUMERO CUANDO RECOMENDAMOS UN MOVIMIENTO
                val pairPosicionesPrintar = tablero.retornarMovimientosPosiblesDos(posInicialY, posInicialX, reiEncontrado)
                print("TUS MOVIMIENTOS POSIBLES: ")
                for (elem in pairPosicionesPrintar) {
                    print("[${torreAux.convertorNumeroALetraNumero(elem.first, elem.second)}]")
                }
                println()

                torreAux.movimientoTorre(tablero, reiEncontrado)

                torreAux.seleccionada = false
                tablero.tablero[torreAux.posicionFicha.fila][torreAux.posicionFicha.columna] = torreAux

                if (posInicialY != torreAux.posicionFicha.fila || posInicialX != torreAux.posicionFicha.columna) {
                    tablero.tablero[posInicialY][posInicialX] = null
                } else {
                    // MANTENER TURNO NEGRAS YA QUE NO SE HA REALIZADO NINGUNA JUGADA PQ LA POSICIÓN SELECCIONADA ERA JAQUE Y
                    // POR LO TANTO EL REI NO PODÍA IR ALLI (LO PONGO EN TRUE (QUE ES TURNO DE LAS FICHAS BLANCAS) PORQUE POSTERIORMENTE
                    // EN EL MAIN SE HACE UN CAMBIO OTRA VEZ Y POR LO TANTO SERÁ TURNO DE LAS NEGRAS
                    tablero.turnoBlancoONegro = true
                }
            }

        } else if (tablero.tablero[posInicialY][posInicialX] is Caballo) {

            if (tablero.turnoBlancoONegro) { // CABALLO BLANCO

                val caballoAux = Caballo(true, Posicion(posInicialY, posInicialX), true)

                tablero.tablero[posInicialY][posInicialX] = caballoAux // PARA QUE SE VEA EL CABALLO MARCADO COMO SELECCIONADO
                tablero.printarTabero()

                // PARA PRINTAR LA LETRA I EL NUMERO CUANDO RECOMENDAMOS UN MOVIMIENTO
                val pairPosicionesPrintar = tablero.retornarMovimientosPosiblesDos(posInicialY, posInicialX, reiEncontrado)
                print("TUS MOVIMIENTOS POSIBLES: ")
                for (elem in pairPosicionesPrintar) {
                    print("[${caballoAux.convertorNumeroALetraNumero(elem.first, elem.second)}]")
                }
                println()

                caballoAux.movimientoCaballo(tablero, reiEncontrado)

                caballoAux.seleccionada = false
                tablero.tablero[caballoAux.posicionFicha.fila][caballoAux.posicionFicha.columna] = caballoAux

                if (posInicialY != caballoAux.posicionFicha.fila || posInicialX != caballoAux.posicionFicha.columna) {
                    tablero.tablero[posInicialY][posInicialX] = null
                } else {
                    // MANTENER TURNO BLANCASC YA QUE NO SE HA REALIZADO NINGUNA JUGADA PQ LA POSICIÓN SELECCIONADA ERA JAQUE Y
                    // POR LO TANTO EL REI NO PODÍA IR ALLI (LO PONGO EN FALSE (QUE ES TURNO DE LAS FICHAS NEGRAS) PORQUE POSTERIORMENTE
                    // EN EL MAIN SE HACE UN CAMBIO OTRA VEZ Y POR LO TANTO SERÁ TURNO DE LAS BLANCAS
                    tablero.turnoBlancoONegro = false
                }
            } else { // CABALLO NEGRO


                val caballoAux = Caballo(false, Posicion(posInicialY, posInicialX), true)


                tablero.tablero[posInicialY][posInicialX] = caballoAux // PARA QUE SE VEA EL CABALLO MARCADO COMO SELECCIONADO
                tablero.printarTabero()

                // PARA PRINTAR LA LETRA I EL NUMERO CUANDO RECOMENDAMOS UN MOVIMIENTO
                val pairPosicionesPrintar = tablero.retornarMovimientosPosiblesDos(posInicialY, posInicialX, reiEncontrado)
                print("TUS MOVIMIENTOS POSIBLES: ")
                for (elem in pairPosicionesPrintar) {
                    print("[${caballoAux.convertorNumeroALetraNumero(elem.first, elem.second)}]")
                }
                println()

                caballoAux.movimientoCaballo(tablero, reiEncontrado)

                caballoAux.seleccionada = false
                tablero.tablero[caballoAux.posicionFicha.fila][caballoAux.posicionFicha.columna] = caballoAux

                if (posInicialY != caballoAux.posicionFicha.fila || posInicialX != caballoAux.posicionFicha.columna) {
                    tablero.tablero[posInicialY][posInicialX] = null
                } else {
                    // MANTENER TURNO NEGRAS YA QUE NO SE HA REALIZADO NINGUNA JUGADA PQ LA POSICIÓN SELECCIONADA ERA JAQUE Y
                    // POR LO TANTO EL REI NO PODÍA IR ALLI (LO PONGO EN TRUE (QUE ES TURNO DE LAS FICHAS BLANCAS) PORQUE POSTERIORMENTE
                    // EN EL MAIN SE HACE UN CAMBIO OTRA VEZ Y POR LO TANTO SERÁ TURNO DE LAS NEGRAS
                    tablero.turnoBlancoONegro = true
                }
            }

        } else if (tablero.tablero[posInicialY][posInicialX] is Alfil) {

            if (tablero.turnoBlancoONegro) { // ALFIL BLANCO

                val alfilAux = Alfil(true, Posicion(posInicialY, posInicialX), true)


                tablero.tablero[posInicialY][posInicialX] = alfilAux // PARA QUE SE VEA EL ALFIL MARCADO COMO SELECCIONADO
                tablero.printarTabero()

                // PARA PRINTAR LA LETRA I EL NUMERO CUANDO RECOMENDAMOS UN MOVIMIENTO
                val pairPosicionesPrintar = tablero.retornarMovimientosPosiblesDos(posInicialY, posInicialX, reiEncontrado)
                print("TUS MOVIMIENTOS POSIBLES: ")
                for (elem in pairPosicionesPrintar) {
                    print("[${alfilAux.convertorNumeroALetraNumero(elem.first, elem.second)}]")
                }
                println()

                alfilAux.movimientoAlfil(tablero, reiEncontrado)

                alfilAux.seleccionada = false
                tablero.tablero[alfilAux.posicionFicha.fila][alfilAux.posicionFicha.columna] = alfilAux

                if (posInicialY != alfilAux.posicionFicha.fila || posInicialX != alfilAux.posicionFicha.columna) {
                    tablero.tablero[posInicialY][posInicialX] = null
                } else {
                    // MANTENER TURNO BLANCASC YA QUE NO SE HA REALIZADO NINGUNA JUGADA PQ LA POSICIÓN SELECCIONADA ERA JAQUE Y
                    // POR LO TANTO EL REI NO PODÍA IR ALLI (LO PONGO EN FALSE (QUE ES TURNO DE LAS FICHAS NEGRAS) PORQUE POSTERIORMENTE
                    // EN EL MAIN SE HACE UN CAMBIO OTRA VEZ Y POR LO TANTO SERÁ TURNO DE LAS BLANCAS
                    tablero.turnoBlancoONegro = false
                }

            } else { // ALFIL NEGRO

                val alfilAux = Alfil(false, Posicion(posInicialY, posInicialX), true)


                tablero.tablero[posInicialY][posInicialX] = alfilAux // PARA QUE SE VEA EL ALFIL MARCADO COMO SELECCIONADO
                tablero.printarTabero() // PARA QUE SE VEA EL ALFIL MARCADO COMO SELECCIONADO

                // PARA PRINTAR LA LETRA I EL NUMERO CUANDO RECOMENDAMOS UN MOVIMIENTO
                val pairPosicionesPrintar = tablero.retornarMovimientosPosiblesDos(posInicialY, posInicialX, reiEncontrado)
                print("TUS MOVIMIENTOS POSIBLES: ")
                for (elem in pairPosicionesPrintar) {
                    print("[${alfilAux.convertorNumeroALetraNumero(elem.first, elem.second)}]")
                }
                println()

                alfilAux.movimientoAlfil(tablero, reiEncontrado)

                alfilAux.seleccionada = false
                tablero.tablero[alfilAux.posicionFicha.fila][alfilAux.posicionFicha.columna] = alfilAux
                if (posInicialY != alfilAux.posicionFicha.fila || posInicialX != alfilAux.posicionFicha.columna) {
                    tablero.tablero[posInicialY][posInicialX] = null
                } else {
                    // MANTENER TURNO NEGRAS YA QUE NO SE HA REALIZADO NINGUNA JUGADA PQ LA POSICIÓN SELECCIONADA ERA JAQUE Y
                    // POR LO TANTO EL REI NO PODÍA IR ALLI (LO PONGO EN TRUE (QUE ES TURNO DE LAS FICHAS BLANCAS) PORQUE POSTERIORMENTE
                    // EN EL MAIN SE HACE UN CAMBIO OTRA VEZ Y POR LO TANTO SERÁ TURNO DE LAS NEGRAS
                    tablero.turnoBlancoONegro = true
                }
            }

        } else if (tablero.tablero[posInicialY][posInicialX] is Reina) {

            if (tablero.turnoBlancoONegro) { // REINA BLANCA

                val reinaAux = Reina(true, Posicion(posInicialY, posInicialX), true)

                tablero.tablero[posInicialY][posInicialX] = reinaAux // PARA QUE SE VEA LA REINA MARCADO COMO SELECCIONADA
                tablero.printarTabero()

                // PARA PRINTAR LA LETRA I EL NUMERO CUANDO RECOMENDAMOS UN MOVIMIENTO
                val pairPosicionesPrintar = tablero.retornarMovimientosPosiblesDos(posInicialY, posInicialX, reiEncontrado)
                print("TUS MOVIMIENTOS POSIBLES: ")
                for (elem in pairPosicionesPrintar) {
                    print("[${reinaAux.convertorNumeroALetraNumero(elem.first, elem.second)}]")
                }
                println()

                reinaAux.movimientoReina(tablero, reiEncontrado)

                reinaAux.seleccionada = false
                tablero.tablero[reinaAux.posicionFicha.fila][reinaAux.posicionFicha.columna] = reinaAux

                if (posInicialY != reinaAux.posicionFicha.fila || posInicialX != reinaAux.posicionFicha.columna) {
                    tablero.tablero[posInicialY][posInicialX] =
                        null // SI EL REI HA CAMBIADO DE POSICIÓN MARCAR CON NULL SU POSICIÓN ANTIGUA
                    // ESTO ME SIRVE PARA PODER CONTROLAR EL JAQUE YA QUE SI EL MOVIMIENTO DEL REI SUPONE QUE VAYA ESTAR EN JAQUE
                    // EL REI NO PUEDE HACER ESE MOVIMIENTO Y POR LO TANTO SE QUEDARÀ EN LA MISMA POSICIÓN QUE AL INICIO
                    // POR LO CONTRARIO COMO PASA DENTRO DE ESTE IF Y QUE EL REY PUEDA HACER EL MOVIMIENTO, LA CASILLA DÒNDE
                    // SE ENCONTRABA INICIALMENTE EL REI PASA A SER NULA
                } else {
                    // MANTENER TURNO BLANCASC YA QUE NO SE HA REALIZADO NINGUNA JUGADA PQ LA POSICIÓN SELECCIONADA ERA JAQUE Y
                    // POR LO TANTO EL REI NO PODÍA IR ALLI (LO PONGO EN FALSE (QUE ES TURNO DE LAS FICHAS NEGRAS) PORQUE POSTERIORMENTE
                    // EN EL MAIN SE HACE UN CAMBIO OTRA VEZ Y POR LO TANTO SERÁ TURNO DE LAS BLANCAS
                    tablero.turnoBlancoONegro = false
                }
            } else { // REINA NEGRA

                val reinaAux = Reina(false, Posicion(posInicialY, posInicialX), true)

                tablero.tablero[posInicialY][posInicialX] = reinaAux // PARA QUE SE VEA LA REINA MARCADA COMO SELECCIONADO
                tablero.printarTabero()

                // PARA PRINTAR LA LETRA I EL NUMERO CUANDO RECOMENDAMOS UN MOVIMIENTO
                val pairPosicionesPrintar = tablero.retornarMovimientosPosiblesDos(posInicialY, posInicialX, reiEncontrado)
                print("TUS MOVIMIENTOS POSIBLES: ")
                for (elem in pairPosicionesPrintar) {
                    print("[${reinaAux.convertorNumeroALetraNumero(elem.first, elem.second)}]")
                }
                println()

                reinaAux.movimientoReina(tablero, reiEncontrado)

                reinaAux.seleccionada = false
                tablero.tablero[reinaAux.posicionFicha.fila][reinaAux.posicionFicha.columna] = reinaAux

                if (posInicialY != reinaAux.posicionFicha.fila || posInicialX != reinaAux.posicionFicha.columna) {
                    tablero.tablero[posInicialY][posInicialX] = null
                } else {
                    // MANTENER TURNO NEGRAS YA QUE NO SE HA REALIZADO NINGUNA JUGADA PQ LA POSICIÓN SELECCIONADA ERA JAQUE Y
                    // POR LO TANTO EL REI NO PODÍA IR ALLI (LO PONGO EN TRUE (QUE ES TURNO DE LAS FICHAS BLANCAS) PORQUE POSTERIORMENTE
                    // EN EL MAIN SE HACE UN CAMBIO OTRA VEZ Y POR LO TANTO SERÁ TURNO DE LAS NEGRAS
                    tablero.turnoBlancoONegro = true
                }
            }

        } else if (tablero.tablero[posInicialY][posInicialX] is Rei) {

            if (tablero.turnoBlancoONegro) { // REI BLANCO

                val reiAux = Rei(true, Posicion(posInicialY, posInicialX), true)

                tablero.tablero[posInicialY][posInicialX] = reiAux // PARA QUE SE VEA EL REI MARCADO COMO SELECCIONADO
                tablero.printarTabero()

                // PARA PRINTAR LA LETRA I EL NUMERO CUANDO RECOMENDAMOS UN MOVIMIENTO
                val pairPosicionesPrintar = tablero.retornarMovimientosPosiblesDos(posInicialY, posInicialX, reiEncontrado)
                print("TUS MOVIMIENTOS POSIBLES: ")
                for (elem in pairPosicionesPrintar) {
                    print("[${reiAux.convertorNumeroALetraNumero(elem.first, elem.second)}]")
                }
                println()

                reiAux.movimientoRei(tablero)

                reiAux.seleccionada = false
                tablero.tablero[reiAux.posicionFicha.fila][reiAux.posicionFicha.columna] = reiAux
                if (posInicialY != reiAux.posicionFicha.fila || posInicialX != reiAux.posicionFicha.columna) {
                    tablero.tablero[posInicialY][posInicialX] =
                        null // SI EL REI HA CAMBIADO DE POSICIÓN MARCAR CON NULL SU POSICIÓN ANTIGUA
                    // ESTO ME SIRVE PARA PODER CONTROLAR EL JAQUE YA QUE SI EL MOVIMIENTO DEL REI SUPONE QUE VAYA ESTAR EN JAQUE
                    // EL REI NO PUEDE HACER ESE MOVIMIENTO Y POR LO TANTO SE QUEDARÀ EN LA MISMA POSICIÓN QUE AL INICIO
                    // POR LO CONTRARIO COMO PASA DENTRO DE ESTE IF Y QUE EL REY PUEDA HACER EL MOVIMIENTO, LA CASILLA DÒNDE
                    // SE ENCONTRABA INICIALMENTE EL REI PASA A SER NULA
                } else {
                    // MANTENER TURNO BLANCASC YA QUE NO SE HA REALIZADO NINGUNA JUGADA PQ LA POSICIÓN SELECCIONADA ERA JAQUE Y
                    // POR LO TANTO EL REI NO PODÍA IR ALLI (LO PONGO EN FALSE (QUE ES TURNO DE LAS FICHAS NEGRAS) PORQUE POSTERIORMENTE
                    // EN EL MAIN SE HACE UN CAMBIO OTRA VEZ Y POR LO TANTO SERÁ TURNO DE LAS BLANCAS
                    tablero.turnoBlancoONegro = false
                }
            } else { // REI NEGR0

                val reiAux = Rei(false, Posicion(posInicialY, posInicialX), true)

                tablero.tablero[posInicialY][posInicialX] = reiAux // PARA QUE SE VEA EL REI MARCADO COMO SELECCIONADO
                tablero.printarTabero()

                // PARA PRINTAR LA LETRA I EL NUMERO CUANDO RECOMENDAMOS UN MOVIMIENTO
                val pairPosicionesPrintar = tablero.retornarMovimientosPosiblesDos(posInicialY, posInicialX, reiEncontrado)
                print("TUS MOVIMIENTOS POSIBLES: ")
                for (elem in pairPosicionesPrintar) {
                    print("[${reiAux.convertorNumeroALetraNumero(elem.first, elem.second)}]")
                }
                println()

                reiAux.movimientoRei(tablero)

                reiAux.seleccionada = false
                tablero.tablero[reiAux.posicionFicha.fila][reiAux.posicionFicha.columna] = reiAux

                if (posInicialY != reiAux.posicionFicha.fila || posInicialX != reiAux.posicionFicha.columna) {
                    tablero.tablero[posInicialY][posInicialX] = null
                } else {
                    // MANTENER TURNO NEGRAS YA QUE NO SE HA REALIZADO NINGUNA JUGADA PQ LA POSICIÓN SELECCIONADA ERA JAQUE Y
                    // POR LO TANTO EL REI NO PODÍA IR ALLI (LO PONGO EN TRUE (QUE ES TURNO DE LAS FICHAS BLANCAS) PORQUE POSTERIORMENTE
                    // EN EL MAIN SE HACE UN CAMBIO OTRA VEZ Y POR LO TANTO SERÁ TURNO DE LAS NEGRAS
                    tablero.turnoBlancoONegro = true
                }
            }
        }
    }

}