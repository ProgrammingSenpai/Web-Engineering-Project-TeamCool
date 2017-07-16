package qa.exceptions;

public class NameAlreadyInUseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameAlreadyInUseException(){
		super("Name already in use!");
	}
}
