package edu.ntnu.texasai.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import edu.ntnu.texasai.model.HandPower;
import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.Deck;

public class HandStrengthEvaluator {

    private final HandPowerRanker handPowerRanker;

    @Inject
    public HandStrengthEvaluator(final HandPowerRanker handPowerRanker) {
        this.handPowerRanker = handPowerRanker;
    }

    public Double evaluate(List<Card> playerHoleCards, List<Card> sharedCards, Integer numberOfPlayers) {
        int wins = 0;
        int losses = 0;
        int ties = 0;
        
        Deck deck = new Deck();
        Card hole1 = playerHoleCards.get(0);
        Card hole2 = playerHoleCards.get(1);
        deck.removeCard(hole1);
        deck.removeCard(hole2);
        for (Card card : sharedCards) {
            if (card != null) {
                deck.removeCard(card);
            }
        }
        
        List<List<Card>> couplesOfCards = deck.fromDeckToCouplesOfCard();

        List<Card> playerCards = new ArrayList<Card>();
        playerCards.addAll(playerHoleCards);
        playerCards.addAll(sharedCards);
        HandPower playerRank = handPowerRanker.rank(playerCards);

        for (List<Card> couple : couplesOfCards) {
            List<Card> opponentCards = new ArrayList<Card>();
            opponentCards.addAll(couple);
            opponentCards.addAll(sharedCards);
            for (Card card : sharedCards) {
                opponentCards.add(card);
            }
            HandPower opponentRank = handPowerRanker.rank(opponentCards);
            int result = playerRank.compareTo(opponentRank);
            if (result > 0) {
                wins++;
            } else if (result < 0) {
                losses++;
            } else {
                ties++;
            }
        }
        return calculateHandStrength(wins, ties, losses, numberOfPlayers);
    }

    private Double calculateHandStrength(int wins, int ties, int losses, int numberOfPlayers) {
        double num = (wins + 0.5 * ties);
        double den = (wins + losses + ties);
        double handStrength = Math.pow(num / den, numberOfPlayers);
        return new Double(handStrength);
    }

}
