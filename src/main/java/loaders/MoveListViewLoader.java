package loaders;

import controllers.ServerViewController;
import javafx.fxml.FXMLLoader;

public class MoveListViewLoader implements ViewLoaderInterface
{
	@Override
	public FXMLLoader load()
	{
		FXMLLoader loader = new FXMLLoader(ServerViewController.class
				.getResource("../views/listMove.fxml"));
		return loader;
	}
}
