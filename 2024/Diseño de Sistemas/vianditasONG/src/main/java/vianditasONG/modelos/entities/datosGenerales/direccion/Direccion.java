package vianditasONG.modelos.entities.datosGenerales.direccion;

import vianditasONG.config.Config;
import lombok.*;

import javax.persistence.*;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Direccion {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="localidadDireccion_id", columnDefinition = "BIGINT")
    private Localidad localidad;
    @Column(name = "calle", columnDefinition = "VARCHAR(100)")
    private String calle;
    @Column(name="alturaDireccion", columnDefinition = "VARCHAR(10)")
    private String altura;



    public String direccionCompleta(){
        return calle + " " + altura + ", "
                + this.localidadProvinciaYPais();
    }

    public String direccionParaTransformar(){
        return altura + " " + calle + ", "
                + this.localidadProvinciaYPais();
    }

    private String localidadProvinciaYPais(){
        return localidad.getNombre() + ", "
                + this.provinciaToString(localidad.getProvincia()) + ", "
                + Config.getInstancia().obtenerDelConfig("paisDeLaONG");
    }

    private String provinciaToString(Provincia provincia) {
        return switch (provincia) {
            case BUENOS_AIRES -> "Buenos Aires";
            case CABA -> "CABA";
            case CATAMARCA -> "Catamarca";
            case CHACO -> "Chaco";
            case CHUBUT -> "Chubut";
            case CORDOBA -> "Córdoba";
            case CORRIENTES -> "Corrientes";
            case ENTRE_RIOS -> "Entre Ríos";
            case FORMOSA -> "Formosa";
            case JUJUY -> "Jujuy";
            case LA_PAMPA -> "La Pampa";
            case LA_RIOJA -> "La Rioja";
            case MENDOZA -> "Mendoza";
            case MISIONES -> "Misiones";
            case NEUQUEN -> "Neuquén";
            case RIO_NEGRO -> "Río Negro";
            case SALTA -> "Salta";
            case SAN_JUAN -> "San Juan";
            case SAN_LUIS -> "San Luis";
            case SANTA_CRUZ -> "Santa Cruz";
            case SANTA_FE -> "Santa Fe";
            case SANTIAGO_DEL_ESTERO -> "Santiago del Estero";
            case TIERRA_DEL_FUEGO -> "Tierra del Fuego";
            case TUCUMAN -> "Tucumán";
            default -> "Provincia desconocida";
        };
    }
}
