package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Rello.Board;
import Rello.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import template.CustomBoardView;
import template.stylesheetmanager.StylesheetManager;
import theming.Background;
import theming.CardNode;
import theming.FontFamily;
import theming.FontSize;
import theming.ListNode;
import theming.TextFill;
import theming.Theme;
import theming.ThemeNode;

public class boardCSSController implements Initializable{

	// welp now we coupled here too ):
	Board board; 
	private Client client;
	private Stage stage;
	
    @FXML
    private ListView<String> ListViewPane;
	ObservableList<String> modificationsmssg = FXCollections.observableArrayList();
	
    @FXML
    private Button addChangeButton;
	
    @FXML
    void onAddChange(ActionEvent event) {
    	String node_selected = nodeChoiceBox1.getValue();
    	String prop_selected = propertyChoiceBox.getValue();
    	String prop_value_entered = propertyValue.getText(); 
    	
    	// Do nothing if submitted and null (ADD ERROR MESSAGE LATER)
    	if (node_selected == null || prop_selected == null || prop_value_entered.equals("")) {
    		return; 
    	}
    	
		ThemeNode node = null;
    
		// REFACTOR INTO A FACTOR LATER ON 
		// LATER ALLOW FOR MULTIPLE SELECTION AT TIME 
		// LATER REDUCE COUPLING AND REFACTOR FOR GENERAL USAGE
		
    	// Create node (these two are classes, we switch them on in customboardviewcont and listviewcont)
    	if (node_selected.equals("CardNode")) {
    			node = new CardNode();

    	}
    	else if (node_selected.equals("ListNode")) {
        		node = new ListNode();
    	}
    	else {
    	}	
    
    	// Style node 
		if (prop_selected.equals("TextFill")) {
    		node = new TextFill(node, prop_value_entered);
		}
		else if (prop_selected.equals("Background")) {
    		node  = new Background(node, prop_value_entered);
		}
		
		else if (prop_selected.equals("FontFamily")) {
			node  = new FontFamily(node, prop_value_entered);
		}
		
		else if (prop_selected.equals("FontSize")) {
			node  = new FontSize(node, prop_value_entered);

		}
		else { }
	
		// If node still null, just do nothing 
		// Informal validation
		if (node == null) {
			return; 
		}
		
		board.getTheme().addNode("." + node_selected, node);
		
    	// load custom board view, custom board view should load new changes (writeToCSS --> read new written)
    	client.getUser().getBoard(board.getName()).setTheme(board.getTheme());
    	
        	
    	modificationsmssg.add("Put '" + prop_selected + "' with value '" + prop_value_entered + "' on node '" + node_selected + "'");
    	propertyValue.clear();

    }
	

    @FXML
    private ChoiceBox<String> nodeChoiceBox1;

    @FXML
    private ChoiceBox<String> propertyChoiceBox;

    @FXML
    private Button onSubmitButton;

    @FXML
    private TextField propertyValue;

    public void setModel(Board board) {
    	this.board = board; 
    	
    	ListViewPane.setItems(modificationsmssg); // set the prop
		
		// clean this up later
		ObservableList<String> obs_prop_name_index = FXCollections.observableArrayList();
		for (String key : Theme.getProperty_name_index()) {
			obs_prop_name_index.add(key);
		}

		
		ObservableList<String> obs_node_name_index = FXCollections.observableArrayList();
		for (String key : Theme.getNode_name_index()) {
			obs_node_name_index.add(key);
		}
		
		propertyChoiceBox.setItems(obs_prop_name_index);
		nodeChoiceBox1.setItems(obs_node_name_index);
    }
    
    public void setClient(Client client) {
    	this.client = client; 
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage; 
    }
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	} 
    
    @FXML
    void onSubmit(ActionEvent event) throws IOException {
    	System.out.println("Custom board view wants its css changed!");
    	System.out.println("Nodes existing: " + board.getTheme().getNodes());
		
		// close popup, return to main stage 
    	Stage main_stage = (Stage) stage.getOwner();
    	stage.hide();
		
    	// Load new styling on customboardview 
    	String path_name = "/home/ming/eclipse-workspace/DejaVuu/src/main/java/views/tempfile.css";
    	File temp_file = writeCSSFile(path_name, board.getTheme().constructCSS());
    	StylesheetManager stylesheetManager = new StylesheetManager(temp_file); 
		new CustomBoardView(main_stage, client, board, stylesheetManager).load();
    } 
    
    public File writeCSSFile(String full_read_path, String css) throws IOException {
    	File actual_file = new File(full_read_path);
    	System.out.println(actual_file.getPath());
    	if (actual_file.exists() && actual_file.isFile()) {
			System.out.println("file exists");
    		actual_file.delete();
    	}
    	actual_file.createNewFile();
    	BufferedWriter bw = new BufferedWriter(new FileWriter(actual_file));
    	bw.write(css);
    	bw.close(); 
    	return actual_file; 
    }
}
