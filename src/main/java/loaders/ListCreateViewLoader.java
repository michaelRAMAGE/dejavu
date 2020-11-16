package loaders;

import controllers.ListCreateViewController;
import javafx.fxml.FXMLLoader;

public class ListCreateViewLoader implements ViewLoaderInterface
{

	@Override
	public FXMLLoader load()
	{
		return new FXMLLoader(ListCreateViewController.class
				.getResource("../views/listCreateView.fxml"));
	}

}
