package edu.ntnu.texasai.model.gameproperties;

import edu.ntnu.texasai.controller.phase1.PlayerControllerPhaseIBluff;
import edu.ntnu.texasai.controller.phase1.PlayerControllerPhaseINormal;
import edu.ntnu.texasai.model.Player;

import javax.inject.Inject;

public class PhaseIGameProperties extends GameProperties {
    @Inject
    public PhaseIGameProperties(final PlayerControllerPhaseINormal playerControllerPhaseINormal, final PlayerControllerPhaseIBluff playerControllerPhaseIBluff) {
        super(1000, 1000, 20, 10);

        addPlayer(new Player(1, getInitialMoney(), playerControllerPhaseIBluff));
        addPlayer(new Player(2, getInitialMoney(), playerControllerPhaseIBluff));
        addPlayer(new Player(3, getInitialMoney(), playerControllerPhaseINormal));
        addPlayer(new Player(4, getInitialMoney(), playerControllerPhaseINormal));
    }
}
