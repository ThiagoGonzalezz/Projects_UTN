package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.dtos.outputs.colaboraciones.FormaDeColaboracionOutputDTO;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;

@Builder
public class ConverterFormaDeColaboracionDTO {

    public FormaDeColaboracionOutputDTO convertToDTO(FormaDeColaboracion formaDeColaboracion, boolean activada) {
        String imagen = "";
        String nombre = "";
        String descripcion = "";
        String href = "";
        String stringEnum = "";

        switch (formaDeColaboracion) {
            case DONACION_DINERO:
                stringEnum = "DONACION_DINERO";
                href = "/donaciones-de-dinero";
                nombre ="Donar Dinero";
                imagen = "/img/iconosDonaciones/iconoDonarDinero.png";
                descripcion = "Tu donación económica nos permite adquirir alimentos, mantener nuestros programas y extender nuestra ayuda a más personas cada día. Con tu apoyo financiero, construimos un futuro más brillante para todos.";
                break;
            case OFERTA_PRODUCTOS_Y_SERVICIOS:
                stringEnum = "OFERTA_PRODUCTOS_Y_SERVICIOS";
                href = "/oferta-productos-y-servicios";
                nombre ="Ofrecer Productos y/o Servicios";
                imagen = "/img/iconosDonaciones/iconoOfrecerProducto.png";
                descripcion = "Con tu contribución de productos o servicios, podemos proveer alimentos, herramientas, y atención directa a quienes más lo necesitan. ¡Cada aporte, grande o pequeño, marca la diferencia en la vida de quienes apoyamos!";
                break;
            case HACERSE_CARGO_DE_HELADERAS:
                stringEnum = "HACERSE_CARGO_DE_HELADERAS";
                href = "/colocar-heladera";
                nombre ="Hacerse Cargo de una Heladera";
                imagen = "/img/iconosDonaciones/iconoColocarHeladera.png";
                descripcion = "Al asumir el mantenimiento de una heladera, garantizas que los alimentos donados se mantengan frescos y seguros para su distribución. Tu colaboración es vital para que nuestros esfuerzos lleguen con la mayor calidad posible";
                break;
            case DONACION_VIANDAS:
                stringEnum = "DONACION_VIANDAS";
                href = "/donacion-vianda";
                nombre ="Donar Vianda";
                imagen = "/img/iconosDonaciones/iconoDonarVianda.png";
                descripcion = "Tu donación de viandas nos ayuda a proporcionar comidas nutritivas a quienes más lo necesitan. Cada vianda puede marcar una gran diferencia en la vida de las personas que enfrentan dificultades.";
                break;
            case DISTRIBUCION_VIANDAS:
                stringEnum = "DISTRIBUCION_VIANDAS";
                href = "/distribucion-viandas";
                nombre ="Distribuir Vianda";
                imagen = "/img/iconosDonaciones/iconoRedistribuirVianda.png";
                descripcion = "Con tu colaboración en la distribución de viandas, aseguramos que las comidas lleguen a quienes más lo necesitan. Este esfuerzo garantiza que las viandas se entreguen de manera eficiente y oportuna.";
                break;
            case ENTREGA_TARJETAS:
                stringEnum = "ENTREGA_TARJETAS";
                href = "/registro-persona-vulnerable";
                nombre ="Registrar Persona en Situación Vulnerable";
                imagen = "/img/iconosDonaciones/iconoRegistroPersonaVulnerable.png";
                descripcion = "Registra a personas en situación vulnerable para asegurarnos de que reciban el apoyo adecuado. Tu registro permite que se les brinden recursos y asistencia en momentos críticos.";
                break;
            default:
                break;
        }

        return FormaDeColaboracionOutputDTO.builder()
                .activada(activada)
                .stringEnum(stringEnum)
                .href(href)
                .nombre(nombre)
                .descripcion(descripcion)
                .imagen(imagen)
                .build();
    }

}
