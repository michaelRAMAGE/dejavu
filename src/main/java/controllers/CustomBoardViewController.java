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
import javafx.stage.Modality;
import javafx.stage.Stage;
import loaders.BoardCreateViewLoader;
import loaders.BoardListViewLoader;
import loaders.ListCreateViewLoader;
import loaders.ListViewLoader;
import loaders.SaveChangesViewLoader;
import loaders.ServerViewLoader;
import template.CustomBoardView;
import template.ListCreateView;
import template.ListView;
import template.boardCSSView;

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
    private Button styleBoardButton;

    
    
    @FXML
    private HBox listViewStorageContainer;
	public StringProperty bname;
    
	
	
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
    public Stage createModal() {
    	Stage popup = new Stage();
    	popup.initModality(Modality.APPLICATION_MODAL);
    	popup.initOwner(stage);
    	return popup; 
    }
    
    
    @FXML
    void onStyleBoard(ActionEvent event) throws IOException {
    	Stage popup = createModal(); 
		new boardCSSView(popup, client, board).load();
    }
    
    
    @FXML
    void onAddList(ActionEvent event) throws IOException {
    	new ListCreateView(stage, client, board).load();
    }

    // not going to be implemented in sprint 4
    @FXML
    void onAddMembers(ActionEvent event) {
    }

    @FXML
    void onSave(ActionEvent event) {
    	String new_name = boardTitleTextField.getText(); 		
		client.getUser().getBoard(board.getName()).setName(new_name);
    }
    
    // deprecated. user must exit board to board list and then can exit application if desired 
    @FXML
    void onExitApplication(ActionEvent event) throws IOException {
    }

    @FXML
    void onGoBackToBoardList(ActionEvent event) throws IOException {
    	SaveChangesViewTransition saveChangesView = new SaveChangesViewTransition(stage, client, board); 
    	saveChangesView.showView();
    }

	public void loadAllListViews() throws IOException {
		ArrayList<List> lists = this.board.getLists();
		for (int i=0; i<lists.size(); i++) {
			List list = lists.get(i);
			BorderPane listview = createListView(list, i);
			
			// strapping styling in
			if (board.getTheme().getNodes().get(".ListNode") != null) {
				listview.setStyle(list.getBoard().getTheme().getNodes().get(".ListNode").nodeProperty());
			}
			
			addListViewToContainer(listview); 
		}
	}

	public void addListViewToContainer(BorderPane listView) { 
		int insert_idx = listViewStorageContainer.getChildren().indexOf(addMembersButton);
		if (insert_idx != 0) { insert_idx = insert_idx - 1; }
		listViewStorageContainer.getChildren().add(listView); // do we use show on all children()?????
	}
	
	public BorderPane createListView(List list, int list_idx) throws IOException { 
		ListView listView = new ListView(client, stage, list, list_idx); 
		listView.load(); 
		return listView.getListView(); 
	}	
}
	

