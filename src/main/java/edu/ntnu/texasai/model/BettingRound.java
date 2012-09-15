package edu.ntnu.texasai.model;

import edu.ntnu.texasai.model.opponentmodeling.ContextAction;
import edu.ntnu.texasai.model.opponentmodeling.ContextInformation;
import edu.ntnu.texasai.utils.GameProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BettingRound {
    private final Map<Player, Integer> playerBets = new HashMap<Player, Integer>();
    private final List<ContextInformation> contextInformations = new ArrayList<ContextInformation>();
    private int highestBet = 0;

    public void applyDecision(ContextInformation contextInformation, GameProperties gameProperties) {
        ContextAction contextAction = contextInformation.getContextAction();
        BettingDecision bettingDecision = contextAction.getBettingDecision();
        Player player = contextAction.getPlayer();

        switch (bettingDecision) {
            case CALL:
                placeBet(player, highestBet);
                break;
            case RAISE:
                placeBet(player, highestBet + gameProperties.getBigBlind());
        }

        // Don't save context information for pre flop
        // Hand strength is always 0 b/c there's no shared cards
        if (!contextAction.getBettingRoundName().equals(BettingRoundName.PRE_FLOP)) {
            contextInformations.add(contextInformation);
        }
    }

    public void placeBet(Player player, int bet) {
        Integer playerBet = playerBets.get(player);

        if (playerBet == null) {
            player.removeMoney(bet);
        } else {
            player.removeMoney(bet - playerBet);
        }

        if (bet > highestBet) {
            highestBet = bet;

        } else if (bet < highestBet) {
            throw new IllegalArgumentException(
                    "You can't bet less than the higher bet");
        }

        playerBets.put(player, bet);
    }

    public int getHighestBet() {
        return highestBet;
    }

    public List<ContextInformation> getContextInformations() {
        return contextInformations;
    }

    public int getBetForPlayer(Player player) {
        Integer bet = playerBets.get(player);
        if (bet == null) {
            return 0;
        }
        return bet;
    }

    public int getTotalBets() {
        int totalBets = 0;
        for (Integer bet : playerBets.values()) {
            totalBets += bet;
        }
        return totalBets;
    }

    public int getNumberOfRaises() {
        int numberOfRaises = 0;
        for (ContextInformation contextInformation : contextInformations) {
            if (contextInformation.getContextAction().getBettingDecision().equals(BettingDecision.RAISE)) {
                numberOfRaises++;
            }
        }
        return numberOfRaises;
    }

    public ContextAction getContextActionForPlayer(Player player) {
        for (int i = contextInformations.size(); i > 0; i--) {
            ContextInformation contextInformation = contextInformations.get(i - 1);
            ContextAction contextAction = contextInformation.getContextAction();

            if (contextAction.getPlayer().equals(player)) {
                return contextAction;
            }
        }

        return null;
    }
}
