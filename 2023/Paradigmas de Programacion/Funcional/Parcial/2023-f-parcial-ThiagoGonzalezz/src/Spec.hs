module Spec where
import PdePreludat
import Library
import Test.Hspec

correrTests :: IO ()
correrTests = hspec $ do
  describe "Poderes de las naves" $ do
    it "Poder TIE Fighter" $ do
      (ataque . ejecutarPoder) tieFighter `shouldBe` 75
    it "Parte Durabilidad del poder de X Wing" $ do
      (durabilidad . ejecutarPoder) xWing `shouldBe` 350
    it "Parte Ataque del poder de X Wing" $ do
      (ataque . ejecutarPoder) xWing `shouldBe` 70
    it "Parte Ataque del poder de la Nave de Darth Vader" $ do
      (ataque . ejecutarPoder) naveDeDarthVader `shouldBe` 275
    it "Parte Durabilidad poder de la Nave de Darth Vader" $ do
      (durabilidad . ejecutarPoder) naveDeDarthVader `shouldBe` 455
    it "parte Escudo del poder de Millennium Falcon" $ do
      (escudo . ejecutarPoder) millenniumFalcon `shouldBe` 600
    it "parte Durabilidad del poder de Millennium Falcon" $ do
      (durabilidad . ejecutarPoder) millenniumFalcon `shouldBe` 1050
    it "parte Ataque del poder de Millennium Falcon" $ do
        (ataque . ejecutarPoder) millenniumFalcon `shouldBe` 20
    it "parte Ataque del poder de la Nave Super Poderosa" $ do
        (ataque . ejecutarPoder) naveSuperPoderosa `shouldBe` 540

  describe "DurabilidadTotal" $ do
    it "durabilidadTotal de una flota de una sola nave" $ do
      durabilidadTotal [xWing] `shouldBe` 300
    it "durabilidadTotal de una flota de tres naves" $ do
      durabilidadTotal [xWing, tieFighter, naveDeDarthVader] `shouldBe` 1000

  describe "navePostAtaque" $ do
    it "El daño no es nulo" $ do
      (durabilidad . navePostAtaque naveDeDarthVader) xWing `shouldBe` 350 - (275-150)
    it "el daño es nulo"  $ do
      (durabilidad . navePostAtaque xWing) naveDeDarthVader `shouldBe` 455 

  describe "navePostAtaque" $ do
    it "La nave no esta fuera de combate" $ do
      estaFueraDeCombate xWing `shouldBe` False
    it "La nave esta fuera de combate" $ do
      estaFueraDeCombate xWing{durabilidad = 0} `shouldBe` True
    
  describe "flotaEnemigaPostMision" $ do
    it "ataco a una flota por navesDebiles" $ do
      durabilidadTotal (flotaEnemigaPostMision naveDeDarthVader navesDebiles [xWing, millenniumFalcon]) `shouldBe` 225 + 1000
   
  describe "flotaEnemigaPostMision" $ do
    it "Realizar mision con navesDebiles o navesPeligrosas 2000 conviene con navesDebiles" $ do
    durabilidadTotal (realizarMisionConMejorEstrategia naveDeDarthVader [xWing, millenniumFalcon] navesDebiles (navesPeligrosas 2000)) `shouldBe` 225 + 1000

    --Como los tests eran optativos y ya no me queda mucho tiempo lo dejé por acá