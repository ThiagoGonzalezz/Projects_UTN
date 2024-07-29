module Spec where
import PdePreludat
import Library
import Test.Hspec


correrTests :: IO ()
correrTests = hspec $ do
  describe "Punto 2: Funcion incializarTablero" $ do
    it "Inicializando un tablero vacio de 2x1" $ do
       inicializarTablero 2 1 `shouldBe` UnTablero {celdas= [[(0,0,0,0)],[(0,0,0,0)]], cabezal = (1,1), limites = (2,1)}
    it "Inicializando un tablero vacio de 2x1 con cabezal en (1,1)" $ do
       inicializarTablero 1 3 `shouldBe` UnTablero {celdas = [[(0,0,0,0),(0,0,0,0),(0,0,0,0)]], cabezal = (1,1), limites = (1,3)}
    it "Inicializando un tablero vacio de 2x1 con cabezal en (1,1)" $ do
       inicializarTablero 3 4 `shouldBe` UnTablero {celdas = [[(0,0,0,0),(0,0,0,0),(0,0,0,0),(0,0,0,0)],[(0,0,0,0),(0,0,0,0),(0,0,0,0),(0,0,0,0)],[(0,0,0,0),(0,0,0,0),(0,0,0,0),(0,0,0,0)]], cabezal = (1,1), limites = (3,4)}

  describe "Punto 3: Función auxiliar cambiarDireccion del cabezal" $ do
    it "Cambiar direccion Norte" $ do  
      cambiarDireccion Norte (1,1) `shouldBe` (1,2)
    it "Cambiar direccion Sur" $ do  
      cambiarDireccion Sur (1,1) `shouldBe` (1,0)
    it "Cambiar direccion Este" $ do  
      cambiarDireccion Este (1,1) `shouldBe` (2,1)
    it "Cambiar direccion Oeste" $ do  
      cambiarDireccion Oeste (1,1) `shouldBe` (0,1)

  describe "Punto 3: Funcion mover" $ do
    it "Mover Norte" $ do
      mover Norte (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,1) (2,2))`shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2))
    it "Mover Sur" $ do
      mover Sur (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,1) (2,2))
    it "Mover Este" $ do
      mover Este (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,2) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2))
    it "Mover Oeste" $ do
      mover Oeste (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,2) (2,2))
  
  describe "Punto 3: Función Auxiliar ponerBolitaEnCelda" $ do
    it "Poner bolita roja en una celda" $ do  
      ponerEnCelda BolitaRoja (0,0,0,0) `shouldBe` (1,0,0,0)
    it "Poner bolita azul en una celda" $ do  
      ponerEnCelda BolitaAzul (0,0,0,0) `shouldBe` (0,1,0,0)
    it "Poner bolita verde en una celda" $ do  
      ponerEnCelda BolitaVerde (0,0,0,0) `shouldBe` (0,0,1,0)
    it "Poner bolita negra en una celda" $ do  
      ponerEnCelda BolitaNegra (0,0,0,0) `shouldBe` (0,0,0,1)

  describe "Punto 3: Funcion poner" $ do
    it "Poner bolita roja en la celda actual del tablero" $ do
      poner BolitaRoja (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(1,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2))
    it "Poner bolita azul en la celda actual del tablero" $ do
      poner BolitaAzul (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(0,1,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2))
    it "Poner bolita verda en la celda actual del tablero" $ do
      poner BolitaVerde (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(0,0,1,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2))
    it "Poner bolita negra en la celda actual del tablero" $ do
      poner BolitaNegra (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,1),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2))


  describe "Punto 3: Función auxiliar sacarBolitaEnCelda" $ do
    it "Sacar bolita roja en una celda" $ do  
      sacarEnCelda BolitaRoja (1,1,1,1) `shouldBe` (0,1,1,1)
    it "Sacar bolita azul en una celda" $ do  
      sacarEnCelda BolitaAzul (1,1,1,1) `shouldBe` (1,0,1,1)
    it "Sacar bolita verde en una celda" $ do  
      sacarEnCelda BolitaVerde (1,1,1,1) `shouldBe` (1,1,0,1)
    it "Sacar bolita negra en una celda" $ do  
      sacarEnCelda BolitaNegra (1,1,1,1) `shouldBe` (1,1,1,0)
    it "No saca bolita roja en una celda porque no quedan" $ do  
      sacarEnCelda BolitaRoja (0,1,1,1) `shouldBe` (0,1,1,1)
    it "No saca bolita azul en una celda porque no quedan" $ do  
      sacarEnCelda BolitaAzul (1,0,1,1) `shouldBe` (1,0,1,1)
    it "No saca bolita verde en una celda porque no quedan" $ do  
      sacarEnCelda BolitaVerde (1,1,0,1) `shouldBe` (1,1,0,1)
    it "No saca bolita negra en una celda porque no quedan" $ do  
      sacarEnCelda BolitaNegra (1,1,1,0) `shouldBe` (1,1,1,0)

  describe "Punto 3: Funcion sacar" $ do
    it "Sacar bolita roja en la celda actual del tablero" $ do
      sacar BolitaRoja (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(2,3,3,3)]]) (2,2) (2,2))
    it "Sacar bolita azul en la celda actual del tablero" $ do
      sacar BolitaAzul (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,2,3,3)]]) (2,2) (2,2))
    it "Sacar bolita verde en la celda actual del tablero" $ do
      sacar BolitaVerde (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,2,3)]]) (2,2) (2,2))
    it "Sacar bolita negra en la celda actual del tablero" $ do
      sacar BolitaNegra (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,2)]]) (2,2) (2,2))

  describe "Punto 4: Funcion auxiliar aplicarSentencias" $ do
    it "No aplica ninguna sentencia porque la lista esta vacía" $ do
      aplicarSentencias [] (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2))
    it "Aplica solo una sentencia" $ do
      aplicarSentencias [mover Sur] (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,0) (2,2))
    it "Aplica dos sentencias" $ do
      aplicarSentencias [(mover Sur), (poner BolitaNegra)] (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,3),(3,3,3,3)]]) (2,1) (2,2))
    it "Aplica seis sentencias" $ do
      aplicarSentencias [(mover Sur),(poner BolitaNegra),(sacar BolitaNegra),(poner BolitaRoja),(mover Oeste),(sacar BolitaVerde)] (UnTablero ([[(0,0,1,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(3,2,2,2),(3,3,3,3)]]) (1,1) (2,2))

  describe "Punto 4: Funcion si" $ do
    it "Si se cumple la condición aplica las sentencias a la celda actual del tablero" $ do
      si (hayBolita BolitaRoja) [mover Norte] (UnTablero {celdas = replicate 2 (replicate 1 (1,0,0,0)), cabezal=(1,1), limites = (2,1)})  `shouldBe` UnTablero {celdas = [[(1,0,0,0)],[(1,0,0,0)]], cabezal = (1,2), limites = (2,1)}
    it "Si no se cumple la condición no aplica las sentencias a la celda actual del tablero" $ do
      si (hayBolita BolitaRoja) [mover Norte] (UnTablero {celdas = replicate 2 (replicate 1 (0,1,1,1)), cabezal=(1,1), limites = (2,1)})  `shouldBe` UnTablero {celdas = [[(0,1,1,1)],[(0,1,1,1)]], cabezal = (1,1), limites = (2,1)}

  describe "Punto 4: Funcion siNO" $ do
    it "Si no se cumple la condicion aplica las sentencias a la celda actual del tablero" $ do
     siNo (hayBolita BolitaRoja) [mover Norte] (UnTablero {celdas =replicate 2 (replicate 1 (0,1,1,1)), cabezal=(1,1), limites = (2,1)})  `shouldBe` UnTablero {celdas = [[(0,1,1,1)],[(0,1,1,1)]], cabezal = (1,2), limites = (2,1)}
    it "Si se cumple la condicion aplica las sentencias a la celda actual del tablero" $ do
     siNo (hayBolita BolitaRoja) [mover Norte] (UnTablero {celdas =replicate 2 (replicate 1 (1,0,0,0)), cabezal=(1,1), limites = (2,1)})  `shouldBe` UnTablero {celdas = [[(1,0,0,0)],[(1,0,0,0)]], cabezal = (1,1), limites = (2,1)}

  describe "Punto 4: Funcion Alternativa" $ do
    it "Si se cumple la condicion aplica el primer conjunto de sentencias a la celda actual del tablero" $ do
      alternativa (hayBolita BolitaRoja)  [(mover Norte)] [(mover Este)] (UnTablero {celdas =replicate 2 (replicate 1 (1,0,0,0)), cabezal=(1,1), limites = (2,1)}) `shouldBe` (UnTablero {celdas = replicate 2 (replicate 1 (1,0,0,0)), cabezal = (1,2), limites = (2,1)})
    it "Si no se cumple la condicion aplica el segundo conjunto de sentencias a la celda actual del tablero" $ do
      alternativa (hayBolita BolitaRoja)  [(mover Norte)] [(mover Este)] (UnTablero {celdas =replicate 2 (replicate 1 (0,1,1,1)), cabezal=(1,1), limites = (2,1)}) `shouldBe` (UnTablero {celdas = replicate 2 (replicate 1 (0,1,1,1)), cabezal = (2,1), limites = (2,1)})

  describe "Punto 4: Funcion repetir" $ do
   it "Repite cero veces la función duplicar bolitas sobre el tablero prueba" $ do
      repetir 0 [poner BolitaRoja] (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2))
   it "Repite dos veces la función duplicar bolitas sobre el tablero prueba" $ do
      repetir 2 [poner BolitaRoja] (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(2,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2))
   it "Repite seis veces la función duplicar bolitas sobre el tablero prueba" $ do
      repetir 6 [poner BolitaRoja] (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(6,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2))
 
  describe "Punto 4: Funcion mientras" $ do
    it "No se cumple la condicion por lo cual no aplica ninguna sentencia " $ do    
      mientras (hayBolita BolitaRoja) [poner BolitaAzul]  (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2))
    it "Se cumple una sola vez la condición por lo cual se aplican por unica vez las sentencias" $ do 
      mientras (hayBolita BolitaRoja) [sacar BolitaRoja]  (UnTablero ([[(1,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2))
    it "Se cumple seis veces la condición por lo cual seis veces las sentencias" $ do 
      mientras (hayBolita BolitaRoja) [sacar BolitaRoja, poner BolitaAzul]  (UnTablero ([[(6,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(0,6,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2))

  describe "Punto 4: Funcion irAlBorde" $ do
    it "irAlBorde Norte" $ do
      irAlBorde Norte (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,2) (2,2))
    it "irAlBorde Sur" $ do
      irAlBorde Sur (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,1) (2,2))
    it "irAlBorde Este" $ do
      irAlBorde Este (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,1) (2,2))
    it "irAlBorde Oeste" $ do
      irAlBorde Oeste (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,2) (2,2))

  describe "Punto 5: Funcion puedeMoverse" $ do
    it "El tableroPrueba puede moverse al Norte" $ do
      puedeMoverse Norte (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` True
    it "El tableroPrueba puede moverse al Sur" $ do
      puedeMoverse Sur (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` True
    it "El tableroPrueba puede moverse al Este" $ do
      puedeMoverse Este (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` True
    it "El tableroPrueba puede moverse al Oeste" $ do
      puedeMoverse Oeste (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` True
    it "El tableroPrueba no puede moverse al Norte" $ do
      puedeMoverse Norte (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` False
    it "El tableroPrueba no puede moverse al Sur" $ do
      puedeMoverse Sur (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` False
    it "El tableroPrueba no puede moverse al Este" $ do
      puedeMoverse Este (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (2,2)) `shouldBe` False
    it "El tableroPrueba no puede moverse al Oeste" $ do
      puedeMoverse Oeste (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (2,2)) `shouldBe` False


  describe "Punto 5: Función auxiliar hayBolitaEnCelda" $ do
    it "Hay al menos una bolita roja en la celda" $ do
      hayBolitaEnCelda BolitaRoja (2,0,0,0) `shouldBe` True
    it "Hay al menos una bolita azul en la celda" $ do
      hayBolitaEnCelda BolitaAzul (0,1,0,0) `shouldBe` True
    it "Hay al menos una bolita verde en la celda" $ do
      hayBolitaEnCelda BolitaVerde (0,0,1,0) `shouldBe` True
    it "Hay al menos una bolita negra en la celda" $ do
      hayBolitaEnCelda BolitaNegra (0,0,0,1) `shouldBe` True
    it "No hay ninguna bolita roja en la celda" $ do
      hayBolitaEnCelda BolitaRoja (0,1,1,1) `shouldBe` False
    it "No hay ninguna bolita azul en la celda" $ do
      hayBolitaEnCelda BolitaAzul (1,0,1,1) `shouldBe` False
    it "No hay ninguna bolita verde en la celda" $ do
      hayBolitaEnCelda BolitaVerde (1,1,0,1) `shouldBe` False
    it "No hay ninguna bolita negra en la celda" $ do
      hayBolitaEnCelda BolitaNegra (1,1,1,0) `shouldBe` False
  
  describe "Punto 5: Funcion hayBolita" $ do
    it "El tablero tiene al menos una bolita roja en la celda actual" $ do
      hayBolita BolitaRoja (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,0,0,0)]]) (2,2) (2,2)) `shouldBe` True
    it "El tablero tiene al menos una bolita azul en la celda actual" $ do
      hayBolita BolitaAzul (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(0,3,0,0)]]) (2,2) (2,2)) `shouldBe` True
    it "El tablero tiene al menos una bolita verde en la celda actual" $ do
      hayBolita BolitaVerde (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(0,0,1,0)]]) (2,2) (2,2)) `shouldBe` True
    it "El tablero tiene al menos una bolita negra en la celda actual" $ do
      hayBolita BolitaNegra (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(0,0,0,1)]]) (2,2) (2,2)) `shouldBe` True
    it "El tablero no tiene ninguna bolita roja en la celda actual" $ do
      hayBolita BolitaRoja (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(0,1,1,1)]]) (2,2) (2,2)) `shouldBe` False
    it "El tablero no tiene ninguna bolita azul en la celda actual" $ do
      hayBolita BolitaAzul (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(1,0,1,1)]]) (2,2) (2,2)) `shouldBe` False
    it "El tablero no tiene ninguna bolita verde en la celda actual" $ do
      hayBolita BolitaVerde (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(1,1,0,1)]]) (2,2) (2,2)) `shouldBe` False
    it "El tablero no tiene ninguna bolita negra en la celda actual" $ do
      hayBolita BolitaNegra (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,2,2,2),(1,1,1,0)]]) (2,2) (2,2)) `shouldBe` False

  describe "Punto 5: Función auxiliar cantidadDeBolitasEnCelda" $ do
    it "La cantidad de bolitas rojas en una celda es 7" $ do
      cantBolitasEnCelda BolitaRoja (7,1,1,1) `shouldBe` 7
    it "La cantidad de bolitas azules en una celda es 42" $ do
      cantBolitasEnCelda BolitaAzul (7,42,1,1) `shouldBe` 42
    it "La cantidad de bolitas verdes en una celda es 3" $ do
      cantBolitasEnCelda BolitaVerde (3,1,43,1) `shouldBe` 43
    it "La cantidad de bolitas negras en una celda es 0" $ do
      cantBolitasEnCelda BolitaNegra (7,1,1,0) `shouldBe` 0
  
  describe "Punto 5: Funcion cantidadDeBolitas" $ do
    it "La cantidad de bolitas rojas en el tablero es 2" $ do
      cantidadDeBolitas BolitaRoja (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(2,3,1,1),(3,3,3,3)]]) (1,2) (2,2)) `shouldBe` 2
    it "La cantidad de bolitas azules en el tablero es 69" $ do
      cantidadDeBolitas BolitaAzul (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(1,69,1,1),(3,3,3,3)]]) (1,2) (2,2)) `shouldBe` 69
    it "La cantidad de bolitas verdes en el tablero es 0" $ do
      cantidadDeBolitas BolitaVerde (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(1,2,0,1),(3,3,3,3)]]) (1,2) (2,2)) `shouldBe` 0
    it "La cantidad de bolitas negras en el tablero es 47" $ do
      cantidadDeBolitas BolitaNegra (UnTablero ([[(0,0,0,0),(1,1,1,1)],[(1,2,1,47),(3,3,3,3)]]) (1,2) (2,2)) `shouldBe` 47
  
  describe "Punto 6: Funcion Programa" $ do
    it "Test de Programa 1" $ do
      programa [mover Este, poner BolitaRoja, poner BolitaVerde, mover Norte, poner BolitaAzul, poner BolitaAzul] (inicializarTablero 2 2) `shouldBe` (UnTablero ([[(0,0,0,0),(0,0,0,0)],[(1,0,1,0),(0,2,0,0)]]) (2,2) (2,2))
    it "Test de Programa 2" $ do
      programa [mover Norte, repetir 7 [poner BolitaRoja], poner BolitaVerde, mover Este, repetir 6 [poner BolitaAzul], sacar BolitaAzul] (inicializarTablero 2 2) `shouldBe` (UnTablero ([[(0,0,0,0),(7,0,1,0)],[(0,0,0,0),(0,5,0,0)]]) (2,2) (2,2))
    it "Test de Programa 3" $ do
      programa [mover Norte, repetir 7 [poner BolitaNegra, sacar BolitaNegra], poner BolitaVerde, mover Este, alternativa (hayBolita BolitaAzul) [mover Sur] [mover Oeste], poner BolitaAzul] (inicializarTablero 2 2) `shouldBe` UnTablero {celdas = [[(0,0,0,0),(0,1,1,0)],[(0,0,0,0),(0,0,0,0)]], cabezal = (1,2), limites = (2,2)}
    it "Test de Programa 4" $ do
      programa [mover Este, repetir 4 [mover Norte, mover Sur, sacar BolitaNegra], mover Oeste, siNo (hayBolita BolitaAzul) [mover Norte], si (hayBolita BolitaRoja) [mover Sur]] (inicializarTablero 2 2) `shouldBe` UnTablero {celdas = [[(0,0,0,0),(0,0,0,0)],[(0,0,0,0),(0,0,0,0)]], cabezal = (1,2), limites = (2,2)}
    it "Test de Programa 5" $ do
      programa [mover Este, repetir 4 [mover Norte], repetir 4 [mover Sur] , mover Oeste, si (hayBolita BolitaRoja) [mover Norte], si (hayBolita BolitaRoja) [mover Sur]] (inicializarTablero 2 2) `shouldBe` UnTablero {celdas = [[(0,0,0,0),(0,0,0,0)],[(0,0,0,0),(0,0,0,0)]], cabezal = (1,1), limites = (2,2)}
    it "Test de programa 6 " $ do
      programa [repetir 5 [poner BolitaNegra], poner BolitaAzul, sacar BolitaRoja, mover Norte] (UnTablero ([[(1,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)],[(2,2,2,2),(3,3,3,3)]]) (2,1) (3,2)) `shouldBe` (UnTablero ([[(1,0,0,0),(1,1,1,1)],[(1,3,2,7),(3,3,3,3)],[(2,2,2,2),(3,3,3,3)]]) (2,2) (3,2))
    it "Test de programa 7 " $ do
      programa [mientras ((<=9) . cantidadDeBolitas BolitaVerde) [poner BolitaVerde],  mover Norte, mover Norte] (UnTablero ([[(1,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (3,2)) `shouldBe` (UnTablero ([[(1,0,10,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)],[(2,2,2,2),(3,3,3,3)]]) (1,3) (3,2))
    it "Test de programa 8 " $ do
      programa [alternativa (puedeMoverse Norte) [mover Norte, poner BolitaNegra] [mover Sur, poner BolitaAzul], mover Este, mover Sur] (UnTablero ([[(1,0,0,0),(1,1,1,1)],[(2,2,2,2),(3,3,3,3)],[(2,2,2,2),(3,3,3,3)]]) (1,1) (3,2)) `shouldBe` (UnTablero ([[(1,0,0,0),(1,1,1,2)],[(2,2,2,2),(3,3,3,3)],[(2,2,2,2),(3,3,3,3)]]) (2,1) (3,2))

  describe "Punto 7: Programa de Prueba" $ do
    it "El Programa de Prueba se Ejecuto Correctamente" $ do
      tableroFinal `shouldBe` UnTablero {celdas = [[(0,0,0,0),(0,1,0,2),(15,15,0,0)],[(0,0,0,0),(0,1,0,0),(0,1,10,0)],[(0,0,0,0),(0,1,0,0),(0,0,0,0)]], cabezal = (3,2), limites = (3,3)}
    