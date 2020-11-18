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
import loaders.CustomBoardViewLoader;
import loaders.MoveCardViewLoader;
import loaders.MoveListViewLoader;

public class ListActionsViewController {

	Stage stage;
	public Client client;
	public ArrayList<List> lists; 
	public List list; 
	public int list_idx;
	
	public void setStage(Stage stage) {
		this.stage = stage; 
	}
	
	public void setClient(Client client) {
		this.client = client; 
	}
	
	public void setModel(ArrayList<List> lists, int list_idx) {
		this.lists = lists;
		this.list = lists.get(list_idx);
		this.list_idx = list_idx;
	}
	

    @FXML
    private Button addCardButton;

    @FXML
    private Button moveListButton;
    
    @FXML
    private Button moveCardInListButton;
    

    @FXML
    private Button removeListButton;
    
    @FXML
    void onAddCard(ActionEvent event) throws IOException {
		// Load up card creation view
    	FXMLLoader createCardLoader = (new CardCreateViewLoader()).load();
		BorderPane view = createCardLoader.load();
		CardCreateViewController cont = createCardLoader.getController(); 
		
		cont.setStage(stage);
		cont.setClient(client);
		cont.setModel(this.list, this.list_idx);
		
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
    
    @FXML
    void onRemoveList(ActionEvent event) throws IOException {
    	Stage main_stage = (Stage) stage.getOwner();
    	stage.hide();
    	
    	// remove list and update it accordingly
    	lists.remove(this.list_idx);
    	client.getUser().getBoard(list.getBoard().getName()).setLists(lists);
    	
    	// load board again
		FXMLLoader loader = (new CustomBoardViewLoader()).load();
		BorderPane view = loader.load();
		CustomBoardViewController cont = loader.getController(); 
		
		cont.setStage(main_stage);
		cont.setClient(client);
		cont.setModel(client.getUser().getBoard(list.getBoard().getName())); // send newest version back
		
		Scene s = new Scene(view);
		main_stage.setScene(s);
		main_stage.show(); 
    }

}
