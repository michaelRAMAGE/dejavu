package GUITests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
import controllers.BoardListViewController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import loaders.BoardListViewLoader;
import loaders.SaveChangesViewLoader;
import template.BoardListView;
import utils.GuiTestHelper;
import utils.ServerHelper;


@ExtendWith(ApplicationExtension.class)
public class TestBoardListView
{
	// Helpers
	static ServerHelper serverHelper = new ServerHelper(); 
	static GuiTestHelper testHelper = new GuiTestHelper(); 
	
	// Data 
	static Client client; 
	HashMap<String, Board> user_test_boards;
	
	// Controller
	static BoardListViewController cont; 
	
	@BeforeAll
	static void setup() throws RemoteException, MalformedURLException, AlreadyBoundException {
		serverHelper.bootServer();
		
		// Construct Client object
		String host = "localhost:2099"; // local host with default rmi registry port
    	String email = "jim@gmail.com";
    	String password = "jim123";
		client = testHelper.initializeTestData(email, password);
	}
	

	@Start // Before 
	private void start(Stage stage) throws IOException 
	{ 
		user_test_boards = client.user.getBoards(); // gte user boards 
		(new BoardListView(stage, client)).load(); 
		
	}
	
	@Test
	public void testLoadUserBoards(FxRobot robot) throws InterruptedException {
		Thread.sleep(1000);
		
    	// Get the desired flow pane for BoardListView
		FlowPane board_list_flow_pane = robot.lookup("#boardListFlowPane").queryAs(javafx.scene.layout.FlowPane.class);
		System.out.println(board_list_flow_pane.getChildren().size());
		
		// Compare user boards via getBoards to rendered board buttons		ddd
		for (Board curr_board : user_test_boards.values()) {
			String board_id = curr_board.boardID;
			String child_selector = "#"+board_id; 
			Assertions.assertThat(board_list_flow_pane.lookup(child_selector)).isNotEqualTo(null);
		}
	}
	
	@Test
	public void testClickAUserBoard(FxRobot robot) throws InterruptedException 
	{
		user_test_boards = client.user.getBoards(); // gte user boards 
		Board test_board = user_test_boards.get("Team Jim"); 
		robot.clickOn("#" + test_board.boardID); 
	}
	
	// this is tested in testboardcreateview too
	@Test
	public void testAddBoard(FxRobot robot) throws InterruptedException 
	{		
    	int boards_size = user_test_boards.size(); // this will not update but use as ref

		robot.clickOn("#addBoardButton"); 
		testHelper.enterTextInField(robot, "#nameTextField", "NewBoard");
		robot.clickOn("#createButton");
		
		Thread.sleep(1000);
		
		int should_be_size = ++boards_size; 
		System.out.println(boards_size);
		assert(robot.lookup("#boardListFlowPane")
    			.queryAs(javafx.scene.layout.FlowPane.class)
    			.getChildrenUnmodifiable().size() == should_be_size);
		Thread.sleep(2000);
	}
	
	// this will fail sometimes because the robot sees team jim on two different stages and chooses
	// the wrong one sometimes (i thought targetwindow worked but not always)
	@Test
	public void testRemoveBoard(FxRobot robot) throws InterruptedException 
	{		
		int prev_children = robot.lookup("#boardListFlowPane").queryAs(FlowPane.class).getChildren().size(); 
		robot.clickOn("#removeBoardButton");
		List<Window> stages = robot.listWindows();
		robot.targetWindow(0).clickOn("#choiceBox").clickOn("Team Jim");
//		robot.clickOn(stages.get(1));
//		robot.clickOn(stages.get(1));

		Thread.sleep(1000); 
		// thread issues when set directly
		//		robot.lookup("#choiceBox").queryAs(ChoiceBox.class).setValue("Team Jim");
		Thread.sleep(1000); 
		robot.clickOn("#saveButton"); 

		
		int expected_after_children = --prev_children;
		int actual_after_children = robot.lookup("#boardListFlowPane").queryAs(FlowPane.class).getChildren().size();
		System.out.println("exp " + Integer.toString(expected_after_children));
		System.out.println("act " + Integer.toString(actual_after_children));
		assert(expected_after_children == actual_after_children);

		Thread.sleep(2000);
	}
	
	@Test
	public void logOut(FxRobot robot) throws InterruptedException 
	{
		robot.clickOn("#logoutUserButton"); 
		Assertions.assertThat((robot.lookup("#signInButton").queryAs(javafx.scene.control.Button.class)))
		.isNotEqualTo(null);
		
		Thread.sleep(1000);
	}
	
	
	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}
