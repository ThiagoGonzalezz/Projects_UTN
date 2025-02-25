package vianditasONG.utils.usuarioRolesYPermisos;

import io.javalin.security.RouteRole;
import vianditasONG.utils.Persistente;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Rol implements RouteRole {
    ADMIN,
    PERSONA_JURIDICA,
    PERSONA_HUMANA
}
