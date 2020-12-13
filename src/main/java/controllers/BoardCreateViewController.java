package controllers;

import java.io.IOException;

import Rello.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import template.BoardListView;

public class BoardCreateViewController {
	
	Client client;
	Stage stage; 
	
	public void setClient(Client client)
	{
		// TODO Auto-generated method stub
		this.client = client; 
		ViewHelpText.setText("To add a Board, enter a name below:");
	}

	public void setStage(Stage stage)
	{
		// TODO Auto-generated method stub
		this.stage = stage; 
	}
	
	public void setModel() { } // client has user, user has boards, we just add to it 
	
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
		(new BoardListView(stage, client)).load();

	}

	@FXML
	void onCreate(ActionEvent event) throws IOException
	{
		String new_board_name = nameTextField.getText();
		if (nameTextField.getText() != "") {
			client.getUser().createBoard(new_board_name);
			client.createBoard(new_board_name, client.getUser());
			System.out.println(client.getUser().getBoards());
			(new BoardListView(stage, client)).load();
		}
	}
}

