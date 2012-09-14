package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.BettingRoundName;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.model.opponentmodeling.ContextPlayers;
import edu.ntnu.texasai.model.opponentmodeling.ContextRaises;
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

        if (percentageOfWins > 0.6)
            return BettingDecision.RAISE;
        else if (percentageOfWins < 0.45)
            return BettingDecision.FOLD;
        return BettingDecision.CALL;
    }

    @Override
    public BettingDecision decideAfterFlop(Player player, GameHand gameHand, List<Card> cards) {
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

        if (p > 0.8) {
            return BettingDecision.RAISE;
        } else if (p > 0.4 || canCheck(gameHand, player)) {
            return BettingDecision.CALL;
        }
        return BettingDecision.FOLD;
    }
}
