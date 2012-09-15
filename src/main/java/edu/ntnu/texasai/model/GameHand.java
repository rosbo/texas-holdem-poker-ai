package edu.ntnu.texasai.model;

import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.Deck;
import edu.ntnu.texasai.model.opponentmodeling.ContextAction;
import edu.ntnu.texasai.model.opponentmodeling.ContextInformation;
import edu.ntnu.texasai.utils.GameProperties;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GameHand {
    private final Deque<Player> players;
    private final Deck deck;
    private final List<Card> sharedCards = new ArrayList<Card>();
    private final List<BettingRound> bettingRounds = new ArrayList<BettingRound>();
    private Boolean hasRemoved = true;

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

    public int getTotalBets() {
        int totalBets = 0;
        for (BettingRound bettingRound : bettingRounds) {
            totalBets += bettingRound.getTotalBets();
        }
        return totalBets;
    }

    public BettingRoundName getBettingRoundName() {
        return BettingRoundName.fromRoundNumber(bettingRounds.size());
    }

    public Player getCurrentPlayer() {
        return players.getFirst();
    }

    public List<Card> getSharedCards() {
        return sharedCards;
    }

    public int getPlayersCount() {
        return players.size();
    }

    public BettingRound getCurrentBettingRound() {
        return bettingRounds.get(bettingRounds.size() - 1);
    }

    public List<BettingRound> getBettingRounds() {
        return bettingRounds;
    }

    public void removeCurrentPlayer() {
        players.removeFirst();
        hasRemoved = true;
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

    public Deque<Player> getPlayers() {
        return this.players;
    }

    public void applyDecision(Player player, BettingDecision bettingDecision, GameProperties gameProperties,
                              double handStrength) {
        BettingRound currentBettingRound = getCurrentBettingRound();
        double potOdds = calculatePotOdds(player);
        ContextAction contextAction = new ContextAction(player, bettingDecision, getBettingRoundName(),
                currentBettingRound.getNumberOfRaises(),
                getPlayersCount(), potOdds);
        ContextInformation contextInformation = new ContextInformation(contextAction, handStrength);

        currentBettingRound.applyDecision(contextInformation, gameProperties);

        if (bettingDecision.equals(BettingDecision.FOLD)) {
            removeCurrentPlayer();
        }
    }

    public double calculatePotOdds(Player player) {
        BettingRound currentBettingRound = getCurrentBettingRound();
        int amountNeededToCall = currentBettingRound.getHighestBet() - currentBettingRound.getBetForPlayer(player);
        return (double) amountNeededToCall / (amountNeededToCall + getTotalBets());
    }

    protected Deck getDeck() {
        return deck;
    }
}
