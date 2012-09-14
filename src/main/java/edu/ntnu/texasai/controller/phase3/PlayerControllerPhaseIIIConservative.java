package edu.ntnu.texasai.controller.phase3;

import edu.ntnu.texasai.controller.HandStrengthEvaluator;
import edu.ntnu.texasai.controller.phase2.PlayerControllerPhaseIINormal;
import edu.ntnu.texasai.controller.opponentmodeling.OpponentModeler;
import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;

import javax.inject.Inject;

public class PlayerControllerPhaseIIIConservative extends PlayerControllerPhaseIII {
    @Inject
    public PlayerControllerPhaseIIIConservative(PlayerControllerPhaseIINormal playerControllerPhaseIINormal,
                                                HandStrengthEvaluator handStrengthEvaluator,
                                                OpponentModeler opponentModeler) {
        super(playerControllerPhaseIINormal, handStrengthEvaluator, opponentModeler);
    }

    @Override
    public String toString() {
        return "PhaseIII Conservative";
    }

    @Override
    protected BettingDecision decideBet(GameHand gameHand, Player player,
                                        int oppponentsWithBetterEstimatedHandStrength,
                                        int opponentsModeledCount) {
        if (oppponentsWithBetterEstimatedHandStrength == 0) {
            return BettingDecision.RAISE;
        } else if (canCheck(gameHand, player)) {
            return BettingDecision.CALL;
        } else {
            return BettingDecision.FOLD;
        }
    }
}
