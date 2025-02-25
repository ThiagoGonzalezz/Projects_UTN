package vianditasONG.modelos.repositorios.humanos.imp;

import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import lombok.Builder;


import java.util.List;
import java.util.Optional;
@Builder
public class HumanosRepositorio extends RepositorioGenerico implements IHumanosRepositorio {

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Humano> buscar(Long id) {
        Humano humano = entityManager().find(Humano.class, id);
        if (humano != null && humano.getActivo()) {
            return Optional.of(humano);
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Humano> buscarTodos(){
        return entityManager()
                .createQuery("from " + Humano.class.getName() + " where activo = 1")
                .getResultList();
    }

    @Override
    public Optional<Humano> buscarPorDocumento(TipoDeDocumento tipoDoc, String numDoc) {
        return entityManager()
                .createQuery("from " + Humano.class.getName()+ " where tipoDeDocumento = :tipoDoc AND numero_doc = :numDoc and activo = 1", Humano.class)
                .setParameter("tipoDoc", tipoDoc)
                .setParameter("numDoc", numDoc)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Humano> buscarPorUsuario(Long userId) {
        return entityManager()
                .createQuery("from " + Humano.class.getName()+ " where usuario_id = :userId and activo = 1", Humano.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .findFirst();
    }
}

