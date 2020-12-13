package commands;

import java.rmi.RemoteException;


public interface CommandInterface
{
	public void execute() throws RemoteException;
}
