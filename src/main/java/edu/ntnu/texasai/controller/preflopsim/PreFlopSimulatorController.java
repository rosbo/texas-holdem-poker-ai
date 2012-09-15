package edu.ntnu.texasai.controller.preflopsim;

import edu.ntnu.texasai.controller.EquivalenceClassController;
import edu.ntnu.texasai.controller.StatisticsController;
import edu.ntnu.texasai.model.Game;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.persistence.PreFlopPersistence;
import edu.ntnu.texasai.model.gameproperties.GameProperties;
import edu.ntnu.texasai.utils.Logger;

import javax.inject.Inject;
import java.util.Collection;

public class PreFlopSimulatorController {
    private static final int ROLLOUTS_PER_EQUIV_CLASS = 100;

    private final Game game = new Game();
    private final Logger logger;
    private final GameProperties gameProperties;
    private final PlayerControllerPreFlopRoll playerControllerPreFlopRoll;
    private final EquivalenceClassController equivalenceClassController;
    private final GameHandControllerPreFlopRoll gameHandControllerPreFlopRoll;
    private final StatisticsController statisticsController;
    private final PreFlopPersistence preFlopPersistence;

    @Inject
    public PreFlopSimulatorController(final Logger logger, final GameProperties gameProperties,
                                      final PlayerControllerPreFlopRoll playerControllerPreFlopRoll,
                                      final EquivalenceClassController equivalenceClassController,
                                      final GameHandControllerPreFlopRoll gameHandControllerPreFlopRoll,
                                      final StatisticsController statisticsController,
                                      final PreFlopPersistence preFlopPersistence) {
        this.logger = logger;
        this.gameProperties = gameProperties;
        this.playerControllerPreFlopRoll = playerControllerPreFlopRoll;
        this.equivalenceClassController = equivalenceClassController;
        this.gameHandControllerPreFlopRoll = gameHandControllerPreFlopRoll;
        this.statisticsController = statisticsController;
        this.preFlopPersistence = preFlopPersistence;
    }

    public void play() {
        this.equivalenceClassController.generateAllEquivalenceClass();

        game.addPlayer(new Player(1, gameProperties.getInitialMoney(), playerControllerPreFlopRoll));
        Collection<EquivalenceClass> equivalenceClasses = equivalenceClassController.getEquivalenceClasses();

        for (int numberOfPlayers = 2; numberOfPlayers <= 10; numberOfPlayers++) {
            game.addPlayer(new Player(numberOfPlayers, 0, playerControllerPreFlopRoll));

            for (EquivalenceClass equivalenceClass : equivalenceClasses) {
                statisticsController.initializeStatistics();

                for (int i = 0; i < ROLLOUTS_PER_EQUIV_CLASS; i++) {
                    gameHandControllerPreFlopRoll.play(game, equivalenceClass);
                    game.setNextDealer();
                }

                double percentageWin = (double) statisticsController.getPlayer1Wins() / ROLLOUTS_PER_EQUIV_CLASS;
                preFlopPersistence.persist(numberOfPlayers, equivalenceClass, percentageWin);

                logger.logImportant("=================");
                logger.logImportant("STATISTICS FOR EQUIVALENCE CLASS "
                        + equivalenceClass.toString());
                logger.logImportant("Number of hands played: " + ROLLOUTS_PER_EQUIV_CLASS);
                logger.logImportant("Number players: " + numberOfPlayers);
                logger.logImportant("Percentage of wins is " + percentageWin);
            }
        }
    }
}
