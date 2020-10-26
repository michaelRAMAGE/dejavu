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
		
		System.out.println(users_instance);
		
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
	void testCardSwap() {
		// add two cards, swap them
		Card cschwk1 = week1list.addCard("CSC Homework PG 10");
		Card cschwk2 = week2list.addCard("CSC Homework PG 20");
		
		week1list.moveCardInList(1, 0);
		assertTrue(week1list.getCards().get(0).name.equals("CSC Homework PG 20"));
		
	}
//	
//	@Test
//	void testAddCardMemberSwap() {
//		Card cschwk1 = week1list.addCard("CSC Homework PG 10");
//	}
//	
//	void testRemoveCardMemberSwap() {
//		Card cschwk1 = week1list.addCard("CSC Homework PG 10");
//	}
//	
	
	
	

}
