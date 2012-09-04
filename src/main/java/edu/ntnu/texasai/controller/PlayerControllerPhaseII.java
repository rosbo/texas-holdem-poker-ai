package edu.ntnu.texasai.controller;

import edu.ntnu.texasai.model.*;

import javax.inject.Inject;
import java.util.List;

public class PlayerControllerPhaseII extends PlayerController {
    private final PlayerControllerPhaseI playerControllerPhaseI;

    @Inject
    public PlayerControllerPhaseII(final PlayerControllerPhaseI playerControllerPhaseI) {
        this.playerControllerPhaseI = playerControllerPhaseI;
    }

    @Override
    public BettingDecision decidePreFlop(Player player, GameHand gameHand, List<Card> cards) {
        // TODO: Implement phase II : Pre flop rollout
       return playerControllerPhaseI.decidePreFlop(player, gameHand, cards);
    }

    @Override
    public BettingDecision decideAfterFlop(Player player, GameHand gameHand, List<Card> cards) {
        // TODO: Impleplement pase II : Hand stength
        return playerControllerPhaseI.decideAfterFlop(player, gameHand, cards);
    }
}
