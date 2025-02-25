package vianditasONG.utils.creadorDeCredenciales;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.repositorios.credenciales.imp.CredencialesRepositorio;
import vianditasONG.modelos.servicios.seguridad.encriptadores.EncriptadorSHA1;
import lombok.Builder;

@Builder
public class CreadorDeCredenciales implements ICreadorDeCredenciales, WithSimplePersistenceUnit {
    @Builder.Default
    private EncriptadorSHA1 encriptadorSHA1 = ServiceLocator.getService(EncriptadorSHA1.class);
    @Builder.Default
    private CredencialesRepositorio credencialesRepositorio = ServiceLocator.getService(CredencialesRepositorio.class);

    public Credencial crearCredencial(String mail, Humano colaborador){
        String token = encriptadorSHA1.encriptar(colaborador.getTipoDeDocumento() + colaborador.getNumeroDocumento());
        Credencial credencial = Credencial.of(token, mail, colaborador);

        withTransaction(() -> credencialesRepositorio.guardar(credencial));

        return credencial;
    }
}
