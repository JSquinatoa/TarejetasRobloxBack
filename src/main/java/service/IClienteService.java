package service;

import java.util.List;

import repository.model.Cliente;

public interface IClienteService {

    public void guardarCLiente(Cliente cliente);

    public List<Cliente> buscarTodos();

    public Cliente buscarPorId(Integer id);

    public void actualizarCliente(Cliente cliente);

    public void borrarPorId(Integer id);

}
