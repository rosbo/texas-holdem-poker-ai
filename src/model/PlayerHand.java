package model;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand {
	private List<Card> hand;
	private Player player;
	
	public PlayerHand(){
		this.hand = new ArrayList<Card>();
	}
}
