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
import loaders.MoveListViewLoader;

public class ListActionsViewController {

	Stage stage;
	Client client;
	ArrayList<List> lists; 
	List list; 
	int list_idx;
	
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
    void onMoveList(ActionEvent event) throws IOException {
    	FXMLLoader loader = (new MoveListViewLoader()).load();
		BorderPane view = loader.load();
		MoveListViewController cont = loader.getController(); 

		cont.setStage(stage);		
		cont.setClient(client);
		cont.setModel(lists, list_idx);
    	Stage main_stage = (Stage) stage.getOwner();
    	if (main_stage == null) {
    		System.out.println("main stage is null");
    	}
	  	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
    	stage.show(); 
    }
    
    @FXML
    void onMoveCardInList(ActionEvent event) throws IOException {
    	FXMLLoader loader = (new MoveCardViewLoader()).load();
		BorderPane view = loader.load();
		MoveCardViewController cont = loader.getController(); 
		
		cont.setStage(stage);
		cont.setClient(client);
		cont.setModel(list, list_idx);
		
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
    	stage.show(); 
    }
}
