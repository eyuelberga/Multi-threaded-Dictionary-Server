package exceptions.file;

public class FileWriteException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileWriteException(String message) {
        super(message);
    }

    public FileWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
