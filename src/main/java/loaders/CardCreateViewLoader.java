package loaders;

import controllers.CustomCreateViewController;
import javafx.fxml.FXMLLoader;

public class CardCreateViewLoader implements ViewLoaderInterface
{
	@Override
	public FXMLLoader load()
	{
		return new FXMLLoader(CustomCreateViewController.class
				.getResource("../views/cardCreateView.fxml"));
	}
}
