package edu.ntnu.texasai.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck {
    private List<Card> cards = new ArrayList<Card>();

    public Deck() {
        for (CardSuit suit : CardSuit.values()) {
            for (CardNumber number : CardNumber.values()) {
                Card card = new Card(suit, number);
                cards.add(card);
            }
        }

        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
		return cards;
	}

	public Card removeTopCard() {
        return cards.remove(0);
    }
    
   
    public boolean removeCard(Card card){
    	//TODO: remove card from the deck
    	return cards.remove(card);
    }

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
}
