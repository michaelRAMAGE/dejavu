package loaders;

import controllers.BoardListViewController;
import javafx.fxml.FXMLLoader;

public class CustomBoardViewLoader implements ViewLoaderInterface
{

	@Override
	public FXMLLoader load()
	{
		FXMLLoader loader = new FXMLLoader(BoardListViewController.class
				.getResource("../views/boardView2.fxml"));
		return loader; 
	}

}
