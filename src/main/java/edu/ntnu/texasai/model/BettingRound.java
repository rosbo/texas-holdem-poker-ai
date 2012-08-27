package edu.ntnu.texasai.model;

import java.util.HashMap;
import java.util.Map;

public class BettingRound {
    private final Map<Player, Integer> playerBets = new HashMap<Player, Integer>();

    public void placeBet(Player player, Integer bet){
        player.removeMoney(bet);

        Integer playerBet = playerBets.get(player);
        if(playerBet == null){
            playerBets.put(player, bet);
        } else{
            playerBet += bet;
            playerBets.put(player, playerBet);
        }
    }
}
