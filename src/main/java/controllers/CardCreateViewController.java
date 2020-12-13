package controllers;

import java.io.IOException;

import Rello.Board;
import Rello.Client;
import Rello.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import template.CustomBoardView;
import template.ViewLoaderTemplate;

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
			client.updateBoard(board, client.getUser());
			switchToBoardView(); 
		}
	}
		
	void switchToBoardView() throws IOException {
		
		Stage main_view = (Stage) stage.getOwner();
		
		if (main_view == null) { // forget why i did this
			main_view = stage;
		}
		else {
			stage.hide(); 
		}
		
		String bname = this.board.getName();
		ViewLoaderTemplate view = new CustomBoardView(main_view, client, client.getUser().getBoard(bname));
		view.load();
	}
}
