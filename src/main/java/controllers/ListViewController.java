package controllers;

import java.io.IOException;
import java.util.ArrayList;
import Rello.Card;
import Rello.Client;
import Rello.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import template.CardCreateView;
import template.CustomCardEditView;
import template.ListActionsView;

public class ListViewController {

	public Stage stage; 
	public Client client; 
	public List list;
	public int list_idx; 
	
	
    @FXML
    private TextField listTitleTextField;

    @FXML
    private BorderPane listRoot;    
    
    @FXML
    private Button editListButton;

    @FXML
    private Button addNewCardButton;

    @FXML
    private VBox cardContainer;
    
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
	
	public void loadCards() {
		ArrayList<Card> cards = this.list.getCards(); 
		for (int i=0; i<cards.size(); i++) { 

			Card card = cards.get(i); 
			// Create buttons for flow pane
			String custom_id = Integer.toString(list_idx) + Integer.toString(i);
			Button button = createCardButton(card.getName(), custom_id, new int[]{300, 26});
			int card_idx = i; 
		
			button.getStyleClass().add("CardNode"); // add a styling for card nodes
			
//			listRoot.getStyleClass().add("ListNode"); // this does not work 
//			
//			cardContainer.getStyleClass().add("ListNode"); // this works
			
			button.setOnAction((ActionEvent event) -> { 
				try
				{
					Stage popup = createModal(); 
					new CustomCardEditView(popup, client, list, card_idx, card_idx).load(); 
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}); 
			
			// Add button to flow pane 
//			if (list.getBoard().getTheme().getNodes().get(".CardNode") != null) {
//				button.setStyle(list.getBoard().getTheme().getNodes().get(".CardNode").nodeProperty());
//			}

			cardContainer.getChildren().add(button);
		}
	}
	
    @FXML
    void onAddNewCard(ActionEvent event) throws IOException {
    	Stage popup = createModal();
		new CardCreateView(popup, client, list, list_idx).load();
    }

    // alter this
    @FXML
    void onChangeListTitle(InputMethodEvent event) {
    	
    }

    @FXML
    void onEditList(ActionEvent event) throws IOException {
    	Stage popup = createModal(); 		
		ArrayList<List> lists = client.getUser().getBoard(list.getBoard().getName()).getLists();  
		new ListActionsView(popup, client, lists, list_idx).load(); 
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
