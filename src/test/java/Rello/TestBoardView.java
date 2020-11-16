package Rello;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import controllers.CustomBoardViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.CustomBoardViewLoader;
import utils.GuiTestHelper;
import utils.ServerHelper;


@ExtendWith(ApplicationExtension.class)
public class TestBoardView
{
	
	// Helpers
	static ServerHelper serverHelper = new ServerHelper(); 
	static GuiTestHelper testHelper = new GuiTestHelper(); 
	
	// Data 
	static Client client; 
	static BorderPane view; 
	static CustomBoardViewController cont; 
	static Board board;

	@BeforeAll
	static void setup() throws RemoteException, MalformedURLException, AlreadyBoundException {
		serverHelper.bootServer();
	}

	@Start // Before 
	private void start(Stage stage) throws IOException 
	{ 
		// Initialize 
		FXMLLoader loader = (new CustomBoardViewLoader()).load(); 
    	
    	// Create client and log a user into client
		client = testHelper.initializeTestData("jim@gmail.com", "jim123");
    	assert(client != null); 
    	
    	// Load the view
    	BorderPane view = loader.load(); 
    	cont = loader.getController(); 
    	cont.setClient(client);
    	board = client.getUser().getBoard("Team Jim");
    	cont.setModel(board);
    	cont.setStage(stage);
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
    	stage.show();
	}
	
//	@Test 
//	public void testDeepCheckLists(FxRobot robot) throws InterruptedException, IOException {
//		Thread.sleep(1000);
//		ArrayList<List> lists = board.getLists();	
//		for (int i=0; i<lists.size(); i++) {
//			List list = lists.get(i);
//			ArrayList<Card> cards = list.getCards();
//			String list_selector = "list"+Integer.toString(i);
//			Assertions.assertThat(robot.lookup(list_selector)).isNotEqualTo(null);
//			for (int j=0; j<cards.size(); j++) {
//				Card card = cards.get(j);
//				String card_selector = "#" + Integer.toString(i) + Integer.toString(j);
//				Assertions.assertThat(robot.lookup(card_selector)).isNotEqualTo(null);
//			}	
//		}
//	}
//	
//	@Test 
//	public void testAddList(FxRobot robot) throws InterruptedException, IOException {
//		// Do the adding
//		robot.clickOn("#onAddListButton");
//		Thread.sleep(1000);
//		
//		// adding lists is checked in add list test
//		
//	}
	
	@Test 
	public void testNameChange(FxRobot robot) throws InterruptedException, IOException {
		testHelper.enterTextInField(robot,"#boardTitleTextField", "NewBoardName");
		robot.clickOn("#save");
		Thread.sleep(1000);
	}
	
	// willl not be implemented
	@Test 
	public void testAddMembers() throws InterruptedException, IOException {
		
	}
	
	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}
