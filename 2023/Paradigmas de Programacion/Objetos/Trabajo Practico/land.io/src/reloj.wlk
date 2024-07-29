import wollok.game.*
import screenManagement.*
import teclado.*
import Sonidos.*
import players.*
import managerGeneral.*

const posOut = game.at(82,37)

object reloj {
	var tiempoRestante = 120
	var color = "amarillo"
	
	method tiempoRestante() = tiempoRestante
	
	method avanzarTiempo(){
	if (tiempoRestante != 6){
		 tiempoRestante -= 1
		 }else{
		 	 tiempoRestante -= 1
		 	 color = "rojo"
		 	 sonido.cuentaRegresivaFinal()
		  	 game.schedule(4900,{managerGeneral.finalizarPartida()})
		  	 }
	}
	
	method minutos() = tiempoRestante.div(60)
	
	method segundos() = tiempoRestante - self.minutos()*60
	method segundos1() = self.segundos().div(10)
	method segundos2() = self.segundos() % 10
	
	method iniciarContador() {
		tiempoRestante = 120
		color = "amarillo"
	}
	
	method color() = color
	}
	
object minutero1{
	const property position = game.at(37,31)
    method image() = "minutero0" + reloj.color() + ".png"
}

object minutero2{
	const property position = game.at(38,31) 
	method image() = "minutero" + reloj.minutos().toString() + reloj.color() + ".png"
}

object segundero1{
	const property position = game.at(40,31)
	method image() = "minutero" + reloj.segundos1().toString() + reloj.color() + ".png"
}

object segundero2{
	const property position = game.at(41,31)
	method image() = "minutero" + reloj.segundos2().toString() + reloj.color() + ".png"
}

object timeOut{
	const property position = game.at(37,31)
	method image() = "timeOut.png"
}

object fondoTimeOut{
	const property position = game.at(38,31) 
	method image() = "fondoNegroTimeOut.png" 
}
	

	
	
