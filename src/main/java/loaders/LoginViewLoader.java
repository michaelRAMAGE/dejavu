package loaders;


import controllers.ServerViewController;
import javafx.fxml.FXMLLoader;


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
