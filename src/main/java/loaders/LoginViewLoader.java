package loaders;

import controllers.LoginViewController;
import controllers.ServerViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class LoginViewLoader implements ViewLoaderInterface
{
	@Override
	public FXMLLoader load()
	{
		// load view
		FXMLLoader loader = new FXMLLoader(ServerViewController.class
				.getResource("../views/loginView.fxml"));
		return loader;
	}
}
