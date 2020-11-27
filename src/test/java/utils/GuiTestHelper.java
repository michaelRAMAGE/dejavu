package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.HashMap;

import org.testfx.api.FxRobot;
import Rello.Client;
import javafx.scene.control.TextField;


public class GuiTestHelper
{
	static private String ENV_PATH = "ENV_VARS.txt";
	static private HashMap<String, String> env_vars = readInVars(); 
	
	public GuiTestHelper() {}
	
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

	public void enterTextInField(FxRobot robot, String selector, String text) {
		// User name entry
		robot.lookup(selector).queryAs(TextField.class).clear();
		robot.clickOn(selector);
		robot.write(text);
	}
	
	public boolean checkTextField(FxRobot robot, String selector, String result) {
		return (robot.lookup(selector).queryAs(TextField.class).getText().equals(result));

	}
	
	public boolean checkSelectorFound(FxRobot robot, String selector) {
		return (robot.lookup(selector) != null);
	}

	public void signIn(FxRobot robot, String selector_username, String selector_password, String username, String password) {
		// Enter credentials
		enterTextInField(robot, selector_username, username); 
		enterTextInField(robot, selector_password, password); 

		// Press submit
		robot.clickOn("#signInButton");
	}
	
	public Client initializeTestData(String email, String password) throws MalformedURLException, RemoteException {
		
		// Construct Client object
		String host = env_vars.get("RMI_HOST") + ":" + env_vars.get("RMI_PORT"); // local host with default rmi registry port
		String bind_name = env_vars.get("RMI_BIND_NAME"); // name of reference to remote stub
		Client client = new Client(host, bind_name);

		// Log a user in
    	boolean login_success = client.loginUser(email, password);
    	if (!login_success) {
    		return null;
    	}
    	System.out.println("HOST: " + host + ", " + "BIND_NAME: " + bind_name);
    	return client; 
	}
	
	public void addCard(FxRobot robot, String card_name) {
		robot.clickOn("#addNewCardButton0");
		enterTextInField(robot, "#nameTextField", card_name);
		robot.clickOn("#createButton");
	}
	
	public static String getENV_PATH()
	{
		return ENV_PATH;
	}

	public static void setENV_PATH(String eNV_PATH)
	{
		ENV_PATH = eNV_PATH;
	}

	public static HashMap<String, String> getEnv_vars()
	{
		return env_vars;
	}

	public static void setEnv_vars(HashMap<String, String> env_vars)
	{
		GuiTestHelper.env_vars = env_vars;
	}

}
