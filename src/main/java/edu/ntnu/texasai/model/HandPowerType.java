package edu.ntnu.texasai.model;

public enum HandPowerType {
    HIGH_CARD(1),
    ONE_PAIR(2),
    TWO_PAIR(3),
    THREE_OF_A_KIND(4),
    STRAIGHT(5),
    FLUSH(6),
    FULL_HOUSE(7),
    FOUR_OF_A_KIND(8),
    STRAIGHT_FLUSH(9);

    private final int power;

    private HandPowerType(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}
