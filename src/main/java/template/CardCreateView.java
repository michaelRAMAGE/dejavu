package template;

import java.io.IOException;

import Rello.Client;
import Rello.List;
import controllers.CardCreateViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.CardCreateViewLoader;

public class CardCreateView extends ViewLoaderTemplate
{
	
	Stage stage;
	Client client; 
	List list;
	int list_idx;

	private CardCreateViewController cont;
	private BorderPane view;
	private FXMLLoader loader; 

	
	public CardCreateView(Stage stage, Client client, List list, int list_idx)
	{
		super();
		this.stage = stage;
		this.client = client;
		this.list = list;
		this.list_idx = list_idx;
	}


	@Override
	protected void prepare() throws IOException
	{
    	loader = (new CardCreateViewLoader()).load();
		view = loader.load();
		cont = loader.getController(); 
	}

	@Override
	protected void setData() throws IOException
	{
		// TODO Auto-generated method stub
		cont.setStage(stage);
		cont.setClient(client);
		cont.setModel(list, list_idx);
	}

	@Override
	protected void present()
	{
		// TODO Auto-generated method stub
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	// null pointer
    	stage.show();
	}

}
