package edu.ntnu.texasai.model.cards;

import java.util.ArrayList;
import java.util.List;

public class EquivalenceClassUnsuited extends EquivalenceClass {

    public EquivalenceClassUnsuited(CardNumber number1, CardNumber number2) {
        super(number1, number2);
    }

    @Override
    public List<Card> equivalence2cards() {
        List<Card> cards = new ArrayList<Card>();
        Card card1, card2;
        card1 = new Card(CardSuit.HEART, this.getNumber1());
        card2 = new Card(CardSuit.SPADE, this.getNumber1());
        cards.add(card1);
        cards.add(card2);
        return cards;
    }

    @Override
    public String getType() {
        return "UNSUITED";
    }

    @Override
    public String toString() {
        return "Equivalence Class Unsuited (" + getNumber1() + ","
                + getNumber2() + ")";
    }

}
