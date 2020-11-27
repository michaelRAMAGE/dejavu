package utils;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.HashMap;

import org.testfx.api.FxRobot;

import ENV.EnvHandler;
import Rello.Client;
import javafx.scene.control.TextField;


public class GuiTestHelper
{
	static private HashMap<String, String> env_vars = EnvHandler.getEnvVars(); 
	
	public GuiTestHelper() {}
	
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


}
