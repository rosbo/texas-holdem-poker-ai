package edu.ntnu.texasai.model;

import edu.ntnu.texasai.controller.PlayerController;
import edu.ntnu.texasai.model.cards.Card;

import java.util.Arrays;
import java.util.List;

public class Player {
    private final int number;
    private final PlayerController playerController;
    private int money;
    private List<Card> holeCards;

    public Player(int number, int initialMoney,
            PlayerController playerController) {
        this.number = number;
        this.money = initialMoney;
        this.playerController = playerController;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player)) {
            return false;
        }

        Player otherPlayer = (Player) o;

        return number == otherPlayer.number;
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Player #");
        stringBuilder.append(getNumber());

        if (holeCards != null) {
            stringBuilder.append(holeCards.toString());
        }

        return stringBuilder.toString();
    }

    public BettingDecision decide(GameHand gameHand) {
        return playerController.decide(this, gameHand);
    }

    public int getNumber() {
        return number;
    }

    public int getMoney() {
        return money;
    }

    public void removeMoney(int amount) {
        money -= amount;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public void setHoleCards(Card hole1, Card hole2) {
        holeCards = Arrays.asList(hole1, hole2);
    }

    public List<Card> getHoleCards() {
        return holeCards;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }
}
