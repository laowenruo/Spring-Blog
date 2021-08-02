package cn.isbut.exception;


/**
 * @author Ryan
 */
public class ErrorRequestException extends RuntimeException{
    public ErrorRequestException() {
    }

    public ErrorRequestException(String message) {
        super(message);
    }

    public ErrorRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
