package theming;

import java.io.Serializable;

public class CardNode extends ThemeNode  implements Serializable
{

	private static final long serialVersionUID = 471322423734314348L;

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
