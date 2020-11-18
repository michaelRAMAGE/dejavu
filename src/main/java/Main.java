import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import Rello.Server;
import Rello.User;
import controllers.ServerViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.ServerHelper;

public class Main extends Application
{
	ServerHelper local;
	Server server;
	static Scene s;
	static Stage stage; 

	public static void main(String [] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// boot server -- testing		
		local = new ServerHelper();
		server = local.bootServer("non_test_users.xml");
		User user = server.getUser("jim@gmail.com");
		if (user == null) {
			System.out.println("null");
		}

		// Server is no longer used to start server
		
		// now when we open serverconnection
		stage = primaryStage; 
		// Initialize load for main server view
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("./views/ServerView.fxml")); // entry view
		BorderPane view = loader.load(); 
		ServerViewController cont = loader.getController(); 
		
		// Set model for server
		cont.setStage(primaryStage); // will need to think about how to deal with singleton
		cont.setModel(server); // will need to think about how to deal with singleton
		
		// Set up initial scene
		s = new Scene(view);
		showView(s);
	}
	
	public static void showView(Scene new_scene) {
		s = new_scene; 
		stage.setScene(s);
		stage.show();
	}
	
	@Override
	public void stop() throws AccessException, RemoteException, NotBoundException {
		local.closeServer("non_test_users.xml");
		stage.close();
	}

}
