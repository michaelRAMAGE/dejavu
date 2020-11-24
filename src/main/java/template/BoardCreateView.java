package template;

import java.io.IOException;

import Rello.Client;
import controllers.BoardCreateViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.BoardCreateViewLoader;

public class BoardCreateView extends ViewLoaderTemplate
{
	private Stage stage;
	private BorderPane view;
	private BoardCreateViewController cont;
	private Client client;
	
	public BoardCreateView(Stage stage, Client client) {
		this.stage = stage;
		this.client = client; 
	}
	
	@Override
	protected void prepare() throws IOException
	{
		// TODO Auto-generated method stub
		FXMLLoader loader = (new BoardCreateViewLoader()).load(); 
		view = loader.load(); 
		cont = loader.getController(); 
	}

	@Override
	protected void setData()
	{
		cont.setStage(stage); 
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
