package loaders;

import controllers.ListActionsViewController;
import javafx.fxml.FXMLLoader;

public class ListActionsViewLoader implements ViewLoaderInterface
{

	@Override
	public FXMLLoader load()
	{
		// TODO Auto-generated method stub
		return new FXMLLoader(ListActionsViewController.class
    			.getResource("../views/listActionsView.fxml"));
	}

}
