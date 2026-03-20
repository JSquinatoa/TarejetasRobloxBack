package repository;

import java.util.List;

import repository.model.Administrador;

public interface IAdministradorRepo {

    // CRUD

    void insertarAdministrador(Administrador administrador);

    Administrador seleccionarPorId(Integer id);

    List<Administrador> seleccionarTodos();

    void actualizarAdministrador(Administrador administrador);

    void eliminarPorId(Integer id);

    // Autenticación

    Administrador seleccionarPorUsuario(String usuario);

}
