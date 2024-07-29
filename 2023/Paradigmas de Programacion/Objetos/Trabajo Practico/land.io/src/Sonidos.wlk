import wollok.game.*
import reloj.*

object sonido{
    var musicaInicio //Estan modelados como atributos variables para poder reutilizarlos en caso de reiniciar el juego
    var musicaPrincipioPartida
    var musicaFinalPartida
    var musicaMenuFinal
    var musicaIns
    var musicaCreditos
    
    //MENU INICIO
    
	method configInicio(){
		musicaInicio = game.sound("musicaMenuInicio.mp3")
		game.sound("switchMenu.mp3").volume(0.60)
        musicaInicio.volume(0.5)
        game.schedule(500,{musicaInicio.play()})
        game.schedule(501,{musicaInicio.shouldLoop(true)})
	}
	
	method accionarInicio(){
		musicaInicio.volume(0)
		game.sound("enterPress.mp3").play()
	}
	
	method switchMenu(){
		game.sound("switchMenu.mp3").play()
	}
	
	//PARTIDA
	
	method iniciarPartida(){
		game.sound("countdownInicio.mp3").play()
		game.schedule(3000, {self.configPartida()})
	}
	
	method configPartida(){
		musicaPrincipioPartida = game.sound("musicaPrincipioPartida.mp3")
		musicaPrincipioPartida.volume(0.40)
        musicaFinalPartida = game.sound("musicaFinalPartida.mp3")
        musicaFinalPartida.volume(0.40)
        game.schedule(1,{musicaPrincipioPartida.play()})
 	    game.schedule(95001,{musicaFinalPartida.play()})
	}
	
	method movimientoGiro(){
		game.sound("movimientoGiro.mp3").play()
	}

    //FINAL PARTIDA
	
	method cuentaRegresivaFinal(){
		game.sound("clock.mp3").play()
		game.schedule(2000, {game.sound("countdown3Final.mp3").play()})
	}
	
	method configFinalPartida(){
		musicaFinalPartida.stop()
		game.sound("cornetaTimeOut.mp3").play()
		game.schedule(3000, {game.sound("musicaVictoria.mp3").play()})
		game.schedule(7000, {self.setearMusicaMenuFinal()})
	}
	
	method setearMusicaMenuFinal(){
		musicaMenuFinal = game.sound("musicaMenuFinal.mp3")
		musicaMenuFinal.volume(0.5)
		musicaMenuFinal.play()
		musicaMenuFinal.shouldLoop(true)
	}
	
	method stopMusicaMenuFinal(){
		musicaMenuFinal.stop()
	}
	
 // CREDITOS
	
method configCreditos(){
		musicaCreditos = game.sound("musicaCreditos.mp3")
		game.sound("switchMenu.mp3").volume(0.60)
        musicaCreditos.volume(0.5)
        game.schedule(500,{musicaCreditos.play()})
        game.schedule(501,{musicaCreditos.shouldLoop(true)})
	}



 // INSTRUCCIONES

	
     method configInstrucciones(){
		musicaIns = game.sound("musicaInstrucciones.mp3")
		game.sound("switchMenu.mp3").volume(0.60)
        musicaIns.volume(0.5)
        musicaIns.play()
        musicaIns.shouldLoop(true)
	}
	method stopmusicains(){musicaIns.stop()}
	
	method stopmusicaCreditos(){musicaCreditos.stop()}
	}
	
