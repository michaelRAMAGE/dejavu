package controllers;

import java.io.IOException;

import Rello.Board;
import Rello.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import template.BoardListView;

public class SaveChangesViewController {

	public Client client;
	public Board board; 
	public Stage stage; 
	BoardListViewTransition boardListView;
	
	public void setClient(Client client)
	{
		this.client = client;
	}

	public void setStage(Stage stage)
	{
		this.stage = stage;
	}

	public void setModel(Board board) {
		this.board = board; 
	}
	
	
    @FXML
    private Button saveChangesButton;

    @FXML
    private Button doNotSaveChangesButton;

    @FXML           
    void onDoNotSaveChanges(ActionEvent event) throws IOException {
    	// just go back to board list view 
    	Stage main_stage = (Stage) stage.getOwner(); 
    	stage.hide(); 
    	
    	// load board list view
    	new BoardListView(main_stage, client).load();
    }

    @FXML
    void onSaveChanges(ActionEvent event) throws IOException {
    	// call update board
    	client.updateBoard(board, client.getUser());
    	
    	Stage main_stage = (Stage) stage.getOwner(); 
    	stage.hide(); 
    	
    	// load board list view
    	new BoardListView(main_stage, client).load();
    }
    
    

}
