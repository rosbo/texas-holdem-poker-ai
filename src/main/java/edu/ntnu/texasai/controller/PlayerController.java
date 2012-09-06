package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.*;
import edu.ntnu.texasai.model.cards.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerController {
    public BettingDecision decide(Player player, GameHand gameHand){
        List<Card> cards = new ArrayList<Card>();
        cards.addAll(gameHand.getSharedCards());
        cards.addAll(player.getHoleCards());

        if(cards.size() == 2){
            return decidePreFlop(player, gameHand, cards);
        } else{
            return decideAfterFlop(player, gameHand, cards);
        }
    }

    protected boolean canCheck(GameHand gameHand, Player player) {
        BettingRound bettingRound = gameHand.getCurrentBettingRound();
        return bettingRound.getHighestBet().equals(bettingRound.getBetForPlayer(player));
    }

    protected abstract BettingDecision decidePreFlop(Player player, GameHand gameHand, List<Card> cards);

    protected abstract BettingDecision decideAfterFlop(Player player, GameHand gameHand, List<Card> cards);
}
