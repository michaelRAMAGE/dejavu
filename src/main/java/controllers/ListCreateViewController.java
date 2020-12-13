package controllers;

import java.io.IOException;

import Rello.Board;
import Rello.Client;
import commands.CommandInterface;
import commands.CommandInvoker;
import commands.UpdateBoardCommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import template.CustomBoardView;
import template.ViewLoaderTemplate;

public class ListCreateViewController  {

	public Stage stage; 
	public Client client; 
	public Board board; 
	
    public void setStage(Stage stage) {
    	this.stage = stage; 
    }
    
    public void setModel(Board board) {
    	this.board = board; 
    }
    
    public void setClient(Client client) {
    	this.client = client;
    }
	
    @FXML
    private TextField newListNameTextField;

    @FXML
    private Button cancelListCreateButton;

    @FXML
    private Button createListButton;

    @FXML
    void onCancel(ActionEvent event) throws IOException {
    	new CustomBoardView(stage, client, board).load(); 
    }

    @FXML
    void onCreate(ActionEvent event) throws IOException {
    	// Do local update on board
    	board.addList(newListNameTextField.getText());
    	client.getUser().replaceBoard(board.getName(), board);
    	
    	// Set loader for updated board
    	ViewLoaderTemplate view = new CustomBoardView(stage, client, board);
    	view.load();
  	
    	// Update client
    	client.updateBoard(board, client.getUser());
    }
    

}
