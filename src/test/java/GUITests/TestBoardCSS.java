package GUITests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;



import org.testfx.assertions.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import Rello.Board;
import Rello.Client;
import controllers.CustomBoardViewController;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import template.CustomBoardView;
import theming.Theme;
import utils.TestHelper;
import utils.ServerHelper;


@ExtendWith(ApplicationExtension.class)
public class TestBoardCSS
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
    	
    	// Create client and log a user into client
		client = testHelper.initializeTestData("jim@gmail.com", "jim123");
		client.getUser().getBoard("Team Jim").setTheme(new Theme("dummy_theme"));

		(new CustomBoardView(stage, client, client.getUser().getBoard("Team Jim"))).load(); 
		
		
    	assert(client != null); 
	}
	
	
	// Test helpers 
	ListView<String> getMods(FxRobot robot) {
		return (ListView<String>) robot.lookup("#ListViewPane").queryAll().iterator().next();
	}
	
	void setCardStyle (FxRobot robot, String property, String value) {
		// Select node
		robot.clickOn("#nodeChoiceBox").clickOn("CardNode");
		
		// Select property to modify 
		robot.clickOn("#propertyChoiceBox").clickOn(property);
		
		// Enter property value for property type (later handle bad inputs)
		robot.clickOn("#propertyValue").write(value);
		
	}
	
	void setListStyle (FxRobot robot, String property, String value) {
		// Select node
		robot.clickOn("#nodeChoiceBox").clickOn("ListNode");
		
		// Select property to modify 
		robot.clickOn("#propertyChoiceBox").clickOn(property);
		
		// Enter property value for property type (later handle bad inputs)
		robot.clickOn("#propertyValue").write(value);
	}
	
	@Test 
	public void testStyleAdding(FxRobot robot) throws InterruptedException, IOException {

		robot.clickOn("#styleBoardButton");
		
		ListView<String> mods = getMods(robot); 
		Assertions.assertThat(mods).isEmpty(); 
		
		setCardStyle(robot, "Background", "green");
		robot.clickOn("#addChangeButton");

		setListStyle(robot, "Background", "orange");
		robot.clickOn("#addChangeButton");	

		mods = getMods(robot); 
		Assertions.assertThat(mods).hasExactlyNumItems(2);
		
		robot.clickOn("#onSubmitButton");	
		Thread.sleep(1000);
	}
	
	@Test 
	public void testAddModification(FxRobot robot) throws InterruptedException, IOException {
//		// add testing to test before mods
//		robot.clickOn("#styleBoardButton");
//
//		setCardStyle(robot, "Background", "yellow");
//		robot.clickOn("#addChangeButton");
//
//		setListStyle(robot, "Background", "orange");
//		robot.clickOn("#addChangeButton");	
//
//		robot.clickOn("#onSubmitButton");	
//		assert(robot.lookup("#list0").queryAs(BorderPane.class).getStyle().equals("-fx-background-color: orange;"));
//
//		// Check all cards get their style (existing before change and new after change)
//		assert(robot.lookup("#00").queryAs(Button.class).getStyle().equals("-fx-background-color: yellow;"));
//		
//		testHelper.addCard(robot, "card1");
//		assert(robot.lookup("#01").queryAs(Button.class).getStyle().equals("-fx-background-color: yellow;"));
//		
//		testHelper.addCard(robot, "card2");
//		assert(robot.lookup("#02").queryAs(Button.class).getStyle().equals("-fx-background-color: yellow;"));
		
	}


	
	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}
