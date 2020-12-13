package utils;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.HashMap;

import org.testfx.api.FxRobot;

import ENV.EnvHandler;
import Rello.Client;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


public class TestHelper
{
	static private HashMap<String, String> env_vars = EnvHandler.getEnvVars(); 
	
	public TestHelper() {}
	
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

	// Sign in on signin view
	public void signIn(FxRobot robot, String selector_username, String selector_password, String username, String password) {
		// Enter credentials
		enterTextInField(robot, selector_username, username); 
		enterTextInField(robot, selector_password, password); 

		// Press submit
		robot.clickOn("#signInButton");
	}
	
	// Create Client, Login User 
	public Client initializeTestData(String email, String password) throws MalformedURLException, RemoteException {
		// Create a client
		Client client = clientCreation(); 

		// Log a user in
    	boolean login_success = client.loginUser(email, password);
    	if (!login_success) {
    		return null;
    	}
    	return client; 
	}
	
	// Create Client, Adds User (which returns an active user if exists instead of logging in)
	public Client initTestData(String email, String password) throws MalformedURLException, RemoteException {
		// Create a client
		Client client = clientCreation(); 
		// Log a user in
    	boolean login_success = client.addUser(email, password);
    	if (!login_success) {
    		return null;
    	}
    	return client; 
	}
	
	public Client clientCreation() throws MalformedURLException {
		// Construct Client object
		String host = env_vars.get("RMI_HOST") + ":" + env_vars.get("RMI_PORT"); // local host with default rmi registry port
		String bind_name = env_vars.get("RMI_BIND_NAME"); // name of reference to remote stub
		return new Client(host, bind_name);
	}
	
	public void addCard(FxRobot robot, String card_name) {
		robot.clickOn("#addNewCardButton0");
		enterTextInField(robot, "#nameTextField", card_name);
		robot.clickOn("#createButton");
	}
	
	public void createCard(FxRobot robot, String card_name) {
		enterTextInField(robot, "#nameTextField", card_name);
		robot.clickOn("#createButton");
	}
	
	public void createList(FxRobot robot, String list_name) throws InterruptedException {
		enterTextInField(robot, "#newListNameTextField", list_name);
		robot.clickOn("#createListButton");
	}
	
	public void deleteList(FxRobot robot, String list_idx) {
		robot.clickOn("#editListButton" + list_idx);
		robot.clickOn("#removeListButton");
	}
	
	
	public void moveList(FxRobot robot, String list_idx) {
		robot.clickOn("#editListButton" + list_idx);
		robot.clickOn("#moveListButton"); 
	}
	

	
	public void boardCreation(FxRobot robot, String board_name) {
		enterTextInField(robot, "#nameTextField", board_name);
		robot.clickOn("#createButton");
	}
	
	public void checkListCreation(FxRobot robot, String selector, String list_name) {
		assert(robot.lookup(selector).queryAs(TextField.class) != null);
		assert(robot.lookup(selector).queryAs(TextField.class).getText().equals(list_name));
	}
	
	public void checkCardCreation(FxRobot robot, String selector, String card_name) {
		assert(robot.lookup(selector).queryAs(Button.class) != null);
		assert(robot.lookup(selector).queryAs(Button.class).getText().equals(card_name));
	}
	
	public void selectChoiceBoxOption(FxRobot robot, String selector, String option) {
		// Tries to run in another thread that is not main so queue for run
        Platform.runLater(new Runnable() {
            @SuppressWarnings("unchecked")
			@Override public void run() {
        		robot.lookup(selector).queryAs(ChoiceBox.class).getSelectionModel().select(option);
            }
        });
     
	}
	
	public void selectChoiceBoxOption(FxRobot robot, String selector, String option, String submit_selector) {
		// Tries to run in another thread that is not main so queue for run
        Platform.runLater(new Runnable() {
            @SuppressWarnings("unchecked")
			@Override public void run() {
        		robot.lookup(selector).queryAs(ChoiceBox.class).getSelectionModel().select(option);
            }
        });
		robot.clickOn(submit_selector);
     
	}
	
	public void selectChoiceBoxOption(FxRobot robot, String selector,int option) {
		// Tries to run in another thread that is not main so queue for run
        Platform.runLater(new Runnable() {
            @SuppressWarnings("unchecked")
			@Override public void run() {
        		robot.lookup(selector).queryAs(ChoiceBox.class).getSelectionModel().select(option);
            }
        });
     
	}
}
