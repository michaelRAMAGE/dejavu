package template.stylesheetmanager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import com.sun.javafx.css.StyleManager;

/**
 * This class handles loading stylesheets on the object passed in to loadCSS.
 * There is extendable support to new object classes via implementing StylesheetWrapperInterface.
 * If you want getStyleSheets() to be used on object, do this. 
 */
public class StylesheetManager
{
	
	// File class reads path different compared to getStylesheets.add(path) on other classes
	private String default_css_file = getCSSFiles().get("basetheme");
	private String css_path = "views/";
	private String read_path = "/home/ming/eclipse-workspace/DejaVuu/src/main/java/views/";

	// Keep track of all css files created during runtime changes 
	private static HashMap<String, String> css_files = cssMap();
	
	// CSS file to add to stylesheet and to read from 
	private File temp_css; 
	
	/** This constructor is called when default css should be used.
		(Look at the constructor's in CustomBoardView.java) 
	**/
	public StylesheetManager()
	{
		super();
		o("boardCSSView DID NOT pass file to CustomBoardView (Temporary css == null)");

		// Append default file to default path 
		this.css_path += default_css_file;
		this.read_path += default_css_file;  
		
		// Output 
		o("Filename: " + default_css_file);
	}
	
	
	/** This constructor is called when the css stylesheet needs to updated,
	  	re-applied. temp_css being set means that the view css style needs to be
	  	changed, which is communicated via controllers. 
	  	(Look at the constructor's in CustomBoardView.java) 
	**/
	public StylesheetManager(File temp_css)
	{
		o("boardCSSView DID pass file to CustomBoardView (Temporary css != null)");
		
		// Set new css style file 
		this.temp_css = temp_css; 
		
		// Deprecated name path look up scheme!
//		String file_name = temp_css.getName();		
//		css_path += file_name;
//		read_path += file_name;
			
		// New path lookup scheme
		this.css_path = temp_css.toURI().toString();
		this.read_path = temp_css.toURI().toString();

		// Output 
//		o("Filename: " + file_name);
//		String nickname = file_name.split("\\.")[0];
//		addCSSFile(nickname, file_name);
	}

	private static HashMap<String, String> cssMap() {
		HashMap<String, String> css_files = new HashMap<String, String>();
		css_files.put("basetheme", "basetheme.css");
		return css_files; 
	}
	
	public static void addCSSFile(String nickname, String filename) {
		System.out.println("Adding new css file to map (key: " + nickname + ", value: " + filename + ")");
		css_files.put(nickname, filename);
	}
	
	public static HashMap<String, String> getCSSFiles()
	{
		return css_files;
	}
	
	public void loadCSS(Object s) throws FileNotFoundException {
		// The class that we are loading css on 
		Class load_on = s.getClass(); 
		
//		// Read css file to ensure it was created
//		readFile(read_path);
		
		// Do stylesheet work
		stylesheetManager(load_on, s, css_path); 
	}
	
	/**
	 * Stylesheet manager calls a worker corresponding to setting style on 
	   javafx.scene.Parent || javafx.scene.Scene
	 * @param s
	 * @param full_css_path
	 */
	private void stylesheetManager(Class load_on, Object s, String full_css_path) {
		StylesheetWrapperInterface swrapped;
		try {
			// STYLESHEET GETSTYLESHEET FACTORY
			if (javafx.scene.Parent.class.isAssignableFrom(load_on)) {
				swrapped = new StylesheetParentWrapper(s);	
			}
			else if (javafx.scene.Scene.class.isAssignableFrom(load_on)) {
				swrapped = new StylesheetSceneWrapper(s);	
			}
			else {
				String exception_mssg = load_on.toString() + " is not yet supported by StylesheetWrapperInterface "
						+ "and is not in "
						+ "stylesheetManager factory method";
				throw new StylesheetException(exception_mssg);
			}
			// CALL WORKER METHOD ON WRAPPED STYLESHEET OBJECT
			stylesheetWorker(swrapped, full_css_path);
		}
		catch (StylesheetException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Loads css on objects wrapped with StylesheetWrapperInterface
	 * @param s - a wrapped object that is derived from StylesheetWrapperInterface
	 * @param full_css_path
	 */
	private void stylesheetWorker(StylesheetWrapperInterface s, String full_css_path) {
		o("Loading css from " + full_css_path);
		o("Stylesheets of " + s + ", before: " + s.getStylesheets());
		
//		System.out.println("Get cached image: " + StyleManager.getInstance().getCachedImage(full_css_path));

	
		// clear, load (should dodge caching,...right?)
		// WAS I EVER EVEN DODING THE CACHE??? I WAS NOT USING URI STRINGS 
//		s.getStylesheets().remove(getClass().getResource("/" + full_css_path).toExternalForm()); 	
		s.getStylesheets().remove(full_css_path); 	

//		s.getStylesheets().add(getClass().getResource("/" + full_css_path).toExternalForm()); 	
		s.getStylesheets().add(full_css_path); 	

		// -------------------- REST IS EXPERIMENTING WITH JAVAFX SOURCE CLASSES -----------------------
		o("Stylesheets of " + s + ", after: " + s.getStylesheets());
		
//		StyleManager.getInstance();
		// hmm...what can we do with the singleton stylemanager class api? 
//		o("Stylesheet manager singleton: " + StyleManager.getInstance());
//		
		/**
		 * When the new css is produced and this method is called, the css is not produced, but
		 * the file can be read from the system. In other words, loadStylesheet does not recognize it. 
		 */
		// This returns the sheet at the location...
		System.out.println(StyleManager.loadStylesheet(full_css_path)); // is this where things are cached?
		
//		// delete file for next load 
//		if (temp_css != null) { 
//			temp_css.delete(); 
//		}
	}
	
	// got tired of system.out.println
	private static void o(String message) {
		System.out.println(message);
	}
	
	// Read a file in (css file for our use here)
	public void readFile(String full_read_path) throws FileNotFoundException {
		o("Reading file (" + full_read_path + ")");
		File in_css = new File(full_read_path);
		Scanner myReader = new Scanner(in_css);
		while (myReader.hasNextLine()) {
			o("Read result: " + myReader.nextLine());
		}
		myReader.close(); 	
	}


}
