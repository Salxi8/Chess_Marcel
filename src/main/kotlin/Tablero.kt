class Tablero {

    val tablero : List<ArrayList<Ficha?>>

    private val numFilas = 8
    private val numColumnas = 8


    init {
        val tablero = arrayListOf(arrayListOf<Ficha?>())
        //val letrasTablero = listOf<Char>('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H') // PREGUNTAR QUE ES MILLOR

        for(i in 0 until numFilas){ // AÑADO DOS MAS PARAS PODER MOSTRAR POR PANTALLA LAS LETRAS Y LOS NÚMEROS DEL TABLERO
            tablero.add(arrayListOf())
            for(j in 0 until numColumnas){
                // ====================================
                // CREO TODOS LOS CONTORNOS DEL TABLERO
                /*if (i == 0){
                    if(j == 0 || j == numColumnas + 1)
                        print("  ")
                    else
                        print("·${'A'+ (j - 1)} ")
                }
                else if (i == numFilas + 1){
                    if(j == 0 || j == numColumnas + 1)
                        print("  ")
                    else
                        print("·${'A'+ (j - 1)} ")
                }
                else if (j == 0){
                    print("$i ")
                }
                else if (j == numColumnas + 1){
                    print("$i ")
                }*/
                // ====================================
                // CREO LAS FICHAS DEL TABLERO
                if (i == 1){
                    tablero[i].add(Peon(true, Posicion(i, j))) // CREO PEON BLANCO
                }
                else if (i == 6){
                    tablero[i].add(Peon(false, Posicion(i, j))) // CREO PEON NEGRO
                }
                else if(i == 0 && (j == 0 || j == 7)){
                    tablero[i].add(Torre(true, Posicion(i, j))) // CREO TORRE BLANCA
                }
                else if(i == 7 && (j == 0 || j == 7)){
                    tablero[i].add(Torre(false, Posicion(i, j))) // CREO TORRE NEGRA
                }
                else if(i == 0 && (j == 1 || j == 6)){
                    tablero[i].add(Caballo(true, Posicion(i, j))) // CREO CABALLO BLANCO
                }
                else if(i == 7 && (j == 1 || j == 6)){
                    tablero[i].add(Caballo(false, Posicion(i, j))) // CREO CABALLO NEGRO
                }
                else if(i == 0 && (j == 5 || j == 2)){
                    tablero[i].add(Alfil(true, Posicion(i, j))) // CREO ALFIL BLANCO
                }
                else if(i == 7 && (j == 2 || j == 5)){
                    tablero[i].add(Alfil(false, Posicion(i, j))) // CREO ALFIL NEGRO
                }
                else if(i == 0 && j == 3){
                    tablero[i].add(Reina(true, Posicion(i, j))) // CREO ALFIL BLANCO
                }
                else if(i == 7 && j == 3){
                    tablero[i].add(Reina(false, Posicion(i, j))) // CREO ALFIL NEGRO
                }
                else if(i == 0 && j == 4){
                    tablero[i].add(Rei(true, Posicion(i, j))) // CREO ALFIL BLANCO
                }
                else if(i == 7 && j == 4){
                    tablero[i].add(Rei(false, Posicion(i, j))) // CREO ALFIL NEGRO
                }
                else {
                    tablero[i].add(null) // RESTO DE POSICIONES EN LAS CUÁLES NO HAY FICHAS

                }

            }
            println()
        }

        this.tablero = tablero
    }


    operator fun get(i: Int, j: Int): Ficha? {
        return tablero[i][j]
    }


    fun actualizarTablero(turnoBlancoNegro: Boolean) {

        // APUNTES: MIRAR SI ESTO SE PUEDE HACER EN OTRA FUNCIÓN PRIVADA - seleccionarFichaMover()

        var posInicialY = 0
        var posInicialX = 0

        do { // COMPRUEBO QUE HAYA UNA FICHA USABLE EN LA CASILLA SELECCIONADA
            print("En que FILA se encuentra la FICHA que desea MOVER: ")
            posInicialY = readln().toIntOrNull()?:-1 // PIDO LA FILA EN QUE SE ENCUENTRA LA FICHA EN LA MATRIZ
            print("En que COLUMNA se encuentra la FICHA que desea MOVER: ")
            posInicialX = readln().toIntOrNull()?:-1 // PIDO LA COLUMNA EN QUE SE ENCUENTRA LA FICHA EN LA MATRIZ

            if ((posInicialX < 0 || posInicialX > 7) || (posInicialY < 0 || posInicialY > 7)) { // WARNING DE QUE LA POSICIÓN SELECCIONADA NO FORMA PARTE DEL TABLERO
                println("La casilla [${posInicialY}, ${posInicialX}] no es una casilla válida dentro del tablero de ajedrez")
            }
            else if(tablero[posInicialY][posInicialX] == null){ // WARNING DE QUE LA POSICIÓN SELECCIONADA FORMA PARTE DEL TABLERO PERO NO CONTIENE NINGUNA FICHA
                println("En la casilla [${posInicialY}, ${posInicialX}] no se encuentra ninguna ficha")
            }
            else if(tablero[posInicialY][posInicialX]?.blanca_o_negra  != turnoBlancoNegro){ // WARNING DE QUE LA POSICIÓN SELECCIONADA FORMA PARTE DEL TABLERO,
                if (turnoBlancoNegro == true)                                                // PERO ESTA FICHA ES LA DEL OPONENTE
                    println("La casilla [${posInicialY}, ${posInicialX}] no corresponde a ninguna ficha BLANCA")
                else
                    println("La casilla [${posInicialY}, ${posInicialX}] no corresponde a ninguna ficha NEGRA")
            }
        }while((posInicialX < 0 || posInicialX > 7) || (posInicialY < 0 || posInicialY > 7) || tablero[posInicialY][posInicialX] == null || (tablero[posInicialY][posInicialX]?.blanca_o_negra  != turnoBlancoNegro))

        // =============================================================

        if (tablero[posInicialY][posInicialX] is Peon){

            if (turnoBlancoNegro) { // PEON BLANCO

                val peonAux = Peon(true, Posicion(posInicialY, posInicialX))
                peonAux.seleccionada = true

                tablero[posInicialY][posInicialX] = peonAux // PARA QUE SE VEA EL PEÓN MARCADO COMO SELECCIONADO
                printarTabero(turnoBlancoNegro) // PARA QUE SE VEA EL PEÓN MARCADO COMO SELECCIONADO

                peonAux.movimientoPeon(peonAux, this) // LA FUNCION NECESITA RETORNAR UN PEON?????????
                // PODER TIRAR ATRAS SI NOS HEMOS EQUIVOCADO DE FICHA O QUE DIRECTAMENTE EL SISTEMA TE DIGA QUE ESA FICHA

                peonAux.seleccionada = false
                tablero[peonAux.posicionFicha.fila][peonAux.posicionFicha.columna] = peonAux
            }
            else{ // PEON NEGRO

                val peonAux = Peon(false, Posicion(posInicialY, posInicialX))
                peonAux.seleccionada = true;

                tablero[posInicialY][posInicialX] = peonAux // PARA QUE SE VEA EL PEÓN MARCADO COMO SELECCIONADO
                printarTabero(turnoBlancoNegro) // PARA QUE SE VEA EL PEÓN MARCADO COMO SELECCIONADO

                peonAux.movimientoPeon(peonAux, this)
                // PODER TIRAR ATRAS SI NOS HEMOS EQUIVOCADO DE FICHA O QUE DIRECTAMENTE EL SISTEMA TE DIGA QUE ESA FICHA

                peonAux.seleccionada = false
                tablero[peonAux.posicionFicha.fila][peonAux.posicionFicha.columna] = peonAux

            }
            tablero[posInicialY][posInicialX] = null // POSICIÓN DONDE SE ENCONTRABA LA PIEZA QUEDA NULLA
        }

        else if (tablero[posInicialY][posInicialX] is Torre){

            if (turnoBlancoNegro) { // TORRE BLANCA

                val torreAux = Torre(true, Posicion(posInicialY, posInicialX))
                torreAux.seleccionada = true

                tablero[posInicialY][posInicialX] = torreAux // PARA QUE SE VEA LA TORRE MARCADA COMO SELECCIONADA
                printarTabero(turnoBlancoNegro) // PARA QUE SE VEA LA TORRE MARCADA COMO SELECCIONADA

                torreAux.movimientoTorre(torreAux, this) // LA FUNCION NECESITA RETORNAR UN PEON?????????
                // PODER TIRAR ATRAS SI NOS HEMOS EQUIVOCADO DE FICHA O QUE DIRECTAMENTE EL SISTEMA TE DIGA QUE ESA FICHA

                torreAux.seleccionada = false
                tablero[torreAux.posicionFicha.fila][torreAux.posicionFicha.columna] = torreAux
            }
            else{ // TORRE NEGRA

                val torreAux = Torre(false, Posicion(posInicialY, posInicialX))
                torreAux.seleccionada = true

                tablero[posInicialY][posInicialX] = torreAux // PARA QUE SE VEA LA TORRE MARCADA COMO SELECCIONADA
                printarTabero(turnoBlancoNegro) // PARA QUE SE VEA LA TORRE MARCADA COMO SELECCIONADA

                torreAux.movimientoTorre(torreAux, this) // LA FUNCION NECESITA RETORNAR UN PEON?????????
                // PODER TIRAR ATRAS SI NOS HEMOS EQUIVOCADO DE FICHA O QUE DIRECTAMENTE EL SISTEMA TE DIGA QUE ESA FICHA

                torreAux.seleccionada = false
                tablero[torreAux.posicionFicha.fila][torreAux.posicionFicha.columna] = torreAux
            }
            tablero[posInicialY][posInicialX] = null // POSICIÓN DONDE SE ENCONTRABA LA PIEZA QUEDA NULLA
        }

        else if (tablero[posInicialY][posInicialX] is Caballo){

            if (turnoBlancoNegro) { // CABALLO BLANCO

                val caballoAux = Caballo(true, Posicion(posInicialY, posInicialX))
                caballoAux.seleccionada = true

                tablero[posInicialY][posInicialX] = caballoAux // PARA QUE SE VEA EL CABALLO MARCADO COMO SELECCIONADO
                printarTabero(turnoBlancoNegro)

                caballoAux.movimientoCaballo(caballoAux, this) // LA FUNCION NECESITA RETORNAR UN PEON?????????
                // PODER TIRAR ATRAS SI NOS HEMOS EQUIVOCADO DE FICHA O QUE DIRECTAMENTE EL SISTEMA TE DIGA QUE ESA FICHA

                caballoAux.seleccionada = false
                tablero[caballoAux.posicionFicha.fila][caballoAux.posicionFicha.columna] = caballoAux
            }
            else{ // CABALLO NEGRO

                val caballoAux = Caballo(false, Posicion(posInicialY, posInicialX))
                caballoAux.seleccionada = true

                tablero[posInicialY][posInicialX] = caballoAux // PARA QUE SE VEA EL CABALLO MARCADO COMO SELECCIONADO
                printarTabero(turnoBlancoNegro)

                caballoAux.movimientoCaballo(caballoAux, this) // LA FUNCION NECESITA RETORNAR UN PEON?????????
                // PODER TIRAR ATRAS SI NOS HEMOS EQUIVOCADO DE FICHA O QUE DIRECTAMENTE EL SISTEMA TE DIGA QUE ESA FICHA

                caballoAux.seleccionada = false
                tablero[caballoAux.posicionFicha.fila][caballoAux.posicionFicha.columna] = caballoAux
            }
            tablero[posInicialY][posInicialX] = null // POSICIÓN DONDE SE ENCONTRABA LA PIEZA QUEDA NULLA
        }

        else if (tablero[posInicialY][posInicialX] is Alfil){

            if (turnoBlancoNegro) { // ALFIL BLANCO

                val alfilAux = Alfil(true, Posicion(posInicialY, posInicialX))
                alfilAux.seleccionada = true

                tablero[posInicialY][posInicialX] = alfilAux // PARA QUE SE VEA EL ALFIL MARCADO COMO SELECCIONADO
                printarTabero(turnoBlancoNegro)

                alfilAux.movimientoAlfil(alfilAux, this) // LA FUNCION NECESITA RETORNAR UN PEON?????????
                // PODER TIRAR ATRAS SI NOS HEMOS EQUIVOCADO DE FICHA O QUE DIRECTAMENTE EL SISTEMA TE DIGA QUE ESA FICHA
                // NO SE PUEDE MOVER A NINGUN LADO

                alfilAux.seleccionada = false
                tablero[alfilAux.posicionFicha.fila][alfilAux.posicionFicha.columna] = alfilAux

            }
            else{ // ALFIL NEGRO

                val alfilAux = Alfil(false, Posicion(posInicialY, posInicialX))
                alfilAux.seleccionada = true

                tablero[posInicialY][posInicialX] = alfilAux // PARA QUE SE VEA EL ALFIL MARCADO COMO SELECCIONADO
                printarTabero(turnoBlancoNegro) // PARA QUE SE VEA EL ALFIL MARCADO COMO SELECCIONADO

                alfilAux.movimientoAlfil(alfilAux, this) // LA FUNCION NECESITA RETORNAR UN PEON?????????
                // PODER TIRAR ATRAS SI NOS HEMOS EQUIVOCADO DE FICHA O QUE DIRECTAMENTE EL SISTEMA TE DIGA QUE ESA FICHA

                alfilAux.seleccionada = false
                tablero[alfilAux.posicionFicha.fila][alfilAux.posicionFicha.columna] = alfilAux
            }
            tablero[posInicialY][posInicialX] = null // POSICIÓN DONDE SE ENCONTRABA LA PIEZA QUEDA NULLA
        }
        else if (tablero[posInicialY][posInicialX] is Reina){

            if (turnoBlancoNegro) { // REINA BLANCA

                val reinaAux = Reina(true, Posicion(posInicialY, posInicialX))
                reinaAux.seleccionada = true

                tablero[posInicialY][posInicialX] = reinaAux // PARA QUE SE VEA LA REINA MARCADO COMO SELECCIONADA
                printarTabero(turnoBlancoNegro)

                reinaAux.movimientoReina(reinaAux, this) // LA FUNCION NECESITA RETORNAR UN PEON?????????
                // PODER TIRAR ATRAS SI NOS HEMOS EQUIVOCADO DE FICHA O QUE DIRECTAMENTE EL SISTEMA TE DIGA QUE ESA FICHA
                // NO SE PUEDE MOVER A NINGUN LADO

                reinaAux.seleccionada = false
                tablero[reinaAux.posicionFicha.fila][reinaAux.posicionFicha.columna] = reinaAux
            }
            else{ // REINA NEGRA

                val reinaAux = Reina(false, Posicion(posInicialY, posInicialX))
                reinaAux.seleccionada = true

                tablero[posInicialY][posInicialX] = reinaAux // PARA QUE SE VEA LA REINA MARCADA COMO SELECCIONADO
                printarTabero(turnoBlancoNegro)

                reinaAux.movimientoReina(reinaAux, this) // LA FUNCION NECESITA RETORNAR UN PEON?????????
                // PODER TIRAR ATRAS SI NOS HEMOS EQUIVOCADO DE FICHA O QUE DIRECTAMENTE EL SISTEMA TE DIGA QUE ESA FICHA

                reinaAux.seleccionada = false
                tablero[reinaAux.posicionFicha.fila][reinaAux.posicionFicha.columna] = reinaAux
            }
            tablero[posInicialY][posInicialX] = null // POSICIÓN DONDE SE ENCONTRABA LA PIEZA QUEDA NULLA
        }
        else if (tablero[posInicialY][posInicialX] is Rei){

            if (turnoBlancoNegro) { // REI BLANCO

                val reiAux = Rei(true, Posicion(posInicialY, posInicialX))
                reiAux.seleccionada = true

                tablero[posInicialY][posInicialX] = reiAux // PARA QUE SE VEA EL REI MARCADO COMO SELECCIONADO
                printarTabero(turnoBlancoNegro)

                reiAux.movimientoRei(reiAux, this) // LA FUNCION NECESITA RETORNAR UN PEON?????????
                // PODER TIRAR ATRAS SI NOS HEMOS EQUIVOCADO DE FICHA O QUE DIRECTAMENTE EL SISTEMA TE DIGA QUE ESA FICHA
                // NO SE PUEDE MOVER A NINGUN LADO

                reiAux.seleccionada = false
                tablero[reiAux.posicionFicha.fila][reiAux.posicionFicha.columna] = reiAux

            }
            else{ // REI NEGR0

                val reiAux = Rei(false, Posicion(posInicialY, posInicialX))
                reiAux.seleccionada = true

                tablero[posInicialY][posInicialX] = reiAux // PARA QUE SE VEA EL REI MARCADO COMO SELECCIONADO
                printarTabero(turnoBlancoNegro)

                reiAux.movimientoRei(reiAux, this) // LA FUNCION NECESITA RETORNAR UN PEON?????????
                // PODER TIRAR ATRAS SI NOS HEMOS EQUIVOCADO DE FICHA O QUE DIRECTAMENTE EL SISTEMA TE DIGA QUE ESA FICHA

                reiAux.seleccionada = false
                tablero[reiAux.posicionFicha.fila][reiAux.posicionFicha.columna] = reiAux
            }
            tablero[posInicialY][posInicialX] = null // POSICIÓN DONDE SE ENCONTRABA LA PIEZA QUEDA NULLA
        }
    }

    fun printarTabero(turnoBlancoNegro: Boolean) {

        printTurnoBlancoONegro(turnoBlancoNegro)
        for(i in 0 until numFilas){ // AÑADO DOS MAS PARAS PODER MOSTRAR POR PANTALLA LAS LETRAS Y LOS NÚMEROS DEL TABLERO
            for(j in 0 until numColumnas){
                // PINTO LAS FICHAS DEL TABLERO
                if (tablero[i][j] is Peon){
                    if ((tablero[i][j]?.blanca_o_negra) == true) {
                        if((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♙\u001B[0m ") // COLOR ROJO
                        else
                            print("\u001B[31m♙\u001B[0m ")
                    }
                    else {
                        if((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[7m\u001B[4m♙\u001B[0m ")
                        else
                            print("\u001B[34m♙\u001B[0m ")
                    }
                }
                else if (tablero[i][j] is Torre){
                    if ((tablero[i][j]?.blanca_o_negra) == true) {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♜\u001B[0m ") // COLOR ROJO
                        else
                            print("\u001B[31m♜\u001B[0m ")
                    }
                    else {
                        if((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♜\u001B[0m ")
                        else
                            print("\u001B[34m♜\u001B[0m ")
                    }
                }
                else if (tablero[i][j] is Caballo){
                    if ((tablero[i][j]?.blanca_o_negra) == true) {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♞\u001B[0m ") // COLOR ROJO
                        else
                            print("\u001B[31m♞\u001B[0m ")
                    }
                    else {
                        if((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♞\u001B[0m ")
                        else
                            print("\u001B[34m♞\u001B[0m ")
                    }
                }
                else if (tablero[i][j] is Alfil){
                    if ((tablero[i][j]?.blanca_o_negra) == true) {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♝\u001B[0m ") // COLOR ROJO
                        else
                            print("\u001B[31m♝\u001B[0m ")
                    }
                    else {
                        if((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♝\u001B[0m ")
                        else
                            print("\u001B[34m♝\u001B[0m ")
                    }
                }
                else if (tablero[i][j] is Reina){
                    if ((tablero[i][j]?.blanca_o_negra) == true) {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♛\u001B[0m ") // COLOR ROJO
                        else
                            print("\u001B[31m♛\u001B[0m ")
                    }
                    else {
                        if((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♛\u001B[0m ")
                        else
                            print("\u001B[34m♛\u001B[0m ")
                    }
                }
                else if (tablero[i][j] is Rei){
                    if ((tablero[i][j]?.blanca_o_negra) == true) {
                        if ((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♚\u001B[0m ") // COLOR ROJO
                        else
                            print("\u001B[31m♚\u001B[0m ")
                    }
                    else {
                        if((tablero[i][j]?.seleccionada) == true)
                            print("\u001B[43m\u001B[30m\u001B[4m♚\u001B[0m ")
                        else
                            print("\u001B[34m♚\u001B[0m ")
                    }
                }
                else {
                    if (i % 2 == 0) {
                        if (j % 2 == 0) {
                            print("\u001B[40m\u001B[30m♙\u001B[0m ")
                        }
                        else{
                            print("\u001B[47m\u001B[37m♙\u001B[0m ")
                        }
                    }else{
                        if (j % 2 != 0) {
                            print("\u001B[40m\u001B[30m♙\u001B[0m ")
                        }
                        else{
                            print("\u001B[47m\u001B[37m♙\u001B[0m ")
                        }
                    }
                }

            }
            println()
        }
    }

    private fun printTurnoBlancoONegro(turnoBlancoNegro: Boolean){
        if (turnoBlancoNegro)
            println("\u001B[43m\u001B[30m\u001B[4m[¡ES EL TURNO DE LAS BLANCAS!]\u001B[0m ")
        else
            println("\u001B[43m\u001B[30m\u001B[4m[¡ES EL TURNO DE LAS NEGRAS!]\u001B[0m ")
    }

}