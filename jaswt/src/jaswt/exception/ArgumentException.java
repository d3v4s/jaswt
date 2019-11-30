package jaswt.exception;

/**
 * Class for argument exception
 * @author Andrea Serra
 *
 */
public class ArgumentException extends Exception {
	private static final long serialVersionUID = 5205976289904803887L;

	public ArgumentException() {
	}

	public ArgumentException(String message) {
		super(message);
	}

	public ArgumentException(Throwable cause) {
		super(cause);
	}

	public ArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
