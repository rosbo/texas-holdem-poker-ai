package edu.ntnu.texasai.model;

import edu.ntnu.texasai.controller.PlayerController;
import edu.ntnu.texasai.model.cards.Card;

import java.util.Arrays;
import java.util.List;

public class Player {
    private final Integer number;
    private final PlayerController playerController;
    private Integer money;
    private List<Card> holeCards;

    public Player(Integer number, Integer initialMoney,
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

        return number.equals(otherPlayer.number);
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

    public Integer getNumber() {
        return number;
    }

    public Integer getMoney() {
        return money;
    }

    public void removeMoney(Integer amount) {
        money -= amount;
    }

    public void addMoney(Integer amount) {
        money += amount;
    }

    public void setHoleCards(Card hole1, Card hole2) {
        holeCards = Arrays.asList(hole1, hole2);
    }

    public List<Card> getHoleCards() {
        return holeCards;
    }
}
