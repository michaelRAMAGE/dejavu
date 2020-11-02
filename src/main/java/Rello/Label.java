package Rello;

import java.io.Serializable;
import java.util.EnumMap;

public class Label implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2865056290087922670L;
	public Colors color;
	public String text;
	
	public Label()
	{
		super();
	}

	public Label(Colors color, String text) {
		this.color = color;
		this.text = text; 
	}
	

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		Label other = (Label) obj;
		if (color != other.color) return false;
		if (text == null)
		{
			if (other.text != null)
				return false;
		} 
		else if (!text.equals(other.text)) {
			return false;
		}
		return true;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}
	
	public void changeColor(EnumMap<Colors, Label> current_map, Colors new_color) {
		if (current_map.containsKey(new_color)) {
			current_map.remove(new_color); 
		}
	}

	public Colors getColor()
	{
		return color;
	}

	public void setColor(Colors color)
	{
		this.color = color;
	}


	

	
	
}
