package loaders;

import controllers.SaveChangesViewController;
import javafx.fxml.FXMLLoader;

public class SaveChangesViewLoader implements ViewLoaderInterface
{

	@Override
	public FXMLLoader load()
	{
    	FXMLLoader loader = new FXMLLoader(SaveChangesViewController.class
    			.getResource("../views/saveBoardChangesView.fxml"));
		return loader; 
	}


}
