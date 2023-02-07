class Tablero(var turnoBlancoONegro: Boolean) {

    val tablero: List<ArrayList<Ficha?>> // TABLERO DE AJEDREZ

    private val ficha = Ficha(true, Posicion(0, 0), false)
    private val rei = Rei(turnoBlancoONegro, Posicion(0, 0), false)

    val numFilas = 8 // FILAS TABLERO
    val numColumnas = 8 // COLUMNAS TABLERO


    init {



        this.tablero = jaquePastor()
    }


    operator fun get(i: Int, j: Int): Ficha? {
        return tablero[i][j]
    }


    fun actualizarTablero(): Boolean {

        val reiEncontrado = rei.buscarPosicionRei(this) // BUSCAMOS LA POSICION DEL REI DENTRO DEL TABLERO

        if (reiEncontrado.jaqueMate(this) || reiEncontrado.ahogado(this)){
            // SI HAY JAQUE MATE O AHOGADO RETORNAMOS TRUE (PARA PAUSAR EL BUCLE DE FUERA)
            return true
        }

        rei.printarJaque(this) // SI HAY JAQUE PRINTAS JAQUE (A QUE COLOR DE PIEZAS)

        ficha.actualizarFicha(this, reiEncontrado) // ACTUALIZAMOS POSICION DE LA FICHA

        return false
    }

    // ========================================================= // COMENTADO HASTA AQUÍ
    fun printarTabero() {

        var listaNumeros = 1
        val listaLetras = "..[A][B][C][D][E][F][G][H].."

        printTurnoBlancoONegro()
        print("\u001B[43m\u001B[30m$listaLetras\u001B[0m ")
        println()
        for (i in 0 until numFilas) { // AÑADO DOS MAS PARAS PODER MOSTRAR POR PANTALLA LAS LETRAS Y LOS NÚMEROS DEL TABLERO
            print("\u001B[43m\u001B[30m[$listaNumeros]\u001B[0m ")
            for (j in 0 until numColumnas) {
                // PINTO LAS FICHAS DEL TABLERO
                if (tablero[i][j] is Peon) {
                    if ((tablero[i][j]?.blanca_o_negra) == true) {
                        if ((tablero[i][j]?.seleccionada) == true) {
                            print("\u001B[43m\u001B[30m\u001B[4m♙\u001B[0m ") // COLOR ROJO

                        } else
                            print("\u001B[31m♙\u001B[0m ")
                    } else {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[7m\u001B[4m♙\u001B[0m ")
                        else
                            print("\u001B[34m♙\u001B[0m ")
                    }
                } else if (tablero[i][j] is Torre) {
                    if ((tablero[i][j]?.blanca_o_negra) == true) {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♜\u001B[0m ") // COLOR ROJO
                        else
                            print("\u001B[31m♜\u001B[0m ")
                    } else {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♜\u001B[0m ")
                        else
                            print("\u001B[34m♜\u001B[0m ")
                    }
                } else if (tablero[i][j] is Caballo) {
                    if ((tablero[i][j]?.blanca_o_negra) == true) {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♞\u001B[0m ") // COLOR ROJO
                        else
                            print("\u001B[31m♞\u001B[0m ")
                    } else {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♞\u001B[0m ")
                        else
                            print("\u001B[34m♞\u001B[0m ")
                    }
                } else if (tablero[i][j] is Alfil) {
                    if ((tablero[i][j]?.blanca_o_negra) == true) {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♝\u001B[0m ") // COLOR ROJO
                        else
                            print("\u001B[31m♝\u001B[0m ")
                    } else {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♝\u001B[0m ")
                        else
                            print("\u001B[34m♝\u001B[0m ")
                    }
                } else if (tablero[i][j] is Reina) {
                    if ((tablero[i][j]?.blanca_o_negra) == true) {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♛\u001B[0m ") // COLOR ROJO
                        else
                            print("\u001B[31m♛\u001B[0m ")
                    } else {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♛\u001B[0m ")
                        else
                            print("\u001B[34m♛\u001B[0m ")
                    }
                } else if (tablero[i][j] is Rei) {
                    if ((tablero[i][j]?.blanca_o_negra) == true) {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♚\u001B[0m ") // COLOR ROJO
                        else
                            print("\u001B[31m♚\u001B[0m ")
                    } else {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♚\u001B[0m ")
                        else
                            print("\u001B[34m♚\u001B[0m ")
                    }
                } else {
                    if (i % 2 == 0) {
                        if (j % 2 != 0) {
                            print("\u001B[40m\u001B[30m♙\u001B[0m ")
                        } else {
                            print("\u001B[47m\u001B[37m♙\u001B[0m ")
                        }
                    } else {
                        if (j % 2 == 0) {
                            print("\u001B[40m\u001B[30m♙\u001B[0m ")
                        } else {
                            print("\u001B[47m\u001B[37m♙\u001B[0m ")
                        }
                    }
                }

            }
            print("\u001B[43m\u001B[30m[$listaNumeros]\u001B[0m ")
            println()
            listaNumeros++
        }
        print("\u001B[43m\u001B[30m$listaLetras\u001B[0m ")
        println()
    }

    // ==================================================
    //COMENTADO
    private fun printTurnoBlancoONegro() { // PRINTAR SI ES EL TURNO DE LAS BLANCAS O DE LAS NEGRAS
        if (turnoBlancoONegro)
            println("\u001B[43m\u001B[30m\u001B[4m[¡ES TURNO DE LAS BLANCAS!] \u001B[0m ")
        else
            println("\u001B[43m\u001B[30m\u001B[4m[ ¡ES TURNO DE LAS NEGRAS! ]\u001B[0m ")
    }
    // ==================================================

    fun isKingInCheck(rei: Rei): Boolean { // PER QUAN HA MOGUT LA FICHA CONTRARIA SI EL REI ES TROBA EN JAQUE
        if (turnoBlancoONegro) {
            for (i in 0 until numFilas) {
                for (j in 0 until numColumnas) {
                    val ficha = this[i, j]
                    if (ficha != null) {
                        if (ficha is Peon && !ficha.blanca_o_negra && ficha.posibilidadesMovimientoPeon(rei.posicionFicha.fila, rei.posicionFicha.columna, this)) {

                            return true

                        } else if (ficha is Torre && !ficha.blanca_o_negra && ficha.posibilidadesMovimientoTorre(rei.posicionFicha.fila, rei.posicionFicha.columna, this)) {

                            return true

                        } else if (ficha is Alfil && !ficha.blanca_o_negra && ficha.posibilidadesMovimientoAlfil(rei.posicionFicha.fila, rei.posicionFicha.columna, this)) {

                            return true

                        } else if (ficha is Caballo && !ficha.blanca_o_negra && ficha.posibilidadesMovimientoCaballo(rei.posicionFicha.fila, rei.posicionFicha.columna, this)) {

                            return true

                        } else if (ficha is Reina && !ficha.blanca_o_negra && ficha.posibilidadesMovimientoReina(rei.posicionFicha.fila, rei.posicionFicha.columna, this)) {

                            return true

                        } else if (ficha is Rei && !ficha.blanca_o_negra && ficha.posibilidadesMovimientoRei(rei.posicionFicha.fila, rei.posicionFicha.columna, this)) {

                            return true

                        }
                    }
                }
            }
        } else {
            for (i in 0 until numFilas) {
                for (j in 0 until numColumnas) {
                    val ficha = this[i, j]
                    if (ficha != null) {
                        if (ficha is Peon && ficha.blanca_o_negra && ficha.posibilidadesMovimientoPeon(rei.posicionFicha.fila, rei.posicionFicha.columna, this)) {

                            return true

                        } else if (ficha is Torre && ficha.blanca_o_negra && ficha.posibilidadesMovimientoTorre(rei.posicionFicha.fila, rei.posicionFicha.columna, this)) {

                            return true

                        } else if (ficha is Alfil && ficha.blanca_o_negra && ficha.posibilidadesMovimientoAlfil(rei.posicionFicha.fila, rei.posicionFicha.columna, this)) {

                            return true

                        } else if (ficha is Caballo && ficha.blanca_o_negra && ficha.posibilidadesMovimientoCaballo(rei.posicionFicha.fila, rei.posicionFicha.columna, this)) {

                            return true

                        } else if (ficha is Reina && ficha.blanca_o_negra && ficha.posibilidadesMovimientoReina(rei.posicionFicha.fila, rei.posicionFicha.columna, this)) {

                            return true

                        } else if (ficha is Rei && ficha.blanca_o_negra && ficha.posibilidadesMovimientoRei(rei.posicionFicha.fila, rei.posicionFicha.columna, this)) {

                            return true

                        }
                    }
                }
            }
        }

        return false
    }

    // ============================================================================

    // FICHAS
    fun retornarMovimientosPosiblesTodasLasPiezas(piezasQueQuedan: ArrayList<Pair<Int, Int>>, rei: Rei): ArrayList<Pair<Int, Int>> { //
        val posicionesPosibles = ArrayList<Pair<Int, Int>>()

        for (elem in piezasQueQuedan) {
            val ficha = this[elem.first, elem.second]

            if (ficha is Peon) {
                for (i in 0..numFilas - 1) {
                    for (j in 0..numFilas - 1) {
                        if (ficha.posibilidadesMovimientoPeon(i, j, this)) {
                            if (isKingInCheck(rei)) {
                                val elementoInvadido = tablero[i][j]
                                tablero[i][j] = ficha
                                tablero[elem.first][elem.second] = null

                                if (!isKingInCheck(rei)) {
                                    posicionesPosibles.add(Pair(i, j))
                                }

                                tablero[i][j] = elementoInvadido
                                tablero[elem.first][elem.second] = ficha
                            } else {
                                val elementoInvadido = tablero[i][j]
                                tablero[i][j] = ficha
                                tablero[elem.first][elem.second] = null

                                if (!isKingInCheck(rei)) {
                                    posicionesPosibles.add(Pair(i, j))
                                }

                                tablero[i][j] = elementoInvadido
                                tablero[elem.first][elem.second] = ficha
                            }
                        }
                    }
                }
            } else if (ficha is Reina) {
                for (i in 0..numFilas - 1) {
                    for (j in 0..numFilas - 1) {
                        if (ficha.posibilidadesMovimientoReina(i, j, this)) {
                            if (isKingInCheck(rei)) {
                                val elementoInvadido = tablero[i][j]
                                tablero[i][j] = ficha
                                tablero[elem.first][elem.second] = null

                                if (!isKingInCheck(rei)) {
                                    posicionesPosibles.add(Pair(i, j))
                                }

                                tablero[i][j] = elementoInvadido
                                tablero[elem.first][elem.second] = ficha
                            } else {
                                val elementoInvadido = tablero[i][j]
                                tablero[i][j] = ficha
                                tablero[elem.first][elem.second] = null

                                if (!isKingInCheck(rei)) {
                                    posicionesPosibles.add(Pair(i, j))
                                }

                                tablero[i][j] = elementoInvadido
                                tablero[elem.first][elem.second] = ficha
                            }
                        }
                    }
                }
            } else if (ficha is Torre) {
                for (i in 0..numFilas - 1) {
                    for (j in 0..numFilas - 1) {
                        if (ficha.posibilidadesMovimientoTorre(i, j, this)) {
                            if (isKingInCheck(rei)) {
                                val elementoInvadido = tablero[i][j]
                                tablero[i][j] = ficha
                                tablero[elem.first][elem.second] = null

                                if (!isKingInCheck(rei)) {
                                    posicionesPosibles.add(Pair(i, j))
                                }

                                tablero[i][j] = elementoInvadido
                                tablero[elem.first][elem.second] = ficha
                            } else {
                                val elementoInvadido = tablero[i][j]
                                tablero[i][j] = ficha
                                tablero[elem.first][elem.second] = null

                                if (!isKingInCheck(rei)) {
                                    posicionesPosibles.add(Pair(i, j))
                                }

                                tablero[i][j] = elementoInvadido
                                tablero[elem.first][elem.second] = ficha
                            }
                        }
                    }
                }
            } else if (ficha is Caballo) {
                for (i in 0..numFilas - 1) {
                    for (j in 0..numFilas - 1) {
                        if (ficha.posibilidadesMovimientoCaballo(i, j, this)) {
                            if (isKingInCheck(rei)) {
                                val elementoInvadido = tablero[i][j]
                                tablero[i][j] = ficha
                                tablero[elem.first][elem.second] = null

                                if (!isKingInCheck(rei)) {
                                    posicionesPosibles.add(Pair(i, j))
                                }

                                tablero[i][j] = elementoInvadido
                                tablero[elem.first][elem.second] = ficha
                            } else {
                                val elementoInvadido = tablero[i][j]
                                tablero[i][j] = ficha
                                tablero[elem.first][elem.second] = null

                                if (!isKingInCheck(rei)) {
                                    posicionesPosibles.add(Pair(i, j))
                                }

                                tablero[i][j] = elementoInvadido
                                tablero[elem.first][elem.second] = ficha
                            }
                        }
                    }
                }
            } else if (ficha is Alfil) {
                for (i in 0..numFilas - 1) {
                    for (j in 0..numFilas - 1) {
                        if (ficha.posibilidadesMovimientoAlfil(i, j, this)) {
                            if (isKingInCheck(rei)) {
                                val elementoInvadido = tablero[i][j]
                                tablero[i][j] = ficha
                                tablero[elem.first][elem.second] = null

                                if (!isKingInCheck(rei)) {
                                    posicionesPosibles.add(Pair(i, j))
                                }

                                tablero[i][j] = elementoInvadido
                                tablero[elem.first][elem.second] = ficha
                            } else {
                                val elementoInvadido = tablero[i][j]
                                tablero[i][j] = ficha
                                tablero[elem.first][elem.second] = null

                                if (!isKingInCheck(rei)) {
                                    posicionesPosibles.add(Pair(i, j))
                                }

                                tablero[i][j] = elementoInvadido
                                tablero[elem.first][elem.second] = ficha
                            }
                        }
                    }
                }
            } else if (ficha is Rei) {
                for (i in 0..numFilas - 1) {
                    for (j in 0..numFilas - 1) {
                        if (ficha.posibilidadesMovimientoRei(i, j, this)) {
                            if (isKingInCheck(rei)) {

                                val elementoInvadido = tablero[i][j]
                                tablero[i][j] = ficha
                                tablero[elem.first][elem.second] = null
                                val guardarY = ficha.posicionFicha.fila
                                val guardarX = ficha.posicionFicha.columna
                                ficha.posicionFicha.fila = i
                                ficha.posicionFicha.columna = j

                                if (!isKingInCheck(ficha)) {
                                    posicionesPosibles.add(Pair(i, j))
                                }

                                ficha.posicionFicha.fila = guardarY
                                ficha.posicionFicha.columna = guardarX
                                tablero[elem.first][elem.second] = ficha
                                tablero[i][j] = elementoInvadido
                            } else {

                                val elementoInvadido = tablero[i][j]
                                tablero[i][j] = ficha
                                tablero[elem.first][elem.second] = null
                                val guardarY = ficha.posicionFicha.fila
                                val guardarX = ficha.posicionFicha.columna
                                ficha.posicionFicha.fila = i
                                ficha.posicionFicha.columna = j

                                if (!isKingInCheck(ficha)) {
                                    posicionesPosibles.add(Pair(i, j))
                                }

                                ficha.posicionFicha.fila = guardarY
                                ficha.posicionFicha.columna = guardarX
                                tablero[elem.first][elem.second] = ficha
                                tablero[i][j] = elementoInvadido
                            }
                        }
                    }
                }
            }
        }
        return posicionesPosibles
    }

        fun retornarMovimientosPosiblesDos(posicionY: Int, posicionX: Int, rei: Rei): ArrayList<Pair<Int, Int>> {
        val posicionesPosibles = ArrayList<Pair<Int, Int>>()
        val ficha = this[posicionY, posicionX]

        if (ficha is Peon) {
            for (i in 0..numFilas - 1) {
                for (j in 0..numFilas - 1) {
                    if (ficha.posibilidadesMovimientoPeon(i, j, this)) {
                        if (isKingInCheck(rei)) {
                            val elementoInvadido = tablero[i][j]
                            tablero[i][j] = ficha
                            tablero[posicionY][posicionX] = null

                            if (!isKingInCheck(rei)) {
                                posicionesPosibles.add(Pair(i, j))
                            }

                            tablero[i][j] = elementoInvadido
                            tablero[posicionY][posicionX] = ficha
                        } else {
                            val elementoInvadido = tablero[i][j]
                            tablero[i][j] = ficha
                            tablero[posicionY][posicionX] = null

                            if (!isKingInCheck(rei)) {
                                posicionesPosibles.add(Pair(i, j))
                            }

                            tablero[i][j] = elementoInvadido
                            tablero[posicionY][posicionX] = ficha
                        }
                    }
                }
            }
        } else if (ficha is Reina) {
            for (i in 0..numFilas - 1) {
                for (j in 0..numFilas - 1) {
                    if (ficha.posibilidadesMovimientoReina(i, j, this)) {
                        if (isKingInCheck(rei)) {
                            val elementoInvadido = tablero[i][j]
                            tablero[i][j] = ficha
                            tablero[posicionY][posicionX] = null

                            if (!isKingInCheck(rei)) {
                                posicionesPosibles.add(Pair(i, j))
                            }

                            tablero[i][j] = elementoInvadido
                            tablero[posicionY][posicionX] = ficha
                        } else {
                            val elementoInvadido = tablero[i][j]
                            tablero[i][j] = ficha
                            tablero[posicionY][posicionX] = null

                            if (!isKingInCheck(rei)) {
                                posicionesPosibles.add(Pair(i, j))
                            }

                            tablero[i][j] = elementoInvadido
                            tablero[posicionY][posicionX] = ficha
                        }
                    }
                }
            }
        } else if (ficha is Alfil) {
            for (i in 0..numFilas - 1) {
                for (j in 0..numFilas - 1) {
                    if (ficha.posibilidadesMovimientoAlfil(i, j, this)) {
                        if (isKingInCheck(rei)) {
                            val elementoInvadido = tablero[i][j]
                            tablero[i][j] = ficha
                            tablero[posicionY][posicionX] = null

                            if (!isKingInCheck(rei)) {
                                posicionesPosibles.add(Pair(i, j))
                            }

                            tablero[i][j] = elementoInvadido
                            tablero[posicionY][posicionX] = ficha
                        } else {
                            val elementoInvadido = tablero[i][j]
                            tablero[i][j] = ficha
                            tablero[posicionY][posicionX] = null

                            if (!isKingInCheck(rei)) {
                                posicionesPosibles.add(Pair(i, j))
                            }

                            tablero[i][j] = elementoInvadido
                            tablero[posicionY][posicionX] = ficha
                        }
                    }
                }
            }
        }
        if (ficha is Caballo) {
            for (i in 0..numFilas - 1) {
                for (j in 0..numFilas - 1) {
                    if (ficha.posibilidadesMovimientoCaballo(i, j, this)) {
                        if (isKingInCheck(rei)) {
                            val elementoInvadido = tablero[i][j]
                            tablero[i][j] = ficha
                            tablero[posicionY][posicionX] = null

                            if (!isKingInCheck(rei)) {
                                posicionesPosibles.add(Pair(i, j))
                            }

                            tablero[i][j] = elementoInvadido
                            tablero[posicionY][posicionX] = ficha
                        } else {
                            val elementoInvadido = tablero[i][j]
                            tablero[i][j] = ficha
                            tablero[posicionY][posicionX] = null

                            if (!isKingInCheck(rei)) {
                                posicionesPosibles.add(Pair(i, j))
                            }

                            tablero[i][j] = elementoInvadido
                            tablero[posicionY][posicionX] = ficha
                        }
                    }
                }
            }
        }
        if (ficha is Torre) {
            for (i in 0..numFilas - 1) {
                for (j in 0..numFilas - 1) {
                    if (ficha.posibilidadesMovimientoTorre(i, j, this)) {
                        if (isKingInCheck(rei)) {
                            val elementoInvadido = tablero[i][j]
                            tablero[i][j] = ficha
                            tablero[posicionY][posicionX] = null

                            if (!isKingInCheck(rei)) {
                                posicionesPosibles.add(Pair(i, j))
                            }

                            tablero[i][j] = elementoInvadido
                            tablero[posicionY][posicionX] = ficha
                        } else {
                            val elementoInvadido = tablero[i][j]
                            tablero[i][j] = ficha
                            tablero[posicionY][posicionX] = null

                            if (!isKingInCheck(rei)) {
                                posicionesPosibles.add(Pair(i, j))
                            }

                            tablero[i][j] = elementoInvadido
                            tablero[posicionY][posicionX] = ficha
                        }
                    }
                }
            }
        } else if (ficha is Rei) {
            for (i in 0..numFilas - 1) {
                for (j in 0..numFilas - 1) {
                    if (ficha.posibilidadesMovimientoRei(i, j, this)) {
                        if (isKingInCheck(rei)) {

                            val elementoInvadido = tablero[i][j]
                            tablero[i][j] = ficha
                            tablero[posicionY][posicionX] = null
                            val guardarY = ficha.posicionFicha.fila
                            val guardarX = ficha.posicionFicha.columna
                            ficha.posicionFicha.fila = i
                            ficha.posicionFicha.columna = j

                            if (!isKingInCheck(ficha)) {
                                posicionesPosibles.add(Pair(i, j))
                            }

                            ficha.posicionFicha.fila = guardarY
                            ficha.posicionFicha.columna = guardarX
                            tablero[posicionY][posicionX] = ficha
                            tablero[i][j] = elementoInvadido
                        } else {

                            val elementoInvadido = tablero[i][j]
                            tablero[i][j] = ficha
                            tablero[posicionY][posicionX] = null
                            val guardarY = ficha.posicionFicha.fila
                            val guardarX = ficha.posicionFicha.columna
                            ficha.posicionFicha.fila = i
                            ficha.posicionFicha.columna = j

                            if (!isKingInCheck(ficha)) {
                                posicionesPosibles.add(Pair(i, j))
                            }

                            ficha.posicionFicha.fila = guardarY
                            ficha.posicionFicha.columna = guardarX
                            tablero[posicionY][posicionX] = ficha
                            tablero[i][j] = elementoInvadido
                        }
                    }
                }
            }
        }

        return posicionesPosibles
    }

    // PRESENTACIÓ

    // NORMAL

    fun distNormal(): ArrayList<ArrayList<Ficha?>>{
        val tablero = arrayListOf(arrayListOf<Ficha?>()) // TABLERO QUE SE USARA DURANTE TODO EL JUEGO

        for (i in 0 until numFilas) { // INICIALIZAMOS EL TABLERO CON LAS FICHAS CORRESPONDIENTES
            tablero.add(arrayListOf())
            for (j in 0 until numColumnas) {
                // ====================================
                // CREO LAS FICHAS DEL TABLERO
                if (i == 1) { // CREO PEON BLANCO
                    tablero[i].add(Peon(true, Posicion(i, j), false))
                } else if (i == 6) { // CREO PEON NEGRO
                    tablero[i].add(Peon(false, Posicion(i, j), false))
                } else if (i == 0 && (j == 0 || j == 7)) { // CREO TORRE BLANCA
                    tablero[i].add(Torre(true, Posicion(i, j), false))
                } else if (i == 7 && (j == 0 || j == 7)) { // CREO TORRE NEGRA
                    tablero[i].add(Torre(false, Posicion(i, j), false))
                } else if (i == 0 && (j == 1 || j == 6)) { // CREO CABALLO BLANCO
                    tablero[i].add(Caballo(true, Posicion(i, j), false))
                } else if (i == 7 && (j == 1 || j == 6)) { // CREO CABALLO NEGRO
                    tablero[i].add(Caballo(false, Posicion(i, j), false))
                } else if (i == 0 && (j == 2 || j == 5)) { // CREO ALFIL BLANCO
                    tablero[i].add(Alfil(true, Posicion(i, j), false))
                } else if (i == 7 && (j == 2 || j == 5)) { // CREO ALFIL NEGRO
                    tablero[i].add(Alfil(false, Posicion(i, j), false))
                } else if (i == 0 && j == 4) { // CREO REINA BLANCA
                    tablero[i].add(Reina(true, Posicion(i, j), false))
                } else if (i == 7 && j == 4) { // CREO REINA NEGRA
                    tablero[i].add(Reina(false, Posicion(i, j), false))
                } else if (i == 0 && j == 3) { // CREO REI BLANCO
                    tablero[i].add(Rei(true, Posicion(i, j), false))
                } else if (i == 7 && j == 3) { // CREO REI NEGRO
                    tablero[i].add(Rei(false, Posicion(i, j), false))
                }else {
                    tablero[i].add(null) // RESTO DE POSICIONES EN LAS CUÁLES NO HAY FICHAS
                }
            }
            println()
        }

        return tablero
    }


    fun distJaque(): ArrayList<ArrayList<Ficha?>>{
        val tablero = arrayListOf(arrayListOf<Ficha?>()) // TABLERO QUE SE USARA DURANTE TODO EL JUEGO

        for (i in 0 until numFilas) { // INICIALIZAMOS EL TABLERO CON LAS FICHAS CORRESPONDIENTES
            tablero.add(arrayListOf())
            for (j in 0 until numColumnas) {
                // ====================================
                // CREO LAS FICHAS DEL TABLERO
               if (i == 0 && (j == 0)) { // CREO TORRE BLANCA
                    tablero[i].add(Torre(true, Posicion(i, j), false))
                } else if (i == 3 && (j == 6)) { // CREO CABALLO NEGRO
                    tablero[i].add(Caballo(false, Posicion(i, j), false))
                } else if (i == 1 && (j == 3)) { // CREO ALFIL BLANCO
                    tablero[i].add(Alfil(true, Posicion(i, j), false))
                } else if (i == 7 && j == 7) { // CREO REINA NEGRA
                    tablero[i].add(Reina(false, Posicion(i, j), false))
                } else if (i == 0 && j == 3) { // CREO REI BLANCO
                    tablero[i].add(Rei(true, Posicion(i, j), false))
                } else if (i == 7 && j == 3) { // CREO REI NEGRO
                    tablero[i].add(Rei(false, Posicion(i, j), false))
                }else {
                    tablero[i].add(null) // RESTO DE POSICIONES EN LAS CUÁLES NO HAY FICHAS
                }
            }
            println()
        }

        return tablero
    }

    fun distPeones(): ArrayList<ArrayList<Ficha?>>{
        val tablero = arrayListOf(arrayListOf<Ficha?>()) // TABLERO QUE SE USARA DURANTE TODO EL JUEGO

        for (i in 0 until numFilas) { // INICIALIZAMOS EL TABLERO CON LAS FICHAS CORRESPONDIENTES
            tablero.add(arrayListOf())
            for (j in 0 until numColumnas) {
                // ====================================
                // CREO LAS FICHAS DEL TABLERO
                if (i == 6 && j == 0) { // CREO PEON BLANCO
                    tablero[i].add(Peon(true, Posicion(i, j), false))
                } else if (i == 1 && j == 7) { // CREO PEON NEGRO
                    tablero[i].add(Peon(false, Posicion(i, j), false))
                } else if (i == 0 && j == 3) { // CREO REI BLANCO
                    tablero[i].add(Rei(true, Posicion(i, j), false))
                } else if (i == 7 && j == 3) { // CREO REI NEGRO
                    tablero[i].add(Rei(false, Posicion(i, j), false))
                }else {
                    tablero[i].add(null) // RESTO DE POSICIONES EN LAS CUÁLES NO HAY FICHAS
                }
            }
            println()
        }

        return tablero
    }
    fun distJaqueMateAhogado(): ArrayList<ArrayList<Ficha?>>{
        val tablero = arrayListOf(arrayListOf<Ficha?>()) // TABLERO QUE SE USARA DURANTE TODO EL JUEGO

        for (i in 0 until numFilas) { // INICIALIZAMOS EL TABLERO CON LAS FICHAS CORRESPONDIENTES
            tablero.add(arrayListOf())
            for (j in 0 until numColumnas) {
                // ====================================
                // CREO LAS FICHAS DEL TABLERO
                if (i == 6 && j == 5) { // CREO REINA BLANCA
                    tablero[i].add(Reina(true, Posicion(i, j), false))
                } else if (i == 5 && j == 0) { // CREO REI BLANCO
                    tablero[i].add(Rei(true, Posicion(i, j), false))
                } else if (i == 7 && j == 0) { // CREO REI NEGRO
                    tablero[i].add(Rei(false, Posicion(i, j), false))
                }else {
                    tablero[i].add(null) // RESTO DE POSICIONES EN LAS CUÁLES NO HAY FICHAS
                }
            }
            println()
        }

        return tablero
    }


    fun jaquePastor(): ArrayList<ArrayList<Ficha?>>{
        val tablero = arrayListOf(arrayListOf<Ficha?>()) // TABLERO QUE SE USARA DURANTE TODO EL JUEGO

        for (i in 0 until numFilas) { // INICIALIZAMOS EL TABLERO CON LAS FICHAS CORRESPONDIENTES
            tablero.add(arrayListOf())
            for (j in 0 until numColumnas) {
                // ====================================
                // CREO LAS FICHAS DEL TABLERO
                if (i == 1) { // CREO PEON BLANCO
                    tablero[i].add(Peon(true, Posicion(i, j), false))
                } else if (i == 6) { // CREO PEON NEGRO
                    tablero[i].add(Peon(false, Posicion(i, j), false))
                } else if (i == 0 && (j == 0 || j == 7)) { // CREO TORRE BLANCA
                    tablero[i].add(Torre(true, Posicion(i, j), false))
                } else if (i == 7 && (j == 0 || j == 7)) { // CREO TORRE NEGRA
                    tablero[i].add(Torre(false, Posicion(i, j), false))
                } else if (i == 0 && (j == 1 || j == 6)) { // CREO CABALLO BLANCO
                    tablero[i].add(Caballo(true, Posicion(i, j), false))
                } else if (i == 7 && (j == 1 || j == 6)) { // CREO CABALLO NEGRO
                    tablero[i].add(Caballo(false, Posicion(i, j), false))
                } else if (i == 3 && (j == 5)) { // CREO ALFIL BLANCO
                    tablero[i].add(Alfil(true, Posicion(i, j), false))
                } else if (i == 7 && (j == 2 || j == 5)) { // CREO ALFIL NEGRO
                    tablero[i].add(Alfil(false, Posicion(i, j), false))
                } else if (i == 2 && j == 2) { // CREO REINA BLANCA
                    tablero[i].add(Reina(true, Posicion(i, j), false))
                } else if (i == 7 && j == 4) { // CREO REINA NEGRA
                    tablero[i].add(Reina(false, Posicion(i, j), false))
                } else if (i == 0 && j == 3) { // CREO REI BLANCO
                    tablero[i].add(Rei(true, Posicion(i, j), false))
                } else if (i == 7 && j == 3) { // CREO REI NEGRO
                    tablero[i].add(Rei(false, Posicion(i, j), false))
                }else {
                    tablero[i].add(null) // RESTO DE POSICIONES EN LAS CUÁLES NO HAY FICHAS
                }
            }
            println()
        }

        return tablero
    }




}

