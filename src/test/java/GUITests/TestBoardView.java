package GUITests;

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

import Rello.Board;
import Rello.Card;
import Rello.Client;
import Rello.List;
import controllers.CustomBoardViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import loaders.CustomBoardViewLoader;
import utils.TestHelper;
import utils.ServerHelper;


@ExtendWith(ApplicationExtension.class)
public class TestBoardView
{
	
	// Helpers
	static ServerHelper serverHelper = new ServerHelper(); 
	static TestHelper testHelper = new TestHelper(); 
	
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
    	System.out.println("Setting main stage: " + stage);
    	cont.setStage(stage);
    	cont.setClient(client);
    	board = client.getUser().getBoard("Team Jim");
    	cont.setModel(board);
    	
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
    	stage.show();
	}
	
	@Test 
	public void testDeepCheckLists(FxRobot robot) throws InterruptedException, IOException {
		Thread.sleep(1000);
		ArrayList<List> lists = board.getLists();	
		for (int i=0; i<lists.size(); i++) {
			List list = lists.get(i);
			ArrayList<Card> cards = list.getCards();
			String list_selector = "list"+Integer.toString(i);
			Assertions.assertThat(robot.lookup(list_selector)).isNotEqualTo(null);
			for (int j=0; j<cards.size(); j++) {
				Card card = cards.get(j);
				String card_selector = "#" + Integer.toString(i) + Integer.toString(j);
				Assertions.assertThat(robot.lookup(card_selector)).isNotEqualTo(null);
			}	
		}
	}
	
	@Test 
	public void testAddList(FxRobot robot) throws InterruptedException, IOException {
		// Do the adding
		robot.clickOn("#onAddListButton");
		Thread.sleep(1000);
		testHelper.enterTextInField(robot, "#newListNameTextField", "DummyList");
		robot.clickOn("#createListButton");
		
		// Do the checking
		Assertions.assertThat(robot.lookup("#listViewStorageContainer").queryAs(HBox.class)
				.getChildren().size()).isEqualTo(5);
	    assert(testHelper.checkTextField(robot, "#listTitleTextField3", "DummyList") == true);
		Thread.sleep(1000);
		
	}

	@Test 
	public void testNameChange(FxRobot robot) throws InterruptedException, IOException {
		testHelper.enterTextInField(robot,"#boardTitleTextField", "NewBoardName");
		robot.clickOn("#save");
		
		// check name after the change 
		Thread.sleep(1000);
	}
	
	@Test
	public void testMoveList(FxRobot robot) throws InterruptedException {
		Thread.sleep(3000);
		int before_size = robot.lookup("#listViewStorageContainer").queryAs(HBox.class).getChildren().size();
		robot.clickOn("#editListButton0");
		robot.clickOn("#moveListButton");
		assert(robot.lookup("#currentListLabel").queryAs(javafx.scene.control.Label.class).getText().equals("0, Week1") == true);
		robot.clickOn("#choiceBoxB").clickOn("1, Week2");
		robot.clickOn("#saveButton");
		
		// make sure everything still here
		Assertions.assertThat(robot.lookup("#listViewStorageContainer").queryAs(HBox.class)
				.getChildren().size()).isEqualTo(before_size);
		
		// Week 1 should be after week 2 now
	    assert(testHelper.checkTextField(robot, "#listTitleTextField0", "Week2") == true);
	    assert(testHelper.checkTextField(robot, "#listTitleTextField1", "Week1") == true);
	    
		Thread.sleep(3000);
	}
	
	@Test
	public void testRemoveList(FxRobot robot) throws InterruptedException {
		int prev_lists = robot.lookup("#listViewStorageContainer").queryAs(HBox.class).getChildren().size();
		
		Assertions.assertThat(robot.lookup("#listTitleTextField2").queryAs(TextField.class)
				.getText()).isEqualTo("Week3");
		robot.clickOn("#editListButton2");
		robot.clickOn("#removeListButton");

		Thread.sleep(1000);

		int after_expected = --prev_lists;
		int after_actual = robot.lookup("#listViewStorageContainer").queryAs(HBox.class).getChildren().size();
		assert(after_expected == after_actual); 
		
		// We removed week 3, ensure it is no longer in the list view storage container
		for (int i=0; i<after_actual-1; i++) {
			Assertions.assertThat(robot.lookup("#listTitleTextField"+Integer.toString(i))
					.queryAs(TextField.class)).isNotEqualTo("Week3");
		}
	}
	
// will not be implemented
//	@Test 
//	public void testAddMembers() throws InterruptedException, IOException {
//		
//	}
	
	// TestSaveBoardChanges does more extensive testing on this. 
	// This just demonstrates the button has a function. 
	@Test
	public void testBackToBoardList(FxRobot robot) throws InterruptedException {
		robot.clickOn("#goBackToBoardListButton"); 
		Thread.sleep(5000);
	}
	
	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}
