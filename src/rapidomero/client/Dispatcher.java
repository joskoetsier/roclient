package rapidomero.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.yaml.snakeyaml.Yaml;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * Dispatches jobs to a queue
 * @author jos
 *
 */
public class Dispatcher
{
	private ConnectionFactory factory = new ConnectionFactory();
	private Connection connection;
	private Configuration configuration = new Configuration();
	private String jobDescription = null;

	/**
	 * Constructor. 
	 * @param filename filename of the YAML configuration file
 	 * @param handler callback object to call if the job status changes
	 * @throws IOException
	 */
	public Dispatcher(String filename) throws IOException
	{
		this.configuration.readFile(filename);
	}

	/**
	 * send a job to a queue
	 * @param queue name of the queue to send the job to
	 * @param job Job to send
	 * @param handler callback object
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws JobException
	 */
	public void sendJob(String queue, Job job, final JobStatusHandler handler) throws FileNotFoundException, IOException, JobException
	{
		factory.setHost(this.configuration.getHost(queue));
		this.connection = factory.newConnection();
		
		this.jobDescription = UUID.randomUUID().toString();
		final Channel channel = this.connection.createChannel();
				
		channel.basicPublish("", queue, new AMQP.BasicProperties.Builder().replyTo(this.jobDescription).build(), job.toString()
				.getBytes());
		boolean autoAck = false;

		//durable, exclusive, autodelete
		channel.queueDeclare(this.jobDescription, false, false, true, null);
		channel.basicConsume(this.jobDescription, autoAck, "myconsumer", new DefaultConsumer(channel)
		{
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
			{
				long deliveryTag = envelope.getDeliveryTag();
				JobStatus jobStatus = new JobStatus();
				String message = new String(body);
				Yaml yaml = new Yaml();
				System.out.println(message);
				Map<String, Object> status = (Map<String, Object>) yaml.load(message);
				jobStatus.setJobid((String) status.get("jobid"));
				jobStatus.setMessage((String) status.get("message"));
				jobStatus.setStatus((String) status.get("status"));
				handler.processStatus(jobStatus);
				channel.basicAck(deliveryTag, false);
				if (jobStatus.getStatus().contains("Done") || jobStatus.getStatus().contains("Failed"))
				{
					channel.basicCancel("myconsumer");
					channel.close();
					connection.close();
				}
			}
		});

	}

	/**
	 * the job handler - or queue to listen to replies
	 * @return
	 */
	public String getJobDescription()
	{
		return jobDescription;
	}
}
