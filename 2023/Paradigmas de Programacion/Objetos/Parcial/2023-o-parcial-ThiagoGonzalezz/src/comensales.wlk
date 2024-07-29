import platos.*
import parrillaMiguelito.*

class Comensal {
	var dinero
	var tipoComensal
	
	method dinero() = dinero //solo para un test con efecto
	
	method leAgradaComida(comida) = tipoComensal.leAgrada(comida)
	
	method pagar(cantidad){dinero -= cantidad}
	
	method recibirPlata(cantidad){dinero += cantidad}
	
	method darseUnGusto(){
		const platoElegido = self.elegirPlato(parrillaMiguelito.platosOfrecidos())
		self.comprar(platoElegido)
	}	

    method comprar(plato){parrillaMiguelito.cobrar(plato,self)}
    
    method elegirPlato(platos){
    const platosInteresantes = platos.filter({plato => self.podriaComprar(plato)}) 
    self.validarPoderComprarAlgo(platosInteresantes)
    return self.elegirElMasValorado(platosInteresantes)
   }
   
   method podriaComprar(plato) = self.leAgradaComida(plato) && self.puedePagar(plato)
   
   method puedePagar(plato) = plato.precio() <= dinero
   
   method validarPoderComprarAlgo(platosPosibles){
   	if(platosPosibles.isEmpty()){
   		throw new Exception(message = "La parrilla de Miguelito no tiene en oferta ningun plato que le agrade y le alcance para pagar al comensal")
   	}
   }
   
   method elegirElMasValorado(listaPlatos) = listaPlatos.max({plato => plato.valoracion()})
   
   // considero que no es necesario explotar el programa cuando se le vuelve a asignar el mismo tipo de comensal
   
   method tenerProblemasGastricos(){tipoComensal = celiaco } 
   
   method limitarEconomicamente(){tipoComensal = todoTerreno} //Se dijo que no era necesario validar que pase de celiaco a todoTerreno, aunque sea raro
   
   method seHaceElFiuFiu(){tipoComensal = paladarFino} //Se dijo que no era necesario validar que pase de celiaco a PaladarFino, aunque sea raro

   method esDePaladarFino() = tipoComensal == paladarFino
   
}

object celiaco{
	method leAgrada(comida) = comida.esAptoCeliaco()
}

object paladarFino{
	method leAgrada(comida) = comida.esEspecial() || comida.valoracion() > 100
}

object todoTerreno{
	method leAgrada(comida) = true
}


