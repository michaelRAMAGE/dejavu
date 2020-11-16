package controllers;

import java.io.IOException;
import java.util.ArrayList;
import Rello.Card;
import Rello.Client;
import Rello.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loaders.CardCreateViewLoader;
import loaders.CustomCardEditViewLoader;
import loaders.ListActionsViewLoader;

public class ListViewController {

	public List list;
	public StringProperty list_name; 
	public Stage stage;
	public Client client; 
	public int list_idx; 
	
	
    @FXML
    private TextField listTitleTextField;

	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setClient(Client client) {
		this.client = client; 
	}
	
	public void setModel(List list, int list_idx) {
		this.list = list; 
		this.list_idx = list_idx; 
		list_name = new SimpleStringProperty(list.getName());
		list_name.bindBidirectional(listTitleTextField.textProperty());
		loadCards(); 
	}
	
    @FXML
    private Button editListButton;

    @FXML
    private Button addNewCardButton;

    @FXML
    private VBox cardContainer;

    
	public void loadCards() {
		ArrayList<Card> cards = this.list.getCards(); 
		for (int i=0; i<cards.size(); i++) { 

			Card card = cards.get(i); 
			// Create buttons for flow pane
			String custom_id = Integer.toString(list_idx) + Integer.toString(i);
			Button button = createCardButton(card.getName(), custom_id, new int[]{300, 26});
			
			button.setOnAction((ActionEvent event) -> { 
    			BorderPane view;
				try
				{
					FXMLLoader loader = (new CustomCardEditViewLoader()).load(); 
					view = loader.load(); 
					CustomCardEditViewController cont = loader.getController();
					cont.setClient(client);
					cont.setStage(stage);
					Scene new_scene = new Scene(view);
					stage.setScene(new_scene);	

				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}); 
			// Add button to flow pane 
			cardContainer.getChildren().add(button);
		}
	}
	


    
    @FXML
    void onAddNewCard(ActionEvent event) throws IOException {
		// Load up card creation view
    	FXMLLoader createCardLoader = (new CardCreateViewLoader()).load();
		BorderPane view = createCardLoader.load();
		CardCreateViewController cont = createCardLoader.getController(); 
		cont.setStage(stage);
		cont.setClient(client);
		cont.setModel(list, list_idx);
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
    	stage.show();
    }

    // alter this
    @FXML
    void onChangeListTitle(InputMethodEvent event) {
    	
    }

    @FXML
    void onEditList(ActionEvent event) throws IOException {
    	FXMLLoader loader = (new ListActionsViewLoader()).load();
		BorderPane view = loader.load();
		ListActionsViewController cont = loader.getController();
		cont.setClient(client);
		cont.setModel(list, list_idx);
    	Scene new_scene = new Scene(view);
    	stage.setScene(new_scene);	
    	stage.show(); 
    }
    
    // Creates a button to be rendered into a view 
    public Button createCardButton(String label, String id, int[] size) { // abstract this creation method 
		Button button = new Button(label);
		button.setId(id);
		button.setPrefSize(size[0], size[1]);
		return button; 
    }

}
