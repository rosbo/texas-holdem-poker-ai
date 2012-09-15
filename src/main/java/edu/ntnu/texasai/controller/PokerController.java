package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.Game;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.gameproperties.GameProperties;
import edu.ntnu.texasai.utils.Logger;

import javax.inject.Inject;

public class PokerController {
    private final Game game;
    private final Logger logger;
    private final GameProperties gameProperties;
    private final GameHandController gameHandController;

    @Inject
    public PokerController(final GameHandController gameHandController,
                           final Logger logger, final GameProperties gameProperties) {
        this.gameHandController = gameHandController;
        this.logger = logger;
        this.gameProperties = gameProperties;

        game = new Game(gameProperties.getPlayers());
    }

    public void play() {
        for (int i = 0; i < gameProperties.getNumberOfHands(); i++) {
            gameHandController.play(game);
            game.setNextDealer();
        }

        printFinalStats();
    }

    private void printFinalStats() {
        logger.log("-----------------------------------------");
        logger.log("Statistics");
        logger.log("-----------------------------------------");
        logger.log("Number of hands played: " + game.gameHandsCount());
        for (Player player : game.getPlayers()) {
            logger.log(player.toString() + "(" + player.getPlayerController().toString() + ")" + ": " + player
                    .getMoney() + "$");
        }
    }
}
