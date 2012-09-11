package edu.ntnu.texasai.model.opponentmodeling;

public class ContextInformation {
    private final ContextAction contextAction;
    private final Double handStrength;

    public ContextInformation(ContextAction contextAction, Double handStrength) {
        this.contextAction = contextAction;
        this.handStrength = handStrength;
    }

    public ContextAction getContextAction() {
        return contextAction;
    }

    public Double getHandStrength() {
        return handStrength;
    }
}
