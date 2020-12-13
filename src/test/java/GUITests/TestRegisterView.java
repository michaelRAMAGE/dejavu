package GUITests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import Rello.Client;

import controllers.RegisterViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.RegisterViewLoader;
import utils.TestHelper;
import utils.ServerHelper;


@ExtendWith(ApplicationExtension.class)
public class TestRegisterView
{

	static ServerHelper serverHelper = new ServerHelper(); 
	static TestHelper testHelper = new TestHelper(); 
	static Client client; 
	
	@BeforeAll
	static void setup() throws RemoteException, MalformedURLException, AlreadyBoundException {
		serverHelper.bootServer();
		String host = "localhost:2099"; // local host with default rmi registry port
		String bind_name = "Server"; // name of reference to remote stub
    	client = new Client(host, bind_name); // construct the client
	}
	

	@Start // Before 
	private void start(Stage stage) throws IOException 
	{ 	
		// Initialize 
		FXMLLoader loader = (new RegisterViewLoader()).load(); 

		// Login pane
		BorderPane view = loader.load(); 
		RegisterViewController cont = loader.getController(); 
		
		cont.setStage(stage);
		cont.setClient(client);
		
		Scene new_scene = new Scene(view);
		stage.setScene(new_scene);
		stage.show();
	}
	
	@Test
	public void testRegister(FxRobot robot) throws InterruptedException 
	{
		// Sign in with bad credentials
		testHelper.enterTextInField(robot, "#userNameTextField", "jim@gmail.com"); 
		testHelper.enterTextInField(robot, "#passwordTextField", "bubbles123"); 
		robot.clickOn("#registerButton");
		assert(robot.lookup("#badCredentialsLabel").queryAs(javafx.scene.control.Label.class).isVisible() == true);
		
		testHelper.enterTextInField(robot, "#userNameTextField", ""); 
		testHelper.enterTextInField(robot, "#passwordTextField", "jeff123"); 
		robot.clickOn("#registerButton");
		assert(robot.lookup("#emptyFieldErrorLabel").queryAs(javafx.scene.control.Label.class).isVisible() == true);

		
		testHelper.enterTextInField(robot, "#userNameTextField", "jeff@gmail.com"); 
		testHelper.enterTextInField(robot, "#passwordTextField", "jeff123"); 
		robot.clickOn("#registerButton");
	}
	
	@Test
	public void testGoToLogin(FxRobot robot) throws InterruptedException {
		robot.clickOn("#backToLoginButton");
		Thread.sleep(2000);
	}
	

	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}
