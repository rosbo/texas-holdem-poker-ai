package edu.ntnu.texasai.preflopsim;

import java.util.Deque;
import java.util.List;

import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.Deck;
import edu.ntnu.texasai.model.cards.EquivalenceClass;

public class GameHandPreFlopRoll extends GameHand{

	private EquivalenceClass equivalenceClass;
	
	public GameHandPreFlopRoll(List<Player> players, EquivalenceClass equivalenceClass) {
		super(players);		
		this.equivalenceClass = equivalenceClass;
	}   
	
	/**
	 * Deals the hole cards. The prospective of the simulation is player0's one, so
	 * players0's hole cards are the same of equivalence cards, while the other players
	 * receive random cards form the top of the deck.
	 * */
	private void dealHoleCards() {
		Deck deck = this.getDeck();
		Player player0 = null;
		Deque<Player> players = this.getPlayers();				
		for (Player p : players){//the players are not sorted, the first one is the dealer
			if(p.getNumber().equals(new Integer(0))){
				player0 = p;
				players.remove(player0);
			}
		}		
		List<Card> holeCards = equivalenceClass.equivalence2cards();
		Card hole1 = holeCards.get(0);
		Card hole2 = holeCards.get(1);
		deck.removeCard(hole1);
		deck.removeCard(hole2);			
		player0.setHoleCards(hole1, hole2);		
		//other players card are random
        for (Player player : this.getPlayers()) {
            hole1 = deck.removeTopCard();
            hole2 = deck.removeTopCard();
            player.setHoleCards(hole1, hole2);
        }
    }
	
	
	
	
	
	

}
