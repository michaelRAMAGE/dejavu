package controllers;

import java.io.IOException;
import java.rmi.RemoteException;

import Rello.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.BoardListViewLoader;
import loaders.LoginViewLoader;
import template.BoardListView;
import template.LoginView;

public class RegisterViewController
{

	Stage stage; 
	Client client;
		
    public void setStage(Stage stage)
	{
		this.stage = stage;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	@FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button registerButton;

    @FXML
    private Button backToLoginButton;

    @FXML
    private Label emptyFieldErrorLabel;

    @FXML
    private Label badCredentialsLabel;
    
    @FXML
    void onBackToLogin(ActionEvent event) throws IOException {
    	(new LoginView(stage, client)).load(); 
    }

    @FXML
    void onRegister(ActionEvent event) throws IOException {
    	emptyFieldErrorLabel.setVisible(false);
    	badCredentialsLabel.setVisible(false);
    	if (userNameTextField.getText().equals("") || passwordTextField.getText().equals(""))  {
    		emptyFieldErrorLabel.setVisible(true);
    		return;
    	}
    	
    	boolean success = client.addUser(userNameTextField.getText(), passwordTextField.getText());

    	if (!success) {
    		badCredentialsLabel.setVisible(true);
    		return; 
    	}
    	else {
    		System.out.println(success);

        	// load into board list view 
        	loadBoardListView(); 	
    	}
    	
    	userNameTextField.clear();
    	passwordTextField.clear();

    }
    
    void loadBoardListView() throws IOException {
    	(new BoardListView(stage, client)).load();
    }
}
