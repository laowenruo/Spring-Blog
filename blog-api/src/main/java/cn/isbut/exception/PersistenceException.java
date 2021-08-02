package cn.isbut.exception;


/**
 * @author Ryan
 */
public class PersistenceException extends RuntimeException{
    public PersistenceException() {
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
