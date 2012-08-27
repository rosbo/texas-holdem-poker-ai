package edu.ntnu.texasai.model;

import edu.ntnu.texasai.utils.GameProperties;

import java.util.List;

public class GameHand {
    private final List<Player> players;
    private final GameProperties gameProperties;
    private final Deck deck;
    private Integer currentPlayer = 0;

    public GameHand(List<Player> players, GameProperties gameProperties) {
        this.players = players;
        this.gameProperties = gameProperties;

        deck = new Deck();
    }

    public void play() {
        dealHoleCards();

        BettingRound preFlopBettingRound = new BettingRound();
        takeBlinds(preFlopBettingRound);

        // Pre-flop

        // Pre-turn

        // Pre-river

        // Post-river
    }

    private void dealHoleCards() {
        for (Player player : players) {
            Card hole1 = deck.removeTopCard();
            Card hole2 = deck.removeTopCard();

            player.setHoleCards(hole1, hole2);
        }
    }

    private void takeBlinds(BettingRound preFlopBettingRound) {
        preFlopBettingRound.placeBet(nextPlayer(), gameProperties.getSmallBlind());
        preFlopBettingRound.placeBet(nextPlayer(), gameProperties.getBigBlind());
    }

    private Player nextPlayer(){
        Player nextPlayer = players.get(currentPlayer % players.size());
        currentPlayer++;

        return nextPlayer;
    }
}
