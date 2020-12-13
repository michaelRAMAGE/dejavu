package GUITests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import Rello.Client;
import javafx.stage.Stage;

import template.BoardListView;

import utils.TestHelper;
import utils.ServerHelper;

// We start from board view to demonstrate changes are kept
@ExtendWith(ApplicationExtension.class)
public class TestSaveBoardChanges
{

	static ServerHelper serverHelper = new ServerHelper(); 
	static TestHelper testHelper = new TestHelper(); 
	static Client client; 
	static String test_user_email1 = "jim@gmail.com";
	static String test_user_pass1 = "jim123";
	BoardListView boardListViewLoader; 
	Stage stage; 
	
	@BeforeAll
	static void setup() throws RemoteException, MalformedURLException, AlreadyBoundException {
		serverHelper.bootServer();
    	client = testHelper.initializeTestData(test_user_email1, test_user_pass1);
	}
	
	@Start // Before 
	private void start(Stage stage) throws IOException 
	{ 	
		System.out.println("Setting up..."); 
		// Initialize 
		this.stage = stage; 
		boardListViewLoader = (new BoardListView(stage, client)); 
		boardListViewLoader.load(); 
	}
	
//	@Test
//	public void testSaveNewBoard(FxRobot robot) throws InterruptedException 
//	{
//		System.out.println("Running SaveNewBoard test");
//		// Click on create board
//		robot.clickOn("#addBoardButton"); 
//				
//		// Create board
//		testHelper.boardCreation(robot, "Board1");  
//		robot.clickOn("Board1"); 
//				
//		// Go back to board
//		robot.clickOn("#goBackToBoardListButton"); 
//		
//		// Change the location of file to write to on out
//		// We do this so we do not read in test data and then write back to it,
//		// which affects the next test run. 
//		serverHelper.getServer().setXMLFileName("tempout.xml");
//		
//		// Log out
//		robot.clickOn("#logoutUserButton"); 
//		
//		// Log back in 
//		testHelper.signIn(robot, "#usernameTextBox", "#passwordTextBox", test_user_email1, test_user_pass1);
//		
//		// Check to see changes are retained
//		
//		System.out.println("\n\n");
//	}
//	
//	
//	@Test
//	public void testSaveRemovedBoard(FxRobot robot) throws InterruptedException 
//	{
//		System.out.println("Running SaveRemoveBoard test");
//
//		// Click on remove board --> go to removeboardview
//		robot.clickOn("#removeBoardButton"); 
//
//		// Select the board to remove 
//    	String choiceBoxSelector = "#choiceBox"; 
//    	testHelper.selectChoiceBoxOption(robot, choiceBoxSelector, "Team Jim");
//     
//		robot.clickOn("#saveButton"); 
//
//		// Change the location of file to write to on out
//		// We do this so we do not read in test data and then write back to it,
//		// which affects the next test run. 
//		serverHelper.getServer().setXMLFileName("tempout.xml");
//		
//		// Log out
//		robot.clickOn("#logoutUserButton");
//		
//		// Log back in 
//		testHelper.signIn(robot, "#usernameTextBox", "#passwordTextBox", test_user_email1, test_user_pass1);
//		
//		// Check to see changes are retained
//		
//		System.out.println("\n\n");
//
//	}
	
//	@Test
//	public void testSaveModifiedBoard(FxRobot robot) throws InterruptedException 
//	{
//		System.out.println("Running SaveRModifiedBoard test");
//
//		// Click on board
//		robot.clickOn("Team Jim");
//		
//		// Add a list to board
//		createListAndCheck(robot, "DummyList");
//		
//		// Delete some lists
//		testHelper.deleteList(robot, "0"); 
//		testHelper.deleteList(robot, "0"); // this is the next board in line (rerender and ids change)
//		
//		// Move list to front
//		testHelper.moveList(robot, "1");
//				
//		// Select list to move and move
//		testHelper.selectChoiceBoxOption(robot, "#choiceBoxB", "0, Week1", "#saveButton");
//		
//		// Go back to board
//		robot.clickOn("#goBackToBoardListButton"); 
//		
//		// Log out
//		robot.clickOn("#logoutUserButton"); 
//		
//		// Log back in 
//		testHelper.signIn(robot, "#usernameTextBox", "#passwordTextBox", test_user_email1, test_user_pass1);
//		
//		// Check to see changes are retained
//		robot.clickOn("Team Jim");
//		Thread.sleep(5000);
//		System.out.println("\n\n");
//	}
	
//	@Test
//	public void testdeleteListBoard(FxRobot robot) throws InterruptedException 
//	{
//
//		// Click on board
//		robot.clickOn("Team Jim");
//		
//		// Delete some lists
//		testHelper.deleteList(robot, "0"); 
//		testHelper.deleteList(robot, "0"); // this is the next board in line (rerender and ids change)
//
//		// Go back to board
//		robot.clickOn("#goBackToBoardListButton"); 
//		
//		// Log out
//		robot.clickOn("#logoutUserButton"); 
//		
//		// Log back in 
//		testHelper.signIn(robot, "#usernameTextBox", "#passwordTextBox", test_user_email1, test_user_pass1);
//		
//		// Check to see changes are retained
//		robot.clickOn("Team Jim");
//	}
	
//	@Test
//	public void testMoveListBoard(FxRobot robot) throws InterruptedException 
//	{
//
//		// Click on board
//		robot.clickOn("Team Jim");
//		
//		// Move list to front
//		testHelper.moveList(robot, "0");
//				
//		// Select list to move and move
//		testHelper.selectChoiceBoxOption(robot, "#choiceBoxB", "0, Week1", "#saveButton");
//		
//		// Go back to board
//		robot.clickOn("#goBackToBoardListButton"); 
//		
//		// Log out
//		robot.clickOn("#logoutUserButton"); 
//		
//		// Log back in 
//		testHelper.signIn(robot, "#usernameTextBox", "#passwordTextBox", test_user_email1, test_user_pass1);
//		
//		// Check to see changes are retained
//		robot.clickOn("Team Jim");
//	}
	
	
	void createListAndCheck(FxRobot robot, String list_name1) throws InterruptedException 
	{				

		// Add a list to the board
		robot.clickOn("#onAddListButton");
		testHelper.createList(robot, list_name1);
		
		// Test that the list was created
		testHelper.checkListCreation(robot, list_name1, list_name1);
	}
	
//	@Test
//	public void testSaveCardCreate(FxRobot robot) throws InterruptedException 
//	{		
//		// Add a card to first list
//		String card_button_selector = "#addNewCardButton0";
//		String card_name1 = "card1";
//		robot.clickOn(card_button_selector);		
//		testHelper.createCard(robot, card_name1);
//		testHelper.checkCardCreation(robot,"#01", card_name1);
//		
//		// Back out and save changes 
//		robot.clickOn("#goBackToBoardListButton"); 
//		
//		// Return to board to see if changes retained 
//		String boardID = client.user.getBoards().get("Team Jim").boardID; // gte user boards 
//		robot.targetWindow(0).clickOn("#" + boardID); 
//		
//		testHelper.checkCardCreation(robot, "#01", card_name1);
//	}
	

	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.cleanup();
	}
		
}
