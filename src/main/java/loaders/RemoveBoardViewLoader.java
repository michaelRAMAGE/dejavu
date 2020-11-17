package loaders;

import controllers.ServerViewController;
import javafx.fxml.FXMLLoader;

public class RemoveBoardViewLoader implements ViewLoaderInterface
{

	@Override
	public FXMLLoader load()
	{
		// load view
		FXMLLoader loader = new FXMLLoader(ServerViewController.class
				.getResource("../views/boardRemoveView.fxml"));
		return loader;
	}

}
