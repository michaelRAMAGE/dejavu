package theming;

import java.io.Serializable;

public abstract class NodeDecorator extends ThemeNode implements Serializable
{

	private static final long serialVersionUID = -8021983211251275454L;

	public abstract String nodeProperty();
}
