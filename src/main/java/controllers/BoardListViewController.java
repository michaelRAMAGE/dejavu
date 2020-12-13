package controllers;

import java.io.IOException;
import java.util.HashMap;

import Rello.Board;
import Rello.Client;
import Rello.Server;
import Rello.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loaders.BoardCreateViewLoader;
import loaders.BoardListViewLoader;
import loaders.CustomBoardViewLoader;
import loaders.LoginViewLoader;
import loaders.RemoveBoardViewLoader;
import loaders.ServerViewLoader;
import template.BoardCreateView;
import template.CustomBoardView;
import template.LoginView;
import template.RemoveBoardView;

public class BoardListViewController {
	
	public Client client; 
	public Stage stage; 
	User user; 
	
	// Both the client and user play apart as models
	public void setClient(Client in_client) {
		this.client = in_client; 
    	this.user = client.getUser(); 
    	System.out.println(this.user.getBoard("Team Jim").getLists());
		renderBoardList(); 
	}
	
	public void setStage(Stage stage) {
		this.stage = stage; 
	}
		
    @FXML
    private FlowPane boardListFlowPane;

    @FXML
    private Button removeBoardButton;
    
    @FXML
    private Button addBoard;

    @FXML
    private Button logoutUser;

    @FXML
    void onAddBoard(ActionEvent event) throws IOException {
		(new BoardCreateView(stage, client)).load(); 
    }
    
    @FXML
    void onRemoveBoard(ActionEvent event) throws IOException {
    	Stage popup = createModal(); 
		(new RemoveBoardView(popup, client, user.getBoards())).load(); 
    }
    
    
    public Stage createModal() {
    	Stage popup = new Stage();
    	popup.initModality(Modality.APPLICATION_MODAL);
    	popup.initOwner(this.stage);
    	return popup; 
    }
    
    @FXML
    void onLogoutUser(ActionEvent event) throws IOException {
    	System.out.println("Logging out: " + client.getUser().getBoard("Team Jim").getLists());
		Client new_client = client.logoutUser(this.client.host, this.client.lookup_name);
		(new LoginView(stage, new_client)).load(); 
    }
    
    // Renders the flow pane for the BoardListView
    private void renderBoardList() {
    	HashMap<String, Board> user_boards = user.getBoards();
    	
    	for (String bname : user_boards.keySet()) {
    		
    		// Get board info 
    		Board curr_board = user_boards.get(bname);
    		String board_id = curr_board.boardID; 

    		// Create buttons for flow pane
    		Button button = createBoardListButton(bname, board_id, new int[]{80, 80});
    		button.setOnAction((ActionEvent event) -> { 
    			try
				{
					(new CustomBoardView(stage, client, curr_board)).load();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}); 
    		// Add button to flow pane 
    		boardListFlowPane.getChildren().add(button); 
    	}
    }
    
    // Creates a button to be rendered into a view 
    public Button createBoardListButton(String label, String id, int[] size) {
		Button button = new Button(label);
		button.setId(id);
		button.setPrefSize(size[0], size[1]);
		return button; 
    }

	public void setModel()
	{
		// TODO Auto-generated method stub
		
	}
}