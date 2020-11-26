package template.stylesheetmanager;

import javafx.collections.ObservableList;

public class StylesheetParentWrapper implements StylesheetWrapperInterface
{ 
	javafx.scene.Parent s; 
	public StylesheetParentWrapper(Object s)
	{
		this.s = (javafx.scene.Parent) s; 
	}

	public ObservableList<String> getStylesheets() {
		return s.getStylesheets(); 
	}
	
}
