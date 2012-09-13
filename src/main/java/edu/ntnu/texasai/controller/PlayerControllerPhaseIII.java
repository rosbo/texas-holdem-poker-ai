package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.controller.opponentmodeling.OpponentModeler;
import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.BettingRound;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.opponentmodeling.ContextAction;

import java.util.List;

public abstract class PlayerControllerPhaseIII extends PlayerController {
    private final PlayerControllerPhaseII playerControllerPhaseII;
    private final HandStrengthEvaluator handStrengthEvaluator;
    private final OpponentModeler opponentModeler;

    protected PlayerControllerPhaseIII(PlayerControllerPhaseII playerControllerPhaseII, HandStrengthEvaluator
            handStrengthEvaluator, OpponentModeler opponentModeler) {
        this.playerControllerPhaseII = playerControllerPhaseII;
        this.handStrengthEvaluator = handStrengthEvaluator;
        this.opponentModeler = opponentModeler;
    }

    @Override
    public BettingDecision decidePreFlop(Player player, GameHand gameHand, List<Card> cards) {
        return playerControllerPhaseII.decidePreFlop(player, gameHand, cards);
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
                    double estimatedHandStrength = opponentModeler.getEstimatedHandStrength(contextAction);

                    if (estimatedHandStrength > 0) {
                        opponentsModeledCount++;
                        if (estimatedHandStrength > handStrength) {
                            oppponentsWithBetterEstimatedHandStrength++;
                        }
                    }
                }
            }
        }

        // If we don't have enough context action in the current betting round
        if ((double) opponentsModeledCount / gameHand.getPlayersCount() < 0.5) {
            // We fallback to a phase II bot
            return playerControllerPhaseII.decideAfterFlop(player, gameHand, cards);
        }

        return decideBet(gameHand, player, oppponentsWithBetterEstimatedHandStrength, opponentsModeledCount);
    }

    protected abstract BettingDecision decideBet(GameHand gameHand, Player player,
                                                 int oppponentsWithBetterEstimatedHandStrength,
                                                 int opponentsModeledCount);
}
