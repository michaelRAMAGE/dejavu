package template;

import java.io.IOException;
import java.util.ArrayList;

import Rello.Client;
import Rello.List;
import controllers.ListActionsViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.ListActionsViewLoader;

public class ListActionsView extends ViewLoaderTemplate
{
	Stage stage;
	Client client;
	
	private FXMLLoader loader;
	private BorderPane view;
	private ListActionsViewController cont;
	private ArrayList<List> lists; 
	private int list_idx; 

	public ListActionsView(Stage stage, Client client, ArrayList<List> lists, int list_idx)
	{
		super();
		this.stage = stage;
		this.client = client;
		this.lists = lists;
		this.list_idx = list_idx; 
	}

	@Override
	protected void prepare() throws IOException
	{
    	loader = (new ListActionsViewLoader()).load();
		view = loader.load();
		cont = loader.getController();
	}

	@Override
	protected void setData() throws IOException
	{
		// TODO Auto-generated method stub
		cont.setStage(stage);
		cont.setClient(client);
		cont.setModel(lists, list_idx); // pass in all lists and the current list of this list view
	}

	@Override
	protected void present()
	{
		// TODO Auto-generated method stub
		Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
    	stage.show();
	}

}
