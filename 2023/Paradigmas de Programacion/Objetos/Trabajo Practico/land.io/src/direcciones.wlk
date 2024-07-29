import players.*

//Las direcciones se encargan de desplazar constantemente a los jugadores 
//de que estos no se pasen de los limites
// y de que no puedan darse vuelta instantaneamente

class Direccion {
	method desplazarSiCorresponde(jugador){
		if (self.estaDentroDelLimite(jugador)){ 
		self.desplazar(jugador)
		jugador.puedeSuicidarse(true)
		}else{
		jugador.puedeSuicidarse(false)
	    }
	 }
	 
    method estaDentroDelLimite(jugador)
	
	method desplazar(jugador)
}

class Norte inherits Direccion{
	override method estaDentroDelLimite(jugador) = jugador.position().y() < 27
	
	override method desplazar(jugador){
		jugador.moverNorte()
	}
    
    method opuesto() = sur
}

class Sur inherits Direccion{
	override method estaDentroDelLimite(jugador) = jugador.position().y() > 2
	
	override method desplazar(jugador){
		jugador.moverSur()
	}
    
    method opuesto() = norte
}

class Este inherits Direccion{
	override method estaDentroDelLimite(jugador) = jugador.position().x() < 75
	
	override method desplazar(jugador){
		jugador.moverEste()
	}
    
    method opuesto() = oeste
}

class Oeste inherits Direccion{
	override method estaDentroDelLimite(jugador) = jugador.position().x() > 2
	
	override method desplazar(jugador){
		jugador.moverOeste()
	}
    
    method opuesto() = este
}

const norte = new Norte()
const sur = new Sur() 
const este = new Este()
const oeste = new Oeste()

object quieto {
		method desplazarSiCorresponde(jugador) = jugador.puedeSuicidarse(false)
		
		method opuesto() = self   //quieto no tiene opuesto 		
}
