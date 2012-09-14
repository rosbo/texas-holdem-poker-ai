package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.persistence.PreFlopPersistence;

import javax.inject.Inject;
import java.util.List;

public class PlayerControllerPhaseII extends PlayerController {
    private final EquivalenceClassController equivalenceClassController;
    private final PreFlopPersistence preFlopPersistence;
    private final HandStrengthEvaluator handStrengthEvaluator;

    @Inject
    public PlayerControllerPhaseII(final EquivalenceClassController equivalenceClassController,
                                   final PreFlopPersistence preFlopPersistence,
                                   final HandStrengthEvaluator handStrengthEvaluator) {
        this.equivalenceClassController = equivalenceClassController;
        this.preFlopPersistence = preFlopPersistence;
        this.handStrengthEvaluator = handStrengthEvaluator;
    }

    @Override
    public String toString() {
        return "PhaseII";
    }

    @Override
    public BettingDecision decidePreFlop(Player player, GameHand gameHand, List<Card> cards) {
        Card card1 = cards.get(0);
        Card card2 = cards.get(1);
        EquivalenceClass equivalenceClass = this.equivalenceClassController.cards2Equivalence(card1, card2);
        double percentageOfWins = preFlopPersistence.retrieve(gameHand.getPlayers().size(), equivalenceClass);

        if (percentageOfWins > 0.8)
            return BettingDecision.RAISE;
        else if (percentageOfWins < 0.5)
            return BettingDecision.FOLD;
        return BettingDecision.CALL;
    }

    @Override
    public BettingDecision decideAfterFlop(Player player, GameHand gameHand, List<Card> cards) {
        double handStrength = this.handStrengthEvaluator.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
                gameHand.getPlayers().size());
        if (handStrength > 0.8) {
            return BettingDecision.RAISE;
        } else if (handStrength > 0.4 || canCheck(gameHand, player)) {
            return BettingDecision.CALL;
        }
        return BettingDecision.FOLD;
    }
}
