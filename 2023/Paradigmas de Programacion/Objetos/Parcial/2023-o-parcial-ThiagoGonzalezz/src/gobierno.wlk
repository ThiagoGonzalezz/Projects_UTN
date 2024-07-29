import comensales.*

class Gobierno {
	const comensalesHabitantes = []
	
	method matarEconomia(){
		self.habitantesDePaladarFino().forEach({comensal => comensal.limitarEconomicamente()})
	}
	
	method habitantesDePaladarFino() = comensalesHabitantes.filter({habitante => habitante.esDePaladarFino()})
	
	//Cuando al pais le va muy bien se hacen todos paladar fino
	method convertirseEnPotencia(){
		comensalesHabitantes.forEach({comensal => comensal.seHaceElFiuFiu()})
	}
}
