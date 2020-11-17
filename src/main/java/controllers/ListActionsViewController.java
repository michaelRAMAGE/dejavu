package controllers;

import java.io.IOException;
import java.util.ArrayList;

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
import loaders.MoveCardViewLoader;

public class ListActionsViewController {

	Stage stage;
	Client client;
	ArrayList<List> lists; 
	List list; 
	int list_idx;
	public Stage modalStage; 
	
	public void setStage(Stage stage) {
		this.stage = stage; 
	}
	
	public void setClient(Client client) {
		this.client = client; 
	}
	
	public void setModel(ArrayList<List> lists, int list_idx) {
		this.lists = lists;
		this.list = lists.get(list_idx);
	}
	
	public void setModalStage(Stage modalStage) {
		this.modalStage = modalStage;
	}
	
    @FXML
    private Button addCardButton;

    @FXML
    private Button moveListButton;
    
    @FXML
    private Button moveCardInListButton;
    
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
    
    @FXML
    void onMoveList(ActionEvent event) {
    	
    }
    
    @FXML
    void onMoveCardInList(ActionEvent event) throws IOException {
    	FXMLLoader loader = (new MoveCardViewLoader()).load();
		BorderPane view = loader.load();
		MoveCardViewController cont = loader.getController(); 
		
		cont.setStage(stage);
		cont.setClient(client);
		cont.setModel(list, list_idx);
//		cont.setModalStage(modalStage);
		
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
    	stage.show(); 
    }
}
