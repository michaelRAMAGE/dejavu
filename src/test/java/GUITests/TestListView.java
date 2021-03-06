package GUITests;


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

import Rello.Board;
import Rello.Client;
import Rello.List;

import javafx.scene.control.Button;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import template.ListView;
import utils.TestHelper;
import utils.ServerHelper;

// Note : due to the poor implementation and coupling of some generators,
// many methods below will actually go to BoardView and then modify a single
// list view as opposed to only seeing the list view. This could be fixed by
// setting the loaders via interface as a model. 

@ExtendWith(ApplicationExtension.class)
public class TestListView
{

	static ServerHelper serverHelper = new ServerHelper(); 
	static TestHelper testHelper = new TestHelper(); 
	static Client client; 
	static List list; 
	static int list_idx; 
	
	@BeforeAll
	static void setup() throws RemoteException, MalformedURLException, AlreadyBoundException {
		serverHelper.bootServer();
    	String email = "jim@gmail.com";
    	String password = "jim123";
    	client = testHelper.initializeTestData(email, password);
    	Board board = client.getUser().getBoard("Team Jim");
    	list_idx = 0; 
    	list = board.getList(list_idx);
    	System.out.println("list in test: " + list);
	}
	
	@Start 
	private void start(Stage stage) throws IOException 
	{ 	
		new ListView(client, stage, list, list_idx).load(); 
	}

	public void addCard(FxRobot robot, String card_name) {
		robot.clickOn("#addNewCardButton0");
		testHelper.enterTextInField(robot, "#nameTextField", card_name);
		robot.clickOn("#createButton");
	}
	
	@Test
	public void testChangeListName(FxRobot robot) throws InterruptedException 
	{
		// check initial list name
		String list_s = "#list0";
		String text_field_s = "#listTitleTextField0";
		Assertions.assertThat(robot.lookup(list_s)).isNotEqualTo(null);
		assert(testHelper.checkTextField(robot, text_field_s, "Week2") == false);
		assert(testHelper.checkTextField(robot, text_field_s, "Week1") == true);
		
		// change list name and check if label was changed successfully
		testHelper.enterTextInField(robot, text_field_s, "Week0");
		assert(testHelper.checkTextField(robot, text_field_s, "Week0") == true);
		Thread.sleep(2000);
	}
	

	// this is tested in testcardcreate and boardview
	@Test
	public void testAddCard(FxRobot robot) throws InterruptedException 
	{
		// This actually opens up the entire board list view
		int before_size = robot.lookup("#cardContainer0").queryAs(VBox.class).getChildren().size(); 
		addCard(robot, "NewCard1");	
		addCard(robot, "NewCard2");	
		addCard(robot, "NewCard3");	
		int expected_after = before_size+3; 
		int actual_after = robot.lookup("#cardContainer0").queryAs(VBox.class).getChildren().size(); 
		assert(expected_after == actual_after);
	}
	
	
	@Test
	public void testMoveCard(FxRobot robot) throws InterruptedException {
		addCard(robot, "NewCard1");	
		addCard(robot, "NewCard2");	
		addCard(robot, "NewCard3");	

		robot.clickOn("#editListButton0");
		robot.clickOn("#moveCardInListButton");
		
		robot.clickOn("#choiceBoxA").clickOn("1");
		robot.clickOn("#choiceBoxB").clickOn("2");
		robot.clickOn("#saveButton"); 
		
		assert(robot.lookup("#01").queryAs(Button.class).getText().equals("NewCard3") == true);
		assert(robot.lookup("#02").queryAs(Button.class).getText().equals("NewCard2") == true);
//		Thread.sleep(3500); // if not enough time given, checks will not have enough time to go through 
	}


	@Test
	public void testRemoveCard(FxRobot robot) throws InterruptedException 
	{
		
		int before_size = robot.lookup("#cardContainer0").queryAs(VBox.class).getChildren().size(); 
		robot.clickOn("#00"); // list 0 card 1
		
		
		robot.clickOn("#removeCardButton");
		
		
		int expected_after_size = --before_size;
		int after_size = robot.lookup("#cardContainer0").queryAs(VBox.class).getChildren().size(); 
		assert(expected_after_size == after_size);
		

	}
	
	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}


