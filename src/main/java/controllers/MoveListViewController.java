package controllers;

import java.io.IOException;
import java.util.ArrayList;

import Rello.Board;
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

public class MoveListViewController {

	Client client;
	Stage stage;
	ArrayList<List> lists; 
	int list_idx; 
	
    public void setClient(Client client)
	{
		this.client = client;
	}

	public void setStage(Stage stage)
	{
		this.stage = stage;
	}

	public void setModel(ArrayList<List> lists, int list_idx)
	{
		this.lists = lists;
		this.list_idx = list_idx; 
		currentListLabel.setText(Integer.toString(list_idx) + ", " + lists.get(list_idx).getName());
		ObservableList<String> obs_list = FXCollections.observableArrayList();
		for (int i=0; i<lists.size(); i++) {
			obs_list.add(Integer.toString(i) + ", " + lists.get(list_idx).getName()); // index, name
		}
		choiceBox.setItems(obs_list); 
	}

    @FXML
    private Label currentCardInfoLabel11;

    @FXML
    private Label currentCardInfoLabel12;

    @FXML
    private Label currentListLabel;

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
    	String targetIndexString = choiceBox.getValue(); 
    	String[] targetIndexSplit = targetIndexString.split(",");
    	int targetIndex = Integer.parseInt(targetIndexSplit[0]);
    	
    	// Modify local list and then set it on client to pass back
    	Board board = lists.get(list_idx).getBoard();
    	board.moveList(list_idx, targetIndex);
    	
    	client.getUser().replaceBoard(board.getName(), board);  
    	stage.hide(); 
    	
    	Stage main_stage = (Stage) stage.getOwner();
    	// reload the main stage to reflect changes
		FXMLLoader loader = (new CustomBoardViewLoader()).load(); 

    	// Load the view
    	BorderPane view = loader.load(); 
    	CustomBoardViewController cont = loader.getController();
    
    	cont.setClient(client);
    	cont.setModel(board);
    	cont.setStage(main_stage);
    	Scene new_scene = new Scene(view);
    	main_stage.setScene(new_scene);	
    	main_stage.show();
    }


}
