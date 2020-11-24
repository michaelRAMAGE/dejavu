package theming;

public class FontSize extends NodeDecorator
{
	ThemeNode node;
	String css_prop_name = "-fx-font-size"; 
	String value; 
	
	public FontSize(ThemeNode node, String value)
	{
		super();
		this.node = node;
		this.value = value; 
		node_name = "FontSize";

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
