import wollok.game.*
import direcciones.*
import celdas.*

const spawns = [spawn1, spawn2, spawn3, spawn4, spawn5, spawn6]


class Spawn {
	const posCentralX
	const posCentralY
	
	method reaparecerJugador(jugador){
		spawns.remove(self) //Durante 10 segundos desde que un player reaparezca nadie puede volver a reaparecer en su spwan
		game.schedule(13000, {spawns.add(self)}) 
		jugador.position(game.at(posCentralX, posCentralY))
		game.schedule(3000, {game.addVisual(jugador) self.darTerrenoInicial(jugador) jugador.puedeMoverse(true)}) 	    
	}
	
	method aparecerJugador(jugador){//para el principio de la partida
		spawns.remove(self)
		game.schedule(13000, {spawns.add(self)}) 
		jugador.mira(quieto)
		jugador.position(game.at(posCentralX, posCentralY))
		self.darTerrenoInicial(jugador)
		game.addVisual(jugador)
	}
	
	method darTerrenoInicial(jugador){
	  celdasManager.celda(posCentralX -2, posCentralY - 2).cambiarDuenio(jugador)
	  celdasManager.celda(posCentralX -2 , posCentralY).cambiarDuenio(jugador)
	  celdasManager.celda(posCentralX -2, posCentralY + 2).cambiarDuenio(jugador)
	  celdasManager.celda(posCentralX, posCentralY -2).cambiarDuenio(jugador)
	  celdasManager.celda(posCentralX, posCentralY).cambiarDuenio(jugador)
	  celdasManager.celda(posCentralX, posCentralY +2).cambiarDuenio(jugador)
	  celdasManager.celda(posCentralX +2, posCentralY -2).cambiarDuenio(jugador)
	  celdasManager.celda(posCentralX +2,  posCentralY).cambiarDuenio(jugador)
	  celdasManager.celda(posCentralX +2, posCentralY +2).cambiarDuenio(jugador)
	  }
}

	const spawn1 = new Spawn(posCentralX = 16, posCentralY = 4)
    const spawn2 = new Spawn(posCentralX = 58, posCentralY = 4)
	const spawn3 = new Spawn(posCentralX = 4, posCentralY = 14)
	const spawn4 = new Spawn(posCentralX = 16, posCentralY = 26)
	const spawn5 = new Spawn(posCentralX = 58, posCentralY = 26)
    const spawn6 = new Spawn(posCentralX = 74, posCentralY = 14)
