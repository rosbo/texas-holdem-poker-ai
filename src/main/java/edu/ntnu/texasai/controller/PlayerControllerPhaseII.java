package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.persistence.PersistenceManager;

import javax.inject.Inject;
import java.util.List;

public class PlayerControllerPhaseII extends PlayerController {
    private final PersistenceManager persistanceController;
    private final EquivalenceClassController equivalenceClassController;
    private final HandStrengthEvaluator handStrengthEvaluator;

    @Inject
    public PlayerControllerPhaseII(final EquivalenceClassController equivalenceClassController,
                                   final PersistenceManager persistanceController,
                                   final HandStrengthEvaluator handStrengthEvaluator) {
        this.persistanceController = persistanceController;
        this.equivalenceClassController = equivalenceClassController;
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
        Double percentageOfWins = this.persistanceController.retrievePercentageOfWinsByPlayerAndEquivalenceClass(
                gameHand.getPlayers().size(), equivalenceClass);

        if (percentageOfWins > 0.8)
            return BettingDecision.RAISE;
        else if (percentageOfWins < 0.5)
            return BettingDecision.FOLD;
        return BettingDecision.CALL;
    }

    @Override
    public BettingDecision decideAfterFlop(Player player, GameHand gameHand, List<Card> cards) {
        Double handStrength = this.handStrengthEvaluator.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
                gameHand.getPlayers().size());
        if (handStrength > 0.8) {
            return BettingDecision.RAISE;
        } else if (handStrength > 0.4 || canCheck(gameHand, player)) {
            return BettingDecision.CALL;
        }
        return BettingDecision.FOLD;
    }
}
