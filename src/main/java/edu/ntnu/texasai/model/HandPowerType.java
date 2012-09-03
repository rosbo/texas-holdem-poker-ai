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

    private Integer power;

    private HandPowerType(Integer power) {
        this.power = power;
    }

    public Integer getPower() {
        return power;
    }
}
