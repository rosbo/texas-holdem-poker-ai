package edu.ntnu.texasai.model.cards;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeckTest {
	private Deck deck;
	@Before
	public void setUp() throws Exception {
		this.deck = new Deck();
	}

	@Test
	public void testDeck() {
		List<Card> cards = this.deck.getCards();
		assertEquals(52,cards.size());
		Card card = new Card(CardSuit.CLUB,CardNumber.ACE);
		assertTrue(deck.removeCard(card));
		assertEquals(51,cards.size());	
	}
	
	@Test
	public void testFromDeckToCouplesCards(){
	    //PreFlop
	    assertEquals(1326,this.deck.fromDeckToCouplesOfCard().size()); //52*51/2
	    //PreTurn
	    this.deck.removeTopCard();
	    this.deck.removeTopCard();
	    this.deck.removeTopCard();
	    this.deck.removeTopCard();
	    this.deck.removeTopCard();
	    assertEquals(1081,this.deck.fromDeckToCouplesOfCard().size()); //47*46/2
	    //PreRiver
	    this.deck.removeTopCard();
	    assertEquals(1035,this.deck.fromDeckToCouplesOfCard().size()); //46*45/2
	    //PostRiver
	    this.deck.removeTopCard();
	    assertEquals(990,this.deck.fromDeckToCouplesOfCard().size()); //45*44/2
	}

}
