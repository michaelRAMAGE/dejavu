import Rello.Server;
import controllers.ServerViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application
{

	static Scene s;
	static Stage stage; 

	public static void main(String [] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		stage = primaryStage; 
		// Initialize load for main server view
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("./views/ServerView.fxml")); // entry view
		BorderPane view = loader.load(); 
		ServerViewController cont = loader.getController(); 
		
		// Set model for server
		cont.setModel(Server.getInstance()); // will need to think about how to deal with singleton
		cont.setStage(primaryStage); // will need to think about how to deal with singleton
		
		// Set up initial scene
		s = new Scene(view);
		showView(s);
	}
	
	public static void showView(Scene new_scene) {
		s = new_scene; 
		stage.setScene(s);
		stage.show();
	}

}
