package Rello;

import java.io.Serializable;

public class Checklist implements Component, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6055482212057952479L;
	public boolean checked;

	public Checklist()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Checklist(boolean checked)
	{
		this.checked = checked;
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
	
}
