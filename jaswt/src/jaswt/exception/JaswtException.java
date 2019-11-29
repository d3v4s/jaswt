package jaswt.exception;

public class JaswtException extends Exception {
	private static final long serialVersionUID = 5802652816736292027L;

	public JaswtException() {
	}

	public JaswtException(String message) {
		super(message);
	}

	public JaswtException(Throwable cause) {
		super(cause);
	}

	public JaswtException(String message, Throwable cause) {
		super(message, cause);
	}

	public JaswtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
