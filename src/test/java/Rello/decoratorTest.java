package Rello;

// This tests decoration and css constructor via decoration
// Theme class and decorator classes are used in here

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import theming.Background;
import theming.CardNode;
import theming.FontSize;
import theming.ListNode;
import theming.TextFill;
import theming.Theme;
import theming.ThemeNode;

class decoratorTest
{

	
	@BeforeEach
	void setUp() throws Exception
	{
	}

	
	@Test
	void testDecoration()
	{
		ThemeNode node = new CardNode(); // we would call this styling on a button 
		node = new FontSize(node, "10"); 
		node  = new Background(node, "blue"); 
		assert(node.nodeProperty().equals("-fx-background-color: blue;-fx-font-size: 10;"));
		
		String css_block = node.getName() + "{" + node.nodeProperty() + "}";
	}
	
	// Tests addNode on theme
	@Test
	void testAddNode()
	{
		// Dummy initialization 
		User dummy_user = new User("dummy_user", "dummy_password");
		Board dummy_board = new Board("dummy_board", dummy_user); 
		Theme dummy_theme = new Theme("dummy_theme");
		
		// Dummy theme setting 
		dummy_board.setTheme(dummy_theme); 

		// Create node instance and get CardNode's name (in this case, its class in css) 
		ThemeNode node = new CardNode(); 
		String node_name = node.getName(); 
		
		// Make sure we have the correct node name 
		assert(node_name.equals(".CardNode"));
		
		// Wrap node in background prop 
		node = new Background(node, "blue"); // node.getName() will now be Background, not .CardNode
		
		// Add this new node to theme
		dummy_board.getTheme().addNode(node_name, node);
		
		// The node was created
		assert(dummy_board.getTheme().getNodes().get(node_name) != null);
		
		// The node is here and has the correct value
		assert(dummy_board.getTheme().getNodes().get(node_name).nodeProperty().equals("-fx-background-color: blue;"));
		
//		System.out.println("nodes: " + dummy_board.getTheme().getNodes()); // just for the eyes to see 
	}
	
	// Tests constructCSS in Theme class
	@Test
	void testConstructCSS()
	{
		// Dummy initialization 
		User dummy_user = new User("dummy_user", "dummy_password");
		Board dummy_board = new Board("dummy_board", dummy_user); 
		Theme dummy_theme = new Theme("dummy_theme");
		
		// Dummy theme setting 
		dummy_board.setTheme(dummy_theme); 

		// Create node instance and get CardNode's name (in this case, its class in css) 
		ThemeNode node = new CardNode(); 
		String node_name = node.getName(); 
		
		// Wrap node in background prop 
		node = new Background(node, "blue"); // node.getName() will now be Background, not .CardNode
		
		// Add this new node to theme
		dummy_board.getTheme().addNode(node_name, node);
		
		// wrap node again 
		node = new TextFill(node, "red");
		dummy_board.getTheme().addNode(node_name, node);
				
		assert(dummy_board.getTheme().constructCSS().equals(".CardNode{-fx-text-fill: red;-fx-background-color: blue;} ")); // extra space matters
		
		// -----------------------------
		
		// Add a new node to dummy theme 
		ThemeNode node2 = new ListNode(); 
		String node_name2 = node2.getName(); 
		
		// Wrap new node in background prop 
		node2 = new Background(node2, "blue"); 
		
		// Add this new node to theme
		dummy_board.getTheme().addNode(node_name2, node2);
		
//		System.out.println(dummy_board.getTheme().constructCSS()); 
		assert(dummy_board.getTheme().constructCSS()
				.equals(".CardNode{-fx-text-fill: red;-fx-background-color: blue;} .ListNode{-fx-background-color: blue;} ")); 

	}
}
