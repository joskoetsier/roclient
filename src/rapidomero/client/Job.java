package rapidomero.client;

import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * A job, encoded as a set of key-value pairs.
 * @author jos
 *
 */
public class Job {	

	private Map<String, String> variables = new HashMap<String, String>();
	
	public Job()
	{
		
	}
	
	public Job(Map<String, String> variables)
	{
		this.variables.putAll(variables);
	}
	
	public void setVariable(String name, String value)
	{
		this.variables.put(name, value);
	}
	
	public String toString()
	{
		Yaml yaml = new Yaml();
		return yaml.dump(variables);
	}


}
