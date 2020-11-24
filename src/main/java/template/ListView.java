package template;

import java.io.IOException;

import Rello.Client;
import Rello.List;
import controllers.ListViewController;
import javafx.fxml.FXMLLoader;
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
	
	
	public ListView(Client client, Stage stage, List list, int list_idx)
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
		loader = (new ListViewLoader()).load(); 
		listView = loader.load();
//		listView.getStylesheets().add("views/tempfile.css"); // tempfile for now 

		System.out.println("list view: " + listView.getStyleClass()); 
		System.out.println("list view: " + listView.getStyle()); 
		
//		listView.getStyleClass().add(".ListNode"); // add ListNode class for styling
		
		cont = loader.getController(); 
	}

	@Override
	protected void setData() throws IOException
	{
		String custom_id = "list" + Integer.toString(list_idx); // assigning an id
		listView.setId(custom_id);
		
		cont.setStage(this.stage);
		cont.setModel(list, list_idx);
		cont.setClient(client);
	}

	@Override
	protected void present()
	{
		// TODO Auto-generated method stub

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
