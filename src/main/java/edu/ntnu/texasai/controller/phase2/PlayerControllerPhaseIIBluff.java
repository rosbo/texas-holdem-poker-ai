package edu.ntnu.texasai.controller.phase2;

import edu.ntnu.texasai.controller.EquivalenceClassController;
import edu.ntnu.texasai.controller.HandStrengthEvaluator;
import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.BettingRoundName;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.model.opponentmodeling.ContextPlayers;
import edu.ntnu.texasai.persistence.PreFlopPersistence;

import javax.inject.Inject;
import java.util.List;

public class PlayerControllerPhaseIIBluff extends PlayerControllerPhaseII {
    private final EquivalenceClassController equivalenceClassController;
    private final PreFlopPersistence preFlopPersistence;

    @Inject
    public PlayerControllerPhaseIIBluff(final EquivalenceClassController equivalenceClassController,
                                        final PreFlopPersistence preFlopPersistence,
                                        final HandStrengthEvaluator handStrengthEvaluator) {
        super(handStrengthEvaluator);

        this.equivalenceClassController = equivalenceClassController;
        this.preFlopPersistence = preFlopPersistence;
    }

    @Override
    public String toString() {
        return "PhaseII bluff";
    }

    @Override
    public BettingDecision decidePreFlop(Player player, GameHand gameHand, List<Card> cards) {
        Card card1 = cards.get(0);
        Card card2 = cards.get(1);
        EquivalenceClass equivalenceClass = this.equivalenceClassController.cards2Equivalence(card1, card2);
        double percentageOfWins = preFlopPersistence.retrieve(gameHand.getPlayers().size(), equivalenceClass);

        if (percentageOfWins > 0.6 || percentageOfWins < 0.1)
            return BettingDecision.RAISE;
        else if (percentageOfWins < 0.45)
            return BettingDecision.FOLD;
        return BettingDecision.CALL;
    }

    @Override
    public BettingDecision decideAfterFlop(Player player, GameHand gameHand, List<Card> cards) {
        double p = calculateCoefficient(gameHand, player);

        // Bluff
        if(p < 0.2){
            if(gameHand.getBettingRoundName().equals(BettingRoundName.POST_FLOP)){
                return BettingDecision.RAISE;
            }
            else if(ContextPlayers.valueFor(gameHand.getPlayersCount()).equals(ContextPlayers.FEW)){
                // Not too much player in post-turn and post-river
                return BettingDecision.RAISE;
            }
            else{
                return BettingDecision.FOLD;
            }
        }

        if (p > 0.8) {
            return BettingDecision.RAISE;
        } else if (p > 0.4 || canCheck(gameHand, player)) {
            return BettingDecision.CALL;
        }
        return BettingDecision.FOLD;
    }
}
