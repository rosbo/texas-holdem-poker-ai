package main.java;

import main.java.controller.PokerManager;

public class Main {
    public static void main(String[] args) {
        PokerManager pokerManager = new PokerManager(2);

        for(int i = 0; i < 1000; i++){
            pokerManager.playHand();
        }
    }
}
