package service;

import java.util.List;

import repository.model.Administrador;
import service.to.LoginRequestTo;
import service.to.LoginResponseTo;

public interface IAdministradorService {

    // CRUD

    void guardarAdministrador(Administrador administrador);

    Administrador buscarPorId(Integer id);

    List<Administrador> buscarTodos();

    void actualizar(Administrador administrador);

    void borrarPorId(Integer id);

    // Autenticación

    /**
     * Verifica las credenciales del administrador.
     * Si son correctas, genera y devuelve un JWT firmado.
     * Si son incorrectas, lanza AdministradorAuthException.
     */
    LoginResponseTo autenticar(LoginRequestTo loginRequest);

}
