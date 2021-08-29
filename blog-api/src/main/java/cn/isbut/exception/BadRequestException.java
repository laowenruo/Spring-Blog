package cn.isbut.exception;

/**
 * @author Ryan
 * @Description: 非法请求异常
 *
 */

public class BadRequestException extends RuntimeException {
	public BadRequestException() {
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
