package edu.ntnu.texasai.preflopsim;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import edu.ntnu.texasai.controller.EquivalenceClassController;
import edu.ntnu.texasai.controller.GameHandController;
import edu.ntnu.texasai.controller.PlayerControllerPreFlopRoll;
import edu.ntnu.texasai.model.Game;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.utils.GameProperties;
import edu.ntnu.texasai.utils.Logger;

public class PreFlopSimController {
	private final Game game;
	private final Logger logger;
	private final GameProperties gameProperties;
	private final GameHandController gameHandController;
	private final PlayerControllerPreFlopRoll playerControllerPreFlopRoll;
	private final EquivalenceClassController equivalenceClassController;
	private final GameHandControllerPreFlopRoll gameHandControllerPreFlopRoll;

	@Inject
	public PreFlopSimController(final GameHandController gameHandController,
			final Logger logger, final GameProperties gameProperties,
			final PlayerControllerPreFlopRoll playerControllerPreFlopRoll, final EquivalenceClassController equivalenceClassController, final GameHandControllerPreFlopRoll gameHandControllerPreFlopRoll) {
		this.gameHandController = gameHandController;
		this.logger = logger;
		this.gameProperties = gameProperties;
		this.playerControllerPreFlopRoll = playerControllerPreFlopRoll;
		this.equivalenceClassController = equivalenceClassController;
		this.gameHandControllerPreFlopRoll = gameHandControllerPreFlopRoll;
		game = new Game(new ArrayList<Player>());
	}

	public void play() {
		
		this.equivalenceClassController.generateAllEquivalenceClass();
		
		game.addPlayer(new Player(1, gameProperties.getInitialMoney(),
				playerControllerPreFlopRoll));
		Collection<EquivalenceClass> equivalenceClasses = equivalenceClassController.getEquivalenceClasses();
		
		for (int j = 1; j <= 10; j++) { // from 2 to 10 players
			game.addPlayer(new Player(j, gameProperties.getInitialMoney(),
					playerControllerPreFlopRoll));
			for (int i = 0; i < gameProperties.getNumberOfHands(); i++) {
				for(EquivalenceClass eqCl : equivalenceClasses){
					gameHandControllerPreFlopRoll.play(game,eqCl);
					game.setNextDealer();					
				}
			}

		}
		printFinalStats();
	}

	private void printFinalStats() {
		logger.log("-----------------------------------------");
		logger.log("Statistics");
		logger.log("-----------------------------------------");
		logger.log("Number of hands played: " + game.gameHandsCount());
		for (Player player : game.getPlayers()) {
			logger.log(player.toString() + ": " + player.getMoney() + "$");
		}
	}
}
