package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards = new ArrayList<Card>();

    public Deck() {
        initCards();
    }

    private List<Card> initCards() {
        List<Card> deck = new ArrayList<Card>();

        for (CardSuit suit : CardSuit.values()) {
            for (CardNumber number : CardNumber.values()) {
                Card card = new Card(suit, number);
                deck.add(card);
            }
        }

        return deck;
    }

    public Card removeTopCard() {
        return cards.remove(0);
    }

    public Card getTopCard() {
        return cards.get(0);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}
