package edu.ntnu.texasai.controller.phase2;

import edu.ntnu.texasai.controller.HandStrengthEvaluator;
import edu.ntnu.texasai.controller.PlayerController;
import edu.ntnu.texasai.model.BettingRoundName;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.opponentmodeling.ContextPlayers;
import edu.ntnu.texasai.model.opponentmodeling.ContextRaises;

public abstract class PlayerControllerPhaseII extends PlayerController {
    private final HandStrengthEvaluator handStrengthEvaluator;

    protected PlayerControllerPhaseII(final HandStrengthEvaluator handStrengthEvaluator) {
        this.handStrengthEvaluator = handStrengthEvaluator;
    }

    protected double calculateCoefficient(GameHand gameHand, Player player){
        double p = this.handStrengthEvaluator.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
                gameHand.getPlayers().size());

        // Last round, why not?
        if (gameHand.getBettingRoundName().equals(BettingRoundName.POST_RIVER)) {
            p += 0.1;
        }
        // Many player mean more money
        if(ContextPlayers.valueFor(gameHand.getPlayersCount()).equals(ContextPlayers.MANY)){
            p+=0.1;
        }
        // Lot of raises, be careful
        if(ContextRaises.valueFor(gameHand.getCurrentBettingRound().getNumberOfRaises()).equals(ContextRaises.MANY)){
            p-=0.1;
        }

        return p;
    }
}
