package GUITests;



import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;


import org.testfx.assertions.api.Assertions;
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
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loaders.CustomBoardViewLoader;
import template.CustomBoardView;
import theming.Theme;
import utils.GuiTestHelper;
import utils.ServerHelper;


@ExtendWith(ApplicationExtension.class)
public class TestBoardCSSFileLoad
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
    	// Create client and log a user into client
		client = testHelper.initializeTestData("jim@gmail.com", "jim123");
		client.getUser().getBoard("Team Jim").setTheme(new Theme("dummy_theme"));
		(new CustomBoardView(stage, client, client.getUser().getBoard("Team Jim"))).load(); 
    	assert(client != null); 
	}
	
	void setCardStyle (FxRobot robot, String property, String value) {
		// Select node
		robot.clickOn("#nodeChoiceBox").clickOn("CardNode");
		
		// Select property to modify 
		robot.clickOn("#propertyChoiceBox").clickOn(property);
		
		// Enter property value for property type (later handle bad inputs)
		robot.clickOn("#propertyValue").write(value);
		robot.clickOn("#addChangeButton");	

		
	}
	
	void setListStyle (FxRobot robot, String property, String value) {
		// Select node
		robot.clickOn("#nodeChoiceBox").clickOn("ListNode");
		
		// Select property to modify 
		robot.clickOn("#propertyChoiceBox").clickOn(property);
		
		// Enter property value for property type (later handle bad inputs)
		robot.clickOn("#propertyValue").write(value);
		robot.clickOn("#addChangeButton");	

	}
	
	@Test 
	public void testStyleClass(FxRobot robot) throws InterruptedException, IOException {

		// Initial style should be loaded in 
		// check that our initial file is in the path 
		
		String default_css_file = "views/basetheme.css";
		assert(robot.lookup("#boardViewRoot").queryAs(BorderPane.class).getStylesheets().contains(default_css_file));	
		
		System.out.println(robot.lookup("#boardViewRoot").queryAs(BorderPane.class).getStyleClass());
//		
		// ROY in ROYGBIV (card and list each)
		// R -- red 
//		robot.clickOn("#styleBoardButton");
//		setCardStyle(robot, "Background", "red");
//		setListStyle(robot, "Background", "black");
//		robot.clickOn("#onSubmitButton");
//		
//		@SuppressWarnings("unchecked")
//		FilteredList<Node> listChildren = 
//				robot.lookup("#listViewStorageContainer").queryAs(javafx.scene.Parent.class)
//				.getChildrenUnmodifiable().filtered(t -> t.getId().contains("list"));
//		
//		// All lists should have ListNode class
//		ArrayList<String> list_ids = new ArrayList<String>();
//		for (Node list : listChildren) {
//			System.out.println(list);
//			
//			String selector = list.getId(); 
//			assertTrue(list.getStyleClass().contains("ListNode"));
//			list_ids.add("#"+selector);
//		}
//		System.out.println("list ids collected: " + list_ids);
//	
//		// All cards should have CardNode class
//		for (int c=0; c<list_ids.size(); c++) {			
//			ObservableList<Node> cards_with_style_class = robot.lookup("#cardContainer"+Integer.toString(c)).queryAs(VBox.class).getChildrenUnmodifiable();
//			for (Node card : cards_with_style_class) {
//				System.out.println(card);
//				
//				assertTrue(card.getStyleClass().contains("CardNode"));
//			}
//		}
//		
		// Demonstrate we can make change after changes have already been made 
//		// O -- orange 
//		robot.clickOn("#styleBoardButton");
//		setCardStyle(robot, "Background", "orange");
//		setListStyle(robot, "Background", "orange");
//		robot.clickOn("#onSubmitButton");	
//		
//		// Y -- yellow
//		robot.clickOn("#styleBoardButton");
//		setCardStyle(robot, "Background", "yellow");
//		setListStyle(robot, "Background", "yellow");
//		robot.clickOn("#onSubmitButton");	

		// File should be created 
		// -----
	}
	
	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}
