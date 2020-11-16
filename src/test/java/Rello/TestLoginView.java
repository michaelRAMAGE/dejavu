package Rello;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import controllers.ServerViewController;
import controllers.LoginViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.GuiTestHelper;
import utils.ServerHelper;


@ExtendWith(ApplicationExtension.class)
public class TestLoginView
{

	static ServerHelper serverHelper = new ServerHelper(); 
	static GuiTestHelper testHelper = new GuiTestHelper(); 
	static Client client; 
	
	@BeforeAll
	static void setup() throws RemoteException, MalformedURLException, AlreadyBoundException {
		serverHelper.bootServer();
	}
	

	@Start // Before 
	private void start(Stage stage) throws IOException 
	{ 	
		// Initialize 
		FXMLLoader loader = new FXMLLoader(ServerViewController.class
				.getResource("../views/loginView.fxml"));

		// Login pane
		Parent view = loader.load(); 
		LoginViewController cont = loader.getController(); 
		
		// Construct client
		String host = "localhost:1099"; // local host with default rmi registry port
		String bind_name = "Server"; // name of reference to remote stub
    	client = new Client(host, bind_name); // construct the client
		
		cont.setClient(client);
		cont.setStage(stage); // because stage is static, we do not need to do this (make static on main)?	
		Scene new_scene = new Scene(view);
		stage.setScene(new_scene);
		stage.show();
	}
	
	@Test
	public void testLogin(FxRobot robot) throws InterruptedException 
	{
		Thread.sleep(1000);
		
		// Sign in with bad credentials
		testHelper.signIn(robot, "#usernameTextBox", "#passwordTextBox", "jim@gmail.com", "jim12");
		
		// Ensure that error label becomes visible
		Assertions.assertThat(robot.lookup("#incorrectLoginLabel").queryAs(javafx.scene.control.Label.class).isVisible()).isTrue(); 
//		assert(!robot.lookup("#incorrectLoginLabel").queryAs(javafx.scene.control.Label.class).isVisible());
		
		// Sign in with good credentials
		testHelper.signIn(robot, "#usernameTextBox", "#passwordTextBox", "jim@gmail.com","jim123");
	}
	

	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}
