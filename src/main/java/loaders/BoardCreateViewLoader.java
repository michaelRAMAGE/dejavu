package loaders;
import controllers.BoardCreateViewController;
import javafx.fxml.FXMLLoader;

public class BoardCreateViewLoader implements ViewLoaderInterface
{
	@Override
	public FXMLLoader load()
	{
		return new FXMLLoader(BoardCreateViewController.class
				.getResource("../views/boardCreateView.fxml"));
	}
}
