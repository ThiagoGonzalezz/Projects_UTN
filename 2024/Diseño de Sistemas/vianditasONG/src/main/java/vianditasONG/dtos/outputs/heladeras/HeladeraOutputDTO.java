package vianditasONG.dtos.outputs.heladeras;



import lombok.*;
import vianditasONG.modelos.entities.heladeras.EstadoHeladera;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class HeladeraOutputDTO {

    private Long id;
    private String nombreHeladera;
    private String modeloId;
    private String nombrePuntoEstrategico;
    private Integer cantidadViandas;
    private String fechaDeRegistro;
    private String estadoHeladera;
    private Integer diasDesactivada;
    private String fechaUltimaDesactivacion;
    private Float temperatura;
    private String fechaTempertauraRegistrada;
    private String encargadoHeladera;

}



