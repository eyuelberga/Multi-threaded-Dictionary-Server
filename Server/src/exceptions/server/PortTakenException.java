package exceptions.server;

public class PortTakenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PortTakenException(String message) {
        super(message);
    }

    public PortTakenException(String message, Throwable cause) {
        super(message, cause);
    }

}