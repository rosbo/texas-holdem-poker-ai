package model;

public class Game {
    private final Integer TOTAL_HANDS = 1000;

    public void startGame() {
        Integer playedHands = 0;
        while (playedHands < TOTAL_HANDS) {
            playNewHand();
        }
    }

    public void playNewHand() {
        //todo
    }
}
