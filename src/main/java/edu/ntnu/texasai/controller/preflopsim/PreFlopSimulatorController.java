package edu.ntnu.texasai.controller.preflopsim;

import edu.ntnu.texasai.controller.EquivalenceClassController;
import edu.ntnu.texasai.controller.StatisticsController;
import edu.ntnu.texasai.model.Game;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.persistence.PersistenceManager;
import edu.ntnu.texasai.utils.GameProperties;
import edu.ntnu.texasai.utils.Logger;

import javax.inject.Inject;
import java.util.Collection;

public class PreFlopSimulatorController {
    // TODO: Must be at least 100
    private static final int ROLLOUTS_PER_EQUIV_CLASS = 10;

    private final Game game = new Game();
    private final Logger logger;
    private final GameProperties gameProperties;
    private final PlayerControllerPreFlopRoll playerControllerPreFlopRoll;
    private final EquivalenceClassController equivalenceClassController;
    private final GameHandControllerPreFlopRoll gameHandControllerPreFlopRoll;
    private final StatisticsController statisticsController;
    private final PersistenceManager persistenceController;

    @Inject
    public PreFlopSimulatorController(final Logger logger, final GameProperties gameProperties,
                                      final PlayerControllerPreFlopRoll playerControllerPreFlopRoll,
                                      final EquivalenceClassController equivalenceClassController,
                                      final GameHandControllerPreFlopRoll gameHandControllerPreFlopRoll,
                                      final StatisticsController statisticsController,
                                      final PersistenceManager persistenceController) {
        this.logger = logger;
        this.gameProperties = gameProperties;
        this.playerControllerPreFlopRoll = playerControllerPreFlopRoll;
        this.equivalenceClassController = equivalenceClassController;
        this.gameHandControllerPreFlopRoll = gameHandControllerPreFlopRoll;
        this.statisticsController = statisticsController;
        this.persistenceController = persistenceController;
    }

    public void play() {
        this.equivalenceClassController.generateAllEquivalenceClass();

        game.addPlayer(new Player(0, gameProperties.getInitialMoney(), playerControllerPreFlopRoll));
        Collection<EquivalenceClass> equivalenceClasses = equivalenceClassController.getEquivalenceClasses();

        // TODO: Generate 2 to 10 players
        for (int numberOfPlayers = 2; numberOfPlayers <= 6; numberOfPlayers++) {
            game.addPlayer(new Player(numberOfPlayers - 1, 0, playerControllerPreFlopRoll));

            for (EquivalenceClass equivalenceClass : equivalenceClasses) {
                statisticsController.initializeStatistics();

                for (int i = 0; i < ROLLOUTS_PER_EQUIV_CLASS; i++) {
                    gameHandControllerPreFlopRoll.play(game, equivalenceClass);
                    game.setNextDealer();
                }

                double percentageOfWinsPlayer0 = (double) statisticsController.getPlayer0Wins() /
                        ROLLOUTS_PER_EQUIV_CLASS;
                persistenceController.persistResult(numberOfPlayers, equivalenceClass,
                        percentageOfWinsPlayer0);

                logger.log("=================");
                logger.log("STATISTICS FOR EQUIVALENCE CLASS "
                        + equivalenceClass.toString());
                logger.log("Number of hands played: "+ ROLLOUTS_PER_EQUIV_CLASS);
                logger.log("Number players: " + numberOfPlayers);
                logger.log("Percentage of wins is " + percentageOfWinsPlayer0);
            }
        }
    }
}
