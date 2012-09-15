package edu.ntnu.texasai.controller.phase3;

import edu.ntnu.texasai.controller.HandStrengthEvaluator;
import edu.ntnu.texasai.controller.PlayerController;
import edu.ntnu.texasai.controller.phase2.PlayerControllerPhaseIINormal;
import edu.ntnu.texasai.controller.opponentmodeling.OpponentModeler;
import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.BettingRound;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.opponentmodeling.ContextAction;
import edu.ntnu.texasai.model.opponentmodeling.ModelResult;

import java.util.List;

public abstract class PlayerControllerPhaseIII extends PlayerController {
    private final PlayerControllerPhaseIINormal playerControllerPhaseIINormal;
    private final HandStrengthEvaluator handStrengthEvaluator;
    private final OpponentModeler opponentModeler;

    protected PlayerControllerPhaseIII(PlayerControllerPhaseIINormal playerControllerPhaseIINormal, HandStrengthEvaluator
            handStrengthEvaluator, OpponentModeler opponentModeler) {
        this.playerControllerPhaseIINormal = playerControllerPhaseIINormal;
        this.handStrengthEvaluator = handStrengthEvaluator;
        this.opponentModeler = opponentModeler;
    }

    @Override
    public BettingDecision decidePreFlop(Player player, GameHand gameHand, List<Card> cards) {
        return playerControllerPhaseIINormal.decidePreFlop(player, gameHand, cards);
    }

    @Override
    public BettingDecision decideAfterFlop(Player player, GameHand gameHand, List<Card> cards) {
        BettingRound currentBettingRound = gameHand.getCurrentBettingRound();
        double handStrength = handStrengthEvaluator.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
                gameHand.getPlayersCount());
        int opponentsModeledCount = 0;
        int oppponentsWithBetterEstimatedHandStrength = 0;

        for (Player opponent : gameHand.getPlayers()) {
            // Only try to model opponent
            if (!opponent.equals(player)) {
                ContextAction contextAction = currentBettingRound.getContextActionForPlayer(opponent);

                if (contextAction != null) {
                    ModelResult modelResult = opponentModeler.getEstimatedHandStrength(contextAction);

                    // If we don't have enough occurence or if the variance is big, the information is not valuable
                    if (modelResult.getNumberOfOccurences() > 10 && modelResult.getHandStrengthDeviation() <= 0.15) {
                        opponentsModeledCount++;
                        if (modelResult.getHandStrengthAverage() > handStrength) {
                            oppponentsWithBetterEstimatedHandStrength++;
                        }
                    }
                }
            }
        }

        // If we don't have enough context action in the current betting round
        if ((double) opponentsModeledCount / gameHand.getPlayersCount() < 0.5) {
            // We fallback to a phase II bot
            return playerControllerPhaseIINormal.decideAfterFlop(player, gameHand, cards);
        }

        return decideBet(gameHand, player, oppponentsWithBetterEstimatedHandStrength, opponentsModeledCount);
    }

    protected abstract BettingDecision decideBet(GameHand gameHand, Player player,
                                                 int oppponentsWithBetterEstimatedHandStrength,
                                                 int opponentsModeledCount);
}
