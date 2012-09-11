package edu.ntnu.texasai.model.opponentmodeling;

public class ContextAggregate {
    private final ContextAction contextAction;
    private Integer numberOfOccurrences = 0;
    private Double handStrengthAverage = 0d;

    public ContextAggregate(final ContextAction contextAction) {
        this.contextAction = contextAction;
    }

    public void addOccurrence(Double handStrength) {
        handStrengthAverage = (handStrength + numberOfOccurrences * handStrengthAverage) / (numberOfOccurrences + 1);
        numberOfOccurrences++;
    }

    public ContextAction getContextAction() {
        return contextAction;
    }

    public Double getHandStrengthAverage() {
        return handStrengthAverage;
    }
}
