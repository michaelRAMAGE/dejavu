package commands;

import java.io.IOException;
import template.ViewLoaderTemplate;

public class UpdateBoardCommand implements CommandInterface {
	public ViewLoaderTemplate view; 

	public UpdateBoardCommand(ViewLoaderTemplate view) {
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
