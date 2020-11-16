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
import javafx.stage.Stage;
import loaders.BoardCreateViewLoader;
import loaders.BoardListViewLoader;
import loaders.CustomBoardViewLoader;
import loaders.LoginViewLoader;
import loaders.ServerViewLoader;

public class BoardListViewController {
	
	public Client client; 
	public Stage stage; 
	User user; 
	
	// Both the client and user play apart as models
	public void setClient(Client in_client) {
		this.client = in_client; 
    	this.user = client.getUser(); 
		renderBoardList(); 
	}
	
	public void setStage(Stage stage) {
		this.stage = stage; 
	}
		
    @FXML
    private FlowPane boardListFlowPane;

    @FXML
    private Button addBoard;

    @FXML
    private Button logoutUser;

    @FXML
    void onAddBoard(ActionEvent event) throws IOException {
		// Initialize 
		FXMLLoader loader = (new BoardCreateViewLoader()).load(); 
		BorderPane view = loader.load();
		BoardCreateViewController cont = loader.getController(); 
		cont.setClient(client);
		cont.setStage(stage);
		Scene new_scene = new Scene(view);
		stage.setScene(new_scene);
		stage.show();
    }


    @FXML
    void onLogoutUser(ActionEvent event) throws IOException {
		FXMLLoader loader = (new LoginViewLoader()).load(); 
		BorderPane view = loader.load();
		LoginViewController cont = loader.getController(); 
		cont.setStage(stage);
		Client new_client = new Client(this.client.host, this.client.lookup_name);
		cont.setClient(new_client);
		Scene new_scene = new Scene(view);
		stage.setScene(new_scene);
		stage.show();
    }
    
    // Renders the flow pane for the BoardListView
    private void renderBoardList() {
    	HashMap<String, Board> user_boards = user.getBoards();
    	
    	for (String bname : user_boards.keySet()) {
    		// Get board info 
    		Board curr_board = user_boards.get(bname);
    		String board_id = curr_board.boardID; 
    		
    		// Call the replaceAll() method 
//            board_id = board_id.replaceAll("\\s", ""); 
    		// Create buttons for flow pane
    		Button button = createBoardListButton(bname, board_id, new int[]{80, 80});
    		button.setOnAction((ActionEvent event) -> { 
    			System.out.println("clicked");
    			FXMLLoader loader = (new CustomBoardViewLoader()).load();
    			BorderPane view;
				try
				{
					view = loader.load();
					CustomBoardViewController cont = loader.getController(); 
	    			cont.setStage(stage);
	    			cont.setClient(client);

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