package service.exception;

// este es para errores de la capa de datos

public class ClienteDataAccessException extends RuntimeException {

    public ClienteDataAccessException(String message) {
        super(message);
    }

    public ClienteDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
