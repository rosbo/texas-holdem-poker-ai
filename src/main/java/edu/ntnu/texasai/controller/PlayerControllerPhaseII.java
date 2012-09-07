package edu.ntnu.texasai.controller;

import java.util.List;

import javax.inject.Inject;

import edu.ntnu.texasai.model.BettingDecision;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.persistence.PersistenceController;
import edu.ntnu.texasai.utils.GameProperties;

public class PlayerControllerPhaseII extends PlayerController {
	private final PlayerControllerPhaseI playerControllerPhaseI;
	private final PersistenceController persistanceController;
	private final EquivalenceClassController equivalenceClassController;
//	private final GameProperties gameProperties;
	@Inject
	public PlayerControllerPhaseII(
			final PlayerControllerPhaseI playerControllerPhaseI,
			final EquivalenceClassController equivalenceClassController,
			final PersistenceController persistanceController) {
		this.playerControllerPhaseI = playerControllerPhaseI;
		this.persistanceController = persistanceController;
		this.equivalenceClassController = equivalenceClassController;
//		this.gameProperties = gameProperties;
	}

	@Override
	public BettingDecision decidePreFlop(Player player, GameHand gameHand,
			List<Card> cards) {
//		// TODO: Implement phase II : Pre flop rollout
		// I should fix an exception of guice about gameProperties and circular dependencies
//		Card card1 = cards.get(0);
//		Card card2 = cards.get(1);
//		EquivalenceClass equivalenceClass = this.equivalenceClassController.cards2Equivalence(card1, card2);
//		Double percentageOfWins = this.persistanceController.retrievePercentageOfWinsByPlayerAndEquivalenceClass(gameProperties.getPlayers().size(), equivalenceClass);
//		
//		if(percentageOfWins.doubleValue() > 0.66)
//			return BettingDecision.RAISE;
//		else if (percentageOfWins.doubleValue() < 0.33)
//			return BettingDecision.FOLD;
//		else
//			return BettingDecision.CALL;
			return playerControllerPhaseI.decidePreFlop(player, gameHand, cards);

	}

	@Override
	public BettingDecision decideAfterFlop(Player player, GameHand gameHand,
			List<Card> cards) {
		// TODO: Impleplement phase II : Hand stength
		return playerControllerPhaseI.decideAfterFlop(player, gameHand, cards);
	}
}
