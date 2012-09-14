package edu.ntnu.texasai.controller.phase3;

import edu.ntnu.texasai.controller.HandStrengthEvaluator;
import edu.ntnu.texasai.controller.phase2.PlayerControllerPhaseIINormal;
import edu.ntnu.texasai.controller.opponentmodeling.OpponentModeler;
import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;

import javax.inject.Inject;

public class PlayerControllerPhaseIIIAgressive extends PlayerControllerPhaseIII {
    @Inject
    public PlayerControllerPhaseIIIAgressive(PlayerControllerPhaseIINormal playerControllerPhaseIINormal,
                                                HandStrengthEvaluator handStrengthEvaluator,
                                                OpponentModeler opponentModeler) {
        super(playerControllerPhaseIINormal, handStrengthEvaluator, opponentModeler);
    }

    @Override
    public String toString() {
        return "PhaseIII Agressive";
    }

    @Override
    protected BettingDecision decideBet(GameHand gameHand, Player player,
                                        int oppponentsWithBetterEstimatedHandStrength,
                                        int opponentsModeledCount) {
        if ((double) oppponentsWithBetterEstimatedHandStrength / opponentsModeledCount > 0.5) {
            return BettingDecision.RAISE;
        } else if (canCheck(gameHand, player)) {
            return BettingDecision.CALL;
        } else {
            return BettingDecision.FOLD;
        }
    }
}
