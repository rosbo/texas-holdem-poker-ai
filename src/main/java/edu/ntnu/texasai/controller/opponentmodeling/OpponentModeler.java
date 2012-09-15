package edu.ntnu.texasai.controller.opponentmodeling;

import edu.ntnu.texasai.model.BettingRound;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.opponentmodeling.ContextAction;
import edu.ntnu.texasai.model.opponentmodeling.ContextAggregate;
import edu.ntnu.texasai.model.opponentmodeling.ContextInformation;
import edu.ntnu.texasai.model.opponentmodeling.ModelResult;
import edu.ntnu.texasai.persistence.OpponentsModelPersistence;

import javax.inject.Inject;
import java.util.*;

public class OpponentModeler {
    private final Map<Player, List<ContextAggregate>> playerModels = new HashMap<Player, List<ContextAggregate>>();
    private final OpponentsModelPersistence opponentsModelPersistence;

    @Inject
    public OpponentModeler(final OpponentsModelPersistence opponentsModelPersistence) {
        this.opponentsModelPersistence = opponentsModelPersistence;
    }

    public void save(GameHand gameHand) {
        Deque<Player> showdownPlayers = gameHand.getPlayers();

        for (BettingRound bettingRound : gameHand.getBettingRounds()) {
            for (ContextInformation contextInformation : bettingRound.getContextInformations()) {
                Player player = contextInformation.getContextAction().getPlayer();

                if (showdownPlayers.contains(player)) {
                    // Only save context opponent modeling for players who reach showdown
                    addToPlayerModel(contextInformation);
                }
            }
        }
    }

    public ModelResult getEstimatedHandStrength(ContextAction contextAction) {
        return opponentsModelPersistence.retrieve(contextAction);
    }

    public Map<Player, List<ContextAggregate>> getPlayerModels() {
        return playerModels;
    }

    private void addToPlayerModel(ContextInformation contextInformation) {
        ContextAggregate contextAggregate = getContextAggregate(contextInformation.getContextAction());
        contextAggregate.addOccurrence(contextInformation.getHandStrength());
    }

    private ContextAggregate getContextAggregate(ContextAction contextAction) {
        Player player = contextAction.getPlayer();

        List<ContextAggregate> contextAggregates = playerModels.get(player);

        if (contextAggregates == null) {
            contextAggregates = new ArrayList<ContextAggregate>();
            playerModels.put(player, contextAggregates);
        }


        for (ContextAggregate contextAggregate : contextAggregates) {
            if (contextAggregate.getContextAction().equals(contextAction)) {
                return contextAggregate;
            }
        }

        ContextAggregate contextAggregate = new ContextAggregate(contextAction);
        contextAggregates.add(contextAggregate);

        return contextAggregate;
    }
}
