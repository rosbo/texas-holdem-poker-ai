package edu.ntnu.texasai;

import edu.ntnu.texasai.controller.PokerManager;

public class Main {
    public static void main(String[] args) {
        PokerManager pokerManager = new PokerManager(2);
        pokerManager.play();
    }
}