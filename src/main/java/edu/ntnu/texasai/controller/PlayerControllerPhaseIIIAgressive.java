package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.controller.opponentmodeling.OpponentModeler;
import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;

import javax.inject.Inject;

public class PlayerControllerPhaseIIIAgressive extends PlayerControllerPhaseIII {
    @Inject
    public PlayerControllerPhaseIIIAgressive(PlayerControllerPhaseII playerControllerPhaseII,
                                                HandStrengthEvaluator handStrengthEvaluator,
                                                OpponentModeler opponentModeler) {
        super(playerControllerPhaseII, handStrengthEvaluator, opponentModeler);
    }

    @Override
    public String toString() {
        return "PhaseIII Agressive";
    }

    @Override
    protected BettingDecision decideBet(GameHand gameHand, Player player,
                                        Integer oppponentsWithBetterEstimatedHandStrength,
                                        Integer opponentsModeledCount) {
        if ((double) oppponentsWithBetterEstimatedHandStrength / opponentsModeledCount > 0.5) {
            return BettingDecision.RAISE;
        } else if (canCheck(gameHand, player)) {
            return BettingDecision.CALL;
        } else {
            return BettingDecision.FOLD;
        }
    }
}
