package edu.ntnu.texasai.preflopsim;

import java.util.List;

import javax.inject.Inject;

import edu.ntnu.texasai.controller.HandPowerRanker;
import edu.ntnu.texasai.controller.PlayerController;
import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.HandPower;
import edu.ntnu.texasai.model.HandPowerType;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.Card;

/**
 * A naive player that cannot fold but only bet. Used during pre flop rollout
 * simulations
 * */
public class PlayerControllerPreFlopRoll extends PlayerController {
    private final HandPowerRanker handPowerRanker;

    @Inject
    public PlayerControllerPreFlopRoll(final HandPowerRanker handPowerRanker) {
        this.handPowerRanker = handPowerRanker;
    }

    @Override
    public BettingDecision decidePreFlop(Player player, GameHand gameHand,
            List<Card> cards) {
        Card card1 = cards.get(0);
        Card card2 = cards.get(1);

        if (card1.getNumber().equals(card2.getNumber())) {
            return BettingDecision.RAISE;
        } else if (card1.getNumber().getPower() + card2.getNumber().getPower() > 16
                || canCheck(gameHand, player)) {
            return BettingDecision.CALL;
        } else {
            return BettingDecision.CALL;
        }
    }

    @Override
    public BettingDecision decideAfterFlop(Player player, GameHand gameHand,
            List<Card> cards) {
        HandPower handPower = handPowerRanker.rank(cards);

        HandPowerType handPowerType = handPower.getHandPowerType();
        if (handPowerType.equals(HandPowerType.HIGH_CARD)) {
            if (canCheck(gameHand, player)) {
                return BettingDecision.CALL;
            }
            return BettingDecision.CALL;
        } else if (handPowerType.getPower() >= HandPowerType.THREE_OF_A_KIND.getPower()) {
            return BettingDecision.RAISE;
        } else {
            return BettingDecision.CALL;
        }
    }
}
