package ENV;
import java.io.BufferedReader;
//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public final class EnvHandler
{
	static private String ENV_PATH = "src/main/java/ENV/ENV_VARS.txt";
	static private HashMap<String, String> env_vars = readInVars(); 
	static private String base_path = env_vars.get("BASE_PATH"); 
	static private String css_path = env_vars.get("CSS_FOLDER"); 
	static private String full_css_path = base_path + css_path; 
	static private String default_css_filename = env_vars.get("DEFAULT_CSS_FILENAME");
	static private String default_css_file = env_vars.get("DEFAULT_CSS_FILE");

	
	public static String getDefaultCssFilename()
	{
		return default_css_filename;
	}

	public static String getDefaultCssFile()
	{
		return default_css_file;
	}

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

	public static String getCssPath()
	{
		return css_path;
	}
	
	public static String getBasePath()
	{
		return base_path;
	}

	public static String getFullCssPath()
	{
		return full_css_path;
	}
	
}
