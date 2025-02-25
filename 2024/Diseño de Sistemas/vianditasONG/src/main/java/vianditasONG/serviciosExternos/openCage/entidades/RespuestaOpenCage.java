package vianditasONG.serviciosExternos.openCage.entidades;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RespuestaOpenCage {
    @Getter
    private List<Resultado> results;

    public RespuestaOpenCage() {
        this.results = new ArrayList<>();
    }

    public void agregarResultados(Resultado ... resultados){
        Collections.addAll(this.results, resultados);
    }
}
