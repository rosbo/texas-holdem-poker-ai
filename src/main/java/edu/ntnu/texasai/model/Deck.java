package edu.ntnu.texasai.model;

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

    public Card removeTopCard() {
        return cards.remove(0);
    }
}
