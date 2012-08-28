package edu.ntnu.texasai.model;

import java.util.List;

public class HandPower implements Comparable<HandPower> {
    private final HandPowerType handPowerType;
    private final List<Integer> tieBreakingInformation;

    public HandPower(final HandPowerType handPowerType, final List<Integer> tieBreakingInformation) {
        this.handPowerType = handPowerType;
        this.tieBreakingInformation = tieBreakingInformation;
    }

    @Override
    public int compareTo(HandPower handPower) {
        return 0;
    }

    public HandPowerType getHandPowerType() {
        return handPowerType;
    }

    public List<Integer> getTieBreakingInformation() {
        return tieBreakingInformation;
    }
}
