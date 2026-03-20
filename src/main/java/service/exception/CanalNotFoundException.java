package service.exception;

// este es para cuando no se encuentra el canal
public class CanalNotFoundException extends RuntimeException {

    public CanalNotFoundException(String message) {
        super(message);
    }

    public CanalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}