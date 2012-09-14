package edu.ntnu.texasai.model.opponentmodeling;

import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.BettingRoundName;
import edu.ntnu.texasai.model.Player;

public class ContextAction {
    private final Player player;
    private final BettingDecision bettingDecision;
    private final BettingRoundName bettingRoundName;
    private final ContextRaises contextRaises;
    private final ContextPlayers contextPlayers;
    private final ContextPotOdds contextPotOdds;

    public ContextAction(Player player, BettingDecision bettingDecision, BettingRoundName bettingRoundName,
                         int numberOfRaises, int numberOfPlayersRemaining, double potOdds) {
        this.player = player;
        this.bettingDecision = bettingDecision;
        this.bettingRoundName = bettingRoundName;
        contextRaises = ContextRaises.valueFor(numberOfRaises);
        contextPlayers = ContextPlayers.valueFor(numberOfPlayersRemaining);
        contextPotOdds = ContextPotOdds.valueFor(potOdds);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ContextAction)) {
            return false;
        }

        ContextAction other = (ContextAction) o;

        return (player.equals(other.player) && bettingDecision.equals(other.bettingDecision) && bettingRoundName
                .equals(other.bettingRoundName) && contextRaises.equals(other.contextRaises) && contextPlayers.equals
                (other.contextPlayers) && contextPotOdds.equals(other.contextPotOdds));
    }

    public Player getPlayer() {
        return player;
    }

    public BettingDecision getBettingDecision() {
        return bettingDecision;
    }

    public BettingRoundName getBettingRoundName() {
        return bettingRoundName;
    }

    public ContextRaises getContextRaises() {
        return contextRaises;
    }

    public ContextPlayers getContextPlayers() {
        return contextPlayers;
    }

    public ContextPotOdds getContextPotOdds() {
        return contextPotOdds;
    }
}
