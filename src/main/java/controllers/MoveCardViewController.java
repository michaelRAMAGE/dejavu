package controllers;

import java.io.IOException;
import java.util.ArrayList;

import Rello.Card;
import Rello.Client;
import Rello.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.CustomBoardViewLoader;
import template.CustomBoardView;

public class MoveCardViewController {
	
	Stage modalStage;
	Stage stage; 
	Client client;
	List list; 
	int list_idx; 
	
	public void setStage(Stage stage) {
		this.stage = stage; 
	}
	
	public void setClient(Client client)
	{
		this.client = client;
	}

	public void setModel(List list, int list_idx)
	{
		this.list = list;
		this.list_idx = list_idx; 
		ObservableList<Integer> obs_cards = FXCollections.observableArrayList();
		for (int i=0; i<list.getCards().size(); i++) {
			obs_cards.add(i);
		}
		choiceBoxA.setItems(obs_cards);
		choiceBoxB.setItems(obs_cards);
	}

	@FXML
    private Label currentCardInfoLabel11;

    @FXML
    private Label currentCardInfoLabel12;

    @FXML
    private ChoiceBox<Integer> choiceBoxA;

    @FXML
    private Label currentCardInfoLabel121;

    @FXML
    private ChoiceBox<Integer> choiceBoxB;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    void onCancel(ActionEvent event) {
    	stage.hide(); 
    }

    @FXML
    void onSave(ActionEvent event) throws IOException {
    	int startIdx = choiceBoxA.getValue();
    	int endIdx = choiceBoxB.getValue(); 
    
    	// Modify local list and then set it on client to pass back
    	list.moveCardInList(startIdx, endIdx);
    	
    	client.getUser().getBoard(list.getBoard().getName()).getLists().set(list_idx, list);  
    	client.updateBoard(list.getBoard(), client.getUser());
    	
    	Stage main_stage = (Stage) stage.getOwner();
    	stage.hide(); 

    	new CustomBoardView(main_stage, client, client.getUser().getBoard(list.getBoard().getName())).load(); 
    }

}
