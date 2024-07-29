import wollok.game.*
import managerGeneral.*
import Sonidos.*
import teclado.*

const menuFinalSelect = new MenuFinalSelect(position=game.at(20,7))
const menuCreditos = new MenuCreditos(position = game.at(0,0))  
const menuInstrucciones = new MenuInstrucciones(position = game.at(0,0))
const menuSelect = new MenuSelect(position = game.at(24,12))

class Menu{
	var seleccionado = 0
	const property position
	method seleccionado() = seleccionado
	method modo() = seleccionado%3
	method sumarSeleccionado(num){
		seleccionado += num
	}
	method image()
}

class MenuCreditos inherits Menu{
	override method image() = "creditos.png"
	
	method volverAlMenu() {
		managerGeneral.configMenuInicial()
		sonido.stopmusicaCreditos()
	}
}

class MenuFinalSelect inherits Menu{
	override method image() = "menuFinalSelect" + self.modo().toString() +".png"
}

class MenuInstrucciones inherits Menu{
	
	method reiniciarSeleccionado(){
		seleccionado = 0
	}
	override method image() = "MenuInstrucciones" + self.modo().toString() +".png"
	
	method volverAlMenu() {
		managerGeneral.configMenuInicial()
		sonido.stopmusicains()
	}
	
}

class MenuSelect inherits Menu{
	override method modo() = seleccionado%4
	
	override method image() = "MenuSelect" + self.modo().toString() +".png"
}






// PARA LA SECUENCIA DE IMAGENES DENTRO DE LAS INSTRUCCIONES, FALTAN IMAGENES FORMATO : tutorial+numero de imagen de instrucciones+numero de imagen de tutorial+.png

object tutoriales {
	var x =0
	const property position = game.at(14,7)
	method sumarx() {x = x+1}
	method restox() = x%12
	method image() = "tutorial"+ menuInstrucciones.modo().toString()+self.restox()+".png"
	method transicion() {game.onTick(1000,"transicion",{self.sumarx()})}
}

//"tutorial"+menuInstrucciones.modo().toString()+self.restox()+".png"


object managerMenuInicio{
	
	method accionar(){
		if(menuSelect.modo() == 3){
			managerGeneral.cerrarJuego()
		}else if(menuSelect.modo() == 2){
			managerGeneral.mostrarCreditos()
		}else if(menuSelect.modo() == 1){
			managerGeneral.mostrarInstrucciones()
		}else{
			managerGeneral.cargarPartida()
		}
	}
}



object managerMenuFinal{
	
	method accionar(){
		sonido.stopMusicaMenuFinal()
		if(menuFinalSelect.modo() == 0){
			managerGeneral.cargarPartida()
		}else if(menuFinalSelect.modo() == 1){ 
			managerGeneral.configMenuInicial()
		}else{
			managerGeneral.cerrarJuego()
		}
	}
}