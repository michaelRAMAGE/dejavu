package controllers;

import java.io.IOException;

import Rello.Client;
import Rello.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.CardCreateViewLoader;

public class ListActionsViewController {

	Stage stage;
	Client client;
	List list; 
	int list_idx; 
	
	public void setStage(Stage stage) {
		this.stage = stage; 
	}
	
	public void setClient(Client client) {
		this.client = client; 
	}
	
	public void setModel(List list, int list_idx) {
		this.list = list; 
	}
	
    @FXML
    private Button addCardButton;

    @FXML
    void onAddCard(ActionEvent event) throws IOException {
		// Load up card creation view
    	FXMLLoader createCardLoader = (new CardCreateViewLoader()).load();
		BorderPane view = createCardLoader.load();
		CardCreateViewController cont = createCardLoader.getController(); 
		cont.setStage(stage);
		cont.setClient(client);
		cont.setModel(list, list_idx);
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
    	stage.show(); 
    }
}
