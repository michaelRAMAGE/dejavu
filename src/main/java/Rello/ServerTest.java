package Rello;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ServerTest
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
		server.addUser("odysseus@gmail.com","odysseus123");
	}

	@Test
	void test() throws RemoteException
	{
		// Test the the test method on client (just prints)
		client.tester(); 
	}
	
	@Test
	void loginUserTest() throws RemoteException
	{
		// Test the the test method on client (just prints)
		String email = "jim@gmail.com";
		String password = "jim123"; 
		
		// returns true if a non-null user obtained from server
		assertTrue(client.loginUser(email, password) == true); 
		
		// checks to see that the user is being set on the client
		assertTrue(client.user != null); 
	}
	
	@Test
	void createBoardTest() throws RemoteException
	{
		// log into a user -- client is already logged in
		User user = client.user; 
		assertTrue(user != null);
		
		// Create board
		client.createBoard("testboard", user);
		
		// Test that the board was created on user
		assertTrue(client.user.getBoard("testboard") != null); 
		
		// get that board id now
		String board_id = client.user.getBoard("testboard").boardID;
		
		// Test that board was created on the board index
		assertTrue(Server.getBoardsIndex().get(board_id) != null);

		// clear boards
		client.user.getBoards().clear();
		Server.getBoardsIndex().clear();
		
		// make sure they are cleared
		assertTrue(client.user.getBoards().size() == 0);
		assertTrue(Server.getBoardsIndex().size() == 0); 
		
		
		// ALL PASS? Good!
	}
	
	@Test
	void updateBoardTest() throws RemoteException
	{
		// locate a board to test
			// we use the board created in createBoardTest()
			// this board should be around still
		
		// log into a user -- client is already logged in
		User user = client.user; 
		assertTrue(user != null);
		
		// Create board
		client.createBoard("testboard2", user);
		
		// get the test board 
		Board test_board = client.user.getBoard("testboard2"); 
		String test_board_id = test_board.boardID; 
		
		// make a change
		test_board.setName("newtestboard2");
		
		// ensure that key was changed
		assertTrue(client.user.getBoard("newtestboard2") != null);
		
		// if passed, then the client's board is AOK with that update
		
		// Now update board
		boolean user_updated = client.updateBoard(client.user.getBoard("newtestboard2"), client.user);
		assertTrue(user_updated == true); // successfully received updated user object from server
		assertTrue(Server.getBoardsIndex().get(test_board_id) != null); // check that board was updated on server board index
		
		// clear boards
		client.user.getBoards().clear();
		Server.getBoardsIndex().clear();
		
		// make sure they are cleared
		assertTrue(client.user.getBoards().size() == 0);
		assertTrue(Server.getBoardsIndex().size() == 0); 
	}
	
	@AfterAll
	static void tearDown() throws AccessException, RemoteException, NotBoundException 
	{
		server.closeServer(registry, bind_name);
	}

}
