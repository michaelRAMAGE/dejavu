package controllers;

import java.io.IOException;

import Rello.Card;
import Rello.Client;
import Rello.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.CustomBoardViewLoader;
import template.CustomBoardView;

public class CustomCardEditViewController {

	public Stage stage; 
	public Client client; 
	public List list; 
	public int card_idx; 
	public int list_idx; 
	
	public void setStage(Stage stage) {
		this.stage = stage; 
	}
	
	public void setClient(Client client) {
		this.client = client; 
	}
	
	public void setModel(List list, int list_idx, int card_idx) {
		this.list = list;
		this.card_idx = card_idx; 
	}
	
    @FXML
    private TextField cardNameTextField;


    @FXML
    private Button removeCardButton;
    
    @FXML
    private TextArea descriptionTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button addLabelButton;

    // not implementing in sprint4
    @FXML
    void onAddLabel(ActionEvent event) {
    	// load add label view 
    	
    }

    @FXML
    void onRemoveCard(ActionEvent event) throws IOException {
    	// Remove the card
    	list.removeCard(card_idx);
    	this.client.getUser().replaceBoard(list.getBoard().getName(), list.getBoard());
    	
    	// switch out
    	switchToBoardView(); 
    }
    
    @FXML
    void onSave(ActionEvent event) throws IOException {
    	// Set new card name
    	list.getCards().get(card_idx).setName(cardNameTextField.getText());
    	
     	// Set new card description
    	list.getCards().get(card_idx).setDescription(descriptionTextField.getText());
    	
    	// Save labels -- currently do not support labels.  
    	
    	// switch out
    	switchToBoardView(); 
    }
    
    void switchToBoardView() throws IOException {
    	// update client with local list
//    	client.getUser().getBoard(list.getBoard().getName()).getList(list_idx).setCards(list.getCards());
//    	client.getUser().replaceBoard(list.getBoard().getName(), list.getBoard()); // easier way to do above save ^
    	
		Stage main_stage = (Stage) stage.getOwner(); 
		stage.hide(); // hide modal
    	
    	// Rerender board 
		this.client.updateBoard(list.getBoard(), client.getUser());
		new CustomBoardView(main_stage, client, list.getBoard()).load();
    }

}