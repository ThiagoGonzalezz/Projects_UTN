import wollok.game.*
import direcciones.*
import Sonidos.*
import celdas.*
import spawns.*
import reloj.*

const jugadores = [jugadorRojo, jugadorAzul, jugadorVerde]

const numSonid = [1,2,3,4]
 
const jugadorRojo = new Jugador(nombre="jugadorRojo", image="jugadorRojo.jpg")
const jugadorAzul = new Jugador(nombre="jugadorAzul", image="jugadorAzul.jpg")
const jugadorVerde = new Jugador(nombre="jugadorVerde", image="jugadorVerde.jpg")


class Jugador{

	var property position = game.at(6,6)
	var property mira = quieto 
	var puedeMoverse = false
	var puedeSuicidarse = true
	
	var image //= "enojado.jpg"
	
	const cuello = #{}
	var terreno = #{}
	
	const property nombre
	
	var kills = 0
	
	var tiempoSobrevivido = 120
	
	
	//MOVIMIENTOS
	
	method image() = image
	
	method puedeSuicidarse(bool){
		puedeSuicidarse = bool
	}

	method puedeMoverse(_puedeMoverse){
		puedeMoverse = _puedeMoverse
	}
		
	method cambiarMira(direc){mira = direc}
	
	method moverYAccionarCelda(){
		if(puedeMoverse){
	  mira.desplazarSiCorresponde(self)
	  celdasManager.celda(self.position().x(), self.position().y()).interactuarCelda(self)
	  }
	  }
	
	method moverNorte() {
		position = position.up(2)
		}
	method moverSur() {
		position = position.down(2)
	}
	method moverOeste() {
		position = position.left(2)
	}
	method moverEste() {
		position = position.right(2)
	}
	
	//No hay ningun error
	method mirar(direc){
		if(mira != direc &&  direc != mira.opuesto() && puedeMoverse){ 
			mira = direc
			sonido.movimientoGiro()
		}
	}
	
	method cambiarImagen(){
		const imagenes = ["enojado.jpg", "feliz.jpg"]
		image = imagenes.anyOne()
	}
	
	method nombre()=nombre
	
	//PARTIDA
	
	method agregarCelda(celda){
		terreno.add(celda)
	}
	
	method agregarCuello(celda){
		cuello.add(celda)
	}
	
	method adueniarseCuello(){
		if(cuello.isEmpty().negate()){
		cuello.forEach({celda => celda.cambiarDuenio(self) celda.estanRobando(false)})
		terreno = terreno + cuello
		cuello.clear()
		}
	}
	
	method suicidarse(){
		if(puedeSuicidarse){
		game.sound("suicidio" + (numSonid.anyOne()).toString() + ".mp3").play()
		terreno.forEach({celda => celda.desadueniar()})
		self.morir()}
	}
	
	method morir(){
		self.mirar(quieto)
		self.puedeMoverse(false)
		cuello.forEach({celda => celda.desrobar()})
		cuello.clear()
		game.removeVisual(self)
		self.reiniciarContadores()
		spawns.anyOne().reaparecerJugador(self)
	}
	
	method perderCelda(celda){
		terreno.remove(celda)
	}
	
	method terreno() = terreno
	
	method matar(ladron){
		game.sound("kill" + (numSonid.anyOne()).toString() + ".mp3").play()
		ladron.terreno().forEach({celda => celda.cambiarDuenio(self)})
		kills += 1
		ladron.morir()
	}
	
	method desrobar(celda) = cuello.remove(celda)

   //ESTADISTICAS
   
   method reiniciarContadores(){
   	kills = 0
   	tiempoSobrevivido = reloj.tiempoRestante()
    terreno.clear()
   }
   
   method kills() = kills
   
   method tiempoSobrevivido() = tiempoSobrevivido
   
}


