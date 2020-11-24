package theming;

public class Background extends NodeDecorator
{
	ThemeNode node;
	private String css_prop_name = "-fx-background-color";
	private String value;

	public Background(ThemeNode node, String value)
	{
		super();
		this.node = node;
		this.value = value; 
		node_name = "Background";

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
