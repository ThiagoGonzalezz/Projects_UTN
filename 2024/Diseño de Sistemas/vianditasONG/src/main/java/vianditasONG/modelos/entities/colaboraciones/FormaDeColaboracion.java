package vianditasONG.modelos.entities.colaboraciones;

import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboradores.Humano;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;




public enum FormaDeColaboracion {
    DONACION_VIANDAS,
    DISTRIBUCION_VIANDAS,
    DONACION_DINERO,
    ENTREGA_TARJETAS,
    OFERTA_PRODUCTOS_Y_SERVICIOS,
    HACERSE_CARGO_DE_HELADERAS
}
