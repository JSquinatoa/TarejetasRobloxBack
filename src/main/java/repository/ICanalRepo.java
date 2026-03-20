package repository;

import java.util.List;

import repository.model.Canal;

public interface ICanalRepo {
    
    // CRUD  

    public void insertarCanal(Canal canal);

    public Canal seleccionarPorId(Integer id);
    public List<Canal> seleccionarTodos();

    public void actualizar(Canal canal);

    public void eliminarPorId(Integer id);

}
