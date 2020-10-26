package Rello;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class cardTest
{
	Users users_instance;
	Board team_jim;
	User jim;
	List week1list;
	List week2list;
	List week3list;

	@BeforeEach
	void setUp() throws Exception
	{

		// Set up users
		users_instance = Users.getInstance(); // start users
			
		// add user jim
		jim = users_instance.addUser("jim@gmail.com","jim123");

		// create board on jim
		team_jim = jim.createBoard("Team Jim");
		
		// add some lists
		week1list = team_jim.addList("Week1");
		week2list = team_jim.addList("Week2");
		week3list = team_jim.addList("Week3");
	}

	@Test
	void testAddCard()
	{
		// add card and see if it was added
		Card cschwk1 = week1list.addCard("CSC Homework PG 10");
		assertTrue(week1list.getCards().get(0) != null);
		users_instance.resetInstance();
	}
	
	@Test
	void testCardMoveIntraList() {
		// add two cards, move them
		week1list.addCard("CSC Homework PG 10");
		week1list.addCard("CSC Homework PG 20");
		
		// was the move successful? 
//		System.out.println(week1list.getCards().get(0).name);
		week1list.moveCardIntraList(1, 0);
		
//		System.out.println(week1list.getCards().get(1).name);
		assertTrue(week1list.getCards().get(0).name.equals("CSC Homework PG 20"));
		users_instance.resetInstance();
	}
	
	@Test
	void testCardMoveInterList() {
		// add two cards
		week1list.addCard("CSC Homework PG 10");
		week2list.addCard("CSC Homework PG 20");
		
		// move card 0 in week1list to index 0 in week2list
		week1list.moveCardInterList(0, 0, week2list);
		assertTrue(week2list.getCards().get(0).name.equals("CSC Homework PG 20"));
		assertTrue(week2list.getCards().get(1).name.equals("CSC Homework PG 10"));
		
		Card cschwk3 = week1list.addCard("CSC Homework PG 30");
		// move new card 0 in week1list to index 1 in week2list
		week1list.moveCardInterList(0, 1, week2list);
		assertTrue(week2list.getCards().get(0).name.equals("CSC Homework PG 20"));
		assertTrue(week2list.getCards().get(1).name.equals("CSC Homework PG 30"));
		assertTrue(week2list.getCards().get(2).name.equals("CSC Homework PG 10"));
	}
}
