package rapidomero.client;


public class SendJob {

	/**
	 * Example code. Submits a simple job
	 * @param args
	 */
	
	
	public static void main(String[] args) throws Exception
	{
		//new job status handler. the method 'processStatus' is called every time status changes.
		JobStatusHandler handler = new JobStatusHandler() 
			{
			    public void processStatus(JobStatus status) 
				{
			    	System.out.println(status.status);
			    }
			};
		
		//Instantiates a new Dispatcher Object
	   Dispatcher dis = new Dispatcher("conf/config.yaml");
	   //Create a new job with a few key-value pairs
	   Job job = new Job();
	   job.setVariable("ID","1");
	   job.setVariable("H","4000");
	   job.setVariable("V","4000");
	   //send the job to a queue, using a callback handler. 
	   dis.sendJob("pyramid", job, handler);
	}

}
