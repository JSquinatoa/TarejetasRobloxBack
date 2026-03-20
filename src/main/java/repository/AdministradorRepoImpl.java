package repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.jboss.logging.Logger;

import repository.model.Administrador;

@ApplicationScoped
@Transactional
public class AdministradorRepoImpl implements IAdministradorRepo {

    // esta varaible me sirve para mostrar los logs en la consola
    private static final Logger LOG = Logger.getLogger(AdministradorRepoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertarAdministrador(Administrador administrador) {
        if (administrador == null) {
            throw new IllegalArgumentException("El administrador no puede ser null");
        }

        try {
            this.entityManager.persist(administrador);
        } catch (Exception e) {
            LOG.error("Error al insertar el administrador", e);
            throw new RuntimeException("Error al insertar el administrador", e);
        }
    }

    @Override
    public Administrador seleccionarPorId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del administrador no puede ser null");
        }

        try {
            return this.entityManager.find(Administrador.class, id);
        } catch (Exception e) {
            LOG.errorf(e, "Error al buscar el administrador con ID %s", id);
            throw new RuntimeException("Error al buscar el administrador por ID", e);
        }
    }

    @Override
    @SuppressWarnings("null")
    public List<Administrador> seleccionarTodos() {
        try {
            TypedQuery<Administrador> myQuery = this.entityManager.createQuery("SELECT a FROM Administrador a", Administrador.class);
            return myQuery.getResultList();
        } catch (Exception e) {
            LOG.error("Error al listar todos los administradores", e);
            throw new RuntimeException("Error al listar todos los administradores", e);
        }
    }

    @Override
    public void actualizarAdministrador(Administrador administrador) {
        if (administrador == null || administrador.getId() == null) {
            throw new IllegalArgumentException("El administrador y su ID son requeridos para actualizar");
        }

        try {
            this.entityManager.merge(administrador);
        } catch (Exception e) {
            LOG.errorf(e, "Error al actualizar el administrador con ID %s", administrador.getId());
            throw new RuntimeException("Error al actualizar el administrador", e);
        }
    }

    @Override
    @SuppressWarnings("null")
    public void eliminarPorId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del administrador no puede ser null");
        }

        try {
            Administrador adminAEliminar = this.entityManager.find(Administrador.class, id);
            this.entityManager.remove(adminAEliminar);
        } catch (Exception e) {
            LOG.errorf(e, "Error al eliminar el administrador con ID %s", id);
            throw new RuntimeException("Error al eliminar el administrador", e);
        }
    }

    @Override
    @SuppressWarnings("null")
    public Administrador seleccionarPorUsuario(String usuario) {
        if (usuario == null || usuario.isBlank()) {
            throw new IllegalArgumentException("El usuario no puede ser null o vacío");
        }

        try {
            return this.entityManager
                    .createQuery("SELECT a FROM Administrador a WHERE a.usuario = :usuario", Administrador.class)
                    .setParameter("usuario", usuario)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            LOG.errorf(e, "Error al buscar el administrador con usuario %s", usuario);
            throw new RuntimeException("Error al buscar el administrador por usuario", e);
        }
    }

}
