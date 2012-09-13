package edu.ntnu.texasai.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.texasai.dependencyinjection.TexasModule;
import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.CardNumber;
import edu.ntnu.texasai.model.cards.CardSuit;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HandStrengthEvaluatorTest {

    private HandStrengthEvaluator handStrengthEvaluator;

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new TexasModule());
        this.handStrengthEvaluator = injector.getInstance(HandStrengthEvaluator.class);
    }
    
    @Test
    public void testEvaluate(){
        List<Card> playerCards = new ArrayList<Card>();
        List<Card> sharedCards = new ArrayList<Card>();
        Card card1 = new Card(CardSuit.HEART, CardNumber.ACE); 
        Card card2 = new Card(CardSuit.CLUB, CardNumber.ACE);
        Card card3 = new Card(CardSuit.DIAMOND, CardNumber.QUEEN); 
        Card card4 = new Card(CardSuit.CLUB, CardNumber.TEN);
        Card card5 = new Card(CardSuit.CLUB, CardNumber.KING);
        playerCards.add(card1);
        playerCards.add(card2);
        sharedCards.add(card3);
        sharedCards.add(card4);
        sharedCards.add(card5);
        double d = this.handStrengthEvaluator.evaluate(playerCards, sharedCards, 2);
        System.out.println(d);
        d = this.handStrengthEvaluator.evaluate(playerCards, sharedCards, 3);
        System.out.println(d);
        d = this.handStrengthEvaluator.evaluate(playerCards, sharedCards, 4);
        System.out.println(d);
        d = this.handStrengthEvaluator.evaluate(playerCards, sharedCards, 5);
        System.out.println(d);
        d = this.handStrengthEvaluator.evaluate(playerCards, sharedCards, 6);
        System.out.println(d);
        d = this.handStrengthEvaluator.evaluate(playerCards, sharedCards, 7);
        System.out.println(d);
        d = this.handStrengthEvaluator.evaluate(playerCards, sharedCards, 8);
        System.out.println(d);
        d = this.handStrengthEvaluator.evaluate(playerCards, sharedCards, 9);
        System.out.println(d);
        d = this.handStrengthEvaluator.evaluate(playerCards, sharedCards, 10);
        System.out.println(d);
    }

}
