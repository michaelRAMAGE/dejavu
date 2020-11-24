package template;

import java.io.IOException;

import Rello.Client;
import Rello.List;
import controllers.CustomCardEditViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.CustomCardEditViewLoader;

public class CustomCardEditView extends ViewLoaderTemplate
{
	
	Stage stage; // can be moved to parent
	Client client; 
	List list;
	int list_idx; 
	int card_idx; 
	
	
	FXMLLoader loader; // can be added to parent
	CustomCardEditViewController cont; 
	BorderPane view;
	
	public CustomCardEditView(Stage stage, Client client, List list, int list_idx, int card_idx)
	{
		super();
		this.stage = stage;
		this.client = client;
		this.list = list;
		this.list_idx = list_idx;
		this.card_idx = card_idx; 
	}
	
	@Override
	protected void prepare() throws IOException
	{
		loader = (new CustomCardEditViewLoader()).load(); 
		view = loader.load(); 
		cont = loader.getController();
	}

	@Override
	protected void setData() throws IOException
	{
		// TODO Auto-generated method stub
		cont.setStage(stage);
		cont.setClient(client);
		cont.setModel(list, list_idx, card_idx);
	}

	@Override
	protected void present()
	{
		Scene new_scene = new Scene(view);
		stage.setScene(new_scene);
		stage.show(); 
	}

}
