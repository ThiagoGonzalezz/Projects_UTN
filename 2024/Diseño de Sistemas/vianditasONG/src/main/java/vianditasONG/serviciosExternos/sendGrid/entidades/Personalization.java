package vianditasONG.serviciosExternos.sendGrid.entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Personalization {

    public List<To> to = new ArrayList<>();;

    public Personalization() {
        this.to = new ArrayList<>();
    }

    public void agregarTos(To ... tos) {
        Collections.addAll(this.to, tos);
    }
}
