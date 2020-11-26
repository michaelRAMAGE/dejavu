package template;

import java.io.IOException;

import Rello.Client;
import Rello.List;
import controllers.ListViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.ListViewLoader;

public class ListView extends ViewLoaderTemplate
{
	Stage stage;
	Client client; 
	FXMLLoader loader;
	BorderPane listView;
	ListViewController cont;
	private List list;
	private int list_idx;
	private String custom_id; 
	
	public ListView(Client client, Stage stage, List list, int list_idx)
	{
		super();
		this.stage = stage;
		this.client = client;
		this.list = list; 
		this.list_idx = list_idx; 
	}
	
	public ListView(Client client, Stage stage, List list, int list_idx, String custom_id)
	{
		super();
		this.stage = stage;
		this.client = client;
		this.list = list; 
		this.list_idx = list_idx; 
		this.custom_id = custom_id; 
	}

	@Override
	protected void prepare() throws IOException
	{
		loader = (new ListViewLoader()).load(); 
		listView = loader.load();
		listView.setId(custom_id);
		cont = loader.getController(); 
//		listView.getStyleClass().add("ListNode");
	}

	@Override
	protected void setData() throws IOException
	{
		String custom_id = "list" + Integer.toString(list_idx); // assigning an id
		listView.setId(custom_id);
		listView.getStyleClass().add("ListNode");
		cont.setStage(this.stage);
		cont.setModel(list, list_idx);
		cont.setClient(client);
	}

	@Override
	protected void present()
	{
    	Scene new_scene = new Scene(listView);
    	stage.setScene(new_scene);	
    	stage.show();
	}

	public Stage getStage()
	{
		return stage;
	}

	public void setStage(Stage stage)
	{
		this.stage = stage;
	}

	public BorderPane getListView()
	{
		return listView;
	}

	public void setListView(BorderPane listView)
	{
		this.listView = listView;
	}

}
