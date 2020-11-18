package controllers;

import java.io.IOException;

import Rello.Board;
import Rello.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loaders.SaveChangesViewLoader;

public class SaveChangesViewTransition
{
	private Stage stage;
	private Board board;
	private Client client;

	public SaveChangesViewTransition(Stage stage, Client client, Board board) {
		this.stage = stage;
		this.client = client; 
		this.board = board; 
	}
	
    public Stage createModal() {
    	Stage popup = new Stage();
    	popup.initModality(Modality.APPLICATION_MODAL);
    	popup.initOwner(stage);
    	return popup; 
    }
    
	
	public void showView() throws IOException {
		// display is a modal
		Stage popup = createModal(); 
		
		FXMLLoader loader = (new SaveChangesViewLoader()).load(); 
		GridPane view = loader.load();
		SaveChangesViewController cont = loader.getController(); 
		cont.setStage(popup);
		cont.setClient(client);
		cont.setModel(board);
		Scene s = new Scene(view);
		popup.setScene(s);
		popup.show();
	}
}
