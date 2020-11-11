package Rello;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	User createBoard(String bname, User user) throws RemoteException;
	User loginUser(String email, String password) throws RemoteException;
	User addUser(String email, String password) throws RemoteException; // 
	User updateBoard(Board board, User user) throws RemoteException; // exit/logout/save changes//every update
	void tester() throws RemoteException; 
 }
