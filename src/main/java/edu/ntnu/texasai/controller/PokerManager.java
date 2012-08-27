package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.Game;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.utils.GameProperties;

import java.util.ArrayList;
import java.util.List;

public class PokerManager {
    private final Game game;

    public PokerManager(final Integer numberOfPlayers) {
        // TODO: Add DI
        GameProperties gameProperties = new GameProperties();

        List<Player> players = new ArrayList<Player>();
        for (int i = 0; i <= numberOfPlayers; i++) {
            players.add(new Player(i, gameProperties.getInitialMoney()));
        }

        game = new Game(players, gameProperties);
    }

    public void play() {
        game.play();
    }
}
