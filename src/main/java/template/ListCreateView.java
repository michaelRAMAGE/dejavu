package template;

import java.io.IOException;

import Rello.Board;
import Rello.Client;
import controllers.ListCreateViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.ListCreateViewLoader;

public class ListCreateView extends ViewLoaderTemplate
{

	FXMLLoader loader; 
	BorderPane view;
	ListCreateViewController cont;
	private Stage stage;
	private Board board;
	private Client client;
	
	public ListCreateView(Stage stage, Client client, Board board)
	{
		this.stage = stage;
		this.board = board;
		this.client = client;
	}

	@Override
	protected void prepare() throws IOException
	{
		loader = (new ListCreateViewLoader()).load();
    	view = loader.load(); 
    	cont = loader.getController(); 
	}

	@Override
	protected void setData() throws IOException
	{
    	cont.setStage(stage);
    	cont.setClient(client);
		cont.setModel(board); 
	}

	@Override
	protected void present()
	{
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);
    	stage.show();
	}

}
