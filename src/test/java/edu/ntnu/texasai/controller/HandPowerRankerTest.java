package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.*;
import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.CardNumber;
import edu.ntnu.texasai.model.cards.CardSuit;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HandPowerRankerTest {
    private final HandPowerRanker handPowerRanker = new HandPowerRanker();

    @Test
    public void royalFlushTest() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardSuit.HEART, CardNumber.TEN));
        cards.add(new Card(CardSuit.HEART, CardNumber.JACK));
        cards.add(new Card(CardSuit.HEART, CardNumber.QUEEN));
        cards.add(new Card(CardSuit.HEART, CardNumber.KING));
        cards.add(new Card(CardSuit.HEART, CardNumber.ACE));

        cards.add(new Card(CardSuit.HEART, CardNumber.NINE));
        cards.add(new Card(CardSuit.CLUB, CardNumber.EIGHT));

        HandPower handPower = handPowerRanker.rank(cards);

        assertEquals(HandPowerType.STRAIGHT_FLUSH, handPower.getHandPowerType());
        assertEquals(Arrays.asList(CardNumber.ACE), handPower.getTieBreakingInformation());
    }

    @Test
    public void straightFlushTest() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardSuit.HEART, CardNumber.THREE));
        cards.add(new Card(CardSuit.HEART, CardNumber.FOUR));
        cards.add(new Card(CardSuit.HEART, CardNumber.FIVE));
        cards.add(new Card(CardSuit.HEART, CardNumber.SIX));
        cards.add(new Card(CardSuit.HEART, CardNumber.SEVEN));

        cards.add(new Card(CardSuit.HEART, CardNumber.TWO));
        cards.add(new Card(CardSuit.CLUB, CardNumber.EIGHT));

        HandPower handPower = handPowerRanker.rank(cards);

        assertEquals(HandPowerType.STRAIGHT_FLUSH, handPower.getHandPowerType());
        assertEquals(Arrays.asList(CardNumber.SEVEN), handPower.getTieBreakingInformation());
    }

    @Test
    public void fourOfAKindTest() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardSuit.HEART, CardNumber.KING));
        cards.add(new Card(CardSuit.SPADE, CardNumber.KING));
        cards.add(new Card(CardSuit.CLUB, CardNumber.KING));
        cards.add(new Card(CardSuit.DIAMOND, CardNumber.KING));

        cards.add(new Card(CardSuit.HEART, CardNumber.SEVEN));
        cards.add(new Card(CardSuit.HEART, CardNumber.TWO));
        cards.add(new Card(CardSuit.CLUB, CardNumber.ACE));

        HandPower handPower = handPowerRanker.rank(cards);

        assertEquals(HandPowerType.FOUR_OF_A_KIND, handPower.getHandPowerType());
        assertEquals(Arrays.asList(CardNumber.KING, CardNumber.ACE), handPower.getTieBreakingInformation());
    }

    @Test
    public void fullHouseTest() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardSuit.HEART, CardNumber.KING));
        cards.add(new Card(CardSuit.CLUB, CardNumber.KING));
        cards.add(new Card(CardSuit.DIAMOND, CardNumber.KING));
        cards.add(new Card(CardSuit.HEART, CardNumber.QUEEN));
        cards.add(new Card(CardSuit.CLUB, CardNumber.QUEEN));

        cards.add(new Card(CardSuit.DIAMOND, CardNumber.QUEEN));
        cards.add(new Card(CardSuit.CLUB, CardNumber.ACE));

        HandPower handPower = handPowerRanker.rank(cards);

        assertEquals(HandPowerType.FULL_HOUSE, handPower.getHandPowerType());
        assertEquals(Arrays.asList(CardNumber.KING, CardNumber.QUEEN), handPower.getTieBreakingInformation());
    }

    @Test
    public void flushTest() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardSuit.HEART, CardNumber.THREE));
        cards.add(new Card(CardSuit.HEART, CardNumber.KING));
        cards.add(new Card(CardSuit.HEART, CardNumber.QUEEN));
        cards.add(new Card(CardSuit.HEART, CardNumber.ACE));
        cards.add(new Card(CardSuit.HEART, CardNumber.FIVE));

        cards.add(new Card(CardSuit.HEART, CardNumber.TWO));
        cards.add(new Card(CardSuit.CLUB, CardNumber.ACE));

        HandPower handPower = handPowerRanker.rank(cards);

        assertEquals(HandPowerType.FLUSH, handPower.getHandPowerType());
        assertEquals(Arrays.asList(CardNumber.ACE, CardNumber.KING, CardNumber.QUEEN,
                CardNumber.FIVE, CardNumber.THREE), handPower.getTieBreakingInformation());
    }

    @Test
    public void straightTest() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardSuit.HEART, CardNumber.THREE));
        cards.add(new Card(CardSuit.SPADE, CardNumber.FOUR));
        cards.add(new Card(CardSuit.CLUB, CardNumber.FIVE));
        cards.add(new Card(CardSuit.HEART, CardNumber.SIX));
        cards.add(new Card(CardSuit.HEART, CardNumber.SEVEN));

        cards.add(new Card(CardSuit.HEART, CardNumber.TWO));
        cards.add(new Card(CardSuit.CLUB, CardNumber.NINE));

        HandPower handPower = handPowerRanker.rank(cards);

        assertEquals(HandPowerType.STRAIGHT, handPower.getHandPowerType());
        assertEquals(Arrays.asList(CardNumber.SEVEN), handPower.getTieBreakingInformation());
    }

    @Test
    public void threeOfAKindTest() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardSuit.HEART, CardNumber.ACE));
        cards.add(new Card(CardSuit.SPADE, CardNumber.ACE));
        cards.add(new Card(CardSuit.CLUB, CardNumber.ACE));

        cards.add(new Card(CardSuit.HEART, CardNumber.SIX));
        cards.add(new Card(CardSuit.HEART, CardNumber.SEVEN));

        HandPower handPower = handPowerRanker.rank(cards);

        assertEquals(HandPowerType.THREE_OF_A_KIND, handPower.getHandPowerType());
        assertEquals(Arrays.asList(CardNumber.ACE, CardNumber.SEVEN, CardNumber.SIX),
                handPower.getTieBreakingInformation());
    }

    @Test
    public void twoPairsTest() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardSuit.HEART, CardNumber.ACE));
        cards.add(new Card(CardSuit.SPADE, CardNumber.ACE));
        cards.add(new Card(CardSuit.HEART, CardNumber.TWO));
        cards.add(new Card(CardSuit.SPADE, CardNumber.TWO));
        cards.add(new Card(CardSuit.CLUB, CardNumber.SIX));
        cards.add(new Card(CardSuit.HEART, CardNumber.SIX));

        HandPower handPower = handPowerRanker.rank(cards);

        assertEquals(HandPowerType.TWO_PAIR, handPower.getHandPowerType());
        assertEquals(Arrays.asList(CardNumber.ACE, CardNumber.SIX, CardNumber.TWO),
                handPower.getTieBreakingInformation());
    }

    @Test
    public void onePairTest() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardSuit.HEART, CardNumber.ACE));
        cards.add(new Card(CardSuit.SPADE, CardNumber.ACE));

        cards.add(new Card(CardSuit.CLUB, CardNumber.SIX));
        cards.add(new Card(CardSuit.HEART, CardNumber.KING));
        cards.add(new Card(CardSuit.HEART, CardNumber.SEVEN));

        HandPower handPower = handPowerRanker.rank(cards);

        assertEquals(HandPowerType.ONE_PAIR, handPower.getHandPowerType());
        assertEquals(Arrays.asList(CardNumber.ACE, CardNumber.KING, CardNumber.SEVEN, CardNumber.SIX),
                handPower.getTieBreakingInformation());
    }

    @Test
    public void highCardTest() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardSuit.HEART, CardNumber.ACE));
        cards.add(new Card(CardSuit.SPADE, CardNumber.QUEEN));
        cards.add(new Card(CardSuit.CLUB, CardNumber.SIX));
        cards.add(new Card(CardSuit.HEART, CardNumber.KING));
        cards.add(new Card(CardSuit.HEART, CardNumber.SEVEN));

        HandPower handPower = handPowerRanker.rank(cards);

        assertEquals(HandPowerType.HIGH_CARD, handPower.getHandPowerType());
        assertEquals(Arrays.asList(CardNumber.ACE, CardNumber.KING, CardNumber.QUEEN, CardNumber.SEVEN, CardNumber.SIX),
                handPower.getTieBreakingInformation());
    }
}
