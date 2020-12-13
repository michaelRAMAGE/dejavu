package commands;

import java.io.IOException;
import template.ViewLoaderTemplate;

public class RemoveBoardCommand implements CommandInterface {
	public ViewLoaderTemplate view; 

	public RemoveBoardCommand(ViewLoaderTemplate view) {
		this.view = view; 
	}
	
	@Override
	public void execute() {
		try {
			view.load(); // load the new view
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
