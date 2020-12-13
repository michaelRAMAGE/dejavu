package theming;

import java.io.Serializable;

public class TextFill extends NodeDecorator implements Serializable
{

	private static final long serialVersionUID = 2935949285925626109L;
	
	ThemeNode node;
	String css_prop_name = "-fx-text-fill"; 
	String value; 
	
	public TextFill(ThemeNode node, String value)
	{
		super();
		this.node = node;
		this.value = value; 
		node_name = "TextFill";
	}

	@Override
	public String nodeProperty()
	{
		if (value.equals(null) || (node == null)) {
			return ""; 
		}
		return css_prop_name + ": " + value + ";" + node.nodeProperty();
	}
	
}
