package exceptions.parse;

public class ParsingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

}