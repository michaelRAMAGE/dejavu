package controllers;

import java.io.IOException;
import java.util.HashMap;

import Rello.Board;
import Rello.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import template.BoardListView;

public class RemoveBoardViewController {

	Stage stage; 
	Client client; 
	HashMap<String, Board> boards; 
	
    public void setStage(Stage stage)
	{
		this.stage = stage;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}
	
	public void setModel(HashMap<String, Board> boards) {
		this.boards = boards; 
		ObservableList<String> obs_boards = FXCollections.observableArrayList();
		for (String key : boards.keySet()) {
			obs_boards.add(key);
		}
		choiceBox.setItems(obs_boards);
	}


	@FXML
    private Label currentCardInfoLabel11;

    @FXML
    private Label currentCardInfoLabel121;

    @FXML
    private ChoiceBox<String> choiceBox;

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
    	Stage main_stage = (Stage) stage.getOwner();
    	stage.hide();

    	client.getUser().removeBoard(choiceBox.getValue());
    	
//    	System.out.println(boards.get(choiceBox.getValue()));
//    	client.removeBoard(client.getUser().getBoard(choiceBox.getValue()), client.getUser()); // bug
    	
    	new BoardListView(main_stage, client).load(); 
    }
}
