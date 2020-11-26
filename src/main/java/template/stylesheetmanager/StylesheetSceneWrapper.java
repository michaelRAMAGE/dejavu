package template.stylesheetmanager;

import javafx.collections.ObservableList;

public class StylesheetSceneWrapper implements StylesheetWrapperInterface
{ 
	javafx.scene.Scene s; 
	public StylesheetSceneWrapper(Object s)
	{
		this.s = (javafx.scene.Scene) s; 
	}

	public ObservableList<String> getStylesheets() {
		return s.getStylesheets(); 
	}
	
}
