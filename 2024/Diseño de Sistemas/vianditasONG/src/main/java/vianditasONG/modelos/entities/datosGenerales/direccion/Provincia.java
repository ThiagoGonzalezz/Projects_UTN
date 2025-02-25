package vianditasONG.modelos.entities.datosGenerales.direccion;

import java.text.Normalizer;

public enum Provincia {
    BUENOS_AIRES,
    CABA,
    CATAMARCA,
    CHACO,
    CHUBUT,
    CORDOBA,
    CORRIENTES,
    ENTRE_RIOS,
    FORMOSA,
    JUJUY,
    LA_PAMPA,
    LA_RIOJA,
    MENDOZA,
    MISIONES,
    NEUQUEN,
    RIO_NEGRO,
    SALTA,
    SAN_JUAN,
    SAN_LUIS,
    SANTA_CRUZ,
    SANTA_FE,
    SANTIAGO_DEL_ESTERO,
    TIERRA_DEL_FUEGO,
    TUCUMAN,
    DESCONOCIDA;



    public static boolean esProvinciaValida(String nombreProvincia) {
        String nombreNormalizado = normalizar(nombreProvincia);

        for (Provincia provincia : Provincia.values()) {
            // Verificamos si el nombre normalizado contiene el nombre de la provincia
            if (nombreNormalizado.contains(normalizar(provincia.name()))) {
                return true;
            }
        }
        return false;
    }

    public static Provincia fromString(String provincia) {
        String provinciaNormalizada = normalizar(provincia);

        for (Provincia p : Provincia.values()) {
            if (provinciaNormalizada.contains(normalizar(p.name()))) {
                return p;
            }
        }
        throw new IllegalArgumentException("No se encontró una provincia correspondiente para: " + provincia);
    }

    // Función para normalizar tildes y convertir guiones bajos a espacios
    private static String normalizar(String input) {
        String sinGuiones = input.toUpperCase()
                .replace(" ", "_")
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U");
        String normalizado = Normalizer.normalize(sinGuiones, Normalizer.Form.NFD);
        return normalizado.replaceAll("\\p{M}", ""); // Eliminar los diacríticos (tildes)
    }
}
