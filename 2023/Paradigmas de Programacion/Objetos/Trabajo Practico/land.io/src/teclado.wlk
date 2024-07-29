import wollok.game.*
import Sonidos.*
import players.*
import direcciones.*
import menus.*
import screenManagement.*


object teclado {
	method configInicio(){
	keyboard.up().onPressDo({sonido.switchMenu() menuSelect.sumarSeleccionado(3)})
	keyboard.down().onPressDo({sonido.switchMenu() menuSelect.sumarSeleccionado(1)})
	keyboard.enter().onPressDo({sonido.accionarInicio() managerMenuInicio.accionar()})
	}
	method configInstrucciones(){
	keyboard.left().onPressDo({sonido.switchMenu() menuInstrucciones.sumarSeleccionado(2)})
	keyboard.right().onPressDo({sonido.switchMenu() menuInstrucciones.sumarSeleccionado(1)})
	keyboard.control().onPressDo({menuInstrucciones.volverAlMenu() })
	}
	method configCreditos(){
	keyboard.control().onPressDo({menuCreditos.volverAlMenu() })
	}
	method configPartida(){
	keyboard.up().onPressDo({jugadorRojo.mirar(norte)})
	keyboard.down().onPressDo({jugadorRojo.mirar(sur)})
	keyboard.left().onPressDo({jugadorRojo.mirar(oeste)})
	keyboard.right().onPressDo({jugadorRojo.mirar(este)})
	keyboard.control().onPressDo({jugadorRojo.mirar(quieto)})
	keyboard.w().onPressDo({jugadorAzul.mirar(norte)})
	keyboard.s().onPressDo({jugadorAzul.mirar(sur)})
	keyboard.a().onPressDo({jugadorAzul.mirar(oeste)})
	keyboard.d().onPressDo({jugadorAzul.mirar(este)})
	keyboard.shift().onPressDo({jugadorAzul.mirar(quieto)})
	keyboard.i().onPressDo({jugadorVerde.mirar(norte)})
	keyboard.k().onPressDo({jugadorVerde.mirar(sur)})
	keyboard.j().onPressDo({jugadorVerde.mirar(oeste)})
	keyboard.l().onPressDo({jugadorVerde.mirar(este)})
	keyboard.space().onPressDo({jugadorVerde.mirar(quieto)})
	}
	
	method configFinal(){
	keyboard.up().onPressDo({sonido.switchMenu() menuFinalSelect.sumarSeleccionado(2)})
	keyboard.down().onPressDo({sonido.switchMenu() menuFinalSelect.sumarSeleccionado(1)})
	keyboard.enter().onPressDo({sonido.accionarInicio() managerMenuFinal.accionar()})
	}
}
