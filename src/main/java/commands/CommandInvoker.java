package commands;

import java.rmi.RemoteException;

public class CommandInvoker
{
	CommandInterface command; // command object 
	
	public CommandInvoker()
	{
		super();
	}

	public void setCommand(CommandInterface command) { 
		this.command = command; 
	}
	
	public void invokeCommand() throws RemoteException {
		command.execute(); 
	}
}
