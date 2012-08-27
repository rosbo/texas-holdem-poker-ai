package edu.ntnu.texasai.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards = new ArrayList<Card>();

    public Deck() {
        initCards();
        shuffle();
    }

    public Card removeTopCard() {
        return cards.remove(0);
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

    private void shuffle() {
        Collections.shuffle(cards);
    }
}
