package theming;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class Theme implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Theme()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	static ArrayList<String> node_name_index = new ArrayList<String>(Arrays.asList("CardNode","ListNode"));
	
	static ArrayList<String> property_name_index = new ArrayList<String>(Arrays.asList("FontSize","FontFamily",
			"Background", "TextFill")); 
	
	static public ArrayList<String> getNode_name_index()
	{
		return node_name_index;
	}

	public void setNode_name_index(ArrayList<String> node_name_index)
	{
		this.node_name_index = node_name_index;
	}

	static public ArrayList<String> getProperty_name_index()
	{
		return property_name_index;
	}

	public void setProperty_name_index(ArrayList<String> property_name_index)
	{
		this.property_name_index = property_name_index;
	}

	String name; 
	HashMap<String, ThemeNode> nodes;
	
	
	public Theme(String name)
	{
		super();
		this.name = name;
		this.nodes = new HashMap<String, ThemeNode>(); 
		
		
//		node_name_index = new ArrayList<String>();
//		node_name_index.add("CardNode");
//		node_name_index.add("ListNode");
//		property_name_index = new ArrayList<String>();
//		
//		property_name_index.add("FontSize");
//		property_name_index.add("FontFamily");
//		property_name_index.add("Background");
//		property_name_index.add("TextFill");
	}
	
	public Theme(String name, HashMap<String, ThemeNode> nodes)
	{
		super();
		this.name = name;
		this.nodes = nodes;
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public HashMap<String, ThemeNode> getNodes()
	{
		return nodes;
	}
	
	public void setNodes(HashMap<String, ThemeNode> nodes)
	{
		this.nodes = nodes;
	}
	
	public ThemeNode addNode(String node_name, ThemeNode node) {
		return nodes.put(node_name, node);
	}
	
	
	public ThemeNode deleteNode(String node_name) {
		return nodes.remove(node_name);
	}
	
	
	// A css block is one block in a css file (one ThemeNode would be one block)
	public String constructCSSBlock(String node_name, ThemeNode node) {
		return node_name + "{" + node.nodeProperty() + "}";
	}
	
	// Constructs a theme from the existing nodes in nodes attribute
    public String constructCSS() {
    	HashMap<String, ThemeNode> nodes = this.getNodes();
    	String css = ""; // we will iterate over theme nodes and attach css blocks 
    	for (String elem : nodes.keySet()) {
    		css += constructCSSBlock(elem, nodes.get(elem)); 
    		css += " ";
    	}
    	return css; 
    }
    
    public void diffProps(String in_node_name, ThemeNode in_node) {
    	if (nodes.get(in_node_name) == null) { // node does not exist
    		return;
    	}
    	else {
    		String diffed_string = in_node_name + "{"; 
    		// existing properties on the node with this name
    		String[] str_css = nodes.get(in_node_name).nodeProperty().split(";");
    		List<String> css = new ArrayList<String>();
    		css = Arrays.asList(str_css);
    		
    		// properties on incoming object 
    		String[] str_in_css = in_node.nodeProperty().split(";");
    		List<String> in_css = new ArrayList<String>();
    		in_css = Arrays.asList(str_in_css);
    		
    		// Take everything props has that in_props does not have and put on in_props
    		for (String str : css) {
    			
    			String[] prop_pair = str.split(":");
    			
    			String prop_name = prop_pair[0];
    			String prop_value = prop_pair[1];
    			
    			for (String in_str : in_css) {
    				String[] in_prop_pair = in_str.split(":");
    				if (prop_name.equals(in_prop_pair[0])) { // take the newest value
    					diffed_string += prop_name + ": " + in_prop_pair[1] + ";";

    				}
    				else {
    					diffed_string += prop_name + ": " + in_prop_pair[1] + ";";
    				}
    			}
    			
    			// is it in the in_css? if not, append it
    			System.out.println("pair...");
    			System.out.println(prop_name);
    			System.out.println(prop_value);
    		
    			
    		
    			
    		}
    		
    	}
    }
 }
