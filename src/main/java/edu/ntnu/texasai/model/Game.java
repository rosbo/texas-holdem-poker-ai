package edu.ntnu.texasai.model;

import edu.ntnu.texasai.utils.GameProperties;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final GameProperties gameProperties;
    private final List<Player> players;
    private final List<GameHand> gameHands = new ArrayList<GameHand>();

    public Game(List<Player> players, GameProperties gameProperties) {
        this.players = players;
        this.gameProperties = gameProperties;
    }

    public void play() {
        for (int i = 0; i <= gameProperties.getNumberOfHands(); i++) {
            GameHand gameHand = new GameHand(players, gameProperties);
            gameHand.play();
            gameHands.add(gameHand);
        }

        setNextDealer();
    }

    private void setNextDealer() {
        Player formerDealer = players.remove(0);
        players.add(formerDealer);
    }
}
