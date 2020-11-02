package Rello;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class cardTest
{
	Server users_instance;
	Board team_jim;
	User jim;
	List week1list;
	List week2list;
	List week3list;

	@BeforeEach
	void setUp() throws Exception
	{
		// Set up users
		users_instance = Server.getInstance(); // start users
			
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
		users_instance.resetInstance();
	}
	
	@Test 
	void testAddCardMember() {
		// add cards to lists
		Card c1 = week1list.addCard("CSC Homework PG 10");
		User olly = users_instance.addUser("olly@gmail.com","olly123");
		c1.addMember(olly, jim); // he is not member of board
		
		// this test should fail because olly not on board
		assertTrue(!c1.getMembers().containsKey("olly@gmail.com"));
		
		// add olly to board and now we can add olly to a card
		team_jim.addMember(olly, jim);
		assertTrue(team_jim == c1.board);
		c1.addMember(olly, jim); // he is not member of board
		assertTrue(c1.getMembers().containsKey("olly@gmail.com"));
		users_instance.resetInstance();
	}
	
	@Test
	void testRemoveCardMember() {
		Card c1 = week1list.addCard("CSC Homework PG 10");
		User olly = users_instance.addUser("olly@gmail.com","olly123");
		team_jim.addMember(olly, jim);
		c1.addMember(olly, jim); 
		assertTrue(c1.getMembers().containsKey("olly@gmail.com"));		
		c1.removeMember(olly, jim); 
		assertTrue(!c1.getMembers().containsKey("olly@gmail.com"));
		users_instance.resetInstance();
	}
	
	@Test 
	void testAddLabel() {
		// add some cards to lists
		Card cschwk1 = week1list.addCard("CSC Homework PG 10");
		
		// create and add labels to cards
		Colors green = Colors.GREEN; 
		Label green_label = new Label(green, "complete");
		cschwk1.addLabel(green_label);
		assertTrue(cschwk1.getLabels().containsKey(green));
		assertTrue(cschwk1.getLabels().get(green).getText().equals("complete"));
		users_instance.resetInstance();
	}
	
	@Test 
	void testRemoveLabel() {
		// add some cards to lists
		Card cschwk1 = week1list.addCard("CSC Homework PG 10");
		
		// create and add labels to cards
		Colors green = Colors.GREEN; 
		Label green_label = new Label(green, "complete");
		
		// add label and make sure it is here
		cschwk1.addLabel(green_label);
		assertTrue(cschwk1.getLabels().containsKey(green));
		assertTrue(cschwk1.getLabels().get(green).getText().equals("complete"));
		
		// remove label
		cschwk1.removeLabel(green);
		// make sure it is no longer here 
		assertTrue(!cschwk1.getLabels().containsKey(green));	
		users_instance.resetInstance();
	}
	
	// component testing for card
	@Test
	void testDescriptionComponent() {
		Card cschwk1 = week1list.addCard("CSC Homework PG 10");
		// no components on card yet --> size 0
		assertTrue(cschwk1.getComponents().size() == 0);
		
		// add description component --> to size 1
		Component desc1 = new Description("Read from page 10 to 19");
		cschwk1.addComponent(desc1);
		assertTrue(cschwk1.getComponents().size() == 1);
		
		// remove description component --> back to size 0
		cschwk1.removeComponent(0);
		assertTrue(cschwk1.getComponents().size() == 0);
		
		// changing name is trivial
	}	
}
