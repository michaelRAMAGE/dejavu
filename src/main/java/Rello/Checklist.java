package Rello;

public class Checklist implements Component
{
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
