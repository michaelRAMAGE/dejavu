package Rello;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import controllers.BoardListViewController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import loaders.BoardListViewLoader;
import loaders.SaveChangesViewLoader;
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
		String host = "localhost:1099"; // local host with default rmi registry port
		String bind_name = "Server"; // name of reference to remote stub
    	client = new Client(host, bind_name); // construct the client
	}
	

	@Start // Before 
	private void start(Stage stage) throws IOException 
	{ 
		// Initialize 
		FXMLLoader loader = (new BoardListViewLoader()).load(); 
    	
    	// Log user in on Client object and make sure login is successful
    	String email = "jim@gmail.com";
    	String password = "jim123";
    	boolean login_success = client.loginUser(email, password);
    	assert(login_success == true); 
    	
    	// Load the view
    	BorderPane view = loader.load(); 
    	cont = loader.getController(); 
    	cont.setClient(client);
    	
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
    	stage.show();
	}
	
	@Test
	public void testLoadUserBoards(FxRobot robot) throws InterruptedException {
		Thread.sleep(1000);
		user_test_boards = client.user.getBoards(); // gte user boards 
		
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
		Thread.sleep(1000);
		user_test_boards = client.user.getBoards(); // gte user boards 
		Board test_board = user_test_boards.get("Team Jim"); 
		robot.clickOn("#" + test_board.boardID); 
	}
	
	@Test
	public void testAddBoard(FxRobot robot) throws InterruptedException 
	{
		Thread.sleep(1000);
	}
	
	@Test
	public void logOut(FxRobot robot) throws InterruptedException 
	{
		Thread.sleep(1000);
	}
	
	
	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}
