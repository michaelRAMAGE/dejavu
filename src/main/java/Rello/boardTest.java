package Rello;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class boardTest
{
	Server users_instance = Server.getInstance(); 
	Board team_jim; 
	User jim; 
	User alfred;
	User odysseus; 
	

	@BeforeEach
	void setUp() throws Exception
	{
		jim = new User("jim@gmail.com","jim123");
		alfred = new User("alfred@gmail.com","alfred123");
		odysseus = new User("odysseus@gmail.com","odysseus123");
		users_instance.addUser(jim);
		users_instance.addUser(alfred);
		users_instance.addUser(odysseus);
	}

	@Test
	void testBoardCreate()
	{
		// Check that new board is being created on a user
		team_jim = jim.createBoard("TeamJim");
		assertTrue(jim.getBoard("TeamJim") != null);
	}
	
	@Test
	void testBoardAddRemoveMember()
	{
		// Check that new board is being created on a user
		team_jim = jim.createBoard("TeamJim");
		assertTrue(!team_jim.addMember(alfred, alfred)); // alfred tries to add himself
		assertTrue(team_jim.addMember(alfred, jim)); // jim adds alfred 
		
		// alfred should also have the board in his boards list now
		assertTrue(alfred.getBoards().containsKey("TeamJim"));
		team_jim.addMember(odysseus, jim);

		// REFERENCE checking
		// make sure they all have the same reference value (seeing how java manages value passing)
		assertTrue(team_jim == odysseus.getBoard("TeamJim"));
		assertTrue(team_jim == alfred.getBoard("TeamJim"));
		// -->  the above implies a change to one changes the other, like removing a member	 (double check)	
		boolean result = team_jim.removeMember(alfred, jim);
		// --> odysseus's board should not contain alfred anymore
		assertTrue(!odysseus.getBoard("TeamJim").members.containsKey("alfred@gmail.com"));
	}
}
