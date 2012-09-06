package edu.ntnu.texasai.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.CardNumber;
import edu.ntnu.texasai.model.cards.CardSuit;
import edu.ntnu.texasai.model.cards.Deck;

public class DeckTest {
	private Deck deck;
	@Before
	public void setUp() throws Exception {
		this.deck = new Deck();
	}

	@Test
	public void testDeck() {
		//fail("Not yet implemented");
		List<Card> cards = this.deck.getCards();
		assertEquals(52,cards.size());
		Card card = new Card(CardSuit.CLUB,CardNumber.ACE);
		assertTrue(deck.removeCard(card));
		assertEquals(51,cards.size());
	
	}

}
