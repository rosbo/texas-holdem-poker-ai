package edu.ntnu.texasai.model.opponentmodeling;

public class ContextAggregate {
    private final ContextAction contextAction;
    private int numberOfOccurrences = 0;
    private double handStrengthAverage = 0d;

    public ContextAggregate(final ContextAction contextAction) {
        this.contextAction = contextAction;
    }

    public void addOccurrence(double handStrength) {
        handStrengthAverage = (handStrength + numberOfOccurrences * handStrengthAverage) / (numberOfOccurrences + 1);
        numberOfOccurrences++;
    }

    public ContextAction getContextAction() {
        return contextAction;
    }

    public double getHandStrengthAverage() {
        return handStrengthAverage;
    }
}
