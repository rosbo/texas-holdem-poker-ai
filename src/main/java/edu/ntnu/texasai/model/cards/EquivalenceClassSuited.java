package edu.ntnu.texasai.model.cards;

import java.util.ArrayList;
import java.util.List;

public class EquivalenceClassSuited extends EquivalenceClass {

    public EquivalenceClassSuited(CardNumber number1, CardNumber number2) {
        super(number1, number2);
    }

    @Override
    public String getType() {
        return "SUITED";
    }

    @Override
    public List<Card> equivalence2cards() {
        List<Card> cards = new ArrayList<Card>();
        Card card1, card2;
        card1 = new Card(CardSuit.SPADE, this.getNumber1());
        card2 = new Card(CardSuit.SPADE, this.getNumber2());
        cards.add(card1);
        cards.add(card2);
        return cards;
    }

    @Override
    public String toString() {
        return "Equivalence Class Suited (" + getNumber1() + ","
                + getNumber2() + ")";
    }

}
