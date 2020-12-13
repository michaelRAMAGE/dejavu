package Rello;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import utils.ServerHelper;
import utils.TestHelper;

class serverTest
{
	static Client client; 
	static TestHelper testHelper = new TestHelper(); 
	static ServerHelper serverHelper = new ServerHelper();
			
	@BeforeAll
	static void setUp() throws Exception
	{
		// boot up the server
		serverHelper.bootServer("client_server_test.xml");
		serverHelper.getServer().getUsers().clear();
		serverHelper.getServer().getBoardsIndex().clear(); 
	}
	
//	@Test
//	void loginBadUserTest() throws RemoteException, MalformedURLException
//	{
//		// Try to log into client added during the setup
//		client = new Client(registry, bind_name); 
//
//		// BAD CREDS
//		String email = "jim@gmail.com";
//		String password = "jim12"; 
//		
//		// returns true if a non-null user obtained from server
//		assertTrue(client.loginUser(email, password) == false); 
//		// checks to see that the user is being set on the client
//		assertTrue(client.getUser() == null); 
//		
//		// we know the user will have all their data because xml storage testing passed
//		client = null; 
//	}

	
//	@Test
//	void loginBadUserTest() throws RemoteException, MalformedURLException
//	{
//		// Try to log into client added during the setup
//		client = new Client(registry, bind_name); 
//
//		// BAD CREDS
//		String email = "jim@gmail.com";
//		String password = "jim12"; 
//		
//		// returns true if a non-null user obtained from server
//		assertTrue(client.loginUser(email, password) == false); 
//		// checks to see that the user is being set on the client
//		assertTrue(client.getUser() == null); 
//		
//		// we know the user will have all their data because xml storage testing passed
//		client = null; 
//	}
//	
//	@Test
//	void loginGoodUserTest() throws RemoteException, MalformedURLException
//	{
//		// Try to log into client added during the setup
//		client = new Client(registry, bind_name); 
//
//		// GOOD CREDS
//		String email = "jim@gmail.com";
//		String password = "jim123"; 
//		
//		assertTrue(client.loginUser(email, password) == true); 
//		assertTrue(client.getUser() != null); 	
//		
//		// we know the user will have all their data because xml storage testing passed
//		client = null; 
//	}
	
	@Test
	void createBoardTest() throws RemoteException, MalformedURLException
	{
		System.out.println("Create board");

		// Obtain client and log user into client
		String email = "jim@gmail.com";
		String password = "jim123"; 
		
		// Obtain a client, create/retrieve user
		client = testHelper.initTestData(email, password);
		
		// Get user
		User user = client.getUser(); 
		
		// Test before
		HashMap<String, Board> client_boards = client.getUser().getBoards();
		HashMap<String, Board> server_boards = serverHelper.getServer().getBoardsIndex();
		assert((client_boards.size() == 0) && (server_boards.size() == 0));
		
		// Create board
		client.createBoard("testboard1", user);
		user = client.getUser(); // get updated user on client
		client.createBoard("testboard2", user);
		user = client.getUser(); // get updated user on client

		// Test after
		HashMap<String, Board> expected_boards = client.getUser().getBoards();
		HashMap<String, Board> actual_boards = serverHelper.getServer().getBoardsIndex();
		
		// Test that the user is replaced with updated user on server
		assert(serverHelper.getServer().getUser(user.getEmail()) != null);
		assert(serverHelper.getServer().getUser(user.getEmail()).getBoard("testboard1") != null);
		assert(serverHelper.getServer().getUser(user.getEmail()).getBoard("testboard2") != null);

		System.out.println("Expected boards: " + expected_boards);
		System.out.println("Actual boards: " + actual_boards);

		assert((expected_boards.size() ==  actual_boards.size()));
		for (Board board : expected_boards.values()) {
			String inBoardID = board.getBoardID();
			Board actual_board = actual_boards.get(inBoardID);
			assert(actual_board != null);
			assert(actual_board.getName().equals(board.getName()));
			assert(actual_board.getMembers().size() == board.getMembers().size());
		}
		
		
		serverHelper.getServer().getUsers().clear();
		serverHelper.getServer().getBoardsIndex().clear(); 
		System.out.println("\n\n");

	}
	
	@Test
	void updateBoardTest() throws RemoteException, MalformedURLException
	{
		System.out.println("Update board");

		// Obtain client and log user into client
		String email = "jim@gmail.com";
		String password = "jim123"; 
		
		// Obtain a client, create/retrieve user
		client = testHelper.initTestData(email, password);
		
		// Get user
		User user = client.getUser(); 
		
		// Add board on user
		Board testboard = new Board("testboard", user, serverHelper.getServer().getBoardsIndex());
		assert(user.getBoard("testboard") == null);
		user.addBoard(testboard.getName(), testboard);
		assert(user.getBoard("testboard") != null);
		
		// Add board on server's board index
		String testboard_id = testboard.getBoardID();
		assert(serverHelper.getServer().getBoardsIndex().get(testboard_id) == null); 
		serverHelper.getServer().getBoardsIndex().put(testboard_id, testboard); 
		assert(serverHelper.getServer().getBoardsIndex().get(testboard_id) != null); 
		
		// Update the board
		user.getBoard("testboard").setName("testboard_newname");
		client.updateBoard(testboard, user);
		user = client.getUser();
		
		// Test client gets updated
		assert(user.getBoard("testboard") == null);
		assert(user.getBoard("testboard_newname") != null);
		
		Board updated_board = serverHelper.getServer().getBoardsIndex().get(testboard_id);
		
		// Test server gets updated
		assert(updated_board != null);
		assert(updated_board.getBoardID().equals(testboard_id));
		assert(updated_board.getName().equals("testboard_newname"));
		
		serverHelper.getServer().getUsers().clear();
		serverHelper.getServer().getBoardsIndex().clear(); 
		System.out.println("\n\n");

	}
	
	@Test
	void removeBoardTest() throws RemoteException, MalformedURLException
	{
		System.out.println("Remove board");

		// Obtain client and log user into client
		String email = "jim@gmail.com";
		String password = "jim123"; 
		
		// Obtain a client, create/retrieve user
		client = testHelper.initTestData(email, password);
		
		// Get user
		User user = client.getUser(); 
		
		// Add board on user
		Board testboard = new Board("testboard", user, serverHelper.getServer().getBoardsIndex());
		assert(user.getBoard("testboard") == null);
		user.addBoard(testboard.getName(), testboard);
		assert(user.getBoard("testboard") != null);
		serverHelper.getServer().getUser(user.getEmail()).addBoard(testboard.getName(), testboard);
		
		// Add board on server's board index
		String testboard_id = testboard.getBoardID();
		assert(serverHelper.getServer().getBoardsIndex().get(testboard_id) == null); 
		serverHelper.getServer().getBoardsIndex().put(testboard_id, testboard); 
		assert(serverHelper.getServer().getBoardsIndex().get(testboard_id) != null); 
		assert(serverHelper.getServer().getUser(user.getEmail()).getBoard(testboard.getName()) != null);
		System.out.println("BEFORE: User in users on server " + serverHelper.getServer().getUser(user.getEmail())
				+ ", has boards: " + serverHelper.getServer().getUser(user.getEmail()).getBoards());
		
		// Remove the board
		client.removeBoard(testboard, user);
		user = client.getUser();
		
		// Test client does not have board
		assert(user.getBoard(testboard.getName()) == null);		
		Board updated_board = serverHelper.getServer().getBoardsIndex().get(testboard_id);
		
		// Test server does not have the board
		assert(updated_board == null);
		
		// Test that board is removed on server
		assert(serverHelper.getServer().getUser(user.getEmail()) != null);
		assert(serverHelper.getServer().getUser(user.getEmail()).getBoard(testboard.getName()) == null);

		// Test that user has been replaced with updated board deletion on the server
		// Check all users on the server do not have the board
		for (User curr : serverHelper.getServer().getUsers().values()) {
			System.out.println("AFTER: User in users on server " + curr + ", has boards: " + curr.getBoards());
			assert(curr.getBoards().size() == 0);
			assert(curr.getBoard(testboard.getName()) == null);
		}
		assert(serverHelper.getServer().getUser(user.getEmail()).getBoard(testboard.getName()) == null);
		
		// Clear users and boards for next test
		serverHelper.getServer().getUsers().clear();
		serverHelper.getServer().getBoardsIndex().clear(); 
		
		System.out.println("\n\n");

	}
	
	@AfterAll
	static void tearDown() throws AccessException, RemoteException, NotBoundException 
	{
		serverHelper.closeServer();
	}

}
