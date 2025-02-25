package vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.mails;

public interface Mail {
    String getAsunto();
    String getEmisor();
    String getReceptor();
    String getMensaje();
}
