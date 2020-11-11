package Rello;

import java.io.Serializable;

public class Description implements Component, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1113519390734932359L;
	public String description;

	public Description()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Description other = (Description) obj;
		if (description == null)
		{
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

	public Description(String description)
	{
		this.description = description;
	} 
	
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	
}
