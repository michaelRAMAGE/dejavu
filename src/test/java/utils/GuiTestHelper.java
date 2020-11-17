package utils;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.assertj.core.api.Assertions;
import org.testfx.api.FxRobot;

import Rello.Client;
import javafx.scene.control.TextField;


public class GuiTestHelper
{
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
		String host = "localhost:1099"; // local host with default rmi registry port
		String bind_name = "Server"; // name of reference to remote stub
		Client client = new Client(host, bind_name);
		// Log a user in
    	boolean login_success = client.loginUser(email, password);
    	if (!login_success) {
    		return null;
    	}
    	return client; 
	}
}
