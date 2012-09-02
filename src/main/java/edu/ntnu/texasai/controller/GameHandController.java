package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.*;
import edu.ntnu.texasai.utils.GameProperties;

import javax.inject.Inject;

public class GameHandController {
    private final PlayerController playerController;
    private final GameProperties gameProperties;

    @Inject
    public GameHandController(final PlayerController playerController, final GameProperties gameProperties) {
        this.playerController = playerController;
        this.gameProperties = gameProperties;
    }

    public void play(Game game) {
        // TODO: Extract using a injected logger so we will be able to swap between log type (file or console)
        System.out.println("Game Hand #" + (game.gameHandsCount() + 1));
        GameHand gameHand = createGameHand(game);

        playPreFlop(gameHand);

        // TODO: Pre-turn

        // TODO: Pre-river

        // TODO: Post-river
    }

    private void playPreFlop(GameHand gameHand) {
        System.out.println("Pre Flop");
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

        System.out.println(smallBlindPlayer.toString() + ": Small blind");
        System.out.println(bigBlindPlayer.toString() + ": Big blind");

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

        System.out.println(player.toString() + ": " + bettingDecision.toString());
    }

    private GameHand createGameHand(Game game) {
        GameHand gameHand = new GameHand(game.getPlayersFromTheLeftOfTheDealer());
        game.addGameHand(gameHand);
        return gameHand;
    }
}
