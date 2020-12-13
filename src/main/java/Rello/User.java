package Rello;

import java.io.Serializable;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8420532006952901735L;
	public String email; 
	public String password;
	public HashMap<String, Board> boards;
		
	
	public User() { super(); } 
	
	public User(String email, String password) {
		this.email = email;
		this.password = password; 
		this.boards = new HashMap<String, Board>(); 
	}

	public HashMap<String, Board> getBoards()
	{
		return boards;
	}
	
	public Board getBoard(String bname) {
		return boards.get(bname); 
	}
	
	public void setBoards(HashMap<String, Board> boards)
	{
		this.boards = boards;
	}
	
	public void setBoard(String bname, Board board)
	{
		this.boards.put(bname, board);
	}
	
	public void replaceBoard(String bname, Board board)
	{
		this.boards.replace(bname, board);
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void changeBoardKey(String old_name, String new_name) {
		Board old_board = this.getBoard(old_name);
		this.getBoards().put(new_name, old_board);
		this.getBoards().remove(old_name); 
	}

	public boolean userEquals(User user) { // move other equals to Users (one with two user params)
		if (this.email.equals(user.email)) {
			return true; 
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		// call userEquals helper 
		User recovered_user = (User) obj; // recovered recovered_user  
		if (userEquals(recovered_user) == true) { // two recovered_users are equal if same email
			
			// loop over boards items and perform equals on original board and recovered board 
			if (recovered_user.getBoards().size() == this.getBoards().size()) {
				for (String board_name : this.getBoards().keySet()) {
					
					// get the two boards
					Board original_board = this.getBoard(board_name);
					Board recovered_board = recovered_user.getBoard(board_name);
					
					boolean boards_equal = original_board.equals(recovered_board); // getBoard returns Board type
					if (!boards_equal) { // if true, boards are different, return false
						return false; 
					}
				}
				return true; // boards compared, everything is same				
			}
			else {
				return false; // boards are of different size 
			}
		}
		else { // userEquals returned false (users not equal at user email level)
			return false;
		}
	}
	

	// Adds an existing board to a user 
	public void addBoard(String bname, Board new_board) { 
		this.boards.put(bname, new_board);
	}
	
	public void removeBoard(String bname) {
		boards.remove(bname);
	}

	// Creates a board and puts it on user
	// NOTE : boards_index and boards differ
	// in that boards_index uses a board's id as key for hashing,
	// boards uses a board name
	public Board createBoard(String bname) {
		Board new_board = new Board(bname, this);
		this.boards.put(bname, new_board);
		return new_board; 
	}
	
	public Board createBoard(String bname, String id) {
		Board new_board = new Board(bname, id, this);
		boards.put(bname, new_board); 
		return new_board; 
	}
	
	// FOR TESTING ---
	public Board createBoard(String bname, HashMap<String, Board> boards_index) {
		Board new_board = new Board(bname, this, boards_index);
		this.boards.put(bname, new_board);
		return new_board; 
	}
	
	public boolean changePassword(String old_pwd, String new_pwd) {
		if (this.password == old_pwd) {
			this.password = new_pwd; 
			return true; 
		}
		else {
			return false; 
		}
	}
}


