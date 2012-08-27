package main.java.model;

public class Card {
    private final CardSuit suit;
    private final CardNumber number;

    public Card(final CardSuit suit, final CardNumber number) {
        this.suit = suit;
        this.number = number;
    }

    @Override
    public String toString() {
        return suit.toString() + number.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false;
        }

        Card other = (Card) obj;

        return suit.equals(other.suit) && number.equals(other.number);
    }

    public CardSuit getSuit() {
        return suit;
    }

    public CardNumber getNumber() {
        return number;
    }
}
