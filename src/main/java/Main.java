import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


import Rello.Server;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.ServerHelper;
import template.*;

public class Main extends Application
{
	ServerHelper local;
	Server server;
	static Scene s;
	static Stage stage; 

	public static void main(String [] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// boot server -- testing		
		local = new ServerHelper();
		server = local.bootServer("non_test_users.xml");
		stage = primaryStage; 
		new ServerView(stage, server).load();
	}
	
	@Override
	public void stop() throws AccessException, RemoteException, NotBoundException {
		local.closeServer("non_test_users.xml");
		stage.close();
	}

}
