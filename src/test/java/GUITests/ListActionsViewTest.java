package GUITests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import Rello.Client;
import Rello.List;
import org.testfx.framework.junit5.ApplicationExtension;
import controllers.ListActionsViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import loaders.ListActionsViewLoader;
import utils.TestHelper;
import utils.ServerHelper;

@ExtendWith(ApplicationExtension.class)
public class ListActionsViewTest
{
	static ServerHelper serverHelper = new ServerHelper(); 
	static TestHelper testHelper = new TestHelper(); 
	static Client client; 
	static List list; 
	static int list_idx; 
	
	@BeforeAll
	static void setup() throws RemoteException, MalformedURLException, AlreadyBoundException {
		System.out.println("RUNNING LISTACTIONS VIEW");
		serverHelper.bootServer();
	}
	
	@Start // Before 
	private void start(Stage stage) throws IOException 
	{ 	
		// template pattern testing?
		
		// Initialize 
    	FXMLLoader loader = (new ListActionsViewLoader()).load();
		BorderPane view = loader.load();
		ListActionsViewController cont = loader.getController(); 

    	// Create client and log a user into client
		client = testHelper.initializeTestData("jim@gmail.com", "jim123");
    	assert(client != null); 
    	
    	// Get data for model
    	int list_idx = 0; 
    	ArrayList<List> lists = client.getUser().getBoard("Team Jim").getLists(); 
    	list = client.getUser().getBoard("Team Jim").getList(list_idx);
    	
    			
		// Set on controller
		cont.setStage(stage);
		cont.setClient(client);
		cont.setModel(lists, list_idx);

		// Stage and scene setting
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
		stage.show();
	}
	
	// The functionality here is really tested on TestListView, other tests require 
	// these atomic structures to work. Therefore, the views buttons are tested to see
	// if they are here and clickable
	
	// Already tested in board view, createcard view, and listview
	@Test
	public void testAddCard(FxRobot robot) throws InterruptedException
	{
		robot.clickOn("#addCardButton");
		Thread.sleep(2000);
	}
	
	// List view tests this
	@Test
	public void testMoveCard(FxRobot robot) throws InterruptedException
	{
		robot.clickOn("#moveCardInListButton");
		Thread.sleep(2000);
	}
	
	// test move list functionality testing is on TestBoardView
	@Test
	public void testMoveList(FxRobot robot) throws InterruptedException {
		robot.clickOn("#moveListButton");
		Thread.sleep(2000);
	}

	@AfterAll
	static void tearDown() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
}


