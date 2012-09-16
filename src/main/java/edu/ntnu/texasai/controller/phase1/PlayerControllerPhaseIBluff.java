package edu.ntnu.texasai.controller.phase1;

import edu.ntnu.texasai.controller.HandPowerRanker;
import edu.ntnu.texasai.controller.PlayerController;
import edu.ntnu.texasai.model.*;
import edu.ntnu.texasai.model.cards.Card;

import javax.inject.Inject;
import java.util.List;

public class PlayerControllerPhaseIBluff extends PlayerController {
    private final HandPowerRanker handPowerRanker;

    @Inject
    public PlayerControllerPhaseIBluff(final HandPowerRanker handPowerRanker) {
        this.handPowerRanker = handPowerRanker;
    }

    @Override
    public String toString() {
        return "PhaseI bluff";
    }

    @Override
    public BettingDecision decidePreFlop(Player player, GameHand gameHand,
                                         List<Card> cards) {
        Card card1 = cards.get(0);
        Card card2 = cards.get(1);
        int sumPower = card1.getNumber().getPower() + card2.getNumber().getPower();

        if (card1.getNumber().equals(card2.getNumber()) || sumPower <= 8) {
            return BettingDecision.RAISE;
        } else {
            if (sumPower > 16 || canCheck(gameHand, player)) {
                return BettingDecision.CALL;
            } else {
                return BettingDecision.FOLD;
            }
        }
    }

    @Override
    public BettingDecision decideAfterFlop(Player player, GameHand gameHand,
                                           List<Card> cards) {
        HandPower handPower = handPowerRanker.rank(cards);

        HandPowerType handPowerType = handPower.getHandPowerType();
        if (handPowerType.equals(HandPowerType.HIGH_CARD)) {
            return BettingDecision.RAISE;
        } else if (handPowerType.getPower() >= HandPowerType.STRAIGHT.getPower()) {
            return BettingDecision.RAISE;
        } else {
            if(canCheck(gameHand, player)){
                return BettingDecision.CALL;
            }
            return BettingDecision.FOLD;
        }
    }
}
