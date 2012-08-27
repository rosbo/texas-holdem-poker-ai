package edu.ntnu.texasai;

import edu.ntnu.texasai.controller.PokerController;

public class Main {
    public static void main(String[] args) {
        PokerController pokerController = new PokerController(2);
        pokerController.play();
    }
}
