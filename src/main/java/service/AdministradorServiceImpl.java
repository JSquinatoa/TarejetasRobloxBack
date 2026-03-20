package service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.microprofile.jwt.Claims;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import repository.IAdministradorRepo;
import repository.model.Administrador;
import security.Roles;
import service.exception.AdministradorAuthException;
import service.exception.AdministradorDataAccessException;
import service.exception.AdministradorNotFoundException;
import service.to.LoginRequestTo;
import service.to.LoginResponseTo;

@ApplicationScoped
public class AdministradorServiceImpl implements IAdministradorService {

    @Inject
    private IAdministradorRepo administradorRepo;

    @Override
    public void guardarAdministrador(Administrador administrador) {
        try {
            // Encriptamos la contraseña con BCrypt antes de guardar en la BD.
            // BcryptUtil genera automáticamente un salt aleatorio por cada hash.
            administrador.setPassword(BcryptUtil.bcryptHash(administrador.getPassword()));
            this.administradorRepo.insertarAdministrador(administrador);
        } catch (Exception e) {
            throw new AdministradorDataAccessException("Error al guardar el administrador: " + e.getMessage(), e);
        }
    }

    @Override
    public Administrador buscarPorId(Integer id) {
        try {
            Administrador administrador = this.administradorRepo.seleccionarPorId(id);
            if (administrador == null) {
                throw new AdministradorNotFoundException("Administrador con ID " + id + " no encontrado");
            }
            return administrador;
        } catch (AdministradorNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AdministradorDataAccessException("Error al buscar el administrador por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Administrador> buscarTodos() {
        try {
            return this.administradorRepo.seleccionarTodos();
        } catch (Exception e) {
            throw new AdministradorDataAccessException("Error al buscar todos los administradores: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Administrador administrador) {
        try {

            if (administrador.getPassword() == null) {
                Administrador administradorExistente = this.administradorRepo.seleccionarPorId(administrador.getId());
                administrador.setPassword(administradorExistente.getPassword());                
            } else {
                administrador.setPassword(BcryptUtil.bcryptHash(administrador.getPassword()));
            }
            this.administradorRepo.actualizarAdministrador(administrador);
        } catch (Exception e) {
            throw new AdministradorDataAccessException("Error al actualizar el administrador: " + e.getMessage(), e);
        }
    }

    @Override
    public void borrarPorId(Integer id) {
        try {
            Administrador administrador = this.administradorRepo.seleccionarPorId(id);
            if (administrador == null) {
                throw new AdministradorNotFoundException("Administrador con ID " + id + " no encontrado, no se puede borrar");
            }
            this.administradorRepo.eliminarPorId(id);
        } catch (AdministradorNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AdministradorDataAccessException("Error al borrar el administrador por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public LoginResponseTo autenticar(LoginRequestTo loginRequestTo) {
        // 1. Validar que vengan los datos
        if (loginRequestTo == null || loginRequestTo.getUsuario() == null || loginRequestTo.getPassword() == null) {
            throw new AdministradorAuthException("Usuario y contraseña son requeridos");
        }

        // 2. Buscar al administrador por nombre de usuario
        Administrador administrador;
        try {
            administrador = this.administradorRepo.seleccionarPorUsuario(loginRequestTo.getUsuario());
        } catch (Exception e) {
            throw new AdministradorDataAccessException("Error al verificar credenciales: " + e.getMessage(), e);
        }

        // 3. Verificamos el password usando BCrypt (compara el texto plano con el hash guardado)
        // BcryptUtil.matches(plain, hash) → true si coinciden, false si no
        if (administrador == null || !BcryptUtil.matches(loginRequestTo.getPassword(), administrador.getPassword())) {
            throw new AdministradorAuthException("Usuario o contraseña incorrectos");
        }

        // 3.5 Agrupación Lógica de Roles
        // Enlaza el rol real del usuario con nuestro rol genérico de permisos
        Set<String> rolesDelToken = new HashSet<>();
        rolesDelToken.add(administrador.getRol()); // Ejemplo: "Admin"

        if (Roles.SUPER_ADMIN.equals(administrador.getRol()) || Roles.ADMIN.equals(administrador.getRol())) {
            rolesDelToken.add(Roles.ACCESO_ADMINISTRADORES);
        }

        // 4. Generar el JWT firmado con la clave privada (privateKey-pkcs8.pem)
        // El token incluye:
        //   - subject: el ID del administrador
        //   - upn (User Principal Name): el usuario
        //   - groups: TODOS los roles (el real + el genérico ACCESO_ADMINISTRADORES)
        //   - issuer: el que configuramos en application.properties
        //   - expiración: 8 horas
        String token = Jwt.issuer("tarjetascodigos") // IMPORTANTE DEBE COINCIDIR CON application.properties
                .subject(administrador.getId().toString())
                .claim(Claims.upn, administrador.getUsuario())
                .groups(rolesDelToken) // Pasamos el set de roles lógicos
                .expiresIn(28800) // 28800 segundos = 8 horas
                .sign();

        // 5. Construir y devolver la respuesta
        LoginResponseTo respuesta = new LoginResponseTo();
        respuesta.setToken(token);
        respuesta.setNombre(administrador.getNombre());
        respuesta.setRol(administrador.getRol());

        return respuesta;
    }

}
