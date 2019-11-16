package jaswt.exception;

/**
 * Class fo parameter exception
 * 
 * @author Andrea Serra
 *
 */
public class ParameterException extends Exception {
	private static final long serialVersionUID = 5205976289904803887L;

	public ParameterException() {
	}

	public ParameterException(String message) {
		super(message);
	}

	public ParameterException(Throwable cause) {
		super(cause);
	}

	public ParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
