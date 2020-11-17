package controllers;

import java.io.IOException;
import java.util.ArrayList;

import Rello.Board;
import Rello.Card;
import Rello.Client;
import Rello.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.CustomBoardViewLoader;

public class CardCreateViewController
{
	Client client; 
	List list; 
	Stage stage; 
	Board board; 
	int list_idx; 
	
	
	public void setClient(Client client) {
		this.client = client; 
	}
	
	public void setModel(List list, int list_idx) {
		this.list = list;
		this.list_idx = list_idx; 
		this.board = list.getBoard(); 
	}
	
	public void setStage(Stage stage) {
		this.stage = stage; 
	}

    @FXML
    protected Label ViewHelpText;

    @FXML
    protected TextField nameTextField;
    
    @FXML
    protected Button cancelCreationButton;

    @FXML
    protected Button createButton;

	
	@FXML
	void onCancel(ActionEvent event) throws IOException
	{
		switchToBoardView(); 
	}

	@FXML
	void onCreate(ActionEvent event) throws IOException
	{
		if (nameTextField.getText() != "") {
			String bname = this.board.getName();
			client.getUser().getBoard(bname).getList(list_idx).addCard(nameTextField.getText()); 
//			client.updateBoard(board, client.getUser());
			switchToBoardView(); 		
		}
	}
		
	void switchToBoardView() throws IOException {
		String bname = this.board.getName();
		FXMLLoader loader = (new CustomBoardViewLoader()).load();
		BorderPane view = loader.load();
		CustomBoardViewController cont = loader.getController(); 
		cont.setStage(stage);
		cont.setClient(client);
		cont.setModel(client.getUser().getBoard(bname)); // send newest version back
		Scene s = new Scene(view);
		stage.setScene(s);
		stage.show(); 
	}
}
