package vianditasONG.modelos.servicios.seguridad.encriptadores;
import org.apache.commons.codec.digest.DigestUtils;
public class EncriptadorMD5 implements Encriptador{
    public String encriptar(String password) {
            return DigestUtils.md5Hex(password);
    }
}
