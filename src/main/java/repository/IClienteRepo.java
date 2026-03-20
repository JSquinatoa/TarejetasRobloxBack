package repository;

import java.util.List;

import repository.model.Cliente;

public interface IClienteRepo { 
    
    public void insertarCLiente(Cliente cliente);

    public List<Cliente> seleccionarTodos();

    public Cliente seleccionarPorId(Integer id);

    public void actualizarCliente(Cliente cliente);

    public void eliminarPorId(Integer id);

}
