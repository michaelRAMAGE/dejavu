package template;

import java.io.FileNotFoundException;
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
	
	protected abstract void present() throws FileNotFoundException; // this will be same for all but requires view and those differ
		
}
