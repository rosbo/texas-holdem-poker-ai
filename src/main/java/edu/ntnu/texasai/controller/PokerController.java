package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.Game;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.utils.GameProperties;
import edu.ntnu.texasai.utils.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PokerController {
    private final Game game;
    private final Logger logger;
    private final GameProperties gameProperties;
    private final GameHandController gameHandController;

    @Inject
    public PokerController(final GameHandController gameHandController, final Logger logger,
                           final GameProperties gameProperties) {
        this.gameHandController = gameHandController;
        this.logger = logger;
        this.gameProperties = gameProperties;

        game = createGame();
    }

    public void play() {
        for (int i = 0; i < gameProperties.getNumberOfHands(); i++) {
            gameHandController.play(game);
            game.setNextDealer();
        }

        printStats();
    }

    private Game createGame() {
        List<Player> players = createPlayers();

        return new Game(players);
    }

    private List<Player> createPlayers() {
        List<Player> players = new ArrayList<Player>();
        for (int i = 1; i <= gameProperties.getNumberOfPlayers(); i++) {
            players.add(new Player(i, gameProperties.getInitialMoney()));
        }
        return players;
    }

    private void printStats() {
        logger.log("-----------------------------------------");
        logger.log("Statistics");
        logger.log("-----------------------------------------");
        logger.log("Number of hands played: " + game.gameHandsCount());
        for (Player player : game.getPlayers()) {
            logger.log(player.toString() + ": " + player.getMoney() + "$");
        }
    }
}
