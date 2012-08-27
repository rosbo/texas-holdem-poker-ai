package controller;

import model.Deck;

public class DeckManager {
    public Deck getShuffledDeck() {
        Deck deck = new Deck();
        deck.shuffle();
        return deck;
    }
}
