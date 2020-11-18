package Rello;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.HashMap;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/** This class is a Singleton!! **/
public class Server extends UnicastRemoteObject implements ServerInterface
{
	private static Registry registry; // rmi server registry
	
	private static final long serialVersionUID = -2; 
	private static Server uniqueInstance = null; // singleton class instance
	
	// Different servers have different xmls
	private static String SERIALIZED_FILE_NAME = "users.xml"; // xml storage file name || ip_data.xml 
	
	// pivot of our board model (main data store)
	private static HashMap<String, User> users; // global users hashmap --> stored to xml/retrieved from xml
	
	// pivot of our board model (main data store)
	private static HashMap<String, Board> board_index; // key : boardname, value : board
	
	
	// Private constructor 
	private Server() throws RemoteException 
	{ 
		super(); 
		users = new HashMap<String, User>(); 
		board_index = new HashMap<String, Board>(); 
		setAllData(); 
	}
	
	// Static method to get single class instance
	public static synchronized Server getInstance() {
		if (uniqueInstance == null) {
			try
			{
				uniqueInstance = new Server();
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}  
		return uniqueInstance; 
	}

	// Reset single class instance
	public void resetInstance() {
		uniqueInstance = null;
		users = null; 
		board_index = null; 
		registry = null; 
	}
	
	// Boot server with default name binding
	public Registry bootServer() throws RemoteException, MalformedURLException {
		setUsers(readFromDisk()); // read users from disk
		registry = LocateRegistry.createRegistry(2099); // FIRST ARGUMENT IS IP ADDRESS
		registry.rebind("Server", uniqueInstance);	
		return registry;
	}
	
	// Boot server with a non-default bind name
	public Registry bootServer(String bind_name) throws RemoteException, MalformedURLException {
		setUsers(readFromDisk()); // read users from disk
		setBoardIndex(); // set up the board index
		registry = LocateRegistry.createRegistry(2089);
		registry.rebind(bind_name, uniqueInstance);	
		return registry; 
	}
	
	// added in 
	// Boot server for remote host on a specified port (null host 
	public Registry bootServer(String host, int port) throws RemoteException, MalformedURLException {
		setUsers(readFromDisk()); // read users from disk
		setBoardIndex(); // set up the board index
		registry = LocateRegistry.getRegistry(host, port);
		return registry; 
	}

	// Clean up server stuff (does this work for remote hosts?)
	public void closeServer(Registry registry, String bind_name) throws AccessException, RemoteException, NotBoundException {
		registry.unbind(bind_name); // clean up server bindings 
		storeToDisk(); // store to disk on close
	}
	
	// Clean up server
	public void closeServer(String bind_name) throws AccessException, RemoteException, NotBoundException {
		registry.unbind(bind_name); // clean up server bindings 
		storeToDisk(); // store to disk on close
	}

	public void setAllData() {
		setUsers(readFromDisk()); // read users from disk
		setBoardIndex(); // set up the board index
	}

	// Log user into server
	@Override
	public User loginUser(String email, String password) throws RemoteException {
		User user_obj = authenticateUser(email, password); // if authenticated, returns user, otherwise null
		return user_obj; 
	}
	
	// Update user boards when an authorized user updates a board
	@Override 
	public User updateBoard(Board board, User user) throws RemoteException {
		String board_in_id = board.boardID; // client's updated board's board id
		String board_in_name = board.getName(); 
		User user_owner = board.getOwner(); 
		
		board_index.replace(board_in_id, board);
		user_owner.replaceBoard(board_in_name, board); // update owner and children get updated?
		
		return user; // return user back
	}

	// Register a user's new board
	@Override
	public User createBoard(String bname, User user) throws RemoteException
	{
		Board new_board = user.createBoard(bname); // create board on user
		board_index.put(new_board.boardID, new_board); // add board to index
		return user; // return the updated user to client
	}
	
	// Authenticate a user's credentials : return user on success, null on failure
	private User authenticateUser(String email, String password) {
		User desired_user = getUser(email);
		if (desired_user == null) { // no user with email found, return null
			return null;
		}
		if (!desired_user.password.equals(password)) { // valid email, but wrong password, return null
			System.out.println(desired_user.password);
			return null; 
		}
		System.out.println(desired_user);

		return desired_user; 		
	}
	
	// Add user using user object
	public boolean addUser(User user) { 
		if (user != null) { 
			users.put(user.email, user);
			return true; 
		}
		else {
			return false; 
		}
	}
	
	// Add user using email and password 
	public User addUser(String email, String password) { 
		System.out.println("email to try: " + email);
		if (!users.containsKey(email)) { 
			System.out.println(users);
			User new_user = new User(email, password);
			users.put(email, new_user);
			return new_user; 
		}
		else {
			return users.get(email); 
		}
	}
	
	// Add user using email and password 
	public User registerUser(String email, String password) { 
		System.out.println("email to try: " + email);
		if (!users.containsKey(email)) { 
			System.out.println(users);
			User new_user = new User(email, password);
			users.put(email, new_user);
			return new_user; 
		}
		else {
			return null; 
		}
	}
	
	// Set all users 
	public void setUsers(HashMap<String, User> users_in) {
		users = users_in; // assign users (typically called from storeToDisk())
	}

	// Get user using user object 
	public User getUser(User user) {
		if (user != null) {
			String key = user.email; 
			User fetched_user = users.get(key);
			return fetched_user; // check if null from caller			
		}
		return null; 
	}
	
	// Get user using email
	public User getUser(String email) {
		String key = email; 
		User fetched_user = users.get(key);
		return fetched_user; // check if null from caller
	}
	
	// Get all users
	public HashMap<String, User> getUsers() {
		return users; 
	}
	
	// Get all boards
	public static HashMap<String, Board> getBoardsIndex()
	{
		return board_index;
	}
	
	// Remove user using email
	public User removeUser(String email) {
		String key = email; 
		User fetched_user = users.get(key);
		return fetched_user; // check if null from caller
	}
	
	// Remove user using User object
	public User removeUser(User user) {
		if (user != null) { 
			String key = user.email; 
			User fetched_user = users.get(key);
			return fetched_user; // check if null from caller			
		}
		return null; 
	}
	
	// XML STORAGE 
	// Call this to create another file for testing
	public void setXMLFileName(String filename) {
		SERIALIZED_FILE_NAME = filename; 
	}
	
	// Store users to disk
	public void storeToDisk() { // store out to XML
		XMLEncoder encoder=null;
		System.out.println(SERIALIZED_FILE_NAME);
		try {
		encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(SERIALIZED_FILE_NAME)));
		} catch(FileNotFoundException fileNotFound){
			System.out.println("ERROR: While Creating or Opening the File");
		}
		System.out.println("writing file");
		encoder.writeObject(users); // users is a hashmap<email, users>
		encoder.close();
	}
	
	
	// Read users from disk
	public static HashMap<String, User> readFromDisk() { // read from XML 
		if (users != null) { // only recover on restart 
			XMLDecoder decoder=null;		
			
			File file = new File(SERIALIZED_FILE_NAME);
			boolean exists = file.exists();
			if (exists == false) {
				return new HashMap<String, User>(); 
			}
			
			try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
			} catch (FileNotFoundException e) {
			}
			@SuppressWarnings("unchecked")
			HashMap<String, User> all_users = (HashMap<String, User>) decoder.readObject(); // object is hashmap<email, users>	
			return all_users; 
		}
		return new HashMap<String, User>(); 
 
	}
	
	// Sets the global board index
	public void setBoardIndex() { // will be useful when called after a readFromDisk or when users is set
		for (User user : users.values()) { 
			HashMap<String, Board> user_boards = user.getBoards();
			if (user_boards == null) { // if user has no boards, continue to next iteration
				continue; 
			}
			for (Board board : user_boards.values()) {
				if (board_index.containsKey(board.boardID)) {
					board_index.put(board.boardID, board);
				}
			}	
		}
	}

	// Deep comparison of two maps of users
	@Override
	public boolean equals(Object obj) {
		@SuppressWarnings("unchecked")
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

	@Override
	public void tester() throws RemoteException
	{
		// TODO Auto-generated method stub
//		System.out.println("Method called from client.");
	}

	@Override
	public User removeBoard(Board board, User user) throws RemoteException
	{
		for (User u : users.values()) {
			if (u.getBoards().containsKey(board.getName())) {
				u.removeBoard(board.getName());		
			}
		}
		board_index.remove(board.getName());
		return users.get(user.getEmail());
	}
}
