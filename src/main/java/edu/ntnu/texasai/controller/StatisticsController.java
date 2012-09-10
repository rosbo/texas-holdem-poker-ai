package edu.ntnu.texasai.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import edu.ntnu.texasai.model.Player;

public class StatisticsController {

    private Map<Integer, Integer> players2wins;
    private Map<Integer, Integer> players2ties = new HashMap<Integer, Integer>();
    private Integer player0winnings;

    @Inject
    public StatisticsController() {
        this.players2wins = new HashMap<Integer, Integer>();
        player0winnings = new Integer(0);
    }

    public void initializeStatistics() {
        for (int i = 0; i <= 10; i++) {
            this.players2wins.put(i, 0);
            this.players2ties.put(i, 0);
        }
        player0winnings = new Integer(0);
    }

    public void storeWinners(List<Player> winners) {        
        for (Player winner : winners) {
            if (winner.getNumber() == 0) {
                player0winnings++;
            }
        }
    }

    public Map<Integer, Integer> getPlayers2wins() {
        return players2wins;
    }

    public void setPlayers2wins(Map<Integer, Integer> players2wins) {
        this.players2wins = players2wins;
    }

    public Map<Integer, Integer> getPlayers2ties() {
        return players2ties;
    }

    public void setPlayers2ties(Map<Integer, Integer> players2ties) {
        this.players2ties = players2ties;
    }

    public void clearStatistics() {
        this.players2ties.clear();
        this.players2wins.clear();
    }

    public Double getPercentageOfWinsPlayer0(Integer numberOfHands) {       
        return new Double(player0winnings / new Double(numberOfHands));
    }

    public Integer getPlayer0Wins() {
        return player0winnings;
    }


}
