import wollok.game.*
import Sonidos.*
import teclado.*
import screenManagement.*
import celdas.*
import players.*
import spawns.*
import menus.*
import reloj.*

object managerGeneral{
	
	method configurarJuego(){
		game.title("land.io")
		game.width(80)
	    game.height(35)
	    game.cellSize(20)
	    game.boardGround("fondoCeldas.png")
		}
	
	method configMenuInicial(){
		game.clear()
		screenManagement.configuarMenuInicial()
		sonido.configInicio()
	    teclado.configInicio()
	}
	
	
	method mostrarCreditos(){
		game.clear()
		screenManagement.configCreditos()
		teclado.configCreditos()
		sonido.configCreditos()
	}
	
	method mostrarInstrucciones(){
		game.clear()
		menuInstrucciones.reiniciarSeleccionado()
		screenManagement.configInstrucciones()
		tutoriales.transicion()
		teclado.configInstrucciones()
		sonido.configInstrucciones()
	}	
	
	method cerrarJuego(){
		game.clear()
		game.width(1)
	    game.height(1)
	    game.cellSize(1)
	}
	
	method cargarPartida(){
		game.clear()
		reloj.iniciarContador()
		contadorInicio.estadoInicial()
		screenManagement.cargarPartida() 
		celdasManager.inicializarPartida()
	}
	
	method iniciarPartida(){
		jugadores.forEach({jugador => jugador.reiniciarContadores() spawns.anyOne().aparecerJugador(jugador)})
		screenManagement.configPartida()
		sonido.iniciarPartida()
		teclado.configPartida()
		contadorInicio.iniciar()
	}
	
	method finalizarPartida(){
		jugadores.forEach({jugador => jugador.puedeMoverse(false)})
		game.removeTickEvent("Actualizar Reloj")
		game.removeTickEvent("Mover a los Jugadores")
		screenManagement.terminarPartida()
		game.schedule(7450, {teclado.configFinal()})
		sonido.configFinalPartida()
	}
}
