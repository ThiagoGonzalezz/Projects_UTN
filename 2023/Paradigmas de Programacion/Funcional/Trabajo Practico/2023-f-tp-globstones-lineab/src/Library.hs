
module Library where
import PdePreludat


type Filas = Number
type Columnas = Number
type Cantidad = Number
type Limites = (Number , Number)   --Esto lo vamos a usar para ver hasta donde se puede mover el cabezal y donde no

--Tablero para testear
tableroInicial1 = UnTablero [[(0,0,0,0),(0,0,0,0),(0,0,0,0)],[(0,0,0,0),(0,0,0,0),(0,1,10,0)],[(0,0,0,0),(0,0,0,0),(0,0,0,0)]] (1,1) (3,3)

--Punto 1
data Bolita = BolitaRoja | BolitaAzul | BolitaVerde | BolitaNegra deriving (Show,Eq)
type BolitasRojas = Number
type BolitasAzules = Number
type BolitasVerdes = Number
type BolitasNegras = Number
type Celda = (BolitasRojas, BolitasAzules, BolitasVerdes, BolitasNegras)
data Direccion = Norte | Sur | Este | Oeste deriving (Show, Eq)
type Cabezal = (Columnas, Filas)

data Tablero = UnTablero {
    celdas :: [[Celda]],
    cabezal :: Cabezal,
    limites :: Limites
} deriving (Show, Eq)

--Punto 2
celdaVacia :: Celda
celdaVacia = (0,0,0,0)

posicionCabezal :: Cabezal
posicionCabezal = (1,1)

inicializarTablero :: Columnas -> Filas -> Tablero
inicializarTablero columnas filas = UnTablero {
    celdas = replicate columnas (replicate filas celdaVacia),
    cabezal = posicionCabezal,
    limites = (columnas, filas)
}


--Punto 3
mover :: Direccion -> Tablero ->Tablero
mover dir (UnTablero celdas cabezal limites) = UnTablero{
     celdas = celdas,
     cabezal = cambiarDireccion dir cabezal,
     limites = limites
}

cambiarDireccion :: Direccion -> Cabezal -> Cabezal
cambiarDireccion dir (columnas, filas)
    | dir == Norte = (columnas, filas + 1)
    | dir == Sur = (columnas, filas - 1 )
    | dir == Este = (columnas + 1 , filas)
    | dir == Oeste = (columnas - 1, filas)
    | otherwise = (columnas, filas)

aplicarFuncionCeldaActual :: Tablero -> (Celda -> Celda) -> Tablero
aplicarFuncionCeldaActual (UnTablero tablero (colActual , filaActual) limites) funcion = UnTablero {
     celdas = take (colActual - 1) tablero ++
     [take (filaActual - 1) (tablero !! ( colActual - 1)) ++ [ funcion (tablero !! (colActual - 1) !! (filaActual - 1)) ] ++ drop (filaActual) (tablero !! (colActual - 1))] ++
     drop (colActual) tablero,
     cabezal = (colActual , filaActual),
     limites = limites
}

ponerEnCelda :: Bolita -> Celda -> Celda
ponerEnCelda bolita (rojas,azules,verdes,negras)
    | bolita == BolitaRoja = (rojas+1,azules,verdes,negras)
    | bolita == BolitaAzul = (rojas,azules+1,verdes,negras)
    | bolita == BolitaVerde = (rojas,azules,verdes+1,negras)
    | bolita == BolitaNegra = (rojas,azules,verdes,negras+1)
    | otherwise = (rojas,azules,verdes,negras)

sacarEnCelda :: Bolita -> Celda -> Celda
sacarEnCelda bolita (rojas,azules,verdes,negras)
    | bolita == BolitaRoja  &&  rojas > 0 = (rojas-1,azules,verdes,negras)
    | bolita == BolitaAzul && azules > 0 = (rojas,azules-1,verdes,negras)
    | bolita == BolitaVerde && verdes > 0= (rojas,azules,verdes-1,negras)
    | bolita == BolitaNegra && negras > 0= (rojas,azules,verdes,negras-1)
    | otherwise = (rojas,azules,verdes,negras)

poner :: Bolita -> Tablero -> Tablero
poner bolita tablero = aplicarFuncionCeldaActual tablero (ponerEnCelda bolita)

sacar :: Bolita -> Tablero -> Tablero
sacar bolita tablero = aplicarFuncionCeldaActual tablero (sacarEnCelda bolita)

--Punto 4: Sentencias compuestas

{-Esta funcion va aplicando la primer sentencia de la lista al tablero,
 y vuelve a invocarse con el nuevo tablero y el resto de sentencias.
 Una vez que se queda sin sentencias por aplicar, retorna el ultimo tablero
-}
--a
aplicarSentencias :: [Tablero -> Tablero] -> Tablero -> Tablero
aplicarSentencias [] tablero = tablero
--aplicarSentencias (f:fs) tablero = aplicarSentencias fs (f tablero)
aplicarSentencias fs tablero = foldl (\ tablero fs -> fs tablero) tablero fs

si :: (Tablero -> Bool)  -> [(Tablero->Tablero)] -> Tablero -> Tablero
si condicion sentencias tablero 
    | condicion tablero = aplicarSentencias sentencias tablero
    | otherwise = tablero


siNo :: (Tablero -> Bool) ->  [(Tablero->Tablero)] -> Tablero ->  Tablero
siNo condicion sentencias tablero 
    | not (condicion tablero) = aplicarSentencias sentencias tablero
    | otherwise = tablero


alternativa :: (Tablero -> Bool) -> [(Tablero->Tablero)] -> [(Tablero -> Tablero)] -> Tablero -> Tablero
alternativa condicion sentenciasCasoV sentenciasCasoF tablero
    |condicion tablero = aplicarSentencias sentenciasCasoV tablero
    |otherwise = aplicarSentencias sentenciasCasoF tablero

--b

--Esta funcion es recursiva, va aplicando las sentencias y reduciendo el contador,
--Una vez que llega a 0 retorna el tablero final
repetir :: Number -> [Tablero->Tablero] -> Tablero -> Tablero
repetir 0 _ tablero = tablero
repetir contador sentencias tablero = repetir (contador -1) sentencias (aplicarSentencias sentencias tablero)

--c

--Este es muy parecido al repetir, en caso de que la condicion sea verdadera,
--se vuelve a invocar la misma función pero con el tablero resultante de aplicar las sentencias,
--se va invocando sucesivamente hasta que la condición sea falsa.
mientras :: (Tablero -> Bool) -> [Tablero -> Tablero] -> Tablero -> Tablero
mientras condicion sentencias tablero
    | condicion tablero = mientras condicion sentencias (aplicarSentencias sentencias tablero)
    | otherwise = tablero
--d

irAlBorde :: Direccion -> Tablero -> Tablero
irAlBorde dir (UnTablero celdas cabezal limites) = UnTablero{
    celdas = celdas,
    cabezal = borde dir limites cabezal,
    limites = limites
}

borde:: Direccion -> Limites -> Cabezal -> Cabezal
borde dir (colMax , filaMax) (colActual , filaActual)
    | dir == Norte = (colActual, filaMax)
    | dir == Sur = (colActual, 1)
    | dir == Este = (colMax, filaActual)
    | dir == Oeste = (1, filaActual)
    | otherwise = (colActual, filaActual)


--5
--a
puedeMoverse :: Direccion -> Tablero -> Bool
puedeMoverse direccion (UnTablero tablero (colActual, filaActual) (l1, l2))
    | direccion == Norte && filaActual < l2 = True
    | direccion == Sur && filaActual > 1 = True
    | direccion == Este && colActual < l1 = True
    | direccion == Oeste && colActual > 1 = True
    | otherwise = False

--b
hayBolita :: Bolita -> Tablero -> Bool
hayBolita bolita (UnTablero celdas (colActual , filaActual) limites) = hayBolitaEnCelda bolita (celdas !! (filaActual - 1) !! (colActual - 1))

hayBolitaEnCelda :: Bolita -> Celda -> Bool
hayBolitaEnCelda bolita (rojas,azules,verdes,negras)
    | bolita == BolitaRoja = rojas /= 0
    | bolita == BolitaAzul = azules /= 0
    | bolita == BolitaVerde = verdes /= 0
    | bolita == BolitaNegra = negras /= 0
    | otherwise = 0 /= 0

--c
cantidadDeBolitas :: Bolita -> Tablero -> Cantidad
cantidadDeBolitas bolita (UnTablero celdas (colActual , filaActual) limites) = cantBolitasEnCelda bolita (celdas !! (filaActual - 1) !! (colActual - 1))

cantBolitasEnCelda :: Bolita -> Celda -> Number
cantBolitasEnCelda bolita (rojas,azules,verdes,negras)
    | bolita == BolitaRoja = rojas
    | bolita == BolitaAzul = azules
    | bolita == BolitaVerde = verdes
    | bolita == BolitaNegra = negras
    | otherwise = 0

--6
programa :: ([Tablero -> Tablero] -> Tablero -> Tablero)
programa = aplicarSentencias

--7
tableroInicial :: Tablero
tableroInicial = inicializarTablero 3 3

tableroFinal :: Tablero
tableroFinal = programa [ mover Norte,
                          poner BolitaNegra,
                          poner BolitaNegra,
                          poner BolitaAzul,
                          mover Norte,
                          repetir 15 [poner BolitaRoja, poner BolitaAzul],
                          alternativa (hayBolita BolitaVerde) [mover Este, poner BolitaNegra] [mover Sur, mover Este, poner BolitaAzul],
                          mover Este,
                          mientras ((((>=) 9) . (cantidadDeBolitas BolitaVerde))) [(poner BolitaVerde)],
                          poner BolitaAzul
                          ] tableroInicial1


--EJEMPLOS PARA PROBAR LAS FUNCIONES

tableroGrande :: Tablero
tableroGrande= UnTablero {celdas =replicate 9 (replicate 9 (5,5,5,5)), cabezal=(1,1), limites = (9,9)}

tableroChico :: Tablero
tableroChico= UnTablero {celdas =replicate 3 (replicate 3(5,5,5,5)), cabezal=(1,1), limites = (3,3)}

celdaPrueba :: Celda
celdaPrueba = (1,1,10,1)

tableroPrueba :: Tablero
tableroPrueba = UnTablero {celdas =replicate 2 (replicate 2 celdaPrueba), cabezal=(1,1), limites = (2,2)}
