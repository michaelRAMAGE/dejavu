package controllers;

import java.io.IOException;
import java.rmi.RemoteException;

import Rello.Client;
import Rello.Server;
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
import loaders.RegisterViewLoader;
import loaders.ServerViewLoader;
import loaders.ViewLoaderInterface;

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
    	
    	client.tester();
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
//    	FXMLLoader loader = (new ServerViewLoader()).load(); 
//    	BorderPane view = loader.load();
//    	ServerViewController cont = loader.getController(); 
//    	cont.setStage(stage);
    }

    
    void loadBoardListView() throws IOException {
    	FXMLLoader loader = (new BoardListViewLoader()).load();
    	BorderPane view = loader.load(); 
    	BoardListViewController cont = loader.getController(); 
    	cont.setStage(stage);
    	cont.setClient(client);
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
    	stage.show(); 
    }    
    
    @FXML
    void onGoToRegister(ActionEvent event) throws IOException {
    	FXMLLoader loader = (new RegisterViewLoader()).load();
    	BorderPane view = loader.load(); 
    	RegisterViewController cont = loader.getController(); 
    	cont.setStage(stage);
    	cont.setClient(client);
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
    	stage.show(); 
    }
}
