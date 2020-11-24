package Rello;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class serverTest
{
	static String bind_name = "Server"; 
	static Server server; 
	static Registry registry; 
	static Client client; 
	
	@BeforeAll
	static void setUp() throws Exception
	{
		// Initialize server and client
		server = Server.getInstance(); // get server instance
		
		// Set file path (if it does not exist on read in for boot, just store it out after for next)
		server.setXMLFileName("client_server_test.xml");
		
		registry = server.bootServer(bind_name); // bind and get registry
		
		client = new Client(registry, bind_name); 
		
		// Add in a tester users
		server.addUser("jim@gmail.com","jim123");
	}

	void resetStateHelper() {
		
	}
	
	@Test
	void loginBadUserTest() throws RemoteException, MalformedURLException
	{
		// Try to log into client added during the setup
		client = new Client(registry, bind_name); 

		// BAD CREDS
		String email = "jim@gmail.com";
		String password = "jim12"; 
		
		// returns true if a non-null user obtained from server
		assertTrue(client.loginUser(email, password) == false); 
		// checks to see that the user is being set on the client
		assertTrue(client.getUser() == null); 
		
		// we know the user will have all their data because xml storage testing passed
		client = null; 
	}
	
	@Test
	void loginGoodUserTest() throws RemoteException, MalformedURLException
	{
		// Try to log into client added during the setup
		client = new Client(registry, bind_name); 

		// GOOD CREDS
		String email = "jim@gmail.com";
		String password = "jim123"; 
		
		assertTrue(client.loginUser(email, password) == true); 
		assertTrue(client.getUser() != null); 	
		
		// we know the user will have all their data because xml storage testing passed
		client = null; 
	}
	
	@Test
	void createBoardTest() throws RemoteException, MalformedURLException
	{

		client = new Client(registry, bind_name); 
		String email = "jim@gmail.com";
		String password = "jim123"; 
		assertTrue(client.loginUser(email, password) == true); 
		
		// log into a user -- client is already logged in
		User user = client.getUser(); 
		assertTrue(user != null);
		
		// Create board
		client.createBoard("testboard", user);
		
		// Test that the board was created on user
		assertTrue(client.getUser().getBoard("testboard") != null); 		
		// ** testing the size
//		assertTrue(client.getUser().getBoards().size() == 2);
		
		// get that board id now
		String board_id = client.getUser().getBoard("testboard").boardID;
		
		// Test that board was created on the board index
		assertTrue(Server.getBoardsIndex().get(board_id) != null);
		assertTrue(Server.getBoardsIndex().get(board_id).getOwner().getEmail().equals("jim@gmail.com"));
		assertTrue(Server.getBoardsIndex().get(board_id).getName().equals("testboard"));

		// The clearing is done because the xmls will be updated on next run,
		// which would change the results of running the test a second time
		
		// clear boards
		client.user.getBoards().clear();
		Server.getBoardsIndex().clear();
		
		// make sure they are cleared
		assertTrue(client.user.getBoards().size() == 0);
		assertTrue(Server.getBoardsIndex().size() == 0); 
		
		// ALL PASS? Good!
		client = null; 
	}
	
	@Test
	void updateBoardTest() throws RemoteException, MalformedURLException
	{
		client = new Client(registry, bind_name); 
		String email = "jim@gmail.com";
		String password = "jim123"; 
		assertTrue(client.loginUser(email, password) == true); 

		// locate a board to test
			// we use the board created in createBoardTest()
			// this board should be around still
		
		// log into a user -- client is already logged in
		User user = client.getUser(); 
		assertTrue(user != null);
		
		assert(client.getUser().getBoard("testboard2") == null);
		// ^^ not on client, not on server. can only check server given a board idx
		
		// Create board
		client.createBoard("testboard2", user);
		
		// get the test board 
		Board test_board = client.getUser().getBoard("testboard2"); 
		String test_board_id = test_board.boardID; 
		assert(Server.getBoardsIndex().get(test_board_id) != null);
		
		// make a change
		test_board.setName("newtestboard2");
		
		// ensure that key was changed
		assertTrue(client.user.getBoard("newtestboard2") != null);
		
		// if passed, then the client's board is AOK with that update
		
		// Now update board
		boolean user_updated = client.updateBoard(client.user.getBoard("newtestboard2"), client.user);
		assertTrue(user_updated == true); // successfully received updated user object from server
		assertTrue(Server.getBoardsIndex().get(test_board_id) != null); // check that board was updated on server board index
		assertTrue(Server.getBoardsIndex().get(test_board_id).getOwner().getEmail().equals("jim@gmail.com")); // check that board was updated on server board index

		// clear boards
		client.user.getBoards().clear();
		Server.getBoardsIndex().clear();
		
		// make sure they are cleared
		assertTrue(client.user.getBoards().size() == 0);
		assertTrue(Server.getBoardsIndex().size() == 0); 
		
		client = null;
	}
	
	@AfterAll
	static void tearDown() throws AccessException, RemoteException, NotBoundException 
	{
		server.closeServer(registry, bind_name);
	}

}
