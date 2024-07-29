import celdas.*
import wollok.game.*
import players.*


class Digito{
	var jugador
	const property position
	method image()
}
class Digito1R inherits Digito{
	override method image() = "ranking" + (rankingManager.porcentajeCeldas(jugador) . div(10)) . toString() + ".png"
}

class Digito2R inherits Digito{
	override method image() = "ranking" + (rankingManager.porcentajeCeldas(jugador) %10) .toString() +  ".png"
}

object rankingManager {
	method porcentajeCeldas(jugador)= ((jugador.terreno().size()) * 100) . div(532)
	}

class FotoJugador inherits Digito{
	override method image() = "ranking_" +  jugador . nombre()+ ".png"
	}

class Porcentaje {
	var position
	method position()=position
	method image() = "rankingPorcentaje.png"}
	
const fotoJugadorRojo = new FotoJugador (position=game.at(10,31), jugador=jugadorRojo)
const fotoJugadorAzul = new FotoJugador (position=game.at(15,31), jugador=jugadorAzul)
const fotoJugadorVerde = new FotoJugador (position=game.at(20,31), jugador=jugadorVerde)
const digito1Rojo = new Digito1R (jugador=jugadorRojo, position=game.at(11,31))
const digito1Azul = new Digito1R (jugador=jugadorAzul, position=game.at(16,31))
const digito1Verde = new Digito1R (jugador=jugadorVerde, position=game.at(21,31))
const digito2Rojo = new Digito2R (jugador=jugadorRojo, position=game.at(12,31))
const digito2Azul = new Digito2R (jugador=jugadorAzul, position=game.at(17,31))
const digito2Verde = new Digito2R (jugador=jugadorVerde, position=game.at(22,31))





//ESTADISTICAS FINAL PARTIDA

object killer{
	var killer
	var kills
	
	method image() = "statKiller_" + killer.nombre() + ".png"
   
    method position() = game.at(22,8)
   
    method establecerKiller(){
    	killer = jugadores.max{jugador => jugador.kills()}
    	kills = killer.kills()
    }
    
    method establecerContadores(){
    	if(kills < 10){
    		contadorKills1.digito(kills)
    		game.addVisual(contadorKills1)
    	}else{
    		contadorKills1.digito(kills.div(10))
    		contadorKills2.digito(kills % 10)
    		game.addVisual(contadorKills1)
    		game.addVisual(contadorKills2)
    	}
    	}
    	
    	method mostrarKiller(){
    		self.establecerKiller()
    		game.addVisual(self)
    		self.establecerContadores()
    		
    	}
}

const contadorKills1 = new ContadorStat(posX = 26, posY = 8)

const contadorKills2 = new ContadorStat(posX = 27, posY = 8)

object sobreviviente{
	var sobreviviente
	var tiempoSobrevivido
	
	method image() = "statSobreviviente_" + sobreviviente.nombre() + ".png"
   
   method position() = game.at(49,8)
   
    method establecerSobreviviente(){
    	sobreviviente = jugadores.max{jugador => jugador.tiempoSobrevivido()}
    	tiempoSobrevivido = sobreviviente.tiempoSobrevivido()
    }
    
    method establecerContadores(){
    	minuteroTiempoSobr.digito(tiempoSobrevivido.div(60))
    	segunderoTiempoSobr1.digito((tiempoSobrevivido % 60).div(10))
    	segunderoTiempoSobr2.digito(((tiempoSobrevivido % 60)%10))
    	}
    	
    	method mostrarSobreviviente(){
    		self.establecerSobreviviente()
    		self.establecerContadores()
    		game.addVisual(self)
    		game.addVisual(minuteroTiempoSobr)
    		game.addVisual(segunderoTiempoSobr1)
    		game.addVisual(segunderoTiempoSobr2)
    	}
}

const minuteroTiempoSobr = new ContadorStat(posX = 51, posY = 8)

const segunderoTiempoSobr1 = new ContadorStat(posX = 53, posY = 8)

const segunderoTiempoSobr2 = new ContadorStat(posX = 54, posY = 8)


class ContadorStat{
	var digito = 0
	var posX
	var posY
	
	method image() = "contadorStats" + digito.toString() + ".png"
    method position() = game.at(posX,posY)
    method digito(_digito){
    	digito = _digito
    }
}
