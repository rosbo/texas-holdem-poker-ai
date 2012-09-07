package edu.ntnu.texasai.model;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.Deck;

public class GameHand {
    private final Deque<Player> players;
    private final Deck deck;
    private List<Card> sharedCards = new ArrayList<Card>();
    private List<BettingRound> bettingRounds = new ArrayList<BettingRound>();
    Boolean hasRemoved = true;

    public GameHand(List<Player> players) {
        this.players = new LinkedList<Player>(players);

        deck = new Deck();
    }

    public void nextRound() {
        bettingRounds.add(new BettingRound());

        if (getBettingRoundName().equals(BettingRoundName.PRE_FLOP)) {
            dealHoleCards();
        } else if (getBettingRoundName().equals(BettingRoundName.POST_FLOP)) {
            dealFlopCards();
        } else {
            dealSharedCard();
        }
    }

    public Player getNextPlayer() {
        if (!hasRemoved) {
            Player player = players.removeFirst();
            players.addLast(player);
        }
        hasRemoved = false;
        return getCurrentPlayer();
    }

    public Integer getTotalBets() {
        Integer totalBets = 0;
        for (BettingRound bettingRound : bettingRounds) {
            totalBets += bettingRound.getTotalBets();
        }
        return totalBets;
    }

    public BettingRoundName getBettingRoundName(){
        return BettingRoundName.fromRoundNumber(bettingRounds.size());
    }

    public Player getCurrentPlayer() {
        return players.getFirst();
    }

    public List<Card> getSharedCards() {
        return sharedCards;
    }

    public Integer getPlayersCount() {
        return players.size();
    }

    public BettingRound getCurrentBettingRound() {
        return bettingRounds.get(bettingRounds.size() - 1);
    }

    public void removeCurrentPlayer() {
        players.removeFirst();
        hasRemoved = true;
    }

    public Iterable<Player> getActivePlayers() {
        return players;
    }

    protected void dealHoleCards() {
        for (Player player : players) {
            Card hole1 = deck.removeTopCard();
            Card hole2 = deck.removeTopCard();

            player.setHoleCards(hole1, hole2);
        }
    }

    private void dealFlopCards() {
        sharedCards.add(deck.removeTopCard());
        sharedCards.add(deck.removeTopCard());
        sharedCards.add(deck.removeTopCard());
    }

    private void dealSharedCard() {
        sharedCards.add(deck.removeTopCard());
    }
    
    public Deque<Player> getPlayers(){
    	return this.players;
    }
    
    public Deck getDeck(){
    	return this.deck;
    }
}
