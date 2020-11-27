package controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.HashMap;

import ENV.EnvHandler;
import Rello.Client;
import Rello.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import loaders.LoginViewLoader;
import template.LoginView;

public class ServerViewController {

	Server server; 
	Client client; 
	Stage stage; 
	static HashMap<String, String> env_vars = EnvHandler.getEnvVars();
	
	public void setModel(Server server_model) {
		server = server_model; 
	}
	
	public void setStage(Stage server_stage) {
		stage = server_stage; 
	}
	
    @FXML
    private Button defaultConnectButton;

    @FXML
    private TextField ipTextField;

    @FXML
    private Button customConnectButton;   
    
    @FXML
    private Label errorMessageIPTextField;

    @FXML
    void onConnectCustom(ActionEvent event) throws RemoteException, MalformedURLException {
    	System.out.println("Custom connection clicked");
    
		// Construct a client, connect them to host server 
		if (ipTextField.getText().equals("")) {
			// Display error message (enter an ip)
			errorMessageIPTextField.setVisible(true);
			return;
		}
//		String host = ipTextField.getText(); // assume default port 1099, used by rmi
//		String bind_name = "Server"; 
//    	client = new Client(host, bind_name);    	
    }

    @FXML
    void onConnectDefault(ActionEvent event) throws IOException {
		System.out.println("Default connection clicked");
		
		// Construct a client, connect them to host server 
		String host = env_vars.get("RMI_HOST") + ":" + env_vars.get("RMI_PORT");
		String bind_name = env_vars.get("RMI_BIND_NAME"); // name of reference to remote stub
		
    	client = new Client(host, bind_name); // construct the client

    	// Set xml output name
//    	server.setXMLFileName("default_server.xml");
    	
    	// Load client login view
    	(new LoginView(stage, client)).load(); 
    }
    
}
