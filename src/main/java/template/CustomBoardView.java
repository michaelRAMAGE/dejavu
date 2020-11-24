package template;

import java.io.IOException;

import Rello.Board;
import Rello.Client;
import controllers.CustomBoardViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.CustomBoardViewLoader;

public class CustomBoardView extends ViewLoaderTemplate
{
	private Stage stage;
	private BorderPane view;
	private CustomBoardViewController cont;
	private Client client;
	private Board board; 
	
	public CustomBoardView(Stage stage, Client client, Board board) {
		this.stage = stage;
		this.client = client; 
		this.board = board; 
	}
	
	@Override
	protected void prepare() throws IOException
	{
		// TODO Auto-generated method stub
		FXMLLoader loader = (new CustomBoardViewLoader()).load(); 
		view = loader.load(); 
		
		// set custom css for user board styling 
		String path_name = "/home/ming/eclipse-workspace/DejaVuu/src/tempfile.css";
		view.getStylesheets().add(path_name); // tempfile for now 
		
//		view.setStyle(board.getTheme().getNodes().get(".CardNode").nodeProperty());
		
		cont = loader.getController(); 
	}

	@Override
	protected void setData() throws IOException
	{
		cont.setStage(stage); // because stage is static, we do not need to do this (make static on main)?
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
