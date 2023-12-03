package hr.tvz.dev.exception;

public class SameItemException extends Exception {
    public SameItemException(String message) {
        super(message);
    }

    public SameItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SameItemException(Throwable cause) {
        super(cause);
    }
}
