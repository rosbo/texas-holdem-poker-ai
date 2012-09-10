package edu.ntnu.texasai.model.cards;

public enum CardSuit {
    SPADE("\u2660"),
    HEART("\u2665"),
    CLUB("\u2663"),
    DIAMOND("\u2666");

    private final String symbol;

    private CardSuit(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
