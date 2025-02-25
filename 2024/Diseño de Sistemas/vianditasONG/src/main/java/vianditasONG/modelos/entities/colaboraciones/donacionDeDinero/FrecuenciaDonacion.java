package vianditasONG.modelos.entities.colaboraciones.donacionDeDinero;

import lombok.Setter;

public enum FrecuenciaDonacion {
    UNICA_VEZ(0),
    UNA_CADA_SEMANA(1),
    UNA_CADA_DOS_SEMANAS(15),
    UNA_CADA_MES(30);

    private Integer frecuenciaEnDias;

    FrecuenciaDonacion(Integer frecuenciaEnDias) {
        this.frecuenciaEnDias = frecuenciaEnDias;
    }

    public Integer getFrecuenciaEnDias() {
        return this.frecuenciaEnDias;
    }

    // Método estático para obtener la frecuencia a partir de un valor numérico
    public static FrecuenciaDonacion fromFrecuenciaEnDias(Integer dias) {
        for (FrecuenciaDonacion frecuencia : FrecuenciaDonacion.values()) {
            if (frecuencia.getFrecuenciaEnDias().equals(dias)) {
                return frecuencia;
            }
        }
        throw new IllegalArgumentException("No existe una frecuencia con " + dias + " días.");
    }
}


