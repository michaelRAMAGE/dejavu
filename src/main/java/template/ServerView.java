package template;

import java.io.IOException;


import Rello.Server;
import controllers.ServerViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.ServerViewLoader;

public class ServerView extends ViewLoaderTemplate
{
	FXMLLoader loader; 
	BorderPane view; 
	ServerViewController cont; 
	
	Stage stage; 
	Server model; 
	
	public ServerView(Stage stage, Server model) {
		this.stage = stage;
		this.model = model;
	}

	@Override
	protected void prepare() throws IOException
	{
		FXMLLoader loader = (new ServerViewLoader()).load();
		view = loader.load(); 
		cont = loader.getController(); 
	}

	@Override
	protected void setData()
	{
		// Set model for server
		cont.setStage(stage); // will need to think about how to deal with singleton
		cont.setModel(model); // will need to think about how to deal with singleton
	}
	
	@Override
	protected void present()
	{
		Scene new_scene = new Scene(view);
		
		
		// Could add this string at the base of all views like --> String basecss = "views/basetheme.css"
		view.getStylesheets().add("views/basetheme.css");
		
		
		
//		view.getStylesheets().add(".root { -fx-background-color: teal; } Button { -fx-background-color: orange; -fx-font-size: 2em; -fx-text-fill: #0000ff }");
//
//		Button b1 = new Button();
//		Button b2 = new Button(); 
//		
//		
//		view.setBottom(b1);
//		view.setLeft(b2);
		
		new_scene.getStylesheets().add("views/basetheme.css"); 
		System.out.println(new_scene.getStylesheets()); 	
		
		view.getStyleClass().add("Button");
	
		
		view.setStyle("-fx-background-color: blue");
		
		
		view.setStyle("Button: -fx-background-color: green");
		System.out.println("view.getStyleClass(): " + view.getStyleClass());
//
//		System.out.println("view.getStyleClass() ssss: " + view.getStyleClass());
//
//		
//		System.out.println("view.getStylesheets(): " + view.getStylesheets()); 
//		System.out.println("view.getStyleClass(): " + view.getStyle()); 
//		
		
		stage.setScene(new_scene);
		stage.show(); 
	}

}
