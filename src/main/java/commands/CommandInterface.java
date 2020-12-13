package commands;

import java.rmi.RemoteException;

import Rello.Client;

public interface CommandInterface
{
	public void execute() throws RemoteException;
}
