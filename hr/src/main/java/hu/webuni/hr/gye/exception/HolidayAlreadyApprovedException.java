package hu.webuni.hr.gye.exception;

public class HolidayAlreadyApprovedException extends Exception {
	private static final long serialVersionUID = 1L;

	public HolidayAlreadyApprovedException(String errorMessage) {
	        super(errorMessage);
	    }
}
