package edu.ntnu.texasai.utils;

public class GameProperties {
    private final Integer smallBlind;
    private final Integer bigBlind;
    private final Integer initialMoney;
    private final Integer numberOfHands;

    public GameProperties() {
        // TODO: Property load
        smallBlind = 10;
        bigBlind = 20;
        initialMoney = 10000;
        numberOfHands = 1000;
    }

    public Integer getSmallBlind() {
        return smallBlind;
    }

    public Integer getBigBlind() {
        return bigBlind;
    }

    public Integer getInitialMoney() {
        return initialMoney;
    }

    public Integer getNumberOfHands() {
        return numberOfHands;
    }
}
