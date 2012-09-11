package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.controller.opponentmodeling.OpponentModeler;
import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.BettingRound;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.opponentmodeling.ContextAction;
import edu.ntnu.texasai.utils.Logger;

import javax.inject.Inject;
import java.util.List;

// TODO: Make this class abtract and create an agressive and a conservative bot
public class PlayerControllerPhaseIII extends PlayerController {
    private final PlayerControllerPhaseII playerControllerPhaseII;
    private final HandStrengthEvaluator handStrengthEvaluator;
    private final OpponentModeler opponentModeler;
    private final Logger logger;

    @Inject
    public PlayerControllerPhaseIII(final PlayerControllerPhaseII playerControllerPhaseII,
                                    final HandStrengthEvaluator handStrengthEvaluator,
                                    final OpponentModeler opponentModeler,
                                    final Logger logger) {
        this.playerControllerPhaseII = playerControllerPhaseII;
        this.handStrengthEvaluator = handStrengthEvaluator;
        this.opponentModeler = opponentModeler;
        this.logger = logger;
    }

    @Override
    public BettingDecision decidePreFlop(Player player, GameHand gameHand, List<Card> cards) {
        return playerControllerPhaseII.decidePreFlop(player, gameHand, cards);
    }

    @Override
    public BettingDecision decideAfterFlop(Player player, GameHand gameHand, List<Card> cards) {
        BettingRound currentBettingRound = gameHand.getCurrentBettingRound();
        Double handStrength = handStrengthEvaluator.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
                gameHand.getPlayersCount());
        Integer opponentsModeledCount = 0;
        Integer oppponentsWithBetterEstimatedHandStrength = 0;

        for (Player opponent : gameHand.getPlayers()) {
            // Only try to model opponent
            if (!opponent.equals(player)) {
                ContextAction contextAction = currentBettingRound.getContextActionForPlayer(opponent);

                if (contextAction != null) {
                    Double estimatedHandStrength = opponentModeler.getEstimatedHandStrength(contextAction);

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

        // Play if better than the majority
        if ((double) oppponentsWithBetterEstimatedHandStrength / opponentsModeledCount > 0.5) {
            return BettingDecision.RAISE;
        } else if (canCheck(gameHand, player)) {
            return BettingDecision.CALL;
        } else {
            return BettingDecision.FOLD;
        }
    }
}
