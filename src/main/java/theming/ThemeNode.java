package theming;

public abstract class ThemeNode
{
	String node_name = "default";
	
	public abstract String nodeProperty();
	
	public void setName(String name) {
		this.node_name = name; 
	}
	
	public String getName() {
		return node_name; 
	}
}
