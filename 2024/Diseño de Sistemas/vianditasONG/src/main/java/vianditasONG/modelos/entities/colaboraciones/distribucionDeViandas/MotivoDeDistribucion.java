package vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import vianditasONG.utils.Persistente;
import lombok.Builder;

import javax.persistence.*;


public enum MotivoDeDistribucion {
    FALLA_TECNICA,
    HELADERA_POR_VACIARSE,
    OTRO
}
