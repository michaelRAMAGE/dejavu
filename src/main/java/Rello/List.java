package Rello;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;

public class List implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6733510980070055053L;
	public String name; 
	public ArrayList<Card> cards;
	public Board board; 
	
	public List()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public List(String name, Board board) {
		this.name = name; 
		this.board = board; 
		this.cards = new ArrayList<Card>();
	};
		
	@Override
	public boolean equals(Object obj) {
		List recovered_list = (List) obj; 
		ArrayList<Card> original_cards = this.getCards(); 
		ArrayList<Card> recovered_cards = recovered_list.getCards(); 
		
		if (!(this.getName().equals(recovered_list.getName() ))) {
			return false;  
		}
		
		if (!(recovered_list.board.getName().equals(this.board.getName()))) { // IFFFFFY
			return false; 
		}
		
		if(!(recovered_cards.size() == original_cards.size())) {
			return false; 
		}
		
		for (int i=0; i < this.getCards().size(); i++) {
			if (!original_cards.get(i).equals(recovered_cards.get(i))) {
				return false; 
			}
		}
		
		return true; 
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public ArrayList<Card> getCards()
	{
		return cards;
	}

	public void setCards(ArrayList<Card> cards)
	{
		this.cards = cards;
	}

	public Board getBoard()
	{
		return board;
	}

	public void setBoard(Board board)
	{
		this.board = board;
	}
	
	@Override
	public String toString()
	{
		return "List [name=" + name + ", cards=" + cards + "]";
	}
	
	// Overloaded 
	// creates card to add
	public Card addCard(String name) {
		Card new_card = new Card(name, this.board); 
		cards.add(new_card); 
		return new_card; 
	}

	public void moveCardIntraList(int old_idx, int new_idx) {
		// index checking
		if (old_idx < 0 || old_idx >= cards.size()) {
			return;
		}
		if (new_idx < 0 || new_idx >= cards.size()) {
			return;
		}
		
		// if only 1 card, do nothing
		
		// get card
		Card card_to_move = cards.get(old_idx);

		if (cards.size() == 2) {
			Collections.swap(cards,0,1);
		}
		else {
			cards.remove(old_idx); 
			cards.add(new_idx, card_to_move); 
		}
			
	}
	
	public void moveCardInterList(int curr_idx, int insert_at_idx, List target_list) {
		
		ArrayList<Card> target_list_cards = target_list.getCards();
		ArrayList<Card> curr_list_cards = this.getCards();
		
		// check list indexing
		if (insert_at_idx >= target_list_cards.size() || insert_at_idx < 0) {
			return; 
		}
		if (curr_idx >= curr_list_cards.size() || curr_idx < 0) {
			return; 
		}
		
		// move is valid
		Card card_to_move = curr_list_cards.get(curr_idx);
//		System.out.println(card_to_move.name);
		this.cards.remove(curr_idx);
		target_list.cards.add(insert_at_idx, card_to_move);

		if (target_list.cards.size() <= 2) { 
			Collections.swap(target_list.cards,0,1);
		}

	}
	
	public void removeCard(int idx) {
		cards.remove(idx); 
	}
	
}
