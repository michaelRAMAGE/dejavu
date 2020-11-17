package utils;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Rello.Server;

public class ServerHelper
{
	Server server; 
	static Registry registry;
	String bindname = "Server";

	public void bootServer() throws AccessException, RemoteException {
		registry = LocateRegistry.createRegistry(1099); 
		registry.rebind(bindname, Server.getInstance());
	}
	public void closeServer() throws AccessException, RemoteException, NotBoundException {
		registry.unbind(bindname);
	}
}
