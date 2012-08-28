package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.Game;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.utils.GameProperties;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PokerController {
    private final Game game;
    private final GameProperties gameProperties;
    private final GameHandController gameHandController;

    @Inject
    public PokerController(final GameHandController gameHandController, final GameProperties gameProperties) {
        this.gameHandController = gameHandController;
        this.gameProperties = gameProperties;

        game = createGame(gameProperties);
    }

    private Game createGame(GameProperties gameProperties) {
        List<Player> players = new ArrayList<Player>();
        for (int i = 0; i <= gameProperties.getNumberOfPlayers(); i++) {
            players.add(new Player(i, gameProperties.getInitialMoney()));
        }

        return new Game(players);
    }

    public void play() {
        for (int i = 0; i <= gameProperties.getNumberOfHands(); i++) {
            gameHandController.play(game);
            game.setNextDealer();
        }
    }
}
