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
        GameHand gameHand = createGameHand(game);

        gameHand.dealHoleCards();
        playPreFlop(gameHand);

        // TODO: Pre-turn

        // TODO: Pre-river

        // TODO: Post-river
    }

    private void playPreFlop(GameHand gameHand) {
        BettingRound preFlopBettingRound = new BettingRound();
        preFlopBettingRound.placeBet(gameHand.getNextPlayer(), gameProperties.getSmallBlind());
        preFlopBettingRound.placeBet(gameHand.getNextPlayer(), gameProperties.getBigBlind());

        // TODO: Play more turns

        Player player = gameHand.getNextPlayer();
        BettingDecision bettingDecision = playerController.decide(player, gameHand);

        applyDecision(gameHand, preFlopBettingRound, player, bettingDecision);
    }

    private void applyDecision(GameHand gameHand, BettingRound preFlopBettingRound, Player player, BettingDecision
            bettingDecision) {
        Integer highestBet = preFlopBettingRound.getHighestBet();
        switch (bettingDecision){
            case FOLD:
                gameHand.removePlayer(player);
                break;
            case CALL:
                preFlopBettingRound.placeBet(player, highestBet);
                break;
            case RAISE:
                preFlopBettingRound.placeBet(player, highestBet + gameProperties.getBigBlind());
                break;
        }
    }

    private GameHand createGameHand(Game game) {
        GameHand gameHand = new GameHand(game.getPlayersFromTheLeftOfTheDealer());
        game.addGameHand(gameHand);
        return gameHand;
    }
}
