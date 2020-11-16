package loaders;

import controllers.BoardListViewController;
import javafx.fxml.FXMLLoader;

public class BoardListViewLoader implements ViewLoaderInterface
{

	@Override
	public FXMLLoader load()
	{
    	FXMLLoader loader = new FXMLLoader(BoardListViewController.class
    			.getResource("../views/boardListingView.fxml"));
    	return loader; 
	}

}
