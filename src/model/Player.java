package model;

public class Player {
    private Integer number;
    private Integer money;
    private PlayerHand currentHand;

    public Player(Integer number, Integer money) {
        this.number = number;
        this.money = money;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
