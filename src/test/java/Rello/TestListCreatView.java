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

import controllers.ListCreateViewController;
import javafx.fxml.FXMLLoader;
	import javafx.scene.Parent;
	import javafx.scene.Scene;
	import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import loaders.ListCreateViewLoader;
import utils.GuiTestHelper;
	import utils.ServerHelper;


@ExtendWith(ApplicationExtension.class)
public class TestListCreatView
{

	static ServerHelper serverHelper = new ServerHelper(); 
	static GuiTestHelper testHelper = new GuiTestHelper(); 
	static Client client; 
	
	@BeforeAll
	static void setup() throws RemoteException, MalformedURLException, AlreadyBoundException {
		serverHelper.bootServer();
		
		// Construct Client object
		String host = "localhost:1099"; // local host with default rmi registry port
		String bind_name = "Server"; // name of reference to remote stub
    	client = new Client(host, bind_name); // construct the client
	}
	
	@Start // Before 
	private void start(Stage stage) throws IOException 
	{ 	
		// Initialize 
		FXMLLoader loader = (new ListCreateViewLoader()).load(); 
		
    	// Log user in on Client object and make sure login is successful
    	String email = "jim@gmail.com";
    	String password = "jim123";
    	boolean login_success = client.loginUser(email, password);
    	assert(login_success == true); 
    	
		// Login pane
		Parent view = loader.load(); 
		ListCreateViewController cont = loader.getController(); 
		cont.setStage(stage); // because stage is static, we do not need to do this (make static on main)?	
		cont.setClient(client);
    	Board board = client.getUser().getBoard("Team Jim");

    	cont.setModel(board);
		Scene new_scene = new Scene(view);
		stage.setScene(new_scene);
		stage.show();
	}
	
	@Test
	public void testCreateSubmitList(FxRobot robot) throws InterruptedException 
	{

		testHelper.enterTextInField(robot, "#newListNameTextField", "NewList");
		robot.clickOn("#createListButton");
		
		int prev_children = robot.lookup("#listViewStorageContainer").queryAs(HBox.class).getChildren().size(); 

		robot.clickOn("#onAddListButton"); // on board view

		testHelper.enterTextInField(robot, "#newListNameTextField", "NewList");
		robot.clickOn("#createListButton");

		int expected_after_children = ++prev_children;
		int actual_after_children = robot.lookup("#listViewStorageContainer").queryAs(HBox.class).getChildren().size();
		System.out.println("exp " + Integer.toString(expected_after_children));
		System.out.println("act " + Integer.toString(actual_after_children));
		assert(expected_after_children == actual_after_children);
		Thread.sleep(1000);
	}
	
	@Test
	public void testCreateCancelList(FxRobot robot) throws InterruptedException 
	{
		testHelper.enterTextInField(robot, "#newListNameTextField", "NewList");
		robot.clickOn("#cancelListCreateButton");
	}
	
	

	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}


