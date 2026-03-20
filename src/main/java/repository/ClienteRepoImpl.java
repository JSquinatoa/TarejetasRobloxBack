package repository;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import repository.model.Cliente;

@ApplicationScoped
@Transactional
public class ClienteRepoImpl implements IClienteRepo {

    private static final Logger LOG = Logger.getLogger(ClienteRepoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertarCLiente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException("El Cliente no puede ser null");
        }

        try {
            this.entityManager.persist(cliente);
        } catch (Exception e) {
            LOG.error("Error al insertar el cliente", e);
            throw new RuntimeException("Error al insertar el cliente", e);
        }

    }

    @Override
    @SuppressWarnings("null")
    public List<Cliente> seleccionarTodos() {
        try {
            TypedQuery<Cliente> myQuery = this.entityManager.createQuery("SELECT cl FROM Cliente cl", Cliente.class);
            return myQuery.getResultList();
        } catch (Exception e) {
            LOG.error("Error al seleccionar todos los clientes", e);
            throw new RuntimeException("Error al seleccionar todos los clientes", e);
        }
    }

    @Override
    public Cliente seleccionarPorId(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser null");
        }

        try {
            return this.entityManager.find(Cliente.class, id);
        } catch (Exception e) {
            LOG.errorf(e, "Error al seleccionar el cliente con ID %s", id);
            throw new RuntimeException("Error al seleccionar el cliente", e);
        }

    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        if (cliente == null || cliente.getId() == null) {
            throw new IllegalArgumentException("El cliente y su ID son requeridos para actualizar");
        }

        try {
            this.entityManager.merge(cliente);
        } catch (Exception e) {
            LOG.errorf(e, "Error al actualizar el cliente con ID %s", cliente.getId());
            throw new RuntimeException("Error al actualizar el cliente", e);
        }
    }

    @Override
    @SuppressWarnings("null")
    public void eliminarPorId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser null");
        }

        try {
            Cliente cliente = this.entityManager.find(Cliente.class, id);
            this.entityManager.remove(cliente);
        } catch (Exception e) {
            LOG.errorf(e, "Error al eliminar el cliente con ID %s", id);
            throw new RuntimeException("Error al eliminar el cliente", e);
        }
    }

}
