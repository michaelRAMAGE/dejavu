package template;

import java.io.IOException;

public abstract class ViewLoaderTemplate
{
	public void load () throws IOException  {
		prepare();
		setData(); 
		present(); 
	}
	
	protected abstract void prepare() throws IOException;

	protected abstract void setData() throws IOException;
	
	protected abstract void present(); // this will be same for all but requires view and those differ
		
}
