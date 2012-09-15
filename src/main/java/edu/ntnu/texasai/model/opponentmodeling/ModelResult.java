package edu.ntnu.texasai.model.opponentmodeling;

public class ModelResult {
    private double handStrengthAverage;
    private double handStrengthDeviation;
    private int numberOfOccurences;

    public ModelResult(double handStrengthAverage, double handStrengthDeviation, int numberOfOccurences) {
        this.handStrengthAverage = handStrengthAverage;
        this.handStrengthDeviation = handStrengthDeviation;
        this.numberOfOccurences = numberOfOccurences;
    }

    public double getHandStrengthAverage() {
        return handStrengthAverage;
    }

    public double getHandStrengthDeviation() {
        return handStrengthDeviation;
    }

    public int getNumberOfOccurences() {
        return numberOfOccurences;
    }
}
