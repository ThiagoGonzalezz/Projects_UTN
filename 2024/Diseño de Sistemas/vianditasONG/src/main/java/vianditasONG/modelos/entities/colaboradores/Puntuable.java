package vianditasONG.modelos.entities.colaboradores;

public interface Puntuable {

    String obtenerNombreCompleto();
    void restarPuntos(Double cantPuntos);
    void sumarPuntos(Double cantPuntos);

    Double getPuntos();
}
