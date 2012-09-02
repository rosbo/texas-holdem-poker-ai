package edu.ntnu.texasai.model;

import java.util.Arrays;
import java.util.List;

public class Player {
    private final Integer number;
    private Integer money;
    private List<Card> holeCards;

    public Player(Integer number, Integer money) {
        this.number = number;
        this.money = money;
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
            stringBuilder.append(" (");
            stringBuilder.append(holeCards.get(0).toString());
            stringBuilder.append(", ");
            stringBuilder.append(holeCards.get(1).toString());
            stringBuilder.append(")");
        }

        return stringBuilder.toString();
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
