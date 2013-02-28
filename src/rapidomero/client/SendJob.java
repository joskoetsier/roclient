package rapidomero.client;


public class SendJob {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		   Dispatcher dis = new Dispatcher("conf/config.yaml", new JobStatusHandler() {public void processStatus(JobStatus status) {System.out.println(status.status);}});
		   Job job = new Job();
		   job.setVariable("ID","1");
		   job.setVariable("H","4000");
		   job.setVariable("V","4000");
		   //dis.sendJob("pyramidft", job);
		   dis.sendJob("pyramid", job);
		   
	}

}
