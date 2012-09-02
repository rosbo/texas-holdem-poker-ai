package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.*;
import edu.ntnu.texasai.utils.GameProperties;
import edu.ntnu.texasai.utils.Logger;

import javax.inject.Inject;

public class GameHandController {
    private final Logger logger;
    private final PlayerController playerController;
    private final GameProperties gameProperties;

    @Inject
    public GameHandController(final Logger logger, final PlayerController playerController,
                              final GameProperties gameProperties) {
        this.logger = logger;
        this.playerController = playerController;
        this.gameProperties = gameProperties;
    }

    public void play(Game game) {
        logger.log("Game Hand #" + (game.gameHandsCount() + 1));
        GameHand gameHand = createGameHand(game);

        playPreFlop(gameHand);

        // TODO: Pre-turn

        // TODO: Pre-river

        // TODO: Post-river
    }

    private void playPreFlop(GameHand gameHand) {
        logger.log("Pre Flop");
        gameHand.dealHoleCards();
        takeBlinds(gameHand);

        Integer turn = 1;
        Integer numberOfPlayersAtBeginning = gameHand.getPlayersCount();
        Integer toPlay = gameHand.getPlayersCount() - 1;
        while (toPlay > 0) {
            Player player = gameHand.getNextPlayer();
            BettingDecision bettingDecision = playerController.decide(player, gameHand);

            // We can't raise at second turn
            if (turn > numberOfPlayersAtBeginning && bettingDecision.equals(BettingDecision.RAISE)) {
                bettingDecision = BettingDecision.CALL;
            }

            // After a raise, every active players after the raiser must play
            if (bettingDecision.equals(BettingDecision.RAISE)) {
                toPlay = gameHand.getPlayersCount() - 1;
            }

            applyDecision(gameHand, player, bettingDecision);
            turn++;
            toPlay--;
        }
    }

    private void takeBlinds(GameHand gameHand) {
        Player smallBlindPlayer = gameHand.getNextPlayer();
        Player bigBlindPlayer = gameHand.getNextPlayer();

        logger.log(smallBlindPlayer.toString() + ": Small blind");
        logger.log(bigBlindPlayer.toString() + ": Big blind");

        gameHand.getCurrentBettingRound().placeBet(smallBlindPlayer, gameProperties.getSmallBlind());
        gameHand.getCurrentBettingRound().placeBet(bigBlindPlayer, gameProperties.getBigBlind());
    }

    private void applyDecision(GameHand gameHand, Player player, BettingDecision
            bettingDecision) {
        BettingRound bettingRound = gameHand.getCurrentBettingRound();
        Integer highestBet = bettingRound.getHighestBet();
        switch (bettingDecision) {
            case FOLD:
                gameHand.removeCurrentPlayer();
                break;
            case CALL:
                bettingRound.placeBet(player, highestBet);
                break;
            case RAISE:
                bettingRound.placeBet(player, highestBet + gameProperties.getBigBlind());
                break;
        }

        logger.log(player.toString() + ": " + bettingDecision.toString());
    }

    private GameHand createGameHand(Game game) {
        GameHand gameHand = new GameHand(game.getPlayersFromTheLeftOfTheDealer());
        game.addGameHand(gameHand);
        return gameHand;
    }
}
