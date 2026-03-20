package service.exception;

// este es para errores de la capa de datos

public class CanalDataAccessException extends RuntimeException {

    public CanalDataAccessException(String message) {
        super(message);
    }

    public CanalDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}