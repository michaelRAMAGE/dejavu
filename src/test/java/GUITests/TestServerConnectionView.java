package GUITests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import Rello.Server;
import controllers.ServerViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.ServerHelper;

@ExtendWith(ApplicationExtension.class)
public class TestServerConnectionView
{
	static ServerHelper serverHelper = new ServerHelper(); 

	@BeforeAll
	static void setup() throws RemoteException, MalformedURLException, AlreadyBoundException {
		serverHelper.bootServer();
	}
	
	@Start // Before 
	private void start(Stage primaryStage) throws IOException 
	{ 
		// Initialize 
		FXMLLoader loader = new FXMLLoader(Rello.Server.class
				.getResource("../views/ServerView.fxml"));
		
		BorderPane view = loader.load(); 
		ServerViewController cont = loader.getController(); 
		
		cont.setModel(Server.getInstance()); // will need to think about how to deal with singleton
		cont.setStage(primaryStage); // will need to think about how to deal with singleton

		Scene s = new Scene(view);
		primaryStage.setScene(s);
		primaryStage.show();
	}
	
	@Test
	public void testDefaultConnection(FxRobot robot) throws InterruptedException 
	{
		Thread.sleep(1000);
		robot.clickOn("#defaultConnectButton");
		Assertions.assertThat(robot.lookup("#signInButton").queryAs(Button.class)).isNotEqualTo(null);
		Thread.sleep(1000);
	}
	
//	@Test
//	public void testCustomConnection(FxRobot robot) throws InterruptedException 
//	{
//		Thread.sleep(1000);
//		robot.clickOn("#customConnectButton");
//		robot.lookup("#errorMessageIPTextField");
//		
//		robot.clickOn("#customConnectButton");
//		
//		robot.write("127.0.0.1");
//		robot.clickOn("#customConnectButton");
//	}
//	
	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}
