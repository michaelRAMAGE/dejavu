package controllers;

import java.io.IOException;

import Rello.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.BoardListViewLoader;

public class BoardListViewTransition
{
	public Stage stage;
	public Client client; 
	
	public BoardListViewTransition(Stage stage, Client client) {
		this.stage = stage;
		this.client = client; 
	}
	
	public void showView() throws IOException {
    	FXMLLoader loader = (new BoardListViewLoader()).load();
    	BorderPane view = loader.load(); 
    	BoardListViewController cont = loader.getController(); 
    	cont.setStage(stage);
    	cont.setClient(client);
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
	}
}
