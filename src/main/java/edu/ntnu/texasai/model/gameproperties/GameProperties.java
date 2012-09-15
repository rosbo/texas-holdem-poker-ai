package edu.ntnu.texasai.model.gameproperties;

import edu.ntnu.texasai.model.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class GameProperties {
    private final int smallBlind;
    private final int bigBlind;
    private final int initialMoney;
    private final int numberOfHands;
    private final List<Player> players = new ArrayList<Player>();

    protected GameProperties(int numberOfHands, int initialMoney, int bigBlind, int smallBlind) {
        this.numberOfHands = numberOfHands;
        this.initialMoney = initialMoney;
        this.bigBlind = bigBlind;
        this.smallBlind = smallBlind;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public int getInitialMoney() {
        return initialMoney;
    }

    public int getNumberOfHands() {
        return numberOfHands;
    }

    public List<Player> getPlayers() {
        return players;
    }

    protected void addPlayer(Player player){
        players.add(player);
    }
}
