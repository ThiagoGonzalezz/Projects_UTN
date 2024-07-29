import wollok.game.*
import players.*
import reloj.*
import celdas.*
import spawns.*
import estadisticas.*
import menus.*
object screenManagement {
	
	method configuarMenuInicial(){
	game.addVisual(fondoNegro)
	game.addVisual(fondoMenuInicio)
	game.addVisual(menuSelect)
	}
	
	method cargarPartida(){
		game.addVisual(pantallaCarga)
	}
	
	method configPartida(){
    pantallaCarga.desaparecer()
	game.addVisual(marcoFondo)
	game.addVisual(minutero1)
	game.addVisual(minutero2)
	game.addVisual(segundero1)
	game.addVisual(segundero2)
	game.addVisual(logoMenu)
	game.addVisual(contadorInicio)
	game.addVisual(marcoRanking)
	game.addVisual(fondoRanking)
	game.addVisual(fotoJugadorRojo)
	game.addVisual(fotoJugadorAzul)
	game.addVisual(fotoJugadorVerde)	
	game.addVisual(digito1Rojo)
	game.addVisual(digito1Azul)
	game.addVisual(digito1Verde)
	game.addVisual(digito2Rojo)
	game.addVisual(digito2Azul)
	game.addVisual(digito2Verde)
	game.schedule(3000,{
		game.removeVisual(contadorInicio)
		contadorInicio.estadoInicial()
		game.addVisual(goInicio)
		game.schedule(1000, {game.removeVisual(goInicio)})
		jugadores.forEach({jugador => jugador.puedeMoverse(true)})
		game.onTick(200,"Mover a los Jugadores", {jugadores.forEach({jugador => jugador.moverYAccionarCelda()})})
		game.onTick(1000, "Actualizar Reloj", {reloj.avanzarTiempo()})
		})
	}
	

	method configInstrucciones(){
	game.addVisual(menuInstrucciones)
	// PARA LA SECUENCIA DE IMAGENES DENTRO DE LAS INTRUCCIONES
	game.addVisual(tutoriales)
	}
	
	method configCreditos(){
	game.addVisual(menuCreditos)
	}
	
	method terminarPartida(){
		game.addVisual(barraTiempoOut)
		game.removeVisual(minutero1)
		game.removeVisual(minutero2)
		game.removeVisual(segundero1)
		game.removeVisual(segundero2)
		game.addVisual(fondoTimeOut)
		game.addVisual(timeOut)
		game.schedule(3000 , {game.removeVisual(barraTiempoOut) barraWinner.mostrarGanador()})
		game.schedule(7500, {self.configMenuFinal()})
	}
	
	method configMenuFinal(){
		game.removeVisual(barraWinner)
		podio.mostrarPodio()
	    game.addVisual(menuFinalSelect)
	    sobreviviente.mostrarSobreviviente()
	    killer.mostrarKiller()
	    }
}

  

//JUEGO CERRADO

object fondoNegro{
	method image() = "fondoNegro.png"
	
	method position() = game.at(-2,-2)
}

//MENU INICIO

object fondoMenuInicio{
	method image() = "fondoInicioAgrandar.png"
	method position() = game.at(10, 0)
}

//PANTALLA DE CARGA

object pantallaCarga{
	var contador = 1
	
	method position() = game.at(-1,0)

    method image() = "pantallaCarga" + self.modo().toString() + ".png"
    
    method modo() = contador.div(12)
    
    method avanzarContador(){
    	contador += 1
    }
    
    method reiniciarContador(){
    	contador = 1
    }
    
    method desaparecer(){
    	game.removeVisual(self)
    	self.reiniciarContador()
    }
}

//PARTIDA

object contadorInicio{
	var contador = 3
	const property position = game.at(35,10)
	
	method image() = "contadorInicial" + contador.toString() + ".png"
	
	method avanzarContador(){
		contador -= 1
	}

	method iniciar(){
		game.onTick(1000, "contadorInicio" ,{self.avanzarContador()})
		game.schedule(2900, {game.removeTickEvent("contadorInicio")})
	}
	
	method estadoInicial(){contador = 3}
	
}

object goInicio{
	method position() = game.at(27,10)
	method image() = "goInicio.png"
}

object logoMenu{
	method image() = "logotipoMenu.png"
	method position() = game.at(75,32)
}

object marcoFondo{
	method image() = "marcoPartida_" + reloj.color() + ".png" //el marco del reloj esta incluido en el marco violeta, por eso depende del color del reloj
	method position() = game.at(0,0)
}

object fondoRanking{
	method image()="fondoRanking.png"
	method position() = game.at(10,31)
}
	
object marcoRanking{
	method image()="marcoRanking.png"
	method position() = game.at(10,31)}
	

//FINAL PARTIDA

object barraTiempoOut{
	const property position = game.at(-3,13)
	const property image = "barraTiempoFinalizado.png"
}

object barraWinner{
	var ganador 
	const property position = game.at(-1,4)
	method image() =  "ganador" + ganador + ".png"
	method establecerGanador(){
		ganador = (jugadores.max{jugador=>jugador.terreno().size()}).nombre() 
	}
	method mostrarGanador(){
		self.establecerGanador()
		game.addVisual(self)
	}
}
object podio{
	var ganador
	var tercero
	const property position = game.at(20,7)
	method image() = ganador + "_" + tercero + ".png"
	
	method establecerPodio(){
	 ganador = (jugadores.max{jugador=>jugador.terreno().size()}).nombre()
	 tercero = (jugadores.reverse().min{jugador=>jugador.terreno().size()}).nombre()
	}
	method mostrarPodio(){
    		self.establecerPodio()
    		game.addVisual(self)
    }	
}


