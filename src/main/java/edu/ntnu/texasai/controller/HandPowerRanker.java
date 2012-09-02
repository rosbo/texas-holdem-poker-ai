package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.*;
import edu.ntnu.texasai.utils.MapList;

import java.util.*;

public class HandPowerRanker {
    public HandPower rank(List<Card> cards) {
        MapList<CardNumber, Card> numberGroup = getNumberGroup(cards);
        MapList<CardSuit, Card> suitGroup = getSuitGroup(cards);
        List<Card> cardsSortedByNumber = getCardsSortedByNumber(cards);

        CardSuit flushSuit = getFlush(suitGroup);
        CardNumber straightNumber = getStraight(numberGroup);

        // Straight flush
        if (flushSuit != null && straightNumber != null) {
            return new HandPower(HandPowerType.STRAIGHT_FLUSH, Arrays.asList(straightNumber));
        }

        CardNumber cardNumberForFour = getCardNumberForCount(4, numberGroup);
        // Four of a kind
        if (cardNumberForFour != null) {
            return new HandPower(HandPowerType.FOUR_OF_A_KIND, calculateSameKindTie(4, cardNumberForFour,
                    cardsSortedByNumber));
        }

        CardNumber cardNumberForThree = getCardNumberForCount(3, numberGroup);
        CardNumber cardNumberForTwo = getCardNumberForCount(2, numberGroup);
        // Full house
        if (cardNumberForThree != null && cardNumberForTwo != null) {
            return new HandPower(HandPowerType.FULL_HOUSE, Arrays.asList(cardNumberForThree, cardNumberForTwo));
        }

        // Flush
        if (flushSuit != null) {
            return new HandPower(HandPowerType.FLUSH, calculateFlushTie(flushSuit, suitGroup));
        }


        // Straight
        if (straightNumber != null) {
            return new HandPower(HandPowerType.STRAIGHT, Arrays.asList(straightNumber));
        }

        // Three of a kind
        if (cardNumberForThree != null) {
            return new HandPower(HandPowerType.THREE_OF_A_KIND, calculateSameKindTie(3, cardNumberForThree,
                    cardsSortedByNumber));
        }

        if (cardNumberForTwo != null) {
            List<CardNumber> pairsCardNumber = getPairs(numberGroup);
            // Two pair
            if (pairsCardNumber.size() == 2) {
                return new HandPower(HandPowerType.TWO_PAIR, calculateTwoPairsTie(pairsCardNumber,
                        cardsSortedByNumber));
            }
            // One Pair
            else {
                return new HandPower(HandPowerType.ONE_PAIR, calculateSameKindTie(2, cardNumberForTwo,
                        cardsSortedByNumber));
            }
        }

        // High Card
        return new HandPower(HandPowerType.HIGH_CARD, bestCardsNumberInList(cardsSortedByNumber));
    }

    private List<CardNumber> calculateTwoPairsTie(List<CardNumber> pairsCardNumber, List<Card> cardsSortedByNumber) {
        List<CardNumber> tieBreakingInformation = new ArrayList<CardNumber>();

        CardNumber pairCardNumber1 = pairsCardNumber.get(0);
        CardNumber pairCardNumber2 = pairsCardNumber.get(1);

        if (pairCardNumber1.getPower() > pairCardNumber2.getPower()) {
            tieBreakingInformation.add(pairCardNumber1);
            tieBreakingInformation.add(pairCardNumber2);
        } else {
            tieBreakingInformation.add(pairCardNumber2);
            tieBreakingInformation.add(pairCardNumber1);
        }

        for (int i = cardsSortedByNumber.size() - 1; i >= 0; i--) {
            CardNumber cardNumber = cardsSortedByNumber.get(i).getNumber();
            if (!pairsCardNumber.contains(cardNumber)) {
                tieBreakingInformation.add(cardNumber);
                return tieBreakingInformation;
            }
        }
        return null;
    }

    private List<CardNumber> getPairs(MapList<CardNumber, Card> numberGroup) {
        List<CardNumber> pairsCardNumber = new ArrayList<CardNumber>();
        for (List<Card> cards : numberGroup) {
            if (cards.size() == 2) {
                pairsCardNumber.add(cards.get(0).getNumber());
            }
        }
        return pairsCardNumber;
    }

    private List<CardNumber> calculateFlushTie(CardSuit flushSuit, MapList<CardSuit, Card> suitGroup) {
        List<Card> cards = suitGroup.get(flushSuit);
        Collections.sort(cards);
        return bestCardsNumberInList(cards);
    }

    private List<CardNumber> bestCardsNumberInList(List<Card> cards) {
        List<CardNumber> bestCardsNumber = new ArrayList<CardNumber>();
        int i = 0;
        for (Card card : cards) {
            if (i >= cards.size() - 5) {
                bestCardsNumber.add(card.getNumber());
            }

            i++;
        }
        return bestCardsNumber;
    }

    private List<Card> getCardsSortedByNumber(List<Card> cards) {
        List<Card> cardsSortedByNumber = new ArrayList<Card>(cards);
        Collections.sort(cardsSortedByNumber);

        return cardsSortedByNumber;
    }

    private List<CardNumber> calculateSameKindTie(Integer sameKindCount, CardNumber sameKindCardNumber,
                                                  List<Card> cardsSortedByNumber) {
        List<CardNumber> tieBreakingInformation = new ArrayList<CardNumber>();
        tieBreakingInformation.add(sameKindCardNumber);

        Integer left = 5 - sameKindCount;
        for (int i = cardsSortedByNumber.size()-1; i >= 0; i--) {
            Card card = cardsSortedByNumber.get(i);

            if (!card.getNumber().equals(sameKindCardNumber) && left > 0) {
                tieBreakingInformation.add(card.getNumber());
                left--;
            }
        }

        return tieBreakingInformation;
    }

    private CardNumber getCardNumberForCount(Integer count, MapList<CardNumber, Card> numberGroup) {
        for (Map.Entry<CardNumber, List<Card>> entry : numberGroup.entrySet()) {
            if (entry.getValue().size() == count) {
                return entry.getKey();
            }
        }
        return null;
    }

    private CardNumber getStraight(MapList<CardNumber, Card> numberGroup) {
        CardNumber straightNumber = null;
        Integer straightCount = 1;
        Integer prevPower = 0;
        for (CardNumber cardNumber : numberGroup.keySet()) {
            if (cardNumber.getPower().equals(prevPower + 1)) {
                straightCount++;
                if (straightCount >= 5) {
                    straightNumber = cardNumber;
                }
            } else {
                straightCount = 1;
            }
            prevPower = cardNumber.getPower();
        }
        return straightNumber;
    }

    private CardSuit getFlush(MapList<CardSuit, Card> suitGroup) {
        for (List<Card> cards : suitGroup) {
            if (cards.size() >= 5) {
                return cards.get(0).getSuit();
            }
        }
        return null;
    }

    private MapList<CardNumber, Card> getNumberGroup(List<Card> cards) {
        MapList<CardNumber, Card> numberGroup = new MapList<CardNumber, Card>();
        for (Card card : cards) {
            numberGroup.add(card.getNumber(), card);
        }
        return numberGroup;
    }

    private MapList<CardSuit, Card> getSuitGroup(List<Card> cards) {
        MapList<CardSuit, Card> suitGroup = new MapList<CardSuit, Card>();
        for (Card card : cards) {
            suitGroup.add(card.getSuit(), card);
        }
        return suitGroup;
    }
}
