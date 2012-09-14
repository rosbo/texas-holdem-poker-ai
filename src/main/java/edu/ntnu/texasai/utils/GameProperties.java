package edu.ntnu.texasai.utils;

import edu.ntnu.texasai.controller.*;
import edu.ntnu.texasai.model.Player;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GameProperties {
    private final int smallBlind;
    private final int bigBlind;
    private final int initialMoney;
    private final List<Player> players = new ArrayList<Player>();
    private int numberOfHands;

    @Inject
    public GameProperties(final PlayerControllerPhaseI playerControllerPhaseI,
                          final PlayerControllerPhaseII playerControllerPhaseII,
                          final PlayerControllerPhaseIIIConservative controllerPhaseIIIConservative,
                          final PlayerControllerPhaseIIIAgressive controllerPhaseIIIAgressive) {
        smallBlind = 10;
        bigBlind = 20;
        initialMoney = 1000;
        numberOfHands = 300;

        players.add(new Player(1, initialMoney, playerControllerPhaseI));
        players.add(new Player(2, initialMoney, playerControllerPhaseI));
        players.add(new Player(3, initialMoney, playerControllerPhaseII));
        players.add(new Player(4, initialMoney, playerControllerPhaseII));
        players.add(new Player(5, initialMoney, controllerPhaseIIIConservative));
        players.add(new Player(6, initialMoney, controllerPhaseIIIAgressive));
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public int getNumberOfHands() {
        return numberOfHands;
    }

    public void setNumberOfHands(int numberOfHands) {
        this.numberOfHands = numberOfHands;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getInitialMoney() {
        return this.initialMoney;
    }
}
