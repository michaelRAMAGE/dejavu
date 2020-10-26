package Rello;

import java.util.ArrayList;
import java.util.EnumMap;

public class List
{
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
		
	public List(String name, ArrayList<Card> cards, Board board)
	{
		super();
		this.name = name;
		this.cards = cards;
		this.board = board;
	}

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
//			System.out.println(original_cards.get(i).name);
//			System.out.println(recovered_cards.get(i).name);
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
	
	public Card addCard(String name) {
		Card new_card = new Card(name, this.board); 
		cards.add(new_card); 
		return new_card; 
	}
	
	public void moveCardInList(int old_idx, int new_idx) {
		if (new_idx < cards.size()) {
			cards.add(new_idx, cards.get(old_idx)); 
		}
		else {
			cards.add(cards.get(old_idx)); 			
		}
	}
	
	public void removeCard(int idx) {
		cards.remove(idx); 
	}
	
}
