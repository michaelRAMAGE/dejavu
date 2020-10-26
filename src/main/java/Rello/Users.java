package Rello;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Set;

/** This class is a Singleton!! **/
public class Users
{
	
	private static Users uniqueInstance = null; 
	private static final String SERIALIZED_FILE_NAME="users.xml";
	private static HashMap<String, User> users;
	
	private Users() {}
	
	public static Users getInstance() {
		if (uniqueInstance == null) {
			System.out.println(users);
			users = new HashMap<String, User>(); 
			uniqueInstance = new Users(); 
		}
		return uniqueInstance; 
	}

	public void resetInstance() {
		uniqueInstance = null;
	}
	
	// ADDS -- overloaded
	public boolean addUser(User user) { 
		if (user != null) { 
			users.put(user.email, user);
			return true; 
		}
		else {
			return false; 
		}
	}
	public User addUser(String email, String password) { 
		if (!users.containsKey(email)) { 
			User new_user = new User(email, password);
			users.put(email, new_user);
			return new_user; 
		}
		else {
			return null; 
		}
	}
	
	public HashMap<String, User> getUsers() {
		return users; 
	}
	
	// GETS -- overloaded
	public User getUser(User user) {
		if (user != null) {
			String key = user.email; 
			User fetched_user = users.get(key);
			return fetched_user; // check if null from caller			
		}
		return null; 
	}
	
	public User getUser(String email) {
		String key = email; 
		User fetched_user = users.get(key);
		return fetched_user; // check if null from caller
	}
	
	// REMOVES
	public User removeUser(String email) {
		String key = email; 
		User fetched_user = users.get(key);
		return fetched_user; // check if null from caller
	}
	public User removeUser(User user) {
		if (user != null) { 
			String key = user.email; 
			User fetched_user = users.get(key);
			return fetched_user; // check if null from caller			
		}
		return null; 
	}
	
	// XML STORAGE 
	public void storeToDisk() { // store out to XML
		XMLEncoder encoder=null;
		try {
		encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(SERIALIZED_FILE_NAME)));
		} catch(FileNotFoundException fileNotFound){
			System.out.println("ERROR: While Creating or Opening the File");
		}
		encoder.writeObject(users); // users is a hashmap<email, users>
		encoder.close();
	}
	
	public static HashMap<String, User> readFromDisk() { // read from XML 
		if (users != null) { // only recover on restart 
			XMLDecoder decoder=null;		
			try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
			} catch (FileNotFoundException e) {
			System.out.println("ERROR: File users.xml not found");
			}
			@SuppressWarnings("unchecked")
			HashMap<String, User> all_users = (HashMap<String, User>) decoder.readObject(); // object is hashmap<email, users>
			return all_users; 
		}
		return null; 
	}
	
	
	// Deep comparison of two maps of users
	@Override
	public boolean equals(Object obj) {
		System.out.println("in here");
		HashMap<String, User> users_in = (HashMap<String, User>) obj; 
		// iterate over the original users and check against recovered users (testing need)
		for (String user_email : users.keySet()) {
			
			// get value of original user
			User original_user = users.get(user_email);
			User recovered_user = users_in.get(user_email);
			
			// start deep comparison of the two users
			boolean users_equal = original_user.equals(recovered_user); 
			if (!users_equal) {
				return false; 
			}
		}
		return true; // all users must be equal 
	}
	
	
}
