package commands;

import java.util.ArrayList;

import Rello.User;

public class UserActionsTransition
{
	private ArrayList<User> states = new ArrayList<User>();
	private int state_idx; 
	
	public UserActionsTransition(User current_state) {
		states.add(current_state); 
	}

	public ArrayList<User> getStates()
	{
		return states;
	}

	public void setStates(ArrayList<User> states)
	{
		this.states = states;
	}
	
	public void setState(User user) {
		this.states.add(user); 
		state_idx = states.size()-1; 
	}

	public boolean previous() {
		state_idx = state_idx > 0 ? --state_idx : state_idx; 
		if (state_idx == 0) {
			return false; 
		}
		return true; 
	}
	
	public boolean next() {
		state_idx = state_idx < states.size() ? ++state_idx : state_idx; 
		if (state_idx == states.size()-1) {
			return false; 
		}
		return true; 
	}
	
	public User getCurrentState() {
		return states.get(state_idx);
	}
}
