package utils;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

import ENV.EnvHandler;
import Rello.Server;

public class ServerHelper
{	
	static private HashMap<String, String> env_vars = EnvHandler.getEnvVars(); 
	private Server server; 
	private Registry registry;
	public String bindname = env_vars.get("RMI_BIND_NAME");
	public int port = Integer.parseInt(env_vars.get("RMI_PORT"));

	public ServerHelper() { 
		super(); 
	}
	
	public Server bootServer() throws AccessException, RemoteException {
		server = Server.getInstance();
		registry = LocateRegistry.createRegistry(port); 
		registry.rebind(bindname, server);
		return server; 
	}
	
	public Server bootServer(String read_from) throws AccessException, RemoteException {
		server = Server.getInstance(read_from);
		registry = LocateRegistry.createRegistry(port); 
		registry.rebind(bindname, server);
		return server; 
	}
	
	public void closeServer() throws AccessException, RemoteException, NotBoundException {
		server.storeToDisk();
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

	// Only unbind
	public void cleanup() throws AccessException, RemoteException, NotBoundException {
		registry.unbind(bindname);
	}

	public Server getServer()
	{
		return server;
	}
}
