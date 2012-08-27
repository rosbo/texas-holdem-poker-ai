package edu.ntnu.texasai.model;

import java.util.ArrayList;
import java.util.List;

public class GameHand {
    private final List<Player> players;
    private final Deck deck;
    private Integer currentPlayer = 0;
    private List<Card> sharedCards = new ArrayList<Card>();

    public GameHand(List<Player> players) {
        this.players = new ArrayList<Player>(players);

        deck = new Deck();
    }

    public void dealHoleCards() {
        for (Player player : players) {
            Card hole1 = deck.removeTopCard();
            Card hole2 = deck.removeTopCard();

            player.setHoleCards(hole1, hole2);
        }
    }

    public void dealFlop(){
        // TODO:
    }

    public void dealTurn(){
        // TODO:
    }

    public void dealRiver(){
        // TODO:
    }

    public Player getNextPlayer(){
        Player nextPlayer = players.get(currentPlayer % players.size());
        currentPlayer++;

        return nextPlayer;
    }

    public List<Card> getSharedCards() {
        return sharedCards;
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
}
