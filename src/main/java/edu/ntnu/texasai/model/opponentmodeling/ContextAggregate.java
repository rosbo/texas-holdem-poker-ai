package edu.ntnu.texasai.model.opponentmodeling;

import java.util.ArrayList;
import java.util.List;

public class ContextAggregate {
    private final ContextAction contextAction;
    List<Double> handStrengths = new ArrayList<Double>();

    public ContextAggregate(final ContextAction contextAction) {
        this.contextAction = contextAction;
    }

    public void addOccurrence(double handStrength) {
        handStrengths.add(handStrength);
    }

    public ContextAction getContextAction() {
        return contextAction;
    }

    public double getHandStrengthAverage() {
        double sum = 0;
        for (Double handStrength : handStrengths) {
            sum += handStrength;
        }

        return sum / getNumberOfOccurrences();
    }

    public double getDeviation() {
        double avg = getHandStrengthAverage();
        double variance = 0;

        for (Double handStrength : handStrengths) {
            variance += Math.pow(handStrength - avg, 2);
        }

        return Math.sqrt(variance / getNumberOfOccurrences());
    }

    public int getNumberOfOccurrences() {
        return handStrengths.size();
    }
}
