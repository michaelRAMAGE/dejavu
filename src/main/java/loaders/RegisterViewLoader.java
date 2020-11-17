package loaders;

import controllers.ServerViewController;
import javafx.fxml.FXMLLoader;

public class RegisterViewLoader implements ViewLoaderInterface
{

	@Override
	public FXMLLoader load()
	{
		// load view
		FXMLLoader loader = new FXMLLoader(ServerViewController.class
				.getResource("../views/registerView.fxml"));
		return loader;
	}

}
