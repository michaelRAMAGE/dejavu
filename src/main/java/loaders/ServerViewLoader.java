package loaders;

import controllers.ServerViewController;
import javafx.fxml.FXMLLoader;

public class ServerViewLoader implements ViewLoaderInterface
{

	@Override
	public FXMLLoader load()
	{
		return new FXMLLoader(ServerViewController.class.getResource("./views/ServerView.fxml"));
	}

}
