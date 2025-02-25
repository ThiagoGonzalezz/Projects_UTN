package vianditasONG.serviciosExternos.sendGrid.entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolicitudMail {
    public List<Personalization> personalizations;
    public From from;
    public String subject;
    public List<Content> content;

    public SolicitudMail(From from, String subject) {
        this.from = from;
        this.subject = subject;
        this.personalizations = new ArrayList<>();
        this.content = new ArrayList<>();
    }

    public void agregarContent(Content ... contenidos){
        Collections.addAll(this.content, contenidos);
    }

    public void agregarPersonalization(Personalization ...  personalizaciones){
        Collections.addAll(this.personalizations, personalizaciones);
    }
}
