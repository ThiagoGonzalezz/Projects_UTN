package vianditasONG.serviciosExternos.sendGrid.entidades;

import lombok.Builder;

@Builder
public class From {

    public String email;

    public From(String email) {
        this.email = email;
    }
}
