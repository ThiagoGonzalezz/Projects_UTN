

class Plato{
	
	method precio() = 
	if(self.esAptoCeliaco()) self.precioBase() + 1200 else self.precioBase() 
	
	method precioBase() = self.valoracion() * 300
	
	method esAptoCeliaco() // cada tipo de plato tiene su propio comportamiento para la celiaquia

    method valoracion() //cada tipo de plato tiene su propio comportamiento  para la valoracion
    
    method peso() //cada tipo de plato tiene su propio comportamiento para el peso
    
    method esEspecial() = self.peso() > 250 //varios tipos de platos tienen este comportamiento
    
    
}

class Provoleta inherits Plato{
	const property peso
	const tieneEmpanado
	
	override method esAptoCeliaco() = not tieneEmpanado
	
	override method esEspecial() = super() && tieneEmpanado
	
	override method valoracion() = if(self.esEspecial()) 120 else 80
}


class HamburguesaDeCarne inherits Plato{
	const pesoMedallon = 100
 	const tipoPan = panIndustrial
    	
	override method esAptoCeliaco() = tipoPan.esAptoCeliaco()
	
	method pesoTotalMedallones() = pesoMedallon
	
	override method peso() = self.pesoTotalMedallones() + tipoPan.peso()
	
	override method valoracion() = self.peso() / 10
}

class Pan{ //Al ser una clase, para agregar un nuevo pan es tan facil como instanciar esta clase con las caracteristicas del nuevo pan
	const property peso
	const property esAptoCeliaco	
}

object panIndustrial inherits Pan(peso= 60, esAptoCeliaco = false){} //tambien podrian haber sido const

object panCasero inherits Pan(peso = 100, esAptoCeliaco = false){}

object panDeMaiz inherits Pan(peso = 30, esAptoCeliaco = true){}


class HamburguesaDoble inherits HamburguesaDeCarne{
	override method esEspecial() = self.peso() > 500
	
	override method pesoTotalMedallones() = 2 * pesoMedallon
}

class CorteCarne inherits Plato{
	const tipoCorte
	const coccion
	const property peso
	
	override method esAptoCeliaco() = true
	
	override method valoracion() = 100
	
	override method esEspecial() = super() && self.estaAPunto()
	
	method estaAPunto() = coccion == "APunto"
}

class Parrillada inherits Plato{
	const platos = []
	
	override method peso() = platos.sum({plato => plato.peso()})
	
	method tieneAlMenos3Componentes() = platos.size() >= 3
	
	override method esEspecial() = super() && self.tieneAlMenos3Componentes()
	
	override method esAptoCeliaco() = platos.all({plato => plato.esAptoCeliaco()})
	
	override method valoracion() = platos.map({plato => plato.valoracion()}).max()
}





