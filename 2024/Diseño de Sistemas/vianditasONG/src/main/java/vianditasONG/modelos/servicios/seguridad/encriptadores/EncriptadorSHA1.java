package vianditasONG.modelos.servicios.seguridad.encriptadores;
import lombok.Builder;
import org.apache.commons.codec.digest.DigestUtils;

@Builder
public class EncriptadorSHA1 implements Encriptador {

    public String encriptar(String password) {
        return DigestUtils.sha1Hex(password);
    }
}