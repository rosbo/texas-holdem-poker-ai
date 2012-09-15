package edu.ntnu.texasai.controller.phase2;

import edu.ntnu.texasai.controller.HandStrengthEvaluator;
import edu.ntnu.texasai.controller.PlayerController;
import edu.ntnu.texasai.model.BettingRoundName;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.opponentmodeling.ContextRaises;

public abstract class PlayerControllerPhaseII extends PlayerController {
    private final HandStrengthEvaluator handStrengthEvaluator;

    protected PlayerControllerPhaseII(final HandStrengthEvaluator handStrengthEvaluator) {
        this.handStrengthEvaluator = handStrengthEvaluator;
    }

    protected double calculateCoefficient(GameHand gameHand, Player player) {
        double p = this.handStrengthEvaluator.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
                gameHand.getPlayers().size());

        // Decision must depends on the number of players
        p = p * (1 + gameHand.getPlayersCount() / 20);

        // Last round, why not?
        if (gameHand.getBettingRoundName().equals(BettingRoundName.POST_RIVER)) {
            p += 0.3;
        }
        // Lot of raises, be careful
        if (ContextRaises.valueFor(gameHand.getCurrentBettingRound().getNumberOfRaises()).equals(ContextRaises.MANY)) {
            p -= 0.3;
        }

        return p;
    }
}
