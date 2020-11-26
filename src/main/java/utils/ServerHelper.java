package utils;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Rello.Server;

public class ServerHelper
{
	Server server; 
	Registry registry;
	String bindname = "Server";

	public Server bootServer() throws AccessException, RemoteException {
		server = Server.getInstance();
		registry = LocateRegistry.createRegistry(2099); 
		registry.rebind(bindname, server);
		return server; 
	}
	
	public Server bootServer(String read_from) throws AccessException, RemoteException {
		server = Server.getInstance(read_from);
		registry = LocateRegistry.createRegistry(2099); 
		registry.rebind(bindname, server);
		System.out.println(server);
		return server; 
	}
	
	public void closeServer() throws AccessException, RemoteException, NotBoundException {
		server.resetInstance();
		registry.unbind(bindname);
		registry = null;
	}
	public void closeServer(String save_to) throws AccessException, RemoteException, NotBoundException {
		server.setXMLFileName(save_to);
		server.storeToDisk();
		server.resetInstance();
		registry.unbind(bindname);
		
	}
}
