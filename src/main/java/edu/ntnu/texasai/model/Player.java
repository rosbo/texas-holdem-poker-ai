package edu.ntnu.texasai.model;

public class Player {
    private final Integer number;
    private Integer money;
    private Card hole1;
    private Card hole2;

    public Player(Integer number, Integer money) {
        this.number = number;
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Player)){
            return false;
        }

        Player otherPlayer = (Player) o;

        return number.equals(otherPlayer.number);
    }

    @Override
    public int hashCode() {
        return number;
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

    public void setHoleCards(Card hole1, Card hole2) {
        this.hole1 = hole1;
        this.hole2 = hole2;
    }
}
