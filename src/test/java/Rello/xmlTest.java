package Rello;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class xmlTest
{
	Server users = Server.getInstance(); // start users
	Board team_jim;
	User jim;
	User alfred;
	User odysseus;
	
	@BeforeEach
	void setUp() throws Exception
	{	
		users.setXMLFileName("users.xml");
		jim = users.addUser("jim@gmail.com","jim123");
		alfred = users.addUser("alfred@gmail.com","alfred123");
		odysseus = users.addUser("odysseus@gmail.com","odysseus123");
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	void testReadXML()
	{
		team_jim = jim.createBoard("Team Jim");
		
		// add members
		team_jim.addMember(alfred, jim); 
		team_jim.addMember(odysseus, jim);
		
		// add some lists
		List week1list = team_jim.addList("Week1");
		List week2list = team_jim.addList("Week2");
		List week3list = team_jim.addList("Week3");

		// add some cards to lists
		Card cschwk1 = week1list.addCard("CSC Homework PG 10");
		Card cschwk2 = week2list.addCard("CSC Homework PG 20");
		Card cschwk3 = week3list.addCard("CSC Homework PG 30");
		
		
		// create and add labels to cards
		Colors green = Colors.GREEN; 
		Colors red = Colors.RED; 
		Label red_label = new Label(red, "incomplete");
		Label green_label = new Label(green, "complete");
		cschwk1.addLabel(green_label);
		cschwk2.addLabel(green_label);
		cschwk3.addLabel(red_label);
		
		// add some card components
		Component desc1 = new Description("Read from page 10 to 19");
		cschwk1.addComponent(desc1);

		System.out.println(users.getUsers().get("jim@gmail.com").getBoard("Team Jim").getTheme());
		
		users.storeToDisk(); // store to disk
		HashMap<String, User> disk_users = Server.readFromDisk(); // read from disk into variable 
		
		System.out.println(disk_users.get("jim@gmail.com").getBoard("Team Jim").getTheme());

		
		assertTrue(users.getUsers().size() == disk_users.size()); // check that the two have same siz
		assertTrue(users.equals(disk_users)); // check when read back in	
	}

}
