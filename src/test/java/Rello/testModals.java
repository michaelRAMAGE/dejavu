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
import controllers.ListViewController;
import javafx.fxml.FXMLLoader;
	import javafx.scene.Parent;
	import javafx.scene.Scene;
	import javafx.scene.layout.BorderPane;
	import javafx.stage.Stage;
import loaders.CardCreateViewLoader;
import loaders.ListCreateViewLoader;
import loaders.ListViewLoader;
import utils.GuiTestHelper;
	import utils.ServerHelper;


@ExtendWith(ApplicationExtension.class)
public class testModals
{

	
	@BeforeAll
	static void setup() throws RemoteException, MalformedURLException, AlreadyBoundException {
	}
	
	@Start // Before 
	private void start(Stage stage) throws IOException 
	{ 	
		// template pattern testing?
		
		// Initialize 
		FXMLLoader loader = (new ListViewLoader()).load(); 
		BorderPane view = loader.load();
		ListViewController cont = loader.getController();
		cont.setClient(null);
		cont.setStage(stage);
		// Stage and scene setting
		Scene new_scene = new Scene(view);
		stage.setScene(new_scene);
		stage.show();
	}
	

	@Test
	public void testCreateCardSubmit(FxRobot robot) throws InterruptedException 
	{
		robot.clickOn("#editListButton");
		Thread.sleep(10000);
	}
	
	@Test
	public void testCreateCardCancel(FxRobot robot) throws InterruptedException 
	{

	}
	
	
	@AfterAll
	static void done() throws AccessException, RemoteException, NotBoundException, MalformedURLException {

	}
		
}


