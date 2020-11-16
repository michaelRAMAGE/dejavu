package loaders;

import controllers.BoardListViewController;
import javafx.fxml.FXMLLoader;

public class ListViewLoader implements ViewLoaderInterface
{

	@Override
	public FXMLLoader load()
	{
		  return new FXMLLoader(BoardListViewController.class
					.getResource("../views/listView.fxml"));
	}

}
