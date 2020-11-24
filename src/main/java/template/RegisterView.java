package template;

import java.io.IOException;

import Rello.Client;
import controllers.RegisterViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.RegisterViewLoader;

public class RegisterView extends ViewLoaderTemplate
{
	private Stage stage;
	private BorderPane view;
	private RegisterViewController cont;
	private Client client; 
	
	public RegisterView(Stage stage, Client client) {
		this.stage = stage;
		this.client = client; 
	}
	
	@Override
	protected void prepare() throws IOException
	{
		// TODO Auto-generated method stub
		FXMLLoader loader = (new RegisterViewLoader()).load(); 
		view = loader.load(); 
		cont = loader.getController(); 
	}

	@Override
	protected void setData()
	{
		cont.setStage(stage); // because stage is static, we do not need to do this (make static on main)?
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
