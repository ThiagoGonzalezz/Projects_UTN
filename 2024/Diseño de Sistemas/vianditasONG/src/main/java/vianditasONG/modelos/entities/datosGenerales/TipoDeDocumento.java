package vianditasONG.modelos.entities.datosGenerales;

public enum TipoDeDocumento {
    DNI,
    LE,
    LC,
    NINGUNO;

    public static TipoDeDocumento fromInt(int numero) {
        TipoDeDocumento[] valores = TipoDeDocumento.values();
        if (numero < 0 || numero >= valores.length) {
            throw new IllegalArgumentException("Número fuera de rango");
        }
        return valores[numero];
    }
}
