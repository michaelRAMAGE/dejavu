package controllers;

import Rello.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomCardEditViewController {

	public Stage stage; 
	public Client client; 
	
	public void setStage(Stage stage) {
		this.stage = stage; 
	}
	
	public void setClient(Client client) {
		this.client = client; 
	}
	
    @FXML
    private TextField cardNameTextField;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button addLabelButton;

    @FXML
    void onAddLabel(ActionEvent event) {
    	// load add label view 
    	
    }

    @FXML
    void onSave(ActionEvent event) {
    	// save description 
    	
    }

}