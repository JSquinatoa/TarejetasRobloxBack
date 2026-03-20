package service;

import java.util.List;

import repository.model.Canal;

public interface ICanalService {

    // CRUD

    public void guardarCanal(Canal canal);

    public Canal buscarPorId(Integer id);

    public List<Canal> buscarTodos();

    public void actualizar(Canal canal);

    public void borrarPorId(Integer id);
}
