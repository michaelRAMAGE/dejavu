package controllers;
import java.io.IOException;
import java.util.ArrayList;

import Rello.Board;
import Rello.Card;
import Rello.Client;
import Rello.List;
import Rello.Server;
import Rello.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import loaders.BoardCreateViewLoader;
import loaders.BoardListViewLoader;
import loaders.ListCreateViewLoader;
import loaders.ListViewLoader;
import loaders.SaveChangesViewLoader;
import loaders.ServerViewLoader;

public class CustomBoardViewController
{

	public Board board;
	public Stage stage; 
	public Client client; 

	
    @FXML
    private Button addListButton;

    @FXML
    private Button exitApplicationButton;

    @FXML
    private Button goBackToBoardListButton;

    @FXML
    private TextField boardTitleTextField;

    @FXML
    private Button addMembersButton;

    
    @FXML
    private Button save;
    
    
    @FXML
    private HBox listViewStorageContainer;
	public StringProperty bname;
    
    
    @FXML
    void onAddList(ActionEvent event) throws IOException {
    	FXMLLoader loader = (new ListCreateViewLoader()).load();
    	BorderPane view = loader.load(); 
    	ListCreateViewController cont = loader.getController(); 
    	cont.setClient(client);
		cont.setModel(board); 
    	cont.setStage(stage);
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);
    }

    // not going to be implemented in sprint 4
    @FXML
    void onAddMembers(ActionEvent event) {
    }

    @FXML
    void onSave(ActionEvent event) {
    	String new_name = boardTitleTextField.getText(); 
//		System.out.println("Before: " + client.getUser().getBoard(bname.get()).getName()); 
//    	boardTitleTextField.setText(board.getName());
		
		client.getUser().getBoard(board.getName()).setName(new_name);
		System.out.println("After: " + client.getUser().getBoard(new_name).getName()); 
    }
    
    @FXML
    void onExitApplication(ActionEvent event) throws IOException {
		FXMLLoader loader = (new ServerViewLoader()).load(); 
		BorderPane view = loader.load();
		ServerViewController cont = loader.getController(); 
		cont.setStage(stage);
		cont.setModel(Server.getInstance());
		Scene new_scene = new Scene(view);
		stage.setScene(new_scene);
		stage.show();
    }

    @FXML
    void onGoBackToBoardList(ActionEvent event) throws IOException {
    	// return to previous page
    	FXMLLoader loader = (new BoardListViewLoader()).load(); 
    	BorderPane view = loader.load(); 
    	BoardListViewController cont = loader.getController(); 
    	cont.setClient(client);
    	cont.setStage(stage);
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
    }

    public void setStage(Stage stage) { 
    	this.stage = stage;
    }
    
    public void setClient(Client client) {
    	this.client = client; 
    }
    
    public void setModel(Board board) throws IOException {
    	this.board = board; 
    	bname = new SimpleStringProperty(); 
		bname.bindBidirectional(boardTitleTextField.textProperty());
		loadAllListViews(); 
    }


	public void loadAllListViews() throws IOException {
		ArrayList<List> lists = this.board.getLists();
		for (int i=0; i<lists.size(); i++) {
			List list = lists.get(i);
			BorderPane listview = createListView(list, i);
			addListViewToContainer(listview); 
		}
	}

	public void addListViewToContainer(BorderPane listView) { 
		int insert_idx = listViewStorageContainer.getChildren().indexOf(addMembersButton);
		if (insert_idx != 0) { insert_idx = insert_idx - 1; };  
		listViewStorageContainer.getChildren().add(listView); 
	}
	
	public BorderPane createListView(List list, int list_idx) throws IOException { 
		System.out.println("creating list");
		FXMLLoader loader = (new ListViewLoader()).load(); 
		BorderPane listView = loader.load();
		String custom_id = "list"+Integer.toString(list_idx);
		listView.setId(custom_id);
		ListViewController cont = loader.getController(); 
		cont.setModel(list, list_idx);
		return listView; 
	}	
}
	

