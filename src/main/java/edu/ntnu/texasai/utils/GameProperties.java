package edu.ntnu.texasai.utils;

import edu.ntnu.texasai.controller.*;
import edu.ntnu.texasai.model.Player;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GameProperties {
    private final Integer smallBlind;
    private final Integer bigBlind;
    private final Integer initialMoney;
    private final Integer numberOfHands;
    private final List<Player> players = new ArrayList<Player>();

    @Inject
    public GameProperties(final PlayerControllerPhaseI playerControllerPhaseI,
                          final PlayerControllerPhaseII playerControllerPhaseII,
                          final PlayerControllerPhaseIIIConservative controllerPhaseIIIConservative,
                          final PlayerControllerPhaseIIIAgressive controllerPhaseIIIAgressive) {
        smallBlind = 10;
        bigBlind = 20;
        initialMoney = 1000;
        numberOfHands = 100;

        players.add(new Player(1, initialMoney, playerControllerPhaseI));
        players.add(new Player(2, initialMoney, playerControllerPhaseI));
        players.add(new Player(3, initialMoney, playerControllerPhaseII));
        players.add(new Player(4, initialMoney, playerControllerPhaseII));
        players.add(new Player(5, initialMoney, controllerPhaseIIIConservative));
        players.add(new Player(6, initialMoney, controllerPhaseIIIAgressive));
    }

    public Integer getSmallBlind() {
        return smallBlind;
    }

    public Integer getBigBlind() {
        return bigBlind;
    }

    public Integer getNumberOfHands() {
        return numberOfHands;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Integer getInitialMoney() {
        return this.initialMoney;
    }
}
