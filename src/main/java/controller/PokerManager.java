package main.java.controller;

import main.java.model.Game;
import main.java.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PokerManager {
    public static final int INITIAL_AMOUNT_OF_MONEY = 1000;

    private final Game game;

    public PokerManager(final Integer numberOfPlayers) {
        List<Player> players = new ArrayList<Player>();
        for(int i = 0; i <= numberOfPlayers; i++){
            players.add(new Player(i, INITIAL_AMOUNT_OF_MONEY));
        }

        game = new Game(players);
    }

    public void playHand() {
        game.playNewHand();
    }
}
