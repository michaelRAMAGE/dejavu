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
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import loaders.CardCreateViewLoader;
import loaders.CustomCardEditViewLoader;
import loaders.ListActionsViewLoader;

public class ListViewController {

	public List list;
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
		listTitleTextField.setText(list.getName());
		setIds(list_idx); 
		loadCards(); 
	}
	
	public void setIds(int list_idx) {
		listTitleTextField.setId("listTitleTextField"+Integer.toString(list_idx));
		addNewCardButton.setId("addNewCardButton"+Integer.toString(list_idx));
		editListButton.setId("editListButton"+Integer.toString(list_idx));
		cardContainer.setId("cardContainer"+Integer.toString(list_idx));
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
			
			int card_idx = i; 
			button.setOnAction((ActionEvent event) -> { 
    			BorderPane view;
				try
				{
					
					FXMLLoader loader = (new CustomCardEditViewLoader()).load(); 
					view = loader.load(); 
					CustomCardEditViewController cont = loader.getController();
					Stage popup = createModal(); 
					
					cont.setStage(popup);
					cont.setClient(client);
					cont.setModel(list, list_idx, card_idx);
					
					Scene new_scene = new Scene(view);
					popup.setScene(new_scene);
					popup.show(); 
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
    	
    	Stage popup = createModal();
    	
    	System.out.println(addNewCardButton.getId());
		// Load up card creation view
    	FXMLLoader createCardLoader = (new CardCreateViewLoader()).load();
		BorderPane view = createCardLoader.load();
		CardCreateViewController cont = createCardLoader.getController(); 
		cont.setStage(popup);
		cont.setClient(client);
		cont.setModel(list, list_idx);
    	Scene new_scene = new Scene(view);
    	popup.setScene(new_scene);	// null pointer
    	popup.show();
    }

    // alter this
    @FXML
    void onChangeListTitle(InputMethodEvent event) {
    	
    }

    @FXML
    void onEditList(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = (new ListActionsViewLoader()).load();
		BorderPane view = loader.load();
		
    	Stage popup = createModal(); 

		ListActionsViewController cont = loader.getController();
		cont.setStage(popup);
		cont.setClient(client);
		
		ArrayList<List> lists = client.getUser().getBoard(list.getBoard().getName()).getLists();  
		
		System.out.println();
		cont.setModel(lists, list_idx); // pass in all lists and the current list of this list view
		
    	Scene new_scene = new Scene(view);
    	popup.setScene(new_scene);	
    	popup.show(); 
   }
    
    public Stage createModal() {
    	Stage popup = new Stage();
    	popup.initModality(Modality.APPLICATION_MODAL);
    	popup.initOwner(stage);
    	return popup; 
    }
    
    // Creates a button to be rendered into a view 
    public Button createCardButton(String label, String id, int[] size) { // abstract this creation method 
		Button button = new Button(label);
		button.setId(id);
		button.setPrefSize(size[0], size[1]);
		return button; 
    }

}
