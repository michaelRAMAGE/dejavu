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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import loaders.CustomBoardViewLoader;
import template.CustomBoardView;
import theming.Theme;
import utils.GuiTestHelper;
import utils.ServerHelper;


@ExtendWith(ApplicationExtension.class)
public class TestBoardCSS
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
    	
    	// Create client and log a user into client
		client = testHelper.initializeTestData("jim@gmail.com", "jim123");
		client.getUser().getBoard("Team Jim").setTheme(new Theme("dummy_theme"));

		(new CustomBoardView(stage, client, client.getUser().getBoard("Team Jim"))).load(); 
		
		
    	assert(client != null); 
	}
	
//	@Test 
//	public void testStyleAdding(FxRobot robot) throws InterruptedException, IOException {
//		Thread.sleep(2000);
//
//		robot.clickOn("#styleBoardButton");
//		Thread.sleep(5000);
//				
//		setCardStyle(robot, "Background", "yellow");
//		robot.clickOn("#addChangeButton");
//		Thread.sleep(2000);
//
//		setListStyle(robot, "Background", "orange");
//		robot.clickOn("#addChangeButton");	
//		Thread.sleep(2000);
//
//		System.out.println(robot.lookup("#ListViewPane").queryAs(ListView.class).getChildrenUnmodifiable().size());
////		robot.listWindows().get(0).hide();
//
//	}
	
	@Test 
	public void testAddModification(FxRobot robot) throws InterruptedException, IOException {
		Thread.sleep(2000);

		robot.clickOn("#styleBoardButton");
		Thread.sleep(5000);
				
		setCardStyle(robot, "Background", "yellow");
		robot.clickOn("#addChangeButton");
		Thread.sleep(2000);

		setListStyle(robot, "Background", "orange");
		robot.clickOn("#addChangeButton");	
		Thread.sleep(2000);

		robot.clickOn("#onSubmitButton");	
		
		Thread.sleep(3000);
		
		// Check all cards get their style (existing before change and new after change)
		assert(robot.lookup("#list0").queryAs(BorderPane.class).getStyle().equals("-fx-background-color: orange;"));

		assert(robot.lookup("#00").queryAs(Button.class).getStyle().equals("-fx-background-color: yellow;"));
		
		testHelper.addCard(robot, "card1");
		assert(robot.lookup("#01").queryAs(Button.class).getStyle().equals("-fx-background-color: yellow;"));
		
		testHelper.addCard(robot, "card2");
		assert(robot.lookup("#02").queryAs(Button.class).getStyle().equals("-fx-background-color: yellow;"));
		
	}

	void addToOutList(FxRobot robot) {
		
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
	
	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}
