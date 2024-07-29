import platos.*
import comensales.*

object parrillaMiguelito{
	var property platosOfrecidos = [hamburguesaChica, provoletaConGluten] //es var porque intuyo que en un futuro se puede modificar
	var ingresos = 0
	const property clientesRegistrados = #{} // no se pueden repetir, tambien se podria usar una lista y verificar si ya pertenece antes de agregar
	
	method ganarPlata(cantidad){ingresos += cantidad}
	
	method registrarCliente(cliente){clientesRegistrados.add(cliente)}
	
	method cobrar(plato, cliente){
		self.registrarCliente(cliente)
		cliente.pagar(plato.precio())
	    self.ganarPlata(plato.precio())	
	}
	
	method hacerPromocion(cantDinero){
		clientesRegistrados.forEach({cliente => self.regalarPlata(cantDinero, cliente)})
	}
	
	method regalarPlata(cantDinero, cliente){
		cliente.recibirPlata(cantDinero)
	}
}

const hamburguesaChica = new HamburguesaDeCarne(pesoMedallon = 30)
const provoletaConGluten = new Provoleta(peso = 500, tieneEmpanado = true)