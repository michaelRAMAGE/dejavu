package Rello; 
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import commands.CommandInterface;
import commands.CommandInvoker;
import commands.UpdateBoardCommand;
import commands.UserActionsTransition;

public class Client {

	public static ServerInterface SS; // will probably only want to initialize this once?
	public User user; 
	public String host;
	public String lookup_name; 
	public UserActionsTransition actions; 
	public CommandInterface command; 
	
	public Client(String host, String lookup_name) throws MalformedURLException {
		try
		{
			this.host = host;
			this.lookup_name = lookup_name; 
			String rmi_server_path = "rmi://"+host+"/"+lookup_name;
			SS = (ServerInterface) Naming.lookup(rmi_server_path);
		} catch (RemoteException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	
	public User getUser() {
		return this.user; 
	}
	
	// Log a user in
	public boolean loginUser(String email, String password) throws RemoteException {

		if (SS == null) {
			return false; 
		}
		User user_in = SS.loginUser(email, password); 
		if (user_in == null) {
			return false;
		}
		this.user = user_in; 
		this.actions = new UserActionsTransition(this.user); 
		return true; 
	}
	
	// Add user or return their credentials if exist
	public boolean addUser(String email, String password) throws RemoteException {

		if (SS == null) {
			return false; 
		}
		User user_in = SS.registerUser(email, password); 
		if (user_in == null) {
			return false;
		}
		this.user = user_in; 
		this.actions = new UserActionsTransition(this.user); 
		return true; 
	}
	
	void userInfo() {
		System.out.println("Email: " + this.user.getEmail());
		System.out.println("Boards: " + this.user.getBoards());
	}
	
	public boolean updateBoard(Board board, User old_user) throws RemoteException {

		if (SS == null) {
			return false; 
		}
		
		User user_in = SS.updateBoard(board, old_user);
		if (user_in == null) {
			return false;
		}
		
		this.user = user_in; 
		return true; 
	}
	
	public boolean createBoard(String bname, User old_user) throws RemoteException {
		if (SS == null) {
			return false; 
		}
		
		User user_in = SS.createBoard(bname, old_user);
		if (user_in == null) {
			return false;
		}
		
		this.user = user_in; 
		return true; 
	}
	
	public Client logoutUser(String host, String lookup_name) throws RemoteException, MalformedURLException
	{
		SS.logoutUser();
		return new Client(this.host, this.lookup_name);
	}
	
	public boolean removeBoard(Board board, User old_user) throws RemoteException {

		User user_in = SS.removeBoard(board, old_user);
		if (user_in == null) {
			return false;
		}
		
		this.user = user_in; 
		return true; 
	}


	
	public boolean tester() throws RemoteException {
		if (SS == null) {
			return false; 
		}
		SS.tester();
		return true; 
	}
	
	public void setCommand(CommandInterface command) { 
		this.command = command; 
	}
	
	public void invokeCommand() throws RemoteException {
		command.execute(); 
	}


}
	

