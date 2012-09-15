package edu.ntnu.texasai.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<Card>();

    public Deck() {
        for (CardSuit suit : CardSuit.values()) {
            for (CardNumber number : CardNumber.values()) {
                Card card = new Card(suit, number);
                cards.add(card);
            }
        }

        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card removeTopCard() {
        return cards.remove(0);
    }

    public boolean removeCard(Card card) {
        return cards.remove(card);
    }
    
    public List<List<Card>> fromDeckToCouplesOfCard(){
        List<List<Card>> couplesOfCard = new ArrayList<List<Card>>();
        int i,j;
        for(i = 0; i < this.cards.size(); i++){           
            for (j = i+1; j < this.cards.size(); j++){    
                List<Card> tmpCards = new ArrayList<Card>();
                tmpCards.add(this.cards.get(i));
                tmpCards.add(this.cards.get(j));
                couplesOfCard.add(tmpCards);
            }                        
        }
        return couplesOfCard;
    }
}
