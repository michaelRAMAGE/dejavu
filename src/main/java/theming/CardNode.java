package theming;

public class CardNode extends ThemeNode
{
	public CardNode() {
		setName(".CardNode");
	}
	
	public String getName() {
		return node_name; 
	}

	public String nodeProperty()
	{
		return "";
	}
}
