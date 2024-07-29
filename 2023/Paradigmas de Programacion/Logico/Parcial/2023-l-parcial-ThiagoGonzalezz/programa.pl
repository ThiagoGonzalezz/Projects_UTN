% Cancion, Compositores,  Reproducciones
cancion(bailanSinCesar, [pabloIlabaca, rodrigoSalinas], 10600177).
cancion(yoOpino, [alvaroDiaz, carlosEspinoza, rodrigoSalinas], 5209110).
cancion(equilibrioEspiritual, [danielCastro, alvaroDiaz, pabloIlabaca, pedroPeirano, rodrigoSalinas], 12052254).
cancion(tangananicaTanganana, [danielCastro, pabloIlabaca, pedroPeirano], 5516191).
cancion(dienteBlanco, [danielCastro, pabloIlabaca, pedroPeirano], 5872927). 
cancion(lala, [pabloIlabaca, pedroPeirano], 5100530).
cancion(meCortaronMalElPelo, [danielCastro, alvaroDiaz, pabloIlabaca, rodrigoSalinas], 3428854).


% Mes, Puesto, Cancion
rankingTop3(febrero, 1, lala).
rankingTop3(febrero, 2, tangananicaTanganana).
rankingTop3(febrero, 3, meCortaronMalElPelo).
rankingTop3(marzo, 1, meCortaronMalElPelo).
rankingTop3(marzo, 2, tangananicaTanganana).
rankingTop3(marzo, 3, lala).
rankingTop3(abril, 1, tangananicaTanganana).
rankingTop3(abril, 2, dienteBlanco).
rankingTop3(abril, 3, equilibrioEspiritual).
rankingTop3(mayo, 1, meCortaronMalElPelo).
rankingTop3(mayo, 2, dienteBlanco).
rankingTop3(mayo, 3, equilibrioEspiritual).
rankingTop3(junio, 1, dienteBlanco).
rankingTop3(junio, 2, tangananicaTanganana).
rankingTop3(junio, 3, lala).

%%%%%%% INSERTE SOLUCIÓN AQUI %%%%%%%

%1

esCancion(Cancion):- cancion(Cancion, _, _).

esHit(Cancion):-
    esCancion(Cancion),
    forall(mes(Mes), rankingTop3(Mes, _, Cancion)).

mes(Mes):- rankingTop3(Mes, _, _).

%2

noReconocidaPorCriticos(Cancion):-
    tieneMuchasReproducciones(Cancion),
    not(rankingTop3(_, _, Cancion)).

tieneMuchasReproducciones(Cancion):-
    cancion(Cancion, _, Reproducciones),
    Reproducciones > 7000000.

%3

sonColaboradores(Compositor1, Compositor2):-
    compuso(Compositor1, Cancion),
    compuso(Compositor2, Cancion),
    Compositor1 \= Compositor2.

compuso(Compositor, Cancion):-
    cancion(Cancion, Compositores, _),
    member(Compositor, Compositores).

%TRABAJOS

%conductor(AniosDeExperiencia).

%periodista(AniosDeExperiencia, Titulo).

%reportero(AniosDeExperiencia, CantidadDeNotas).

%4

%a
trabajador(tulio, conductor(5)).

%b
trabajador(bodoque, periodista(2, licenciatura)).
trabajador(bodoque, reportero(5, 300)).

%c
trabajador(marioHugo, periodista(10, posgrado)).

%d
trabajador(juanin, conductor(0)).

%(6)

%ayudante(AniosDeExperiencia, Bondad)

trabajador(tom, ayudante(3, 10000)).

%5 

/* En este punto, considero hacer inversible unicamente a estas dos predicados ya que
son las que permiten consultar todos los sueldos de todas las personas, y por lo
que entiendo, esa es la gran utilidad y lo enriquecedor de este punto */

sueldoTotal(Persona, Total):-
    trabajador(Persona, _),
    findall(Sueldo, sueldoPorTrabajo(Persona, Sueldo), ListaSueldos),
    sumlist(ListaSueldos, Total).

sueldoPorTrabajo(Persona, Sueldo):-
    trabajador(Persona, Trabajo),
    sueldo(Trabajo, Sueldo).

%(6)

sueldo(ayudante(AniosDeExperiencia, Bondad), Sueldo):-
    Sueldo is AniosDeExperiencia * 1000 + Bondad * 5000.
%Tom cobra un monton porque me va a promocionar.

%a
sueldo(conductor(AniosDeExperiencia), Sueldo):-
    Sueldo is AniosDeExperiencia * 10000.

%b
sueldo(reportero(AniosDeExperiencia, NotasHechas), Sueldo):-
    Sueldo is 10000 * AniosDeExperiencia + 100 * NotasHechas.

%c

sueldo(periodista(AniosDeExperiencia, Titulo), Sueldo):-
    SueldoBase is 5000 * AniosDeExperiencia,
    aumentoCorrespondiente(Titulo, SueldoBase, Aumento),
    Sueldo is SueldoBase + Aumento.  

aumentoCorrespondiente(licenciatura, SueldoBase, Aumento):-
    Aumento is (SueldoBase * 20) / 100.

aumentoCorrespondiente(posgrado, SueldoBase, Aumento):-
    Aumento is (SueldoBase * 35) / 100.

%6

%Esta mezclado con el punto 4 y 5 para que no aparezca el error de discontinuo.
%trabajo en la linea 84.
%su respectivo sueldo en la linea 105.

/* El concepto que permitio la facil implementacion del nuevo trabajo en todo el programa es
el concepto de polimorfismo. Este concepto se basa en que todo el predicado principal (SueldoTotal) sea unico para
todos los tipos de trabajo(functores), y lo unico que cambia es que cada functor tiene su propio predicado pequeño (sueldo).
La gran ventaja que tiene es que si queremos añadir un nuevo trabajo, con el solo hecho de añadir el trabajo y su forma de
calcular el sueldo, ya funcionaría todo el programa correctamente con la nueva implementación, sin la necesidad de 
modificar el predicado principal. */ 