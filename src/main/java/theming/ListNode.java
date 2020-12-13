package theming;

import java.io.Serializable;

public class ListNode extends ThemeNode implements Serializable
{

	private static final long serialVersionUID = 3394020805476916752L;

	public ListNode() {
		this.node_name = ".ListNode";
	}

	public String nodeProperty() {
		return ""; 
	}
}
