package AppLogic.GameExceptions;

public class InvalidCellException extends Exception{
	private static final long serialVersionUID = 1L;
	 public InvalidCellException() {
		 super("Invalid Cell location");
	 }
}
