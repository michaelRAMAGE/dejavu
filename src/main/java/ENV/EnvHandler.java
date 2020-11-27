package ENV;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public final class EnvHandler
{
	static private String ENV_PATH = "src/main/java/ENV/ENV_VARS.txt";
	static private HashMap<String, String> env_vars = readInVars(); 


	public static void main(String[] args) {
//		 File f=new File("icecreamTopping.txt");
//		 System.out.println(f.getAbsolutePath());
//		 readInVars(); 	
	}
	
	private static HashMap<String, String> readInVars()
	{
		HashMap<String, String> in_env_vars = new HashMap<String, String>(); 
		BufferedReader reader; 
		try
		{
			reader = new BufferedReader(new FileReader(
					ENV_PATH
			));
			String line = reader.readLine();
			while (line != null) {
				String[] key_value = line.split("=");
				in_env_vars.put(key_value[0], key_value[1]);
				line = reader.readLine();
			}
			reader.close(); 
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return in_env_vars;
	}

	public static HashMap<String, String> getEnvVars()
	{
		return env_vars; 
	}
	
	public static String getEnvPath()
	{
		return ENV_PATH; 
	}
	
	public static void setEnvPath(String NEW_ENV_PATH)
	{
		ENV_PATH = NEW_ENV_PATH; 
	}
}
