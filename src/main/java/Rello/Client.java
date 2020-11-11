package Rello; 
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class Client {

	public static ServerInterface SS; // will probably only want to initialize this once?
	public User user; 
	
	public Client(Registry registry, String rmi_server) throws MalformedURLException {
		try
		{
			if (registry == null) {
				throw new NotBoundException(); 
			}	 
			SS = (ServerInterface) registry.lookup(rmi_server);
		} catch (RemoteException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean loginUser(String email, String password) throws RemoteException {
		if (SS == null) {
			return false; 
		}
		User user_in = SS.loginUser(email, password); 
		System.out.println(user_in);

		if (user_in == null) {
			return false;
		}
		user = user_in; 
		return true; 
	}
	
	public boolean addUser(String email, String password) throws RemoteException {
		if (SS == null) {
			return false; 
		}
		User user_in = SS.addUser(email, password); 
		System.out.println(user_in);

		if (user_in == null) {
			return false;
		}
		user = user_in; 
		return true; 
	}
	
	public boolean updateBoard(Board board, User user) throws RemoteException {
		if (SS == null) {
			return false; 
		}
		User user_in = SS.updateBoard(board, this.user);
		if (user_in == null) {
			return false;
		}
		user = user_in; 
		return true; 
		
	}
	
	public boolean createBoard(String bname, User user) throws RemoteException {
		if (SS == null) {
			return false; 
		}
		User user_in = SS.createBoard(bname, user);
		if (user_in == null) {
			return false;
		}
		user = user_in; 
		return true; 
	}
	
	public boolean tester() throws RemoteException {
		if (SS == null) {
			return false; 
		}
		SS.tester();
		return true; 
	}
	
	
}
	

