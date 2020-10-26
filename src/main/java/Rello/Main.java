package Rello;

import java.util.HashMap;

public class Main
{
	public static void main(String[] args)
	{
		Users users = Users.getInstance(); // start users
		
		
		User jim = users.addUser("jim@gmail.com","jim123");
		User alfred = users.addUser("alfred@gmail.com","alfred123");
		User odysseus = users.addUser("odysseus@gmail.com","odysseus123");
		
		Board team_jim = jim.createBoard("Team Jim");
	
		team_jim.addMember(alfred, jim); 
		team_jim.addMember(odysseus, jim);
		
		List week1list = team_jim.addList("Week1");
		List week2list = team_jim.addList("Week2");
		List week4list = team_jim.addList("Week3");

		Card cschwk = week1list.addCard("CSC Homework");
		// end of adding to start user set
	
		
		users.storeToDisk(); // store to disk
		assert(!Users.users.equals(Users.readFromDisk()));
		
		// are start and recovered the same? check (test)
		
		
	}
}
