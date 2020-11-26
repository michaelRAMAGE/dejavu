package GUITests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import Rello.Board;
import Rello.Client;

import org.testfx.framework.junit5.ApplicationExtension;
import controllers.BoardCreateViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.BoardCreateViewLoader;
import utils.GuiTestHelper;
import utils.ServerHelper;

@ExtendWith(ApplicationExtension.class)
public class TestBoardCreateView
{
	static ServerHelper serverHelper = new ServerHelper(); 
	static GuiTestHelper testHelper = new GuiTestHelper(); 
	static Client client; 
	static HashMap<String, Board> boards; 
	
	@BeforeAll
	static void setup() throws RemoteException, MalformedURLException, AlreadyBoundException {
		System.out.println("RUNNING TestBoardCreateView VIEW");

		serverHelper.bootServer();
	}
	
	@Start // Before 
	private void start(Stage stage) throws IOException 
	{ 	
		// template pattern testing?
		
		// Initialize 
		FXMLLoader loader = (new BoardCreateViewLoader()).load(); 
		BorderPane view = loader.load();
		BoardCreateViewController cont = loader.getController(); 
		
    	// Create client and log a user into client
		client = testHelper.initializeTestData("jim@gmail.com", "jim123");
    	assert(client != null); 
    	
    	// Get data for model
    	boards = client.getUser().getBoards();
    			
		// Set on controller
		cont.setStage(stage);
		cont.setClient(client);
   
		// Stage and scene setting
		Scene new_scene = new Scene(view);
		stage.setScene(new_scene);
		stage.show();
	}
	

	
	@Test
	public void testCreateBoard(FxRobot robot) throws InterruptedException
	{
		// Do the adding
    	int boards_size = boards.size(); // this will not update but use as ref
		testHelper.enterTextInField(robot, "#nameTextField", "NewBoard");
		robot.clickOn("#createButton");
		 
		Thread.sleep(1000);
		
		// Make sure it was added to flowpane
		int should_be_size = ++boards_size; 
		System.out.println(boards_size);
		assert(robot.lookup("#boardListFlowPane")
    			.queryAs(javafx.scene.layout.FlowPane.class)
    			.getChildrenUnmodifiable().size() == should_be_size);
	}
	
	@Test
	public void testBoardCardCancel(FxRobot robot) throws InterruptedException 
	{
		robot.clickOn("#cancelCreationButton"); // just...add...nothing
	}

	@AfterAll
	static void tearDown() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
}


