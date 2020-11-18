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
import controllers.CustomBoardViewController;
import controllers.LoginViewController;
import controllers.RegisterViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import loaders.CustomBoardViewLoader;
import loaders.RegisterViewLoader;
import utils.GuiTestHelper;
import utils.ServerHelper;

// We start from board view to demonstrate changes are kept
@ExtendWith(ApplicationExtension.class)
public class TestSaveBoardChanges
{

	static ServerHelper serverHelper = new ServerHelper(); 
	static GuiTestHelper testHelper = new GuiTestHelper(); 
	static Client client; 
	
	@BeforeAll
	static void setup() throws RemoteException, MalformedURLException, AlreadyBoundException {
		serverHelper.bootServer();
    	String email = "jim@gmail.com";
    	String password = "jim123";
    	client = testHelper.initializeTestData(email, password);
	}
	
	@Start // Before 
	private void start(Stage stage) throws IOException 
	{ 	
		// Initialize 
		FXMLLoader loader = (new CustomBoardViewLoader()).load(); 

		// Login pane
		BorderPane view = loader.load(); 
		CustomBoardViewController cont = loader.getController(); 
		
		cont.setStage(stage);
		cont.setClient(client);
		cont.setModel(client.getUser().getBoard("Team Jim"));
		
		Scene new_scene = new Scene(view);
		stage.setScene(new_scene);
		stage.show();
	}
	
	@Test
	public void testModifySaveRecover(FxRobot robot) throws InterruptedException 
	{
		// Add list
		robot.clickOn("#onAddListButton");
		
		Thread.sleep(1000);
		
		testHelper.enterTextInField(robot, "#newListNameTextField", "DummyList");
		robot.clickOn("#createListButton");
		
		Thread.sleep(1000);
		
		robot.clickOn("#goBackToBoardListButton"); 
		robot.clickOn("#saveChangesButton"); 
		
		Thread.sleep(2000);
		
		String boardID = client.user.getBoards().get("Team Jim").boardID; // gte user boards 
		robot.targetWindow(0).clickOn("#" + boardID); 
		
		Thread.sleep(3000); // scroll over and dummy list is still there!
		assert(testHelper.checkTextField(robot, "#listTitleTextField3", "DummyList") == true);

		robot.clickOn("#goBackToBoardListButton"); 
		robot.clickOn("#doNotSaveChangesButton"); 
	}
	
//	@Test
//	public void testGoToLogin(FxRobot robot) throws InterruptedException {
//
//	}
	

	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}
