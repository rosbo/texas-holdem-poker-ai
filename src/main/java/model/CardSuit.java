package main.java.model;

public enum CardSuit {
    SPADE("S"),
    HEART("H"),
    CLUB("C"),
    DIAMOND("D");

    private final String symbol;

    private CardSuit(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
