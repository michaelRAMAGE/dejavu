package controllers;

import java.io.IOException;

import Rello.Board;
import Rello.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import template.CustomBoardView;

public class ListCreateViewController  {

	public Stage stage; 
	public Client client; 
	public Board board; 
	
    @FXML
    private TextField newListNameTextField;

    @FXML
    private Button cancelListCreateButton;

    @FXML
    private Button createListButton;

    @FXML
    void onCancel(ActionEvent event) throws IOException {
    	returnToBoardView(); 
    }

    @FXML
    void onCreate(ActionEvent event) throws IOException {
    	board.addList(newListNameTextField.getText());
    	returnToBoardView(); 
    }
    
    public void returnToBoardView() throws IOException {
//		cont.setStage(stage);
//		cont.setClient(client);
//		cont.setModel(board);
		new CustomBoardView(stage, client, board).load(); 
    }

    public void setStage(Stage stage) {
    	this.stage = stage; 
    }
    
    public void setModel(Board board) {
    	this.board = board; 
    }
    
    public void setClient(Client client) {
    	this.client = client;
    }
}
