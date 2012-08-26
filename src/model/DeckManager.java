package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckManager {

	private List<Card> deck;

	public DeckManager() {
		this.deck = new ArrayList<Card>();
	}

	public List<Card> getDeck() {
		return deck;
	}

	public void setDeck(List<Card> cards) {
		this.deck = cards;
	}

	/**
	 * Creates a new ordered 52 cards deck
	 * */
	public void createNewDeck() {

		String[] suits = new String[4];
		suits[0] = "Spade";
		suits[1] = "Heart";
		suits[2] = "Club";
		suits[3] = "Diamond";

		// int[] values = new int[13];
		// for (int i = 0; i < 13; i++)
		// values[i] = i + 2;

		Card card;
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 13; j++) {
				card = new Card(suits[i], j + 2);
				this.deck.add(card);
			}

	}

	public Card removeTopCard() {
		return this.deck.remove(0);
	}

	public Card getTopCard() {
		return this.deck.get(0);
	}

	public void shuffleDeck() {
		Collections.shuffle(this.deck);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deck == null) ? 0 : deck.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeckManager other = (DeckManager) obj;
		if (deck == null) {
			if (other.deck != null)
				return false;
		} else if (!deck.equals(other.deck))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Deck [cards=" + deck + "]";
	}

}
