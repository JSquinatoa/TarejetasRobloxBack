package service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import repository.IClienteRepo;
import repository.model.Cliente;
import service.exception.ClienteDataAccessException;
import service.exception.ClienteNotFoundException;

@ApplicationScoped
public class ClienteServiceImpl implements IClienteService {

    @Inject
    private IClienteRepo clienteRepo;

    @Override
    public void guardarCLiente(Cliente cliente) {
        try {
            this.clienteRepo.insertarCLiente(cliente);
        } catch (Exception e) {
            throw new ClienteDataAccessException("Error al guardar el cliente", e);
        }
    }

    @Override
    public List<Cliente> buscarTodos() {
        try {
            return this.clienteRepo.seleccionarTodos();
        } catch (Exception e) {
            throw new ClienteDataAccessException("Error al buscar todos los clientes", e);
        }
    }

    @Override
    public Cliente buscarPorId(Integer id) {
        try {
            Cliente c = this.clienteRepo.seleccionarPorId(id);
            if (c == null) {
                throw new ClienteNotFoundException("Cliente con ID " + id + " no encontrado");
            }
            return c;
        } catch (ClienteNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ClienteDataAccessException("Error al buscar el cliente con ID " + id, e);
        }
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        try {
            this.clienteRepo.actualizarCliente(cliente);
        } catch (Exception e) {
            throw new ClienteDataAccessException("Error al actualizar el cliente", e);
        }
    }

    @Override
    public void borrarPorId(Integer id) {
        try {
            this.clienteRepo.eliminarPorId(id);
        } catch (ClienteNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ClienteDataAccessException("Error al borrar el cliente con ID " + id, e);
        }
    }

}
