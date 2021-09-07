package exceptions.server;

public class AcceptConnectionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AcceptConnectionException(String message) {
        super(message);
    }

    public AcceptConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}