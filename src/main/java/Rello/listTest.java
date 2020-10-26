package Rello;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class listTest
{

	// Just set everything once to use throughout
	static Users users = Users.getInstance(); // start users
	static Board team_jim;
	static User jim;
	
	@BeforeAll
	static void setUp() throws Exception
	{
		// add user to users
		jim = users.addUser("jim@gmail.com","jim123");
		
		// create board 
		team_jim = jim.createBoard("Team Jim");
	}

	@Test
	void testAddList()
	{
		// ensure adding a list actually added list
		List week1list = team_jim.addList("Week1");
		assertTrue(team_jim.getList(week1list) != null);
	}
	
	@Test
	void testMoveList()
	{
		// lists are added (week1list above is still here cus static)
		List week2list = team_jim.addList("Week2");
		List week3list = team_jim.addList("Week3");
		
		// swap list 1 and 3, week 1 should be last
		team_jim.moveList(0, 2);
		// check to see if swap performed
		List result = team_jim.getList(2);
		assertTrue(result != null); // should not be null
		assertTrue(team_jim.getList(2).getName().equals("Week1")); // should have this name
	}
	
	// others are getters and setters generated by java

}
