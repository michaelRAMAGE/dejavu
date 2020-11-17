package loaders;

import controllers.ServerViewController;
import javafx.fxml.FXMLLoader;

public class MoveCardViewLoader implements ViewLoaderInterface
{

	@Override
	public FXMLLoader load()
	{
		FXMLLoader loader = new FXMLLoader(ServerViewController.class
				.getResource("../views/moveCard.fxml"));
		return loader;
	}

}
