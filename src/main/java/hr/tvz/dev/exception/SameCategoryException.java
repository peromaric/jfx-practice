package hr.tvz.dev.exception;

public class SameCategoryException extends RuntimeException {
    public SameCategoryException(String message) {
        super(message);
    }

    public SameCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public SameCategoryException(Throwable cause) {
        super(cause);
    }
}
