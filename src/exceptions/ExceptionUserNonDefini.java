package exceptions;

public class ExceptionUserNonDefini extends Exception {
	private static final long serialVersionUID = 8308313378781733555L;
	
	public ExceptionUserNonDefini() {
		super();
	}
	public ExceptionUserNonDefini(String msg) {
		super(msg);
	}
}
