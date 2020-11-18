package controllers;

import java.io.IOException;

import Rello.Board;
import Rello.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.BoardListViewLoader;
import loaders.CustomBoardViewLoader;

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
    	System.out.println("client in here: " + client);
    	board.addList(newListNameTextField.getText());
    	returnToBoardView(); 
    }
    
    public void returnToBoardView() throws IOException {
		FXMLLoader loader = (new CustomBoardViewLoader()).load(); 
		BorderPane view = loader.load(); 
		CustomBoardViewController cont = loader.getController();
		cont.setStage(stage);
		cont.setClient(client);
		cont.setModel(board);

		Scene new_scene = new Scene(view);
		stage.setScene(new_scene);	
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
