package hu.webuni.hr.gye.exception;

public class TooManyRequestParamsException extends Exception {

	private static final long serialVersionUID = 1L;

	public TooManyRequestParamsException(String errorMessage) {
	        super(errorMessage);
	    }
}
