package theming;

import java.io.Serializable;

public class Background extends NodeDecorator implements Serializable
{
	private static final long serialVersionUID = -7825974004124140574L;
	
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
