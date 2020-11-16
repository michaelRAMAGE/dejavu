package Rello;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Board implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4558850599683177950L;
	public String boardID;
	public String name;  //OBS
	public User owner; 
	public HashMap<String, User> members;  //OBS
	public ArrayList<List> lists;  //OBS
	
	public Board()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Board(String name, User owner) {
		this.name = name; 
		this.owner = owner;	
		this.members = new HashMap<String, User>();
		this.lists = new ArrayList<List>();
		this.boardID = generateID(); 
		
	}
	
	// Generate ID for board
	public String generateID() {
		 Random rand = new Random();	
		 int upperbound = 100; 
		 String random = Integer.toString(rand.nextInt(upperbound)); 
		 if (Server.getBoardsIndex() == null) {
			 return random;
		 }
		 else if (Server.getBoardsIndex().containsKey(random) == false) {
			 return random; 
		 }
		 else {
			 return generateID(); 
		 }
	}
	
	@Override
	public boolean equals(Object obj) {
		// call userEquals helper 
		Board recovered_board = (Board) obj; // recovered recovered_board
		
		// name of board and owner same
		String rec_board_name = recovered_board.getName();
		String ori_board_name = this.getName(); 
		User rec_owner = recovered_board.getOwner();
		User ori_owner = this.getOwner();
		
		if (!(rec_board_name.equals(ori_board_name)) || !(rec_owner.userEquals(ori_owner))) {
			return false; 
		} // iffy 
		
		// check sizes 
		if ((recovered_board.getMembers().size() != this.getMembers().size()) || 
				(recovered_board.getLists().size() != this.lists.size())) {
			return false; 
		}
		
		// members
		for (String email : this.getMembers().keySet()) { // iffy
			User recovered_member = recovered_board.getMember(email);
			User original_member = this.getMember(email); 
			boolean members_equal = original_member.userEquals(recovered_member);
			if (!members_equal) {
				return false; 
			}
		}
		
		// lists 
		for (int i=0; i < this.lists.size(); i++) {
			ArrayList<List> recovered_list = recovered_board.getLists();
			ArrayList<List> original_list = this.getLists();
			
			if (!original_list.equals(recovered_list)) {
				return false; 
			}
		}
		
		return true; 
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String new_name)
	{
		owner.changeBoardKey(this.getName(), new_name); 
		this.name = new_name; 
	}

	public User getOwner()
	{
		return owner;
	}

	public void setOwner(User owner)
	{
		this.owner = owner;
	}

	public HashMap<String, User> getMembers()
	{
		return members;
	}

	public void setMembers(HashMap<String, User> members)
	{
		this.members = members;
	}

	public ArrayList<List> getLists()
	{
		return lists;
	}
	
	public User getMember(String email)
	{
		return getMembers().get(email);
	}

	public void setLists(ArrayList<List> lists)
	{
		this.lists = lists;
	}
	
	// Overloading 
	public List getList(String name) {
		for (int i=0; i < this.lists.size(); i++) {
			if (lists.get(i).name == name) {
				return lists.get(i);
			}
		}
		return null;
	}
	
	public List getList(List list) { // do an in depth comparison? not sure
		for (int i=0; i < this.lists.size(); i++) {
			if (this.lists.get(i).name == list.name) {
				return this.lists.get(i);
			}
		}
		return null;
	}
	public List getList(int idx) {
		System.out.println(idx);
		if (idx >= 0 && idx < lists.size()) {
			List desired_list = this.lists.get(idx);
			return desired_list;
		}
		else {
			return null; // cannot satisfy request
		}
		
	}
	
	public void moveList(int old_idx, int new_idx) {
		if (new_idx < lists.size()) {
			lists.add(new_idx, lists.get(old_idx)); 
		}
		else {
			lists.add(lists.get(old_idx)); 			
		}
	}
	
	public List addList(String name) {
		List new_list = new List(name, this);
		lists.add(new_list); 	
		return new_list; 
	}

	public boolean addMember(User user, User requester) {
		if (verifyUser(requester)) {
			this.members.put(user.email, user); // add user as member on this board
			user.addBoard(this.name, this);  // give the user this board on their boards hashmap
			return true; 
		}
		else {
			return false; 
		}
	}
	
	public boolean removeMember(User user, User requester) {
		if (verifyUser(requester)) {
			
			// remove user from members and remove board from user's boards
			this.members.remove(user.email); // remove on admin's end as member on this board
			user.removeBoard(this.name); // revoke access of board from user
			return true; 
		}
		else {
			return false; 
		}		
	}
	
	private boolean verifyUser(User requester) {
		if (requester.email.equals(this.owner.email)) {
			return true; 
		}
		else {
			return false; 
		}
	}
	
	

}
