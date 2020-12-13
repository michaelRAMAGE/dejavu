package theming;

import java.io.Serializable;

public class FontFamily extends NodeDecorator  implements Serializable
{

	private static final long serialVersionUID = -1559402175852237267L;
	
	ThemeNode node;
	String css_prop_name = "-fx-font-family"; 
	String value; 
	
	public FontFamily(ThemeNode node, String value)
	{
		super();
		this.node = node;
		this.value = value;
		node_name = "FontFamily";

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
	
