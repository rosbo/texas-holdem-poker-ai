package edu.ntnu.texasai.model;

import java.util.HashMap;
import java.util.Map;

public class BettingRound {
    private final Map<Player, Integer> playerBets = new HashMap<Player, Integer>();
    private Integer highestBet = 0;

    public void placeBet(Player player, Integer bet) {
        Integer playerBet = playerBets.get(player);

        if (playerBet == null) {
            player.removeMoney(bet);
        } else {
            player.removeMoney(playerBet - bet);
        }

        if (bet >= highestBet) {
            highestBet = bet;
        } else {
            throw new IllegalArgumentException("You can't bet less than the higher bet");
        }

        playerBets.put(player, bet);
    }

    public Integer getHighestBet() {
        return highestBet;
    }

    public Integer getBetForPlayer(Player player) {
        Integer bet = playerBets.get(player);
        if (bet == null) {
            return 0;
        }
        return bet;
    }

    public Integer getTotalBets() {
        Integer totalBets = 0;
        for (Integer bet : playerBets.values()) {
            totalBets += bet;
        }
        return totalBets;
    }
}
