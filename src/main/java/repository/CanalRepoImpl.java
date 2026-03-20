package repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.jboss.logging.Logger;

import repository.model.Canal;

@ApplicationScoped
@Transactional
public class CanalRepoImpl implements ICanalRepo {

    private static final Logger LOG = Logger.getLogger(CanalRepoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertarCanal(Canal canal) {

        if (canal == null) {
            throw new IllegalArgumentException("El canal no puede ser null");
        }

        try {
            this.entityManager.persist(canal);
        } catch (Exception e) {
            LOG.error("Error al insertar el canal", e);
            throw new RuntimeException("Error al insertar el canal", e);
        }
    }

    @Override
    public Canal seleccionarPorId(Integer id) {
        // Validar que el ID no sea nulo para evitar consultas erróneas
        if (id == null) {
            throw new IllegalArgumentException("El ID del canal no puede ser null");
        }

        try {
            // Buscar la entidad en base al ID
            return entityManager.find(Canal.class, id);
        } catch (Exception e) {
            LOG.errorf(e, "Error al buscar el canal con ID %s", id);
            throw new RuntimeException("Error al buscar el canal por ID", e);
        }
    }

    @Override
    @SuppressWarnings("null")
    public List<Canal> seleccionarTodos() {
        try {
            // Obtener todos los registros de la tabla Canal
            TypedQuery<Canal> query = entityManager.createQuery("SELECT c FROM Canal c", Canal.class);
            return query.getResultList();
        } catch (Exception e) {
            LOG.error("Error al listar todos los canales", e);
            throw new RuntimeException("Error al listar todos los canales", e);
        }
    }

    @Override
    public void actualizar(Canal canal) {
        if (canal == null || canal.getId() == null) {
            throw new IllegalArgumentException("El canal y su ID son requeridos para actualizar");
        }

        try {
            entityManager.merge(canal);
        } catch (Exception e) {
            LOG.errorf(e, "Error al actualizar el canal con ID %s", canal.getId());
            throw new RuntimeException("Error al actualizar el canal", e);
        }
    }

    @Override
    @SuppressWarnings("null")
    public void eliminarPorId(Integer id) {
        // Validar el ID para evitar llamadas innecesarias a la base de datos.
        if (id == null) {
            throw new IllegalArgumentException("El ID del canal no puede ser null");
        }

        try {
            // Se busca la entidad antes de eliminarla para evitar excepciones.
            Canal canalAEliminar = entityManager.find(Canal.class, id);
            entityManager.remove(canalAEliminar);
        } catch (Exception e) {
            LOG.errorf(e, "Error al eliminar el canal con ID %s", id);
            throw new RuntimeException("Error al eliminar el canal", e);
        }
    }

}
