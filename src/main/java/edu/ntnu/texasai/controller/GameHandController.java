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
        logger.log("-----------------------------------------");
        logger.log("Game Hand #" + (game.gameHandsCount() + 1));
        logger.log("-----------------------------------------");
        GameHand gameHand = createGameHand(game);

        Boolean hadWinner = false;
        int i = 0;
        while (i < 4 && !hadWinner) {
            hadWinner = playRound(gameHand);
            i++;
        }

        // TODO: Check the stronger
    }

    private Boolean playRound(GameHand gameHand) {
        gameHand.nextRound();
        logBettingRound(gameHand);
        if (gameHand.getBettingRoundCount().equals(1)) {
            takeBlinds(gameHand);
        }

        Integer turn = 1;
        Integer numberOfPlayersAtBeginningOfRound = gameHand.getPlayersCount();
        Integer toPlay = gameHand.getPlayersCount() - 1;
        while (toPlay > 0) {
            Player player = gameHand.getNextPlayer();
            BettingDecision bettingDecision = playerController.decide(player, gameHand);

            // We can't raise at second turn
            if (turn > numberOfPlayersAtBeginningOfRound && bettingDecision.equals(BettingDecision.RAISE)) {
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

        // Check if we have a winner
        if (gameHand.getPlayersCount() == 1) {
            Player winner = gameHand.getCurrentPlayer();
            winner.addMoney(gameHand.getTotalBets());
            return true;
        }
        return false;
    }

    private void logBettingRound(GameHand gameHand) {
        String bettingRoundName = getBettingRoundName(gameHand);
        logger.log(bettingRoundName + " (" + gameHand.getPlayersCount() + " players, " +
                "" + gameHand.getTotalBets() + "$)");

        if (!gameHand.getSharedCards().isEmpty()) {
            StringBuilder sharedCardSB = new StringBuilder();
            sharedCardSB.append("Shared cards: ");
            int i = 0;
            for (Card card : gameHand.getSharedCards()) {
                if (i > 0) {
                    sharedCardSB.append(", ");
                }
                sharedCardSB.append(card.toString());
                i++;
            }
            logger.log(sharedCardSB.toString());
        }
    }

    private String getBettingRoundName(GameHand gameHand) {
        switch (gameHand.getBettingRoundCount()) {
            case 1:
                return "Pre-flop";
            case 2:
                return "Post-flop";
            case 3:
                return "Post-turn";
            case 4:
                return "Post-river";
        }
        throw new IllegalArgumentException("Too many betting rounds");
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
