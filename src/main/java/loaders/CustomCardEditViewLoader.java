package loaders;

import controllers.CustomCardEditViewController;
import javafx.fxml.FXMLLoader;

public class CustomCardEditViewLoader implements ViewLoaderInterface
{

	@Override
	public FXMLLoader load()
	{
    	FXMLLoader loader = new FXMLLoader(CustomCardEditViewController.class
    			.getResource("../views/cardEditView.fxml"));
    	return loader; 
	}

}
