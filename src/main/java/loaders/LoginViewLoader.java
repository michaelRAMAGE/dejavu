package loaders;

import Rello.Client;
import controllers.LoginViewController;
import controllers.ServerViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class LoginViewLoader implements ViewLoaderInterface
{
	FXMLLoader loader; 
	
	@Override
	public FXMLLoader load()
	{
		loader = new FXMLLoader(ServerViewController.class
				.getResource("../views/loginView.fxml"));
		return loader;
	}
}
