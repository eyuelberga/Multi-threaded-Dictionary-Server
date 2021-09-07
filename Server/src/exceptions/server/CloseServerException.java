package exceptions.server;

public class CloseServerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CloseServerException(String message) {
        super(message);
    }

    public CloseServerException(String message, Throwable cause) {
        super(message, cause);
    }

}