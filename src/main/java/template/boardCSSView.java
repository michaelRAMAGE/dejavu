package template;

import java.io.IOException;

import Rello.Board;
import Rello.Client;
import controllers.BoardListViewController;
import controllers.boardCSSController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class boardCSSView extends ViewLoaderTemplate
{

	FXMLLoader loader;
	boardCSSController cont; 
	BorderPane view; 
	Board board; 
	Client client;
	Stage stage; 
	
	public boardCSSView(Stage stage, Client client, Board board)
	{
		super();
		this.stage = stage;
		this.client = client;
		this.board = board;
	}

	@Override
	protected void prepare() throws IOException
	{
    	 loader = new FXMLLoader(boardCSSController.class
    			.getResource("../views/boardCSSView.fxml"));
    	 view = loader.load(); 
    	 cont = loader.getController();

	}

	@Override
	protected void setData() throws IOException
	{
		cont.setStage(stage);
		cont.setModel(board);
		cont.setClient(client);

	}

	@Override
	protected void present()
	{
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);
    	stage.show();
	}

}
