package hu.webuni.hr.gye.exception;

public class NotAccessException  extends Exception {
	private static final long serialVersionUID = 1L;

	public NotAccessException(String errorMessage) {
	        super(errorMessage);
	    }
}
