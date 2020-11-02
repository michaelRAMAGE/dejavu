package Rello;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class userTest
{
	Server users_instance = Server.getInstance(); 
	User jim;
	User alfred;
	User odysseus;
	Board team_jim;

	@BeforeEach
	void setUp() throws Exception
	{
		jim = new User("jim@gmail.com","jim123");
		alfred = new User("alfred@gmail.com","alfred123");
		odysseus = new User("odysseus@gmail.com","odysseus123");
	}
	
	@Test
	void testUserCreds()
	{
		// Check that email is setting (one does the trick)
		assertTrue("jim@gmail.com".equals(jim.getEmail())); 
	}
	
	@Test 
	void testGlobalUsers() {
		// Check that user is added to global set
		users_instance.addUser(jim);
		User user_jim = users_instance.getUser(jim); 
		assertTrue(user_jim != null); 
	}
}
