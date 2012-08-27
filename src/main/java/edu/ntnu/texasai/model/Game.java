package edu.ntnu.texasai.model;

import java.util.Iterator;
import java.util.List;

public class Game {
    private final List<Player> players;
    private Deck deck;
    private Iterator<Player> dealer;
    private List<Player> orders;

    public Game(List<Player> players) {
        this.players = players;
    }

    public void playNewHand() {
        deck = new Deck();

        takeBlinds();

        // You take blind

        // Distribute hole card

        // Pre-flop

        setNextDealer();
    }

    private void takeBlinds() {
        Player smallBlindPlayer = players.get(0);
        Player bigBlindPlayer = players.get(1);

        //smallBlindPlayer.removeMoney();
    }

    private void setNextDealer() {
        Player formerDealer = players.remove(0);
        players.add(formerDealer);
    }
}
