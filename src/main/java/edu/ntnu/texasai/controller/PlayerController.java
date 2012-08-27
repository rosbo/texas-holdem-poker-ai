package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.Card;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;

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

    public abstract BettingDecision decidePreFlop(Player player, GameHand gameHand, List<Card> cards);

    public abstract BettingDecision decideAfterFlop(Player player, GameHand gameHand, List<Card> cards);
}
