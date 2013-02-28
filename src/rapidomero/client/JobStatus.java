package rapidomero.client;

/**
 * Class encapsulates the status of the job
 * @author jos
 *
 */
public class JobStatus 
{
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getJobid() {
		return jobid;
	}
	public void setJobid(String jobid) {
		this.jobid = jobid;
	}
	public String status;
	public String message;
	public String jobid;
}
