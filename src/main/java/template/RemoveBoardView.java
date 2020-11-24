package template;

import java.io.IOException;
import java.util.HashMap;

import Rello.Board;
import Rello.Client;
import controllers.RemoveBoardViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.RemoveBoardViewLoader;

public class RemoveBoardView extends ViewLoaderTemplate
{
	private Stage stage;
	private BorderPane view;
	private RemoveBoardViewController cont;
	private Client client;
	private HashMap<String, Board> boards; 
	
	public RemoveBoardView(Stage stage, Client client, HashMap<String, Board> boards) {
		this.stage = stage;
		this.client = client; 
		this.boards = boards; 		
	}
	
	@Override
	protected void prepare() throws IOException
	{
		// TODO Auto-generated method stub
		FXMLLoader loader = (new RemoveBoardViewLoader()).load(); 
		view = loader.load(); 
		cont = loader.getController(); 
	}

	@Override
	protected void setData()
	{
		cont.setStage(stage); // because stage is static, we do not need to do this (make static on main)?
		cont.setClient(client);
		cont.setModel(boards);
	}

	@Override
	protected void present()
	{
		Scene new_scene = new Scene(view);
		stage.setScene(new_scene);
		stage.show(); 
	}

}
