package Rello;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

public class Card implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9169189339417151079L;
	public String name;  //OBS
	public EnumMap<Colors, Label> labels; // update to Labels for second type  //OBS
	public ArrayList<Component> components;  //OBS
	public Board board;   //OBS
	public HashMap<String, User> members;   //OBS
	
	
	public Card()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Card(String name, Board board) {
		this.name = name;
		this.board = board; 
		this.components = new ArrayList<Component>();
		this.members = new HashMap<String, User>(); 
		this.labels = new EnumMap<Colors, Label>(Colors.class); 
	}; 
	
	
	public ArrayList<Component> getComponents()
	{
		return components;
	}

	public void setComponents(ArrayList<Component> components)
	{
		this.components = components;
	}

	public HashMap<String, User> getMembers()
	{
		return members;
	}

	public void setMembers(HashMap<String, User> members)
	{
		this.members = members;
	}

	@Override
	public boolean equals(Object obj) {
		Card recovered_card = (Card) obj; 
		
		ArrayList<Component> ori_components = this.getComponents(); 
		ArrayList<Component> rec_components = recovered_card.getComponents();
		EnumMap<Colors, Label> ori_labels = this.getLabels();
		EnumMap<Colors, Label> rec_labels = recovered_card.getLabels();
		HashMap<String, User> ori_members = this.getMembers();
		HashMap<String, User> rec_members = recovered_card.getMembers();
		
		if (!(this.name.equals(recovered_card.name))) {
			return false;  
		}
	
		if(!(ori_labels.size() == rec_labels.size())) {
			return false; 
		}

		if (!(ori_components.size() == rec_components.size())) { 
			return false; 
		}
		
		if(!(ori_members.size() == rec_members.size())) {
			return false; 
		}
		
		for (int i=0; i < ori_components.size(); i++) { // do deeper once components refined
			if (ori_components.get(i).getClass() != rec_components.get(i).getClass()) {
				return false; 
			}
			
			if (!ori_components.get(i).equals(rec_components.get(i))) {
				return false; 
			}
		}
		
		for (Colors color : this.getLabels().keySet()) { 
			if (!ori_labels.get(color).equals(rec_labels.get(color))) { 
				return false; 
			}
		}
		
		for (String email : ori_members.keySet()) { 
			if (!ori_members.get(email).userEquals(rec_members.get(email))) {
				return false; 
			}
		} 

		if (!this.board.getName().equals(recovered_card.board.getName())) {
			return false; 
		}
		
		return true; 
	}

	public void addComponent(Component new_component) {
		components.add(new_component);
	}
	
	public void removeComponent(int idx) {
		components.remove(idx);
	}
	
	public EnumMap<Colors, Label> getLabels()
	{
		return labels;
	}

	public void setLabels(EnumMap<Colors, Label> labels)
	{
		this.labels = labels;
	}

	// Label adders (overloaded)
	public void addLabel(Colors color, Label label) {
		labels.put(color, label);
	}
	public void addLabel(Label label) {
		labels.put(label.getColor(), label);
	}
	
	public void removeLabel(Colors color) {
		labels.remove(color);
	}
	
	public void addMember(User user, User requester) {
		if (requester.userEquals(this.board.owner)) {
			System.out.println("here");
			if (board.getMembers().containsKey(user.email)) {
				System.out.println("here");
				members.put(user.email, user);
			}
			else {
				return; 
			}
		}
	}
	
	public void removeMember(User user, User requester) {
		if (requester.userEquals(this.board.owner)) {
			if (board.members.containsKey(user.email)) {
				members.remove(user.email);
			}
			else {
				return; 
			}
		}
	}
	
	public void removeMember(String user_email, User requester) {
		if (requester.userEquals(this.board.owner)) {
			if (board.members.containsKey(user_email)) {
				members.remove(user_email);
			}
			else {
				return; 
			}
		}
	}
}
