// Generating a new view should dodge a view specific cache, right?
// In other words, is the cache on view instance or on Parent?
//			view.setCache(false);


/*** 
 	The new temp file cannot be found in path unless reloaded./
	The file must exist in the initial compilation to be recognized.
	
	What we see is that, the styling is never applied because the the stylesheet add 
	occurs on files that did not exist before the initial build. 
	
	When we add basetheme.css on the very first start, basetheme is applied because it 
	existed during the build, but if we create the file when user requests theme change
	and then load the view and add the new file to view, it is not in the initial build,
	so it will not be loaded.
	
	String url_rsc_str = "../css/" + base_file_name;
	URL class_based_path = CustomBoardViewController.class.getResource(url_rsc_str);
	assert(class_based_path != null); 
**/	
package template;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.sun.javafx.css.StyleManager;

import Rello.Board;
import Rello.Client;
import controllers.CustomBoardViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loaders.CustomBoardViewLoader;
import template.stylesheetmanager.StylesheetManager;

// when stylesheet manager not set in first construct, we get a live theme switch.

public class CustomBoardView extends ViewLoaderTemplate
{
	// WE can load css from view or scene. Set as needed.	
	private boolean run_view_css_loader = true;
	private boolean run_scene_css_loader = false;
	
	// Some important instance vars
	private Stage stage;
	private BorderPane view;
	private CustomBoardViewController cont;
	private Client client;
	private Board board; 
	private static StylesheetManager stylesheetManager; 
	
	// Constructor -- uses default css file
	public CustomBoardView(Stage stage, Client client, Board board) {
		this.stage = stage;
		this.client = client; 
		this.board = board; 
		if (stylesheetManager == null) {
			stylesheetManager = new StylesheetManager(); 
		}
	}
	
	// Constructor -- receives a temp css file 
	public CustomBoardView(Stage stage, Client client, Board board, File temp_css) {
		this.stage = stage;
		this.client = client; 
		this.board = board; 
		stylesheetManager = new StylesheetManager(temp_css);
	}
	
	// Constructor -- accepts stylesheet manager
	public CustomBoardView(Stage stage, Client client, Board board, StylesheetManager in_stylesheetManager) {
		this.stage = stage;
		this.client = client; 
		this.board = board; 
		stylesheetManager = in_stylesheetManager; 
	}
	
	@Override
	protected void prepare() throws IOException
	{
		FXMLLoader loader = (new CustomBoardViewLoader()).load(); 
		view = loader.load(); 
		
		if (run_view_css_loader) {
			stylesheetManager.loadCSS(view);	
		}
		
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
	protected void present() throws FileNotFoundException
	{
		Scene new_scene = new Scene(view);
	
		if (run_scene_css_loader == true) {
			stylesheetManager.loadCSS(new_scene);	
		}
		stage.setScene(new_scene);
		stage.show(); 
	}


	
}
