package edu.ntnu.texasai.controller;

import com.google.inject.Inject;
import edu.ntnu.texasai.model.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsController {

    private Map<Integer, Integer> players2wins;
    private Map<Integer, Integer> players2ties = new HashMap<Integer, Integer>();
    private int player0winnings;

    @Inject
    public StatisticsController() {
        this.players2wins = new HashMap<Integer, Integer>();
        player0winnings = 0;
    }

    public void initializeStatistics() {
        for (int i = 0; i <= 10; i++) {
            this.players2wins.put(i, 0);
            this.players2ties.put(i, 0);
        }
        player0winnings = 0;
    }

    public void storeWinners(List<Player> winners) {        
        for (Player winner : winners) {
            if (winner.getNumber() == 0) {
                player0winnings++;
            }
        }
    }

    public int getPlayer0Wins() {
        return player0winnings;
    }

}
