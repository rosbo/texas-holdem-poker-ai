package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.Game;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.utils.GameProperties;

import java.util.ArrayList;
import java.util.List;

public class PokerController {
    private final Game game;
    private final GameProperties gameProperties;
    private final GameHandController gameHandController;

    public PokerController(final Integer numberOfPlayers) {
        // TODO: Add DI
        gameProperties = new GameProperties();
        gameHandController = new GameHandController();

        List<Player> players = new ArrayList<Player>();
        for (int i = 0; i <= numberOfPlayers; i++) {
            players.add(new Player(i, gameProperties.getInitialMoney()));
        }

        game = new Game(players);
    }

    public void play() {
        for (int i = 0; i <= gameProperties.getNumberOfHands(); i++) {
            gameHandController.play(game);
            game.setNextDealer();
        }
    }
}
