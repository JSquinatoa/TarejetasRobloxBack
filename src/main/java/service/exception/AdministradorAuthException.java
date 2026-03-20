package service.exception;

/**
 * Excepción lanzada cuando las credenciales de login son incorrectas.
 * Es diferente a NotFoundException porque no es que el usuario no exista,
 * sino que la combinación usuario+password no es válida.
 */
public class AdministradorAuthException extends RuntimeException {

    public AdministradorAuthException(String message) {
        super(message);
    }

}
