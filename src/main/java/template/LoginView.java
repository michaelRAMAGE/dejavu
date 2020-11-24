package template;

import java.io.IOException;

import Rello.Client;
import controllers.LoginViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.LoginViewLoader;

public class LoginView extends ViewLoaderTemplate
{
	private Stage stage;
	private BorderPane view;
	private LoginViewController cont;
	private Client client; 
	
	public LoginView(Stage stage, Client client) {
		this.stage = stage;
		this.client = client; 
	}
	
	@Override
	protected void prepare() throws IOException
	{
		// TODO Auto-generated method stub
		FXMLLoader loader = (new LoginViewLoader()).load(); 
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
	}

}
