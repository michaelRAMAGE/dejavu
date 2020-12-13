package Rello;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	User createBoard(String bname, User user) throws RemoteException;
	User loginUser(String email, String password) throws RemoteException;
	void logoutUser() throws RemoteException;
	User registerUser(String email, String password) throws RemoteException; // 
	User updateBoard(Board board, User user) throws RemoteException; 
	void tester() throws RemoteException;
	User removeBoard(Board board, User user) throws RemoteException; 
 }
