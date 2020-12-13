package controllers;

import java.io.IOException;

import Rello.Client;
import Rello.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import template.BoardListView;
import template.RegisterView;
import template.ServerView;

public class LoginViewController {
	
	public Client client; 
	public Stage stage; 
	
	
	public void setClient(Client client)
	{
		this.client = client; 
	}
	
	public void setStage(Stage server_stage) {
		stage = server_stage; 
	}
	
    @FXML
    private Label incNamePassLabel;

    @FXML
    private TextField userName;

    @FXML
    private PasswordField userPass;

    @FXML
    private Button signInButton;
    @FXML
    private Button goToRegister;

    @FXML
    private Button backToServerViewButton;
    
    @FXML
    void onSignInClick(ActionEvent event) throws IOException {
    	boolean success = client.loginUser(userName.getText(), userPass.getText());
    	System.out.println("Login success: " + success);
    	incNamePassLabel.setVisible(!success);	
    	if (success) {
    		loadBoardListView(); 
    	}
    	userName.clear();
    	userPass.clear(); 
    }
    
    @FXML
    void onBackToServerVIew(ActionEvent event) throws IOException {
    	(new ServerView(stage, Server.getInstance())).load(); // sserver is second arg, and we do not want that here so avoid
    }

    
    void loadBoardListView() throws IOException {
    	(new BoardListView(stage, client)).load();
    }    
    
    @FXML
    void onGoToRegister(ActionEvent event) throws IOException {
    	(new RegisterView(stage, client)).load();
    }
}
