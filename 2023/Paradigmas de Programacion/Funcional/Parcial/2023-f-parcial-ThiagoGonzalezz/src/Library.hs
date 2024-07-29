module Library where
import PdePreludat

--1

type Poder = Nave -> Nave

data Nave = UnaNave{
    nombreN::String,
    durabilidad::Number,
    ataque::Number,
    escudo::Number,
    poder::Poder
} deriving (Show, Eq)

--

tieFighter::Nave
tieFighter = UnaNave{
    nombreN = "TIE Fighter",
    durabilidad = 200,
    escudo = 100,
    ataque = 50,
    poder = movimientoTurbo
}

movimientoTurbo::Poder
movimientoTurbo = modificarAtaque 25

modificarAtaque::Number->Nave->Nave
modificarAtaque num nave = nave{ataque = (max 0 . (+ num) . ataque) nave}

--

xWing::Nave
xWing = UnaNave{
    nombreN = "X Wing",
    durabilidad = 300,
    escudo = 150,
    ataque = 100,
    poder =  reparacionDeEmergencia
}

reparacionDeEmergencia::Poder
reparacionDeEmergencia = modificarAtaque (-30) . modificarDurabilidad 50

modificarDurabilidad::Number->Nave->Nave
modificarDurabilidad num nave = nave{durabilidad = (max 0 . (+ num) . durabilidad) nave}

--

naveDeDarthVader::Nave
naveDeDarthVader = UnaNave{
    nombreN = "Nave de Darth Vader",
    durabilidad = 500,
    escudo = 300,
    ataque = 200,
    poder = movimientoSuperTurbo
}

movimientoSuperTurbo::Poder
movimientoSuperTurbo = modificarDurabilidad (-45) . repetir 3 movimientoTurbo 

repetir::Number->(a->a)->a->a
repetir 0 _ elemento = elemento
repetir num funcion elemento = repetir (num - 1) funcion (funcion elemento)

{-
Decidí utilizar esta funcion auxiliar con movimientoTurbo para demostrar que tengo
manejo sobre recursividad y orden superior. Sin embargo, una solución más sencilla sería: 
-}

movimientoTurbo3Veces::Poder
movimientoTurbo3Veces = modificarAtaque 75

--

millenniumFalcon :: Nave
millenniumFalcon = UnaNave{
    nombreN = "Millennium Falcon",
    durabilidad = 1000,
    escudo = 500,
    ataque = 50,
    poder = reparacionDeEmergencia . modificarEscudo 100
}

modificarEscudo::Number->Nave->Nave
modificarEscudo num nave = nave{escudo = (max 0 . (+ num) . escudo) nave}

--

naveSuperPoderosa::Nave
naveSuperPoderosa = UnaNave{
    nombreN = "Nave Super Poderosa",
    durabilidad = 2000,
    escudo = 600,
    ataque = 500,
    poder = superPoderDefinitivo
}

--este poder combina todos los anteriores

superPoderDefinitivo::Poder
superPoderDefinitivo =
    movimientoTurbo . reparacionDeEmergencia . movimientoSuperTurbo . (reparacionDeEmergencia . modificarEscudo 100)

--2

type Flota = [Nave]

durabilidadTotal::Flota->Number
durabilidadTotal = sum . map durabilidad

--3

navePostAtaque::Nave->Nave->Nave
navePostAtaque atacante victima = 
    (modificarDurabilidad (-(danioRecibidoPostPoder atacante victima)) . ejecutarPoder) victima  

ejecutarPoder::Nave->Nave
ejecutarPoder nave = poder nave nave

danioRecibidoPostPoder::Nave->Nave->Number
danioRecibidoPostPoder atacante = danioRecibido (ejecutarPoder atacante) . ejecutarPoder

danioRecibido::Nave->Nave->Number
danioRecibido atacante = max 0 . (-) (ataque atacante) . escudo 

--4

estaFueraDeCombate::Nave->Bool
estaFueraDeCombate = (== 0) . durabilidad

--5

type Estrategia = Nave -> Bool

flotaEnemigaPostMision::Nave -> Estrategia -> Flota -> Flota
flotaEnemigaPostMision nave strat = map (atacarNaveConStrat nave strat)  

atacarNaveConStrat::Nave->Estrategia->Nave->Nave
atacarNaveConStrat atacante strat victima 
    |strat victima = navePostAtaque atacante victima
    |otherwise = victima

--a
navesDebiles::Estrategia
navesDebiles = (< 200) . escudo

--b
navesPeligrosas::Number -> Estrategia
navesPeligrosas valor = (> valor) . ataque

--c
puedenMorir::Nave->Estrategia
puedenMorir nave = estaFueraDeCombate . navePostAtaque nave  

--d
debilesQuePuedenMorir::Nave->Estrategia
debilesQuePuedenMorir nave victima = ((&&) (puedenMorir nave victima) . navesDebiles) victima

--6 

realizarMisionConMejorEstrategia::Nave->Flota->Estrategia->Estrategia->Flota
realizarMisionConMejorEstrategia nave flota strat1 strat2 = laMasDebil (flotaEnemigaPostMision nave strat1 flota) (flotaEnemigaPostMision nave strat2 flota)

laMasDebil::Flota->Flota->Flota
laMasDebil flota1 flota2
    |durabilidadTotal flota1 < durabilidadTotal flota2 = flota1
    |otherwise = flota2

{-
En este ejercicio decidí unificar la tarea de determinar que estrategia es mejor y luego realizar la misión con ella,
ya que, si bien entiendo que hay menos abstracción, al fin y al cabo para decidir que estrategia es mejor habría 
que realizar la mision con las dos estrategias de igual manera, por lo que resulta mucho más optimo 
hacerlo de esta manera
-}

--7

flotaInfinita::Flota
flotaInfinita = xWing : flotaInfinita

{-
Si bien Haskell trabaja con Lazy Evaluation o Evaluación Diferida (que consiste en posponer la evaluación
de las cosas hasta que estos resultados sean realmente necesarios desarrollar el programa), resulta 
imposible determinar la durabilidad total de la flota infinita, ya que para eso es necesario evaluar
caso por caso la durabilidad de todas las naves de la flota, lo cual resulta imposible ya que son infinitas. 
-}

{-
Un ejemplo donde se puede visualizar facilmente la ventaja de la utilizacion de Lazy Evaluation es
cuando queremos acceder a la primer nave de la flota infinita:

*Spec Library Spec> head flotaInfinita
UnaNave {nombreN = "X Wing", durabilidad = 300, ataque = 100, escudo = 150, poder = <una función>}

en este caso es posible evaluar la funcion ya que, la unica flota que es necesaria evaluar es la primera,
por lo que no continua evaluando las infinitas naves restantes.
-}

{-
Cuando se lleva adelante la mision con la flota infinita y la estrategia dada, la respuesta que obtendremos
será que la terminal continuará mostrando por consola las infinitas naves que, segun la estrategia,  
fueron o no atacadas.
-}