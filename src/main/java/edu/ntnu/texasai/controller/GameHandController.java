package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.*;
import edu.ntnu.texasai.utils.GameProperties;
import edu.ntnu.texasai.utils.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GameHandController {
    private final Logger logger;
    private final PlayerController playerController;
    private final HandPowerRanker handPowerRanker;
    private final GameProperties gameProperties;

    @Inject
    public GameHandController(final Logger logger, final PlayerController playerController,
                              final HandPowerRanker handPowerRanker, final GameProperties gameProperties) {
        this.logger = logger;
        this.playerController = playerController;
        this.handPowerRanker = handPowerRanker;
        this.gameProperties = gameProperties;
    }

    public void play(Game game) {
        logger.log("-----------------------------------------");
        logger.log("Game Hand #" + (game.gameHandsCount() + 1));
        logger.log("-----------------------------------------");
        GameHand gameHand = createGameHand(game);

        Boolean hadWinner = false;
        while (gameHand.getBettingRoundCount() < 4 && !hadWinner) {
            hadWinner = playRound(gameHand);
        }

        if (!hadWinner) {
            showDown(gameHand);
        }
    }

    private void showDown(GameHand gameHand) {
        // Showdown
        List<Player> winners = getWinners(gameHand);

        // Gains
        int gain = gameHand.getTotalBets() / winners.size();
        int modulo = gameHand.getTotalBets() % winners.size();
        for (Player winner : winners) {
            int gainAndModulo = gain;
            if (modulo > 0) {
                gainAndModulo += modulo;
            }
            winner.addMoney(gainAndModulo);
            logger.log(winner.toString() + ": WIN! +" + gainAndModulo + "$");

            modulo--;
        }
    }

    private List<Player> getWinners(GameHand gameHand) {
        logger.log("--- Showdown");
        Iterable<Player> activePlayers = gameHand.getActivePlayers();
        List<Card> sharedCards = gameHand.getSharedCards();

        HandPower bestHandPower = null;
        List<Player> winners = new ArrayList<Player>();
        for (Player player : activePlayers) {
            List<Card> mergeCards = new ArrayList<Card>(player.getHoleCards());
            mergeCards.addAll(sharedCards);
            HandPower handPower = handPowerRanker.rank(mergeCards);

            logger.log(player.toString() + ": " + handPower.toString());

            if (bestHandPower == null || handPower.compareTo(bestHandPower) > 0) {
                winners.clear();
                winners.add(player);
                bestHandPower = handPower;
            } else if (handPower.equals(bestHandPower)) {
                winners.add(player);
            }
        }
        return winners;
    }

    private Boolean playRound(GameHand gameHand) {
        gameHand.nextRound();
        logBettingRound(gameHand);
        Integer toPlay = gameHand.getPlayersCount();
        if (gameHand.getBettingRoundCount().equals(1)) {
            takeBlinds(gameHand);
            toPlay--; // Big blinds don't have to call on himself if no raise :)
        }

        Integer turn = 1;
        Integer numberOfPlayersAtBeginningOfRound = gameHand.getPlayersCount();
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
            logger.log(winner.toString() + ": WIN! +" + gameHand.getTotalBets() + "$");
            return true;
        }
        return false;
    }

    private void logBettingRound(GameHand gameHand) {
        String bettingRoundName = getBettingRoundName(gameHand);
        String logMsg = "---" + bettingRoundName;
        logMsg += " (" + gameHand.getPlayersCount() + " players, ";
        logMsg += gameHand.getTotalBets() + "$)";
        if (!gameHand.getSharedCards().isEmpty()) {
            logMsg += " " + gameHand.getSharedCards().toString();
        }
        logger.log(logMsg);
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

        logger.log(smallBlindPlayer.toString() + ": Small blind " + gameProperties.getSmallBlind() + "$");
        logger.log(bigBlindPlayer.toString() + ": Big blind " + gameProperties.getBigBlind() + "$");

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

        logger.log(player.toString() + ": " + bettingDecision.toString() + " " + bettingRound.getBetForPlayer(player)
                + "$");
    }

    private GameHand createGameHand(Game game) {
        GameHand gameHand = new GameHand(game.getPlayers());
        game.addGameHand(gameHand);
        return gameHand;
    }
}
