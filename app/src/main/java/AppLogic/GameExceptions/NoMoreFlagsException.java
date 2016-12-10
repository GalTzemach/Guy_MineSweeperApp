package AppLogic.GameExceptions;

public class NoMoreFlagsException extends Exception{
	private static final long serialVersionUID = 1L;
	 public NoMoreFlagsException() {
		 super("No more avalible flags");
	 }
}
