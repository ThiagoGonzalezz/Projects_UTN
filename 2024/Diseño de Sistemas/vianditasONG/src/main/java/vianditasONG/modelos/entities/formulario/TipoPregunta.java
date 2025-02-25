package vianditasONG.modelos.entities.formulario;

public enum TipoPregunta {
    PREGUNTA_A_DESARROLLAR,
    SINGLE_CHOICE,
    MULTIPLE_CHOICE;

    public String aString() {
        switch (this) {
            case PREGUNTA_A_DESARROLLAR:
                return "Respuesta Libre";
            case SINGLE_CHOICE:
                return "Selección Única";
            case MULTIPLE_CHOICE:
                return "Selección Múltiple";
            default:
                throw new IllegalArgumentException("Tipo de pregunta no reconocido");
        }
    }

    public static TipoPregunta aEntity(String tipo) {
        switch (tipo) {
            case "Respuesta Libre":
                return PREGUNTA_A_DESARROLLAR;
            case "Selección Única":
                return SINGLE_CHOICE;
            case "Selección Múltiple":
                return MULTIPLE_CHOICE;
            default:
                throw new IllegalArgumentException("Tipo de pregunta no reconocido");
        }
    }
}