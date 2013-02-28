package rapidomero.client;

public class JobException extends Exception
{
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
