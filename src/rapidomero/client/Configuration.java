package rapidomero.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.yaml.snakeyaml.Yaml;

public class Configuration 
{
	private Map<String, Map<String, Object>> data = new HashMap<String, Map<String, Object>>();
	private Yaml yaml = new Yaml();
	
	public static Object keysToLowercase(Object in)
	{
		if (in instanceof List)
		{
			List inList = (List) in;
			List outList = new Vector();
			for (Object element:inList)
				outList.add(keysToLowercase(element));
			return outList;
		}
		if (in instanceof Map)
		{
			Map <String, Object> inMap = (Map<String, Object>) in;
			Map outMap = new HashMap();
			for (String key:inMap.keySet())
			{
				Object value = inMap.get(key);
				outMap.put(key.toLowerCase(), keysToLowercase(value));
			}	
			return outMap;
		}
		return in;
	}
	
	public void readFile(String file) throws FileNotFoundException
	{
		InputStream input = new FileInputStream(new File(file));
		Yaml yaml = new Yaml();
		List<Map<String, Object>> queueList=  (List<Map<String, Object>>) Configuration.keysToLowercase(yaml.load(input));
		for (Map<String, Object> queue:queueList)
			this.data.put((String) queue.get("queue"), queue);
		
	}
	
	public String toString()
	{
		return this.yaml.dump(this.data);
	}
	
	public String getHost(String queue) throws JobException
	{
		if (this.data==null || this.data.get(queue)==null)
			throw new JobException("Invalid data");
		return (String) this.data.get(queue).get("host");
	}

	public String getUsername(String queue) throws JobException
	{		
		if (this.data==null || this.data.get(queue)==null)
			throw new JobException("Invalid data");
		return (String) this.data.get(queue).get("username");
	}
	public String getPassword(String queue) throws JobException
	{	
		if (this.data==null || this.data.get(queue)==null)
			throw new JobException("Invalid data");
		return (String) this.data.get(queue).get("password");
	}
	public int getPort(String queue) throws JobException
	{	
		if (this.data==null || this.data.get(queue)==null)
			throw new JobException("Invalid data");
		return (Integer) this.data.get(queue).get("port");
	}
	
}
