package rapidomero.client;

/**
 * Exception class
 * @author jos
 *
 */
public class JobException extends Exception
{
	private static final long serialVersionUID = 1L;
	private String ex;
	public JobException(String ex)
	{
		this.ex = ex;
	}
	public String getException()
	{
		return ex;
	}
}
