package theming;

import java.io.Serializable;

public abstract class ThemeNode implements Serializable
{

	private static final long serialVersionUID = 2729073178918416530L;
	
	String node_name = "default";
	
	// FOR JAVA BEANS 
	public ThemeNode()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getNode_name()
	{
		return node_name;
	}


	public void setNode_name(String node_name)
	{
		this.node_name = node_name;
	}


	public abstract String nodeProperty();
	
	public void setName(String name) {
		this.node_name = name; 
	}
	
	public String getName() {
		return node_name; 
	}
}
