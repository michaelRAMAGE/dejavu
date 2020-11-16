package Rello;

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

import controllers.CardCreateViewController;
import controllers.ListCreateViewController;
import javafx.fxml.FXMLLoader;
	import javafx.scene.Parent;
	import javafx.scene.Scene;
	import javafx.scene.layout.BorderPane;
	import javafx.stage.Stage;
import loaders.CardCreateViewLoader;
import loaders.ListCreateViewLoader;
import utils.GuiTestHelper;
	import utils.ServerHelper;


@ExtendWith(ApplicationExtension.class)
public class TestCardCreateView
{

	static ServerHelper serverHelper = new ServerHelper(); 
	static GuiTestHelper testHelper = new GuiTestHelper(); 
	static Client client; 
	static List list; 
	static int list_idx;
	
	@BeforeAll
	static void setup() throws RemoteException, MalformedURLException, AlreadyBoundException {
		serverHelper.bootServer();
	}
	
	@Start // Before 
	private void start(Stage stage) throws IOException 
	{ 	
		// template pattern testing?
		
		// Initialize 
		FXMLLoader loader = (new ListCreateViewLoader()).load(); 
    	FXMLLoader createCardLoader = (new CardCreateViewLoader()).load();
		BorderPane view = createCardLoader.load();
		CardCreateViewController cont = createCardLoader.getController(); 
		
    	// Create client and log a user into client
		client = testHelper.initializeTestData("jim@gmail.com", "jim123");
    	assert(client != null); 
    	
    	// Get data for model
    	String bname = "Team Jim";
    	list_idx = 0; 
    	list = client.getUser().getBoard(bname).getList(list_idx); 
    
		// Set on controller
		cont.setStage(stage);
		cont.setClient(client);
		cont.setModel(list, list_idx);
   
		// Stage and scene setting
		Scene new_scene = new Scene(view);
		stage.setScene(new_scene);
		stage.show();
	}
	
	@Test
	public void testCreateCardSubmit(FxRobot robot) throws InterruptedException 
	{
		// Do the adding
    	int list_cards_size = list.getCards().size(); // this will not update but use as ref
		testHelper.enterTextInField(robot, "#nameTextField", "NewCard");
		robot.clickOn("#createButton");
		Thread.sleep(1000);
		
		// See if it was added to list
		int new_list_cards_size = list_cards_size++; 
		String selector = "#" + Integer.toString(list_idx) + Integer.toString(new_list_cards_size); 
		Assertions.assertThat(robot.lookup(selector)).isNotEqualTo(null);
	}
	
	@Test
	public void testCreateCardCancel(FxRobot robot) throws InterruptedException 
	{
		robot.clickOn("#cancelCreationButton"); // just...add...nothing
	}
	
	

	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
		serverHelper.closeServer();
	}
		
}


